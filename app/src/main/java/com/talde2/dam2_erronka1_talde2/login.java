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

import static android.content.ContentValues.TAG;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.talde2.dam2_erronka1_talde2.Objetuak.Erabiltzaile;
import com.talde2.dam2_erronka1_talde2.Objetuak.JardueraEntitateak;
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
    private Button loginBtnAnonimo;

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

        loginBtnAnonimo = findViewById(R.id.loginBtnAnonimo);
        loginBtnAnonimo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Anonimo bezala login
                mAuth.signInAnonymously().addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Anonimo login ondo
                            Intent intent = new Intent(login.this, erreserbak.class);
                            startActivity(intent);
                            Toast.makeText(login.this, "Ongi etorri, Anonimoa", Toast.LENGTH_SHORT).show();
                            finish(); // Login aktibitatea ixteko
                        } else {
                            Toast.makeText(login.this, "Errorea, ezin izan da anonimo bezala hasi saioa", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
                                ArrayList<JardueraPertsona> tokiakPertsona = tokiakBete(db, "Ibilbideak");
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
    private ArrayList<JardueraPertsona> tokiakBete(FirebaseFirestore db, String tokiMota){
        ArrayList<JardueraPertsona> tokiak = new ArrayList<>();


        db.collection(tokiMota)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                JardueraPertsona t1 = document.toObject(JardueraPertsona.class);
                                tokiak.add(t1);
                            }
                        } else {
                            Log.d(TAG, "Error dokumentuak lortzen: ", task.getException());
                        }
                    }
                });
        return tokiak;
    }
}