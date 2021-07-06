package br.edu.ifsp.scl.ads.pdm.contatoskt.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import br.edu.ifsp.scl.ads.pdm.contatoskt.AutenticacaoFirebase;
import br.edu.ifsp.scl.ads.pdm.contatoskt.R;
import br.edu.ifsp.scl.ads.pdm.contatoskt.adapter.TarefasAdapter;
import br.edu.ifsp.scl.ads.pdm.contatoskt.controller.TarefaController;
import br.edu.ifsp.scl.ads.pdm.contatoskt.model.Tarefa;
import br.edu.ifsp.scl.ads.pdm.contatoskt.databinding.ActivityMainBinding;
import br.edu.ifsp.scl.ads.pdm.contatoskt.model.TarefaFirebase;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityContatosBinding;
    private ArrayList<Tarefa> tarefasList;
    private TarefasAdapter tarefasAdapter;
    private final int NOVO_CONTATO_REQUEST_CODE = 0;
    private final int CALL_PHONE_PERMISSION_REQUEST_CODE = 1;
    private final int EDITAR_CONTATO_REQUEST_CODE = 2;

    private TarefaController tarefaController;

    private Tarefa tarefa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityContatosBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityContatosBinding.getRoot());

        tarefaController = new TarefaController(this);//new diferente

        // Instanciar o data source
        tarefasList = new ArrayList<>();
        tarefasList = tarefaController.buscaTarefas();
        //popularContatosList();

        /*Thread newThread = new Thread(() -> {
            try {
                Thread.sleep(5000);
                tarefasList = tarefaController.buscaTarefas();
                tarefasAdapter.notifyDataSetChanged();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        newThread.start();*/

        // Instanciar o adapter
        tarefasAdapter = new TarefasAdapter(
                this,
                android.R.layout.simple_list_item_1,
                tarefasList
        );

        // Associando o adapter com o listView
        activityContatosBinding.contatosLv.setAdapter(tarefasAdapter);

        // Registrando listView para o menu de contexto
        registerForContextMenu(activityContatosBinding.contatosLv);

        // Associar um listener de clique para o listView
        activityContatosBinding.contatosLv.setOnItemClickListener(((parent, view, position, id) -> {
            tarefa = tarefasList.get(position);
            Intent detalhesIntent = new Intent(this, TarefaActivity.class);
            detalhesIntent.putExtra(Intent.EXTRA_USER, tarefa);
            startActivity(detalhesIntent);
        }));
    }

    //Teste
    private void popularContatosList(){
        tarefasList.add(
                new Tarefa(
                        "Concluir projeto",
                        "Finalizar páginas de venda",
                        "Xandao",
                        "12/12/2021",
                        "31/12/2021",
                        false,
                        ""
                )
        );
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contatos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.novoContatoMi) {
            Intent novoContatoIntent = new Intent(this, TarefaActivity.class);
            startActivityForResult(novoContatoIntent, NOVO_CONTATO_REQUEST_CODE);
            return true;
        }else if(item.getItemId() == R.id.sairMi) {
            AutenticacaoFirebase.INSTANCE.getFirebaseAuth().signOut();
            AutenticacaoFirebase.INSTANCE.getGoogleSignInClient().signOut();
            finish();
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NOVO_CONTATO_REQUEST_CODE && resultCode == RESULT_OK) {
            Tarefa tarefa = (Tarefa) data.getSerializableExtra(Intent.EXTRA_USER); // modo sem criar constante
            if (tarefa != null) {
                tarefasList.add(tarefa);
                tarefasAdapter.notifyDataSetChanged(); // notifica o adapter a alteracao no conjunto de dados
                tarefaController.insereTarefa(tarefa);
            }
        } else if(requestCode == EDITAR_CONTATO_REQUEST_CODE && resultCode == RESULT_OK){
            //Atualiza o contato
            Tarefa tarefa = (Tarefa) data.getSerializableExtra(Intent.EXTRA_USER);
            int posicao = data.getIntExtra(Intent.EXTRA_INDEX, -1);
            if (tarefa != null && posicao != -1){
                tarefasList.remove(posicao);
                tarefasList.add(posicao, tarefa);
                tarefasAdapter.notifyDataSetChanged();
                tarefaController.atualizaTarefa(tarefa);
            }

        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu_contato, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        //Pegando o contato a partir da posição indicada
        tarefa = tarefasAdapter.getItem(menuInfo.position);

        switch (item.getItemId()){
            case R.id.concluirTarefaMi:
                tarefa.setCompleta(true);
                tarefa.setResponsavel(AutenticacaoFirebase.INSTANCE.getFirebaseAuth().getCurrentUser().getDisplayName());
                activityContatosBinding.contatosLv.getChildAt(menuInfo.position).setBackgroundColor(Color.parseColor("#18A608"));
                tarefasAdapter.notifyDataSetChanged();
                tarefaController.concluiTarefa(tarefa, menuInfo.position);
                return true;
            case R.id.editarContatoMi:
                if(tarefa.isCompleta()){
                    Toast.makeText(this, "Esta tarefa ja foi concluida!", Toast.LENGTH_SHORT).show();
                }else{
                    Intent editarContatoIntent = new Intent(this, TarefaActivity.class);
                    editarContatoIntent.putExtra(Intent.EXTRA_USER, tarefa);
                    editarContatoIntent.putExtra(Intent.EXTRA_INDEX, menuInfo.position);
                    startActivityForResult(editarContatoIntent, EDITAR_CONTATO_REQUEST_CODE);
                }
                return true;
            case R.id.removerContatoMi:
                tarefasList.remove(menuInfo.position);
                tarefaController.removeTarefa(tarefa.getTitulo());
                tarefasAdapter.notifyDataSetChanged();
                return true;
            default:
                return false;
        }
    }

    private void verifyCallPhonePermission() {
        Intent ligarIntent = new Intent(Intent.ACTION_CALL);
        //ligarIntent.setData(Uri.parse("tel: " + contato.getTelefone()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                startActivity(ligarIntent);
            }   else{
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_PERMISSION_REQUEST_CODE);
            }
        }   else{
            startActivity(ligarIntent);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CALL_PHONE_PERMISSION_REQUEST_CODE){
            if(permissions[0].equals(Manifest.permission.CALL_PHONE) && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "É necessário esta permissão para essa funcionalidade", Toast.LENGTH_SHORT).show();
            }
            verifyCallPhonePermission();
        }
    }


    public void onStart() {
        super.onStart();
        if(AutenticacaoFirebase.INSTANCE.getFirebaseAuth().getCurrentUser() == null){
            finish();
        }
    }
}