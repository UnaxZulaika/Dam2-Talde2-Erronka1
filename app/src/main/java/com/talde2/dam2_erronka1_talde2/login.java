package com.talde2.dam2_erronka1_talde2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.talde2.dam2_erronka1_talde2.Objetuak.Erabiltzaile;
import com.talde2.dam2_erronka1_talde2.Objetuak.JardueraPertsona;
import com.talde2.dam2_erronka1_talde2.Objetuak.Tokia;

import java.time.chrono.Era;
import java.util.ArrayList;
import java.util.List;


public class login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText erabiltzaileEditText;
    private EditText pasahitzaEditText;
    private Button loginBtnSartu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();
        erabiltzaileEditText = findViewById(R.id.loginEditErbiltzaile);
        erabiltzaileEditText.setText("admin@gmail.com");

        pasahitzaEditText = findViewById(R.id.loginEditPasahitza);
        pasahitzaEditText.setText("admin123");
        loginBtnSartu = findViewById(R.id.loginBtnSartu);

        loginBtnSartu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String erabiltzaile = erabiltzaileEditText.getText().toString().trim();
                String pasahitza = pasahitzaEditText.getText().toString().trim();

                if (TextUtils.isEmpty(erabiltzaile)) {
                    String sartuEposta = getResources().getString(R.string.sartuEposta);
                    erabiltzaileEditText.setError(sartuEposta);
                } else if (TextUtils.isEmpty(pasahitza)) {
                    String sartuPasahitza = getResources().getString(R.string.sartuPasahitza);
                    pasahitzaEditText.setError(sartuPasahitza);
                } else {
                    saioaHasi(erabiltzaile, pasahitza);
                }
            }
        });

        Button btnErregistratu = findViewById(R.id.loginBtnErregistratu);
        btnErregistratu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);
            }
        });


    }

    private void saioaHasi(String erabiltzaile, String pasahitza) {
        mAuth.signInWithEmailAndPassword(erabiltzaile, pasahitza)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Saioa ondo hasi da
                            FirebaseUser user = mAuth.getCurrentUser();
                            // Erabiltzailearen izena lortzeko metodoa.
                            erabiltzaileIzena(user.getEmail());
                        } else {
                            Exception exception = task.getException();

                            if (exception instanceof FirebaseNetworkException) {
                                String erKonexioa = getResources().getString(R.string.erKonexioaLogin);
                                Toast.makeText(login.this, erKonexioa, Toast.LENGTH_SHORT).show();
                            } else if (exception instanceof FirebaseAuthInvalidUserException) {
                                String erEposta = getResources().getString(R.string.erEpostaLogin);
                                // Correo electrónico no encontrado en Firebase Authentication
                                Toast.makeText(login.this, erEposta, Toast.LENGTH_SHORT).show();
                            } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                                String erPasahitza = getResources().getString(R.string.erPasahitzaLogin);
                                // Credenciales de inicio de sesión inválidas (correo o contraseña incorrectos)
                                Toast.makeText(login.this, erPasahitza, Toast.LENGTH_SHORT).show();
                            } else {
                                String erLogin = getResources().getString(R.string.erLoginLogin);
                                // Otro error no manejado específicamente
                                Toast.makeText(login.this, erLogin, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void erabiltzaileIzena(String uid) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Erabiltzailearen izena lortzeko kontsulta
        db.collection("Erabiltzaileak").document(uid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();

                            if (document.exists()) {
                                // Erabiltzaile izena eskuratzen du
                                Erabiltzaile erabiltzaile = erabiltzaileaBete(document);
                                ArrayList<Tokia> tokiak = tokiakBete(db, "Ibilbideak");
                                // Login zuzenaren mezua erakusten du
                                Toast.makeText(login.this, "Ongi etorri, "+ erabiltzaile.getIzena(), Toast.LENGTH_SHORT).show();


                                // Hurrengo lehiora pasatzen da
                                Intent intent = new Intent(login.this, erreserbak.class);

                                String izena = document.getString("izena");
                                String abizena = document.getString("abizenak");
                                String nan = document.getString("nan");
                                String email = document.getString("email");
                                String mugikorra = document.getString("mugikorra");
                                String erabiltzaileMota = document.getString("erabiltzaileMota");

                                intent.putExtra("USER_izena", izena);
                                intent.putExtra("USER_abizena", abizena);
                                intent.putExtra("USER_nan", nan);
                                intent.putExtra("USER_email", email);
                                intent.putExtra("USER_mugikorra", mugikorra);
                                intent.putExtra("USER_erabiltzaileMota", erabiltzaileMota);


                                startActivity(intent);
                                finish();
                            } else {
                                String erErabiltzailea = getResources().getString(R.string.erErabiltzaileaLogin);
                                // Errorea erabiltzailea ez da aurkitzen
                                Toast.makeText(login.this, erErabiltzailea, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            String errorea = getResources().getString(R.string.erroreaLogin);
                            // Kontsultaren errorea
                            Toast.makeText(login.this, "Errorea: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Erabiltzailearen objetua sortzen du
     * @param document firebase erabiltzailearen kontulta
     * @return erabiltzile objetua beharrezko informazioarekin
     */
    private Erabiltzaile erabiltzaileaBete(DocumentSnapshot document) {
        String izena = document.getString("izena");
        String abizena = document.getString("abizena");
        String nan = document.getString("nan");
        String email = document.getString("email");
        String mugikorra = document.getString("mugikorra");
        String erabiltzaileMota = document.getString("erabiltzaileMota");
        Erabiltzaile erabiltzaile = new Erabiltzaile(izena, nan, abizena, email, mugikorra, erabiltzaileMota);
        return erabiltzaile;
    }
    private ArrayList<Tokia> tokiakBete(FirebaseFirestore db, String tokiMota){
        ArrayList<Tokia> tokiak = new ArrayList<>();

        db.collection("Ibilbideak").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        QuerySnapshot ibilbideak = task.getResult();
                        List<DocumentSnapshot> documentIbilibideak = ibilbideak.getDocuments();
                        for(int i = 0;i<documentIbilibideak.size();i++) {
                            Tokia t1 = new Tokia();
                            String kodea = documentIbilibideak.get(i).getString("code");
                            String ubikazioa = documentIbilibideak.get(i).getString("kokalekua");
                            String deskripzioa = documentIbilibideak.get(i).getString("informazioa");
                            String irudia = documentIbilibideak.get(i).getString("img");
                            if (tokiMota.equals("Ibilbideak")||tokiMota.equals("Aisialdia")) {

                            } else if (tokiMota.equals("Konferentziak")||tokiMota.equals("Kumbreak")||tokiMota.equals("Presentazioak")||tokiMota.equals("Feriak")||tokiMota.equals("Tailerrak")) {

                            } else {
                                Toast.makeText(login.this, "Errorea " + tokiMota +" kargatzen", Toast.LENGTH_SHORT).show();
                            }

                            t1.setUbikazioa(ubikazioa);
                            t1.setDeskripzioa(deskripzioa);
                            t1.setIrudia(irudia);
                            t1.setKodea(kodea);
                            tokiak.add(t1);
                        }



                    }
                 });

        return tokiak;
    }
}