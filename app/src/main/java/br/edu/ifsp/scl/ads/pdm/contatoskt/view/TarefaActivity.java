package br.edu.ifsp.scl.ads.pdm.contatoskt.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.edu.ifsp.scl.ads.pdm.contatoskt.AutenticacaoFirebase;
import br.edu.ifsp.scl.ads.pdm.contatoskt.R;
import br.edu.ifsp.scl.ads.pdm.contatoskt.model.Tarefa;
import br.edu.ifsp.scl.ads.pdm.contatoskt.databinding.ActivityContatoBinding;

public class TarefaActivity extends AppCompatActivity {

    private ActivityContatoBinding activityContatoBinding;
    private Tarefa tarefa;
    private int posicao = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ligando (binding) objetos com as Views
        activityContatoBinding = ActivityContatoBinding.inflate(getLayoutInflater());
        setContentView(activityContatoBinding.getRoot());

        //Adiciona um título novo
        getSupportActionBar().setSubtitle("Adição de Tarefa");

        // Verifica se algum Contato foi recebido
        tarefa = (Tarefa) getIntent().getSerializableExtra(Intent.EXTRA_USER);

        if(tarefa != null){
            //Recebendo posição
            posicao = getIntent().getIntExtra(Intent.EXTRA_INDEX, -1);

            activityContatoBinding.conclusaoTv.setVisibility(View.VISIBLE);
            activityContatoBinding.conclusaoTv.setText("Criada por: "+tarefa.getAutor());

            if(posicao == -1){
                activityContatoBinding.salvarBt.setVisibility(View.GONE);

                if(tarefa.isCompleta()){
                    activityContatoBinding.autorTv.setVisibility(View.VISIBLE);
                    activityContatoBinding.autorTv.setText("Concluida por: "+tarefa.getResponsavel());
                }
                alterarAtivacaoViews(false);
                getSupportActionBar().setSubtitle("Detalhes da Tarefa");
            }   else {
                alterarAtivacaoViews(true);
                activityContatoBinding.nomeEt.setEnabled(false);
                getSupportActionBar().setSubtitle("Edição da Tarefa");
            }

            // Usando os dados para preencher o contato
            activityContatoBinding.nomeEt.setText(tarefa.getTitulo());
            activityContatoBinding.telefoneEt.setText(tarefa.getTermina());
            activityContatoBinding.emailEt.setText(tarefa.getDescricao());

        }
    }

    private void alterarAtivacaoViews(boolean ativo){
        activityContatoBinding.nomeEt.setEnabled(ativo);
        activityContatoBinding.telefoneEt.setEnabled(ativo);
        activityContatoBinding.emailEt.setEnabled(ativo);
    }

    public void onClickButton(View view) {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
        String strDate = dateFormat.format(date);
        tarefa = new Tarefa(
                activityContatoBinding.nomeEt.getText().toString(),
                activityContatoBinding.emailEt.getText().toString(),
                AutenticacaoFirebase.INSTANCE.getFirebaseAuth().getCurrentUser().getDisplayName(),
                strDate,
                activityContatoBinding.telefoneEt.getText().toString(),
                false,
                ""
        );
        if (view.getId() == R.id.salvarBt) {
            Intent retornoIntent = new Intent();
            retornoIntent.putExtra(Intent.EXTRA_USER, tarefa);
            retornoIntent.putExtra(Intent.EXTRA_INDEX, posicao);
            setResult(RESULT_OK, retornoIntent);
            finish();
        }
    }

    public void onStart() {
        super.onStart();
        if(AutenticacaoFirebase.INSTANCE.getFirebaseAuth().getCurrentUser() == null){
            finish();
        }
    }

}