package com.talde2.dam2_erronka1_talde2;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
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
import com.google.firebase.auth.FirebaseUser;
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

        //BD instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        if(izena.equals("anonimoa")){
        }else{//ez bada aninimoa konexioa egin datubasera izena lortzeko(ipintzeko BD-an dagoen izena lehenetik)
            //Datu Baserako
            nrkntEditIzena = findViewById(R.id.nrkntEditIzena);
            nrkntEditIzena.setText("");

            //get izena from BD
            db.collection("Erabiltzaileak").document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot document = task.getResult();
                        nrkntEditIzena.setText(document.get("izena").toString()); //ipini EditTextean BD-ko izena
                    }else{
                        Log.d("Get BD", getResources().getString(R.string.nrkntIzenaEzKargatu)); //console log
                    }
                }
            });
        }


        //desabilitatu testua zartzea edit textean eta datuak gehitu

            //izena
            EditText nrkntEditIzena = findViewById(R.id.nrkntEditIzena);
            nrkntEditIzena.setText(izena);
            nrkntEditIzena.setEnabled(false); nrkntEditIzena.setInputType(0);

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
        //fin desabilitatu dit text idaztea


      //BD BOTOIAK UPDATE ETA DELETE

        //DELETE botoia
        Button btnEzabatu = findViewById(R.id.IdBtnDelete);
        if(izena.equals("anonimoa")){ //anonimoa bada ezin borratzea erabiltzailea(ez da agertzen BD)
            btnEzabatu.setEnabled(false);
        }

        btnEzabatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // AlertDialog bat zabalduko da kontua ezabatu nahi den edo ez aukeratzeko.
                AlertDialog.Builder builder = new AlertDialog.Builder(Nire_Kontua.this);
                builder.setMessage(getResources().getString(R.string.izenaAldatuZiur));
                builder.setPositiveButton(getResources().getString(R.string.bai), new DialogInterface.OnClickListener() {
                    // 'Bai' aukeratzean erabiltzailea ezabatuko da Authenticator eta datubasetik,
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.collection("Erabiltzaileak").document(email)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() { //user deleted successfully
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        mAuth= FirebaseAuth.getInstance();
                                        mAuth.getCurrentUser().delete();
                                        Log.d("delete BD", getResources().getString(R.string.erabiltzaileaEzabatu)); //console log

                                        Intent intent = new Intent(Nire_Kontua.this, login.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {//user deleted failure
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e("delete BD", getResources().getString(R.string.errErabiltzaileaEzabatu), e);//console log
                                    }
                                });
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.ez), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Ez aukeratzean ez du ezer egingo, Alertdialoga itxiko da
                    }
                });
                builder.show();
            }
        });

        //UPDATE botoia
        Button nrkntBtnUpdateIzena = findViewById(R.id.nrkntBtnUpdateIzena);

        if(izena.equals("anonimoa")){ //anonimoa bada ezin borratzea erabiltzailea(ez da agertzen BD)
            nrkntBtnUpdateIzena.setEnabled(false);
        }
        nrkntBtnUpdateIzena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Nire_Kontua.this);
                builder.setTitle(getResources().getString(R.string.aukeratuAldaketa));

                builder.setPositiveButton(getResources().getString(R.string.aldatuPasahitza), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Crear un nuevo cuadro de diálogo para cambiar la contraseña
                        AlertDialog.Builder changePasswordBuilder = new AlertDialog.Builder(Nire_Kontua.this);
                        changePasswordBuilder.setTitle(getResources().getString(R.string.aldatuPasahitza2));

                        final EditText newPasswordEditText = new EditText(Nire_Kontua.this);
                        newPasswordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        newPasswordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        newPasswordEditText.setHint(getResources().getString(R.string.sartuPasahitzBerria));
                        changePasswordBuilder.setView(newPasswordEditText);

                        changePasswordBuilder.setPositiveButton(getResources().getString(R.string.onartu), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String newPassword = newPasswordEditText.getText().toString().trim();

                                if (TextUtils.isEmpty(newPassword) || newPassword.equals("")) {
                                    Toast.makeText(Nire_Kontua.this, getResources().getString(R.string.ezPasahitzaAldatu), Toast.LENGTH_SHORT).show();
                                } else {
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    newPassword = newPasswordEditText.getText().toString().trim();

                                    if (user != null) {
                                        user.updatePassword(newPassword)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            // Pasahitza ondo aldatu da
                                                            String contraseñaUpdateExitosa = getResources().getString(R.string.pasahitzaAldatuta);
                                                            Toast.makeText(Nire_Kontua.this, contraseñaUpdateExitosa, Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            // Pasahitza aldatzena errore bat egon da
                                                            String errorContraseñaUpdate = getResources().getString(R.string.errPasahitzaAldatzen) + " " + task.getException().getMessage();
                                                            Toast.makeText(Nire_Kontua.this, errorContraseñaUpdate, Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }

                                }
                                dialog.dismiss();
                            }
                        });

                        changePasswordBuilder.setNegativeButton(getResources().getString(R.string.ezeztatu), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        changePasswordBuilder.show();
                    }
                });

                builder.setNegativeButton(getResources().getString(R.string.aldatuIzena), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder updateIzenaBuilder = new AlertDialog.Builder(Nire_Kontua.this);
                        updateIzenaBuilder.setTitle(getResources().getString(R.string.sartuIzena));

                        final EditText newIzenaEditText = new EditText(Nire_Kontua.this);
                        updateIzenaBuilder.setView(newIzenaEditText);

                        updateIzenaBuilder.setPositiveButton(getResources().getString(R.string.onartu), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String newIzena = newIzenaEditText.getText().toString().trim();

                                if (TextUtils.isEmpty(newIzena) || newIzena.equals("")) {
                                    String sartuIzena = getString(R.string.izenaErrorea);
                                    nrkntEditIzena.setError(sartuIzena);
                                } else {
                                    // Erabiltzailearen izena eguneratzen du, datu basean
                                    final DocumentReference sfDocRef = db.collection("Erabiltzaileak").document(email);

                                    db.runTransaction(new Transaction.Function<Void>() {
                                        @Override
                                        public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                                            DocumentSnapshot document = transaction.get(sfDocRef);

                                            // Eguneratu (DocRef / izena / nuevo valor)
                                            transaction.update(sfDocRef, getResources().getString(R.string.izena), newIzena);
                                            return null;
                                        }
                                    }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            String erabiltzailea_updateatu_da = getString(R.string.aldaketaOndo);
                                            Toast.makeText(Nire_Kontua.this, erabiltzailea_updateatu_da, Toast.LENGTH_SHORT).show();
                                            Log.d("update BD", getResources().getString(R.string.transakzioOndo)); // Registro en la consola

                                            nrkntEditIzena.setText(newIzena);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e("update BD", getResources().getString(R.string.transakzioaGaizki), e);
                                        }
                                    });
                                }
                                dialog.dismiss();
                            }
                        });
                        updateIzenaBuilder.setNegativeButton(getResources().getString(R.string.ezeztatu), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        updateIzenaBuilder.show();
                    }
                });

                builder.show();
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


        //ERRESERBAK
        Button btnNireKontua = findViewById(R.id.idBtnMenuKatalogoa);

        btnNireKontua.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Nire_Kontua.this, erreserbak.class);

                variables_de_usuario(intent, izena, abizena, nan, email, mugikorra, erabiltzaileMota);

                startActivity(intent);
                finish();
            }
        });

        //EGINDAKO_ERRESERBAK
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