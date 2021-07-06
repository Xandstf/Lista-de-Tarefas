package br.edu.ifsp.scl.ads.pdm.contatoskt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import br.edu.ifsp.scl.ads.pdm.contatoskt.model.Tarefa;
import br.edu.ifsp.scl.ads.pdm.contatoskt.databinding.ViewContatoBinding;

public class TarefasAdapter extends ArrayAdapter<Tarefa> {

    public TarefasAdapter(Context contexto, int layout, ArrayList<Tarefa> tarefasList){
        super(contexto, layout, tarefasList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewContatoBinding viewContatoBinding;
        TarefaViewHolder tarefaViewHolder;

        //Se é necerrásio inflar uma nova célula
        if(convertView == null){
            // Instancia a classe de View Biding que infla uma nova célula
            viewContatoBinding = ViewContatoBinding.inflate(LayoutInflater.from(getContext()));

            //Atribui a nova célula a View que será devolvida preenchida para a ListView
            convertView = viewContatoBinding.getRoot();

            // Pega e guarda referências para as Views Internas
            tarefaViewHolder = new TarefaViewHolder();
            tarefaViewHolder.nomeContatoTv = viewContatoBinding.nomeContatoTv;
            tarefaViewHolder.emailContatoTv = viewContatoBinding.emailContatoTv;
            tarefaViewHolder.telefoneContatoTv = viewContatoBinding.telefoneContatoTv;


            //Associa a View da célula ao Holder que referencia suas Views Internas
            convertView.setTag(tarefaViewHolder);
        }

        // Pega o Holder associado a célula
        tarefaViewHolder = (TarefaViewHolder) convertView.getTag();

        //Atualizar os valores dos TextViews
        Tarefa tarefa = getItem(position);
        tarefaViewHolder.nomeContatoTv.setText(tarefa.getTitulo());
        tarefaViewHolder.emailContatoTv.setText(tarefa.getDescricao());
        tarefaViewHolder.telefoneContatoTv.setText(tarefa.getTermina());

        return convertView;
    }

    private class TarefaViewHolder{
        public TextView nomeContatoTv;
        public TextView emailContatoTv;
        public TextView telefoneContatoTv;
    }
}
