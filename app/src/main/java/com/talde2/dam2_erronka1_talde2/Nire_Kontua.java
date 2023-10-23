package com.talde2.dam2_erronka1_talde2;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import org.checkerframework.checker.units.qual.Current;

public class Nire_Kontua extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText nrkntEditIzena;
    //metodos
        public void variables_de_usuario(Intent intent, String izena,String abizena,String nan,String email,String mugikorra,String erabiltzaileMota) {

            intent.putExtra("USER_izena", izena);
            intent.putExtra("USER_abizena", abizena);
            intent.putExtra("USER_nan", nan);
            intent.putExtra("USER_email", email);
            intent.putExtra("USER_mugikorra", mugikorra);
            intent.putExtra("USER_erabiltzaileMota", erabiltzaileMota);

        };
    //fin metodos


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nire_kontua);

        //menua izkutatzeko lehenik
        LinearLayout aukerak = findViewById(R.id.idLayOpzioak);
        aukerak.setVisibility(View.INVISIBLE);

        //pasatutako datuak
        String izena = getIntent().getStringExtra("USER_izena");
        String abizena = getIntent().getStringExtra("USER_abizena");
        String nan = getIntent().getStringExtra("USER_nan");
        String email = getIntent().getStringExtra("USER_email");
        String mugikorra = getIntent().getStringExtra("USER_mugikorra");
        String erabiltzaileMota = getIntent().getStringExtra("USER_erabiltzaileMota");

        //Datu Baserako
        nrkntEditIzena = findViewById(R.id.nrkntEditIzena);
        nrkntEditIzena.setText("");

        //get izena from BD
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Erabiltzaileak").document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    nrkntEditIzena.setText(document.get("izena").toString());
                }else{
                    Log.d("Get BD", "izena ez da kargatu");
                }
            }
        });

        //desabilitatu testua zartzea edit textean eta datuak gehitu

            //izena
            EditText nrkntEditIzena = findViewById(R.id.nrkntEditIzena);
            nrkntEditIzena.setText(izena);
            //nrkntEditIzena.setEnabled(false); nrkntEditIzena.setInputType(0);

            //abizenak
            EditText nrkntEditAbizenak = findViewById(R.id.nrkntEditAbizenak);
            nrkntEditAbizenak.setText(abizena);
            nrkntEditAbizenak.setEnabled(false); nrkntEditAbizenak.setInputType(0);

            //nan
            EditText nrkntEditNan = findViewById(R.id.nrkntEditNan);
            nrkntEditNan.setText(nan);
            nrkntEditNan.setEnabled(false); nrkntEditNan.setInputType(0);

            //email
            EditText nrkntEditPosta = findViewById(R.id.nrkntEditPosta);
            nrkntEditPosta.setText(email);
            nrkntEditPosta.setEnabled(false); nrkntEditPosta.setInputType(0);

            //mugikorra
            EditText nrkntEditMugikorra = findViewById(R.id.nrkntEditMugikorra);
            nrkntEditMugikorra.setText(mugikorra);
            nrkntEditMugikorra.setEnabled(false); nrkntEditMugikorra.setInputType(0);

            //pasahitza1
            EditText nrkntEditPasahitza = findViewById(R.id.nrkntEditPasahitza);
            nrkntEditPasahitza.setText("******"); //falta meter
            nrkntEditPasahitza.setEnabled(false); nrkntEditPasahitza.setInputType(0);

            //Pasahitza2
            EditText nrkntEditPasahitza2 = findViewById(R.id.nrkntEditPasahitza2);
            nrkntEditPasahitza2.setText("******"); //falta meter
            nrkntEditPasahitza2.setEnabled(false); nrkntEditPasahitza2.setInputType(0);

            //Erabiltzaile mota
            EditText nrkntEditErbltzMta = findViewById(R.id.nrkntEditErbltzMta);
            nrkntEditErbltzMta.setText(erabiltzaileMota);
            nrkntEditErbltzMta.setEnabled(false); nrkntEditErbltzMta.setInputType(0);

            //Ezabatzeko botoia
            Button btnEzabatu = findViewById(R.id.IdBtnDelete);

        btnEzabatu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Erabiltzaileak").document(email)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                mAuth= FirebaseAuth.getInstance();
                                mAuth.getCurrentUser().delete();
                                Log.d(TAG, "erabiltzailea ondo ezabatu da");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Errorea erabiltzailea ezabatzen", e);
                            }
                        });
            }
        });

        //fin desabilitatu dit text idaztea

        //Update database onclic
        //FirebaseFirestore db = FirebaseFirestore.getInstance();

        Button nrkntBtnUpdateIzena = findViewById(R.id.nrkntBtnUpdateIzena);
        nrkntBtnUpdateIzena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String erabiltzaile_izena = nrkntEditIzena.getText().toString().trim();

                if(TextUtils.isEmpty(erabiltzaile_izena) || erabiltzaile_izena==""){ //izena hutzik badago
                    String sartuIzena = getString(R.string.izenaErrorea);
                    nrkntEditIzena.setError(sartuIzena);
                }else{ //UPDATE BD

                    // (coleccion / documento)
                    final DocumentReference sfDocRef = db.collection("Erabiltzaileak").document(email);

                    db.runTransaction(new Transaction.Function<Void>() {
                                @Override
                                public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                                    DocumentSnapshot document = transaction.get(sfDocRef);

                                    //update( DocRef / izena / nuevo valor )
                                    transaction.update(sfDocRef, "izena", erabiltzaile_izena);

                                    // Success
                                    return null;
                                }

                                //LOGS
                            }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    String erabiltzailea_updateatu_da = getString(R.string.aldaketaOndo);
                                    // Errorea erabiltzailea ez da aurkitzen
                                    Toast.makeText(Nire_Kontua.this, erabiltzailea_updateatu_da, Toast.LENGTH_SHORT).show();
                                    Log.d("update BD", "Transaction success!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("update BD", "Transaction failure.", e);
                                }
                            });
                }


            }
        });

        //MENUA
        //menua izkutatzeko lehenik
        LinearLayout opzioak = findViewById(R.id.idLayOpzioak);
        opzioak.setVisibility(View.INVISIBLE);

        Button btnMenuItx = findViewById(R.id.idBtnMenuItxi);
        Button btnMenuZabaldu = findViewById(R.id.IdBtnMenuZabaldu);

        //open
        btnMenuZabaldu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout opzioak = findViewById(R.id.idLayOpzioak);
                opzioak.setVisibility(View.VISIBLE);
            }
        });

        //close
        btnMenuItx.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout Opzioak = findViewById(R.id.idLayOpzioak);
                Opzioak.setVisibility(View.INVISIBLE);
            }
        });

        //close 2
        Button btnAtzera = findViewById(R.id.btnAtzera);
        btnAtzera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout opzioak = findViewById(R.id.idLayOpzioak);
                opzioak.setVisibility(View.INVISIBLE);
            }
        });


        //NIRE KONTUA
        Button btnNireKontua = findViewById(R.id.idBtnMenuKatalogoa);


        btnNireKontua.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Nire_Kontua.this, erreserbak.class);

                variables_de_usuario(intent, izena, abizena, nan, email, mugikorra, erabiltzaileMota);

                startActivity(intent);
            }
        });

        //RESERBAK
        Button btnErregistratu = findViewById(R.id.idBtnMenuNireErreserbak);
        btnErregistratu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Nire_Kontua.this, Egindako_Erreserbak.class);

                variables_de_usuario(intent, izena, abizena, nan, email, mugikorra, erabiltzaileMota);

                startActivity(intent);
            }
        });

        //PRIBATASUN POLITIKAK
        Button idBtnPribatutasunPolitikak = findViewById(R.id.idBtnPribatutasunPolitikak);
        idBtnPribatutasunPolitikak.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Nire_Kontua.this, PribatasunPolitikak.class);

                variables_de_usuario(intent, izena, abizena, nan, email, mugikorra, erabiltzaileMota);

                startActivity(intent);
            }
        });

        //INSTAGRAM
        Button btnEmpresaInsta = findViewById(R.id.btnEmpresaInsta);
        btnEmpresaInsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.instagram.com/OMA_empresa/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        // X
        Button btnEmpresaX = findViewById(R.id.btnEmpresaX);
        btnEmpresaX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://twitter.com/OMA_empresa";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
//menua fin
    }
}