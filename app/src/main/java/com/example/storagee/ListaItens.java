package com.example.storagee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.service.autofill.CustomDescription;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListaItens extends AppCompatActivity {


    List<Model> modelList = new ArrayList<>();
    RecyclerView mRecycleView;
    RecyclerView.LayoutManager layoutManager;

    FloatingActionButton maddbt;

    FirebaseFirestore db;
    Costumizador adapter;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_itens);

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().hide();
        actionBar.setTitle("Lista data");

        db = FirebaseFirestore.getInstance();

        mRecycleView = findViewById(R.id.listareciclaveu);
        maddbt = findViewById(R.id.addbt);

        mRecycleView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(layoutManager);

        pd = new ProgressDialog(this);

        ShowData();

        maddbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListaItens.this,cadastrarItens.class));
                finish();
            }
        });

    }


    private void ShowData() {
        pd.setTitle("Carregando");
        pd.show();

        db.collection("Produtos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                pd.dismiss();
                modelList.clear();

                for (DocumentSnapshot doc:task.getResult()){
                    Model model = new Model(doc.getString("id"),doc.getString("titulo"),
                            doc.getString("descrição"),doc.getString("valor"),doc.getString("quantidade"));
                    modelList.add(model);



                }

                adapter = new Costumizador(ListaItens.this,modelList);
                mRecycleView.setAdapter(adapter);

            }

        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        pd.dismiss();

                        Toast.makeText(ListaItens.this,e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    public void deleteData(int index){
        pd.setTitle("Deletando");
        pd.show();
        db.collection("Produtos").document(modelList.get(index).getId())
                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ListaItens.this,"Deletando",Toast.LENGTH_SHORT).show();
                ShowData();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(ListaItens.this,e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }
}

