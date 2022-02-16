package com.example.storagee;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class TelaUsu extends AppCompatActivity {

    private TextView nomeUsuario,emailUsuario;
    private Button bt_desloga;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioID;
    private TextView bt_ircadasitens;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_usu);

        getSupportActionBar().hide();
        IniciarComponetes();

        bt_ircadasitens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TelaUsu.this,cadastrarItens.class);
                startActivity(intent);


            }
        });

        bt_desloga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(TelaUsu.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
             if (documentSnapshot !=null){
                 nomeUsuario.setText(documentSnapshot.getString("nome"));
                 emailUsuario.setText(email);
             }

            }
        });

    }



    private void IniciarComponetes(){
        nomeUsuario = findViewById(R.id.nomenome);
        emailUsuario = findViewById(R.id.emailemail);
        bt_desloga = findViewById(R.id.bt_deslogar);
        bt_ircadasitens = findViewById(R.id.bt_ircadasitens1);
    }
}