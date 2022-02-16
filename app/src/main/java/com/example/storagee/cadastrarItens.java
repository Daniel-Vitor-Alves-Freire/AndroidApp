package com.example.storagee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class cadastrarItens extends AppCompatActivity {

    EditText mTitulo,mDescricao,mValor,mQuantidade;
    Button mSalvar,mLista;
    ProgressDialog pd;
    FirebaseFirestore db;
    String pID,pTitulo,pDescrição,pValor,pQuantidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_itens);

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().hide();


        mTitulo = findViewById(R.id.produtoEt);
        mDescricao = findViewById(R.id.descricaoEt);
        mValor = findViewById(R.id.valorEt);
        mQuantidade = findViewById(R.id.quantidadeEt);
        mSalvar = findViewById(R.id.bt_salva);
        mLista = findViewById(R.id.bt_mostra);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            actionBar.setTitle("Atualizando Data");
            mSalvar.setText("Atualizando");
            pID = bundle.getString("pID");
            pTitulo = bundle.getString("pTitulo");
            pDescrição = bundle.getString("pDescrição");
            pValor = bundle.getString("pValor");
            pQuantidade = bundle.getString("pQuantidade");

            mTitulo.setText(pTitulo);
            mDescricao.setText(pDescrição);
            mValor.setText(pValor);
            mQuantidade.setText(pQuantidade);

        }else{
            actionBar.setTitle("Add Data");
            mSalvar.setText("Salvando");

        }

        pd = new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();

        mSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle1 = getIntent().getExtras();
                if (bundle != null){

                    String id = pID;
                    String titulo = mTitulo.getText().toString().trim();
                    String descrição = mDescricao.getText().toString().trim();
                    String valor = mValor.getText().toString().trim();
                    String quantidade = mQuantidade.getText().toString().trim();

                    updateData(id,titulo,descrição,valor,quantidade);

                }else{

                    String titulo = mTitulo.getText().toString().trim();
                    String descrição = mDescricao.getText().toString().trim();
                    String valor = mValor.getText().toString().trim();
                    String quantidade = mQuantidade.getText().toString().trim();

                    uploadData(titulo,descrição,valor,quantidade);

                }


            }
        });

        mLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(cadastrarItens.this,ListaItens.class));
                finish();
            }
        });

    }

    private void updateData(String id, String titulo, String descrição, String valor , String quantidade) {

        pd.setTitle("Salvando......");
        pd.show();
        db.collection("Produtos").document(id)
                .update("titulo",titulo,"descrição",descrição,"valor",valor,"quantidade",quantidade)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(cadastrarItens.this,"Salvando.....",Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                pd.dismiss();
                Toast.makeText(cadastrarItens.this,e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void uploadData(String titulo, String descrição, String valor , String quantidade) {
        pd.setTitle("Adicionando ao Firestore");
        pd.show();
        String id = UUID.randomUUID().toString();
        Map<String,Object> doc = new HashMap<>();
        doc.put("id",id);
        doc.put("titulo",titulo);
        doc.put("descrição",descrição);
        doc.put("valor",valor);
        doc.put("quantidade",quantidade);

        db.collection("Produtos").document(id).set(doc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                pd.dismiss();
                Toast.makeText(cadastrarItens.this,"Subindo",Toast.LENGTH_SHORT).show();

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        pd.dismiss();
                        Toast.makeText(cadastrarItens.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });
    }
}