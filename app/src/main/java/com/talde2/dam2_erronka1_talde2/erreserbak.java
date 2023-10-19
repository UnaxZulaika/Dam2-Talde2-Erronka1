package com.talde2.dam2_erronka1_talde2;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.talde2.dam2_erronka1_talde2.Objetuak.Erabiltzaile;
import com.talde2.dam2_erronka1_talde2.Objetuak.JardueraEntitateak;
import com.talde2.dam2_erronka1_talde2.Objetuak.JardueraPertsona;

import java.util.ArrayList;
import java.util.List;

public class erreserbak extends AppCompatActivity {

    ArrayList<JardueraPertsona> tokiakPertsona = new ArrayList<JardueraPertsona>();
    ArrayList<JardueraEntitateak> tokiakEntitatea = new ArrayList<JardueraEntitateak>();

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

    private void datuBaseKarga(String erabiltzaileEmail) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Erabiltzailearen izena lortzeko kontsulta
        db.collection("Erabiltzaileak").document(erabiltzaileEmail).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();

                            if (document.exists()) {
                                // Erabiltzaile izena eskuratzen du
                                Erabiltzaile erabiltzaile = login.erabiltzaileaBete(document);
                               String pertsonaMota = erabiltzaile.getErabiltzaileMota().toString();


                                if (erabiltzaile.getErabiltzaileMota().equals("Pertsona")) {

                                    // Pertsonentzako bakarrik direnak
                                    tokiakPertsona = tokiakBetePertsona(db, "Ibilbideak", pertsonaMota, tokiakPertsona);
                                    //System.out.println(tokiakPertsona.get(0).getKokalekua()+" hola");
                                    System.out.println(erabiltzaile.getErabiltzaileMota().toString());
                                    tokiakPertsona = tokiakBetePertsona(db, "Aisialdiak", pertsonaMota, tokiakPertsona);
                                    System.out.println("ZZZZZZZZZ");
                                    for (int i = 0; i < tokiakPertsona.size(); i++) {
                                        System.out.println(tokiakPertsona.get(i).getMota());
                                        System.out.println("BBBBBBBBBBBB");
                                    }
                                    System.out.println("OOOOOOOOOOOO");
                                } else if (erabiltzaile.getErabiltzaileMota().equals("Enpresa/Entitatea")) {
                                    // Enpresa/Entitatentzako bakarrik direnak
                                    /*tokiakEntitatea = tokiakBeteEntitateak(db, "Aurkezpenak", tokiakEntitatea);
                                    tokiakEntitatea = tokiakBeteEntitateak(db, "Feriak", tokiakEntitatea);
                                    tokiakEntitatea = tokiakBeteEntitateak(db, "Konferentziak", tokiakEntitatea);
                                    tokiakEntitatea = tokiakBeteEntitateak(db, "Kumbreak", tokiakEntitatea);
                                    tokiakEntitatea = tokiakBeteEntitateak(db, "Tailerrak", tokiakEntitatea);
                                } else {
                                    // Denak kargatu
                                    tokiakPertsona = tokiakBetePertsona(db, "Ibilbideak", tokiakPertsona);
                                    tokiakPertsona = tokiakBetePertsona(db, "Aisialdiak", tokiakPertsona);
                                    tokiakEntitatea = tokiakBeteEntitateak(db, "Aurkezpenak", tokiakEntitatea);
                                    tokiakEntitatea = tokiakBeteEntitateak(db, "Feriak", tokiakEntitatea);
                                    tokiakEntitatea = tokiakBeteEntitateak(db, "Konferentziak", tokiakEntitatea);
                                    tokiakEntitatea = tokiakBeteEntitateak(db, "Kumbreak", tokiakEntitatea);
                                    tokiakEntitatea = tokiakBeteEntitateak(db, "Tailerrak", tokiakEntitatea);*/
                                }

                                System.out.println("AAAAAAAAAAAAAAA");



                                for (int i = 0; i < tokiakEntitatea.size(); i++) {
                                    System.out.println(tokiakEntitatea.get(i).getMota());
                                    System.out.println("AAAAAAAAAAAAAA1A");
                                }


                            }
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erreserbak);

        String erabiltzaileEmail = getIntent().getStringExtra("USER_email");
        //datuBaseKarga(erabiltzaileEmail);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        System.out.println("PRUEBAAAAAAAAAAAAAAAA");
        //tokiakPertsona = tokiakBetePertsona(db, "Ibilbideak", tokiakPertsona);
        System.out.println("PRUEBAA2222222222222");
        LinearLayout buttonContainer = findViewById(R.id.filtroak);
        datuBaseKarga(erabiltzaileEmail);
        ArrayList<String> listaBotones = new ArrayList<>();



        for (String textoBoton : listaBotones) {
            Button button = new Button(this);
            button.setText(textoBoton);
            button.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            buttonContainer.addView(button);
        }

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


    //LEHIOAK PASATZERA


        //datos a pasar a todas las pantallas del menu (para nire kontua)
        String izena = getIntent().getStringExtra("USER_izena");
        String abizena = getIntent().getStringExtra("USER_abizena");
        String nan = getIntent().getStringExtra("USER_nan");
        String email = getIntent().getStringExtra("USER_email");
        String mugikorra = getIntent().getStringExtra("USER_mugikorra");
        String erabiltzaileMota = getIntent().getStringExtra("USER_erabiltzaileMota");

        //NIRE KONTUA
        Button btnNireKontua = findViewById(R.id.idBtnMenuKontua);
        btnNireKontua.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(erreserbak.this, Nire_Kontua.class);

                variables_de_usuario(intent, izena, abizena, nan, email, mugikorra, erabiltzaileMota);

                startActivity(intent);
            }
        });

        //RESERBAK
        Button btnErregistratu = findViewById(R.id.idBtnMenuNireErreserbak);
        btnErregistratu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(erreserbak.this, Egindako_Erreserbak.class);

                variables_de_usuario(intent, izena, abizena, nan, email, mugikorra, erabiltzaileMota);

                startActivity(intent);
            }
        });

        //PRIBATASUN POLITIKAK
        Button idBtnPribatutasunPolitikak = findViewById(R.id.idBtnPribatutasunPolitikak);
        idBtnPribatutasunPolitikak.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(erreserbak.this, PribatasunPolitikak.class);

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
    /**
     *
     * @param db firebase instantzia
     * @param tokiMota zein toki kargatuko den
     * @return arraylist bat tokiak objetuak transformatuta
     */
    public static ArrayList<JardueraPertsona> tokiakBetePertsona(FirebaseFirestore db, String tokiMota, String pertsonaMota, ArrayList<JardueraPertsona> tokiakPertsona) {
        db.collection(tokiMota)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                    JardueraPertsona t1 = document.toObject(JardueraPertsona.class);
                                    if (tokiMota.equals("Ibilbideak")){
                                        t1.setMota(JardueraPertsona.jardueraMota.ruta);
                                    } else {
                                        t1.setMota(JardueraPertsona.jardueraMota.aisialdia);
                                    }
                                    tokiakPertsona.add(t1);
                                    System.out.println(tokiakPertsona.get(0).getKokalekua() + "AAAAAAAAAAAAAAAAAA");
                                }
                        } else {
                            Log.d(TAG, "Error dokumentuak lortzen: ", task.getException());
                        }
                    }
                });
        return tokiakPertsona;
    }
    public static ArrayList<JardueraEntitateak> tokiakBeteEntitateak(FirebaseFirestore db, String tokiMota, ArrayList<JardueraEntitateak> tokiakEntitateak) {
        db.collection(tokiMota)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                JardueraEntitateak t1 = document.toObject(JardueraEntitateak.class);
                                if (tokiMota.equals("Aurkezpenak")) {
                                    t1.setMota(JardueraEntitateak.jardueraMota.aurkezpena);
                                } else if (tokiMota.equals("Feriak")){
                                    t1.setMota(JardueraEntitateak.jardueraMota.feria);
                                } else if (tokiMota.equals("Konferentziak")) {
                                    t1.setMota(JardueraEntitateak.jardueraMota.konferentzia);
                                } else if (tokiMota.equals("Kumbreak")) {
                                    t1.setMota(JardueraEntitateak.jardueraMota.kumbrea);
                                } else if (tokiMota.equals("Tailerrak")){
                                    t1.setMota(JardueraEntitateak.jardueraMota.tailerrak);
                                }
                                tokiakEntitateak.add(t1);
                            }
                        } else {
                            Log.d(TAG, "Error dokumentuak lortzen: ", task.getException());
                        }
                    }
                });
        return tokiakEntitateak;
    }

}