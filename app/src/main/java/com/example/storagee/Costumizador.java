package com.example.storagee;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Costumizador extends RecyclerView.Adapter<ViewHolder> {

    ListaItens listActivity;
    List<Model> modelList;
    Context context;

    public Costumizador(ListaItens listActivity, List<Model> modelList) {
        this.listActivity = listActivity;
        this.modelList = modelList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_layout, viewGroup,false);

        ViewHolder viewHolder = new ViewHolder(itemView);

        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int posicao) {

                String titulo = modelList.get(posicao).getTitulo();
                String descri = modelList.get(posicao).getDescricao();
                String valor = modelList.get(posicao).getValor();
                String quantidade = modelList.get(posicao).getQuantidade();
                Toast.makeText(listActivity,titulo+"\n"+descri+"\n"+valor+"\n"+quantidade,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onItemLongClick(View view, int posicao) {

                AlertDialog.Builder builder = new AlertDialog.Builder(listActivity);
                String[] opcao = {"Atualizar","Deletar"};
                builder.setItems(opcao, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        if (which == 0){
                            String id = modelList.get(posicao).getId();
                            String titulo = modelList.get(posicao).getTitulo();
                            String descricao = modelList.get(posicao).getDescricao();
                            String valor = modelList.get(posicao).getValor();
                            String quantidade = modelList.get(posicao).getQuantidade();

                            Intent intent = new Intent(listActivity,cadastrarItens.class);

                            intent.putExtra("pID",id);
                            intent.putExtra("pTitulo",titulo);
                            intent.putExtra("pDescrição",descricao);
                            intent.putExtra("pValor",valor);
                            intent.putExtra("pQuantidade",quantidade);

                            listActivity.startActivity(intent);
                        }
                        if (which == 1){
                            listActivity.deleteData(posicao);

                        }

                    }
                }).create().show();

            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.mTitulo.setText(modelList.get(i).getTitulo());
        viewHolder.mDescricao.setText(modelList.get(i).getDescricao());
        viewHolder.mValor.setText(modelList.get(i).getValor());
        viewHolder.mQuantidade.setText(modelList.get(i).getQuantidade());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
