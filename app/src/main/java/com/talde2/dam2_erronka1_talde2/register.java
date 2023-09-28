package com.talde2.dam2_erronka1_talde2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button regBtnErregistratu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        emailEditText = findViewById(R.id.regEditPosta);
        passwordEditText = findViewById(R.id.regEditPasahitza);
        regBtnErregistratu = findViewById(R.id.regBtnErregistraty);

        regBtnErregistratu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                erabiltzaileaErregistratu();
            }
        });
    }

    // Erregistratzeko metodoa. Email eta pasahitzak artzen ditu, emaia erregistratuta dagoen konprobatzen du,
    // erregistratu gabe badago, erregistroa egiten du eta mezu bat erakusten du erregistroa ondo egin dela.
    // erregistratuta badago, mezu bat erakusten da erregistroa errore bat egon dela.
    private void erabiltzaileaErregistratu() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Erregistroa ondo, erabiltzailea sortu da
                            Toast.makeText(register.this, "Zure erabiltzailea sortu da", Toast.LENGTH_SHORT).show();
                        } else {
                            // Erregistro okerra, error mezua
                            Toast.makeText(register.this, "Erregistro okerra: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}