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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.talde2.dam2_erronka1_talde2.Objetuak.Erabiltzaile;

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
                    String erIzena = getResources().getString(R.string.erIzena);
                    izenaEditText.setError(erIzena);
                }
                // NAN zuzena den konprobatzen du (8 zenbaki eta letra bat. Adibidez: 12345678A)
                else if (!nan.matches("\\d{8}[A-Za-z]")) {
                    String erNan = getResources().getString(R.string.erNan);
                    nanEditText.setError(erNan);
                }
                // Zenbak array dauden konprobatzen du abizen kopuruak jakiteko.
                else if (trimAbizenak.length < 2) {
                    String erAbizenak = getResources().getString(R.string.erAbizenak);
                    abizenaEditText.setError(erAbizenak);
                }
                // Emaila konprobatzen du android-en metodoekin.
                else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    String erEmail = getResources().getString(R.string.erEmail);
                    emailEditText.setError(erEmail);
                }
                // Mugikor zenbakia konprobatzen du android-en metodoekin.
                else if (!Patterns.PHONE.matcher(mugikorra).matches()) {
                    String erMugikorra = getResources().getString(R.string.erMugikorra);
                    mugikorraEditText.setError(erMugikorra);
                }
                // Pasahitza konprobatzeko else if desberdinak erabiltzen ditugu bestela aplikazioa 'crash'-eatzen da.
                // pasahitzak ez daudela hutsik konprobatzen du.
                else if (password1.isEmpty()) {
                    String erPasahitza1 = getResources().getString(R.string.erPasahitza1);
                    passwordEditText.setError(erPasahitza1);
                }
                else if (password2.isEmpty()){
                    String erPasahitza1 = getResources().getString(R.string.erPasahitza1);
                    password2EditText.setError(erPasahitza1);
                }
                // Bi pasahitzak berdinak diren konprobatzen du.
                else if (!password1.equals(password2)) {
                    String erPasahitza2 = getResources().getString(R.string.erPasahitza2);
                    Toast.makeText(register.this, erPasahitza2, Toast.LENGTH_SHORT).show();
                }
                // motaren bat aukeratu den konprobatzen du
                else if (aukeratutakoMota == -1) {
                    String erMotak = getResources().getString(R.string.erMotak);
                    Toast.makeText(register.this, erMotak, Toast.LENGTH_SHORT).show();
                }
                // Dena zuzen badago erregistroa egiteko metodoari deitzen zaio.
                else {
                    // Aukeratutako IDa RadioButton objetu batean sortzen du.
                    RadioButton mota = findViewById(aukeratutakoMota);
                    // erabiltzaileMota String-ean aukeratutako mota gordetzen du.
                    erabiltzaileMota = mota.getText().toString();
                    // NAN-a datu basean existizen den konprobatzen du
                    nanKonprobaketa(nan);
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
                            String erabiltzaileaSortu = getResources().getString(R.string.erabiltzaileaSortu);
                            Toast.makeText(register.this, erabiltzaileaSortu, Toast.LENGTH_SHORT).show();
                            // erabiltzaileaDatuBaseanGorde metodoari deitzen zaio
                            erabiltzaileaDatuBaseanGorde();
                            Intent intent = new Intent(register.this, login.class);
                            startActivity(intent);
                        } else {
                            // Erregistro okerra, error mezua
                            String erOkerra = getResources().getString(R.string.erOkerra);
                            Toast.makeText(register.this, erOkerra + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // NAN-a datu basean existizen den konprobatzen du
    private void nanKonprobaketa(final String nan) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Erabiltzaileak").whereEqualTo("nan", nan).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                // NAN-a datu basean existitzen da
                                String erNanBikoiztua = getResources().getString(R.string.erNanBikoiztua);
                                Toast.makeText(register.this, erNanBikoiztua, Toast.LENGTH_SHORT).show();
                            } else {
                                // NAN-a ez da existitzen. Erregistroa egiten da.
                                mAuth.createUserWithEmailAndPassword(email, password1).addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    // Erregistroa ondo (Authentication). Erabiltzailea sortuta.
                                                    String erabiltzaileaSortu = getResources().getString(R.string.erabiltzaileaSortu);
                                                    Toast.makeText(register.this, erabiltzaileaSortu, Toast.LENGTH_SHORT).show();
                                                    // Datu basean erabiltzailearen informazioa gordetzen da.
                                                    erabiltzaileaDatuBaseanGorde();
                                                    Intent intent = new Intent(register.this, login.class);
                                                    startActivity(intent);
                                                } else {
                                                    // Erregistro okerra. Errore konkretuentzako mezu desberdinekin.
                                                    String erroreMezua = getResources().getString(R.string.erOkerra);

                                                    // Zein izan den errorea.
                                                    Exception exception = task.getException();
                                                    if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                                                        // Pasaitzak ez du formatu ona.
                                                        String erPasahitzFormatua = getResources().getString(R.string.erPasahitzFormatua);
                                                        erroreMezua += erPasahitzFormatua;
                                                    } else if (exception instanceof FirebaseAuthUserCollisionException) {
                                                        // E-posta erregistratuta dago
                                                        String erEpostaErregistratuta = getResources().getString(R.string.erEpostaErregistratuta);
                                                        erroreMezua += erEpostaErregistratuta;
                                                    } else if (exception instanceof FirebaseNetworkException) {
                                                        // Internet konexio gabe
                                                        String erInternet = getResources().getString(R.string.erInternet);
                                                        erroreMezua += erInternet;
                                                    } else {
                                                        // Beste errore batzuk.
                                                        String erEzezaguna = getResources().getString(R.string.erEzezaguna);
                                                        erroreMezua += erEzezaguna + exception.getMessage();
                                                    }
                                                    Toast.makeText(register.this, erroreMezua, Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                });
                            }
                        } else {
                            // FireStoren kontsulta egiteko errorea.
                            String erDbNan = getResources().getString(R.string.erDbNan);
                            Toast.makeText(register.this, erDbNan, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void erabiltzaileaDatuBaseanGorde() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Erabiltzaile erabiltzaile = new Erabiltzaile(izena, nan, abizenak, email, mugikorra, erabiltzaileMota);
        db.collection("Erabiltzaileak").document(email).set(erabiltzaile).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(register.this, getResources().getString(R.string.dbGorde), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String erErrorea = getResources().getString(R.string.erErrorea);
                        Toast.makeText(register.this, erErrorea, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}