package com.talde2.dam2_erronka1_talde2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText izenaEditText;
    private EditText nanEditText;
    private EditText abizenaEditText;
    private EditText emailEditText;
    private EditText mugikorraEditText;
    private EditText passwordEditText;
    private EditText password2EditText;
    private RadioGroup motaRadioGroup;
    private Button regBtnErregistratu;

    private String izena;
    private String nan;
    private String abizenak;
    private String email;
    private String mugikorra;
    private String password1;
    private String password2;
    private int aukeratutakoMota;
    private String erabiltzaileMota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();




        regBtnErregistratu = findViewById(R.id.regBtnErregistraty);

        regBtnErregistratu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                izenaEditText = findViewById(R.id.regEditIzena);
                nanEditText = findViewById(R.id.regEditNan);
                abizenaEditText = findViewById(R.id.regEditAbizenak);
                emailEditText = findViewById(R.id.regEditPosta);
                mugikorraEditText = findViewById(R.id.regEditMugikorra);
                passwordEditText = findViewById(R.id.regEditPasahitza);
                password2EditText = findViewById(R.id.regEditPasahitza2);
                motaRadioGroup = findViewById(R.id.radioGroup);

                izena = izenaEditText.getText().toString();
                nan = nanEditText.getText().toString().toUpperCase();
                abizenak = abizenaEditText.getText().toString();
                email = emailEditText.getText().toString();
                mugikorra = mugikorraEditText.getText().toString();
                password1 = passwordEditText.getText().toString();
                password2 = password2EditText.getText().toString();
                aukeratutakoMota = motaRadioGroup.getCheckedRadioButtonId();

                // izenean ez detektatzeko hutsuneak edo "salto de lineas" dauden.
                String trimIzena = izena.trim();

                //abizeneri trim eta array batean gordetzen da. Lehen abizena lehen arrayean bigarrena bigarrenean.
                String[] trimAbizenak = abizenak.trim().split("\\s+");

                // izena beteta dagoen ikusten du,
                if (trimIzena.isEmpty()) {
                    Toast.makeText(register.this, "Izena bete behar da.", Toast.LENGTH_SHORT).show();
                }
                // NAN zuzena den konprobatzen du (8 zenbaki eta letra bat. Adibidez: 12345678A)
                else if (!nan.matches("\\d{8}[A-Za-z]")) {
                    Toast.makeText(register.this, "Nan formatua okerra", Toast.LENGTH_SHORT).show();
                }
                // Zenbak array dauden konprobatzen du abizen kopuruak jakiteko.
                else if (trimAbizenak.length < 2) {
                    Toast.makeText(register.this, "Zure lehen bi abizenak sartu, mesedez.", Toast.LENGTH_SHORT).show();
                }
                // Emaila konprobatzen du android-en metodoekin.
                else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(register.this, "Emaila okerra da.", Toast.LENGTH_SHORT).show();
                }
                // Mugikor zenbakia konprobatzen du android-en metodoekin.
                else if (!Patterns.PHONE.matcher(mugikorra).matches()) {
                    Toast.makeText(register.this, "Mugikorra zenbakiak izan behar ditu.", Toast.LENGTH_SHORT).show();
                }
                // Pasahitza konprobatzeko else if desberdinak erabiltzen ditugu bestela aplikazioa 'crash'-eatzen da.
                // pasahitzak ez daudela hutsik konprobatzen du.
                else if (password1.isEmpty() || password2.isEmpty()) {
                    Toast.makeText(register.this, "Pasahitza bete mesedez.", Toast.LENGTH_SHORT).show();
                }
                // Bi pasahitzak berdinak diren konprobatzen du.
                else if (!password1.equals(password2)) {
                    Toast.makeText(register.this, "Pasahitzak ez dira berdinak.", Toast.LENGTH_SHORT).show();
                }
                // motaren bat aukeratu den konprobatzen du
                else if (aukeratutakoMota == -1) {
                    Toast.makeText(register.this, "Aukeratu erabiltzaile mota bat.", Toast.LENGTH_SHORT).show();
                }
                // Dena zuzen badago erregistroa egiteko metodoari deitzen zaio.
                else {
                    // Aukeratutako IDa RadioButton objetu batean sortzen du.
                    RadioButton mota = findViewById(aukeratutakoMota);

                    // erabiltzaileMota String-ean aukeratutako mota gordetzen du.
                    erabiltzaileMota = mota.getText().toString();

                    erabiltzaileaErregistratu();
                }
            }
        });
    }

    // Erregistratzeko metodoa. Email eta pasahitzak artzen ditu, emaia erregistratuta dagoen konprobatzen du,
    // erregistratu gabe badago, erregistroa egiten du eta mezu bat erakusten du erregistroa ondo egin dela.
    // erregistratuta badago, mezu bat erakusten da erregistroa errore bat egon dela.
    private void erabiltzaileaErregistratu() {

        mAuth.createUserWithEmailAndPassword(email, password1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Erregistroa ondo, erabiltzailea sortu da
                            Toast.makeText(register.this, "Zure erabiltzailea sortu da", Toast.LENGTH_SHORT).show();
                            // erabiltzaileaDatuBaseanGorde metodoari deitzen zaio
                            erabiltzaileaDatuBaseanGorde();
                            Intent intent = new Intent(register.this, login.class);
                            startActivity(intent);
                        } else {
                            // Erregistro okerra, error mezua
                            Toast.makeText(register.this, "Erregistro okerra: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void erabiltzaileaDatuBaseanGorde() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        erabiltzaileak erabiltzaile = new erabiltzaileak(izena, nan, abizenak, email, mugikorra, erabiltzaileMota);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        String uid = null;
        if (currentUser != null) {
            uid = currentUser.getUid();
        }

        db.collection("erabiltzaileak").document(uid).set(erabiltzaile);

    }
}