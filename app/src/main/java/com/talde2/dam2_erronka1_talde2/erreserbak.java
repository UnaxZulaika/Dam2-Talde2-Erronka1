package com.talde2.dam2_erronka1_talde2;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class erreserbak extends AppCompatActivity {

    ArrayList<JardueraPertsona> tokiakPertsona = new ArrayList<JardueraPertsona>();
    ArrayList<JardueraEntitateak> tokiakEntitatea = new ArrayList<JardueraEntitateak>();

    //metodos

        //pasatu informazioa intent batera
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
        setContentView(R.layout.activity_erreserbak);


        String erabiltzaileEmail = getIntent().getStringExtra("USER_email");
        datuBaseKarga(erabiltzaileEmail);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

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

    // Datu baseko erreserben informazioa kargatzen du
    private void datuBaseKarga(String erabiltzaileEmail) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //anonimoa bada kontrolatzeko
        String izena = getIntent().getStringExtra("USER_izena");

        if (izena.equals("anonimoa")) { //pertsona bezala izango da?

            // Pertsonentzako bakarrik direnak
            tokiakPertsona = tokiakBetePertsona(db, "Ibilbideak", "Pertsona", tokiakPertsona);
            tokiakPertsona = tokiakBetePertsona(db, "Aisialdiak", "Pertsona", tokiakPertsona);

        } else { //ez bada anonimoa

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


                                    tokiakPertsona = tokiakBetePertsona(db, "Aisialdiak", pertsonaMota, tokiakPertsona);


                                    //   cargarBotonesYImagenes();

                                } else if (erabiltzaile.getErabiltzaileMota().equals("Enpresa/Entitatea")) {
                                    // Enpresa/Entitatentzako bakarrik direnak
                                    tokiakEntitatea = tokiakBeteEntitateak(db, "Aurkezpenak", tokiakEntitatea);
                                    tokiakEntitatea = tokiakBeteEntitateak(db, "Feriak", tokiakEntitatea);
                                    tokiakEntitatea = tokiakBeteEntitateak(db, "Konferentziak", tokiakEntitatea);
                                    tokiakEntitatea = tokiakBeteEntitateak(db, "Kumbreak", tokiakEntitatea);
                                    tokiakEntitatea = tokiakBeteEntitateak(db, "Tailerrak", tokiakEntitatea);
                                } else {
                                    // Denak kargatu
                                    tokiakBeteAnonimo(db, "Ibilbideak", tokiakPertsona, tokiakEntitatea);
                                    tokiakBeteAnonimo(db, "Aisialdiak", tokiakPertsona, tokiakEntitatea);
                                    tokiakBeteAnonimo(db, "Aurkezpenak", tokiakPertsona, tokiakEntitatea);
                                    tokiakBeteAnonimo(db, "Feriak", tokiakPertsona, tokiakEntitatea);
                                    tokiakBeteAnonimo(db, "Konferentziak", tokiakPertsona, tokiakEntitatea);
                                    tokiakBeteAnonimo(db, "Kumbreak", tokiakPertsona, tokiakEntitatea);
                                    tokiakBeteAnonimo(db, "Tailerrak", tokiakPertsona, tokiakEntitatea);

                                }

                            } else {
                                Resources resources =  getResources();
                                Log.e(resources.getString(R.string.dokumentuaExistitu), resources.getString(R.string.dokumentuaEzExistitu)); //console log
                            }
                        }else {
                            Resources resources =  getResources();
                            Log.d(TAG, resources.getString(R.string.errDokumentua) + " ", task.getException());
                        }
                    }
                });
        }
    }

    // Datu baseko erreserben informazioa kargatzen du Pertsonentzat
    public ArrayList<JardueraPertsona> tokiakBetePertsona(FirebaseFirestore db, String tokiMota, String pertsonaMota, ArrayList<JardueraPertsona> tokiakPertsona) {
        db.collection(tokiMota)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Ez egoteko bikoiztuta
                            Set<String> uniqueMotas = new HashSet<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData()); //console log
                                JardueraPertsona t1 = document.toObject(JardueraPertsona.class);
                                if (tokiMota.equals("Ibilbideak")) {
                                    t1.setMota(JardueraPertsona.jardueraMota.ruta);
                                } else {
                                    t1.setMota(JardueraPertsona.jardueraMota.aisialdia);
                                }
                                tokiakPertsona.add(t1);

                                //Pertsonentzako erreserba motak gordetzen dira
                                uniqueMotas.add(t1.getMota().toString());
                            }

                            // Filtroen botoiak sortzen dira
                            LinearLayout buttonContainer = findViewById(R.id.filtroak);
                            for (String txtBtn : uniqueMotas) {
                                Button button = new Button(erreserbak.this);
                                button.setText(txtBtn);
                                button.setLayoutParams(new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                ));
                                buttonContainer.addView(button);

                                // Pertsonentzako erreserba guztiak kargatzen dira eta ArrayList desberdinetan gordetzen dira
                                LinearLayout btnImagenLayout = findViewById(R.id.layoutBtnImg);
                                ArrayList<String> imageNames = new ArrayList<String>();
                                ArrayList<Integer> imageIDs = new ArrayList<Integer>();
                                ArrayList<String> kokalekuak = new ArrayList<String>();
                                ArrayList<String> informazioaGuztia = new ArrayList<String>();
                                ArrayList<Double> balorazioak = new ArrayList<>();
                                ArrayList<Double> prezioak = new ArrayList<>();
                                ArrayList<Double> prezioak10 = new ArrayList<>();
                                ArrayList<Double> prezioak20 = new ArrayList<>();

                                for (int i = 0; i < tokiakPertsona.size(); i++) {
                                    imageNames.add(tokiakPertsona.get(i).getImg());
                                    imageIDs.add(tokiakPertsona.get(i).getCode());
                                    kokalekuak.add(tokiakPertsona.get(i).getKokalekua());
                                    informazioaGuztia.add(tokiakPertsona.get(i).getInformazioa());
                                    balorazioak.add(tokiakPertsona.get(i).getBalorazioa());
                                    prezioak.add(tokiakPertsona.get(i).getPrezioa());
                                    prezioak10.add(tokiakPertsona.get(i).getPrezioa10());
                                    prezioak20.add(tokiakPertsona.get(i).getPrezioa20());
                                }

                                LinearLayout currentLinearLayout = null; // Hasiera ez dago beste LinearLayoutik

                                for (int i = 0; i < imageNames.size(); i++) {
                                    String imageName = imageNames.get(i);
                                    int imageID = imageIDs.get(i);
                                    String kokalekua = kokalekuak.get(i);
                                    String informazioa = informazioaGuztia.get(i);
                                    double balorazioaDouble = balorazioak.get(i);
                                    int balorazioa = (int) balorazioaDouble;
                                    double prezioa = prezioak.get(i);
                                    double prezioa10 = prezioak10.get(i);
                                    double prezioa20 = prezioak20.get(i);

                                    String izena = getIntent().getStringExtra("USER_izena");
                                    String abizena = getIntent().getStringExtra("USER_abizena");
                                    String nan = getIntent().getStringExtra("USER_nan");
                                    String email = getIntent().getStringExtra("USER_email");
                                    String mugikorra = getIntent().getStringExtra("USER_mugikorra");
                                    String erabiltzaileMota = getIntent().getStringExtra("USER_erabiltzaileMota");

                                    if (i % 2 == 0) {
                                        // Bikoitia bada, LinearLayout (horizontal) berria sortzen du
                                        currentLinearLayout = new LinearLayout(erreserbak.this);
                                        currentLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                        currentLinearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
                                        btnImagenLayout.addView(currentLinearLayout); // LinearLayout berria, lehen linearLayout-era gehitzen du
                                    }

                                    // ImageButton berriak sortzen dira aktibitate bakaoitzaren argazkiarekin
                                    ImageButton imageButton = new ImageButton(erreserbak.this);
                                    imageButton.setId(imageID);
                                    imageButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            // Erreserba egiteko behar dugun informazio pasatzeko hurrengo activity-etara.
                                            Intent intent = new Intent(erreserbak.this, ErreserbaInfo.class);
                                            intent.putExtra("id", v.getId());
                                            intent.putExtra("img", imageName);
                                            intent.putExtra("kokalekua", kokalekua);
                                            intent.putExtra("informazioa", informazioa);
                                            intent.putExtra("balorazioa", balorazioa);
                                            intent.putExtra("prezioa", prezioa);
                                            intent.putExtra("prezioa10", prezioa10);
                                            intent.putExtra("prezioa20", prezioa20);
                                            intent.putExtra("dbColelction1", "Aisialdiak");
                                            intent.putExtra("dbColelction2", "Ibilbideak");
                                            variables_de_usuario(intent, izena, abizena, nan, email, mugikorra, erabiltzaileMota);
                                            intent.putParcelableArrayListExtra("tokiak_pertsona", tokiakPertsona);

                                            startActivity(intent);
                                        }
                                    });

                                    int resID = getResources().getIdentifier(imageName, "drawable", getPackageName());
                                    // imageButton-aren tamaina
                                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(400, 400);
                                    // botoiaren irudiaren escala
                                    imageButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                                    // irudia kargatzen du
                                    if (resID != 0) {
                                        imageButton.setImageResource(resID);
                                    }
                                    // botoieri margenak ipintzen dio
                                    layoutParams.setMargins(75, 75, 75, 75);
                                    imageButton.setLayoutParams(layoutParams);
                                    // ImgageButton-a LinearLayout berrira sartzen da
                                    currentLinearLayout.addView(imageButton);
                                }


                            }
                        } else {
                            Resources resources =  getResources();
                            Log.d(TAG, resources.getString(R.string.errDokumentua) + " ", task.getException()); //console log
                        }
                    }
                });
        return tokiakPertsona;
    }

    // Datu baseko erreserben informazioa kargatzen du Entitateentzat
    public ArrayList<JardueraEntitateak> tokiakBeteEntitateak(FirebaseFirestore db, String tokiMota, ArrayList<JardueraEntitateak> tokiakEntitateak) {
        db.collection(tokiMota)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Utiliza un conjunto para evitar duplicados
                            Set<String> uniqueMotas = new HashSet<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                JardueraEntitateak t1 = document.toObject(JardueraEntitateak.class);
                                if (tokiMota.equals("Aurkezpenak")) {
                                    t1.setMota(JardueraEntitateak.jardueraMota.aurkezpena);
                                } else if (tokiMota.equals("Feriak")) {
                                    t1.setMota(JardueraEntitateak.jardueraMota.feria);
                                } else if (tokiMota.equals("Konferentziak")) {
                                    t1.setMota(JardueraEntitateak.jardueraMota.konferentzia);
                                } else if (tokiMota.equals("Kumbreak")) {
                                    t1.setMota(JardueraEntitateak.jardueraMota.kumbrea);
                                } else if (tokiMota.equals("Tailerrak")) {
                                    t1.setMota(JardueraEntitateak.jardueraMota.tailerrak);
                                }
                                tokiakEntitateak.add(t1);

                                // Agrega la mota al conjunto
                                uniqueMotas.add(t1.getMota().toString());
                            }

                            LinearLayout buttonContainer = findViewById(R.id.filtroak);

                            for (String txtBtn : uniqueMotas) {
                                Button button = new Button(erreserbak.this);
                                button.setText(txtBtn);
                                button.setLayoutParams(new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                ));

                                buttonContainer.addView(button);
                            }
                        } else {
                            Resources resources =  getResources();
                            Log.d(TAG, resources.getString(R.string.errDokumentua) + " ", task.getException());
                        }
                    }
                });
        return tokiakEntitateak;
    }

    // // Datu baseko erreserben informazioa kargatzen du Anonimoentzat
    public static void tokiakBeteAnonimo(FirebaseFirestore db, String tokiMota, ArrayList<JardueraPertsona> tokiakPertsona, ArrayList<JardueraEntitateak> tokiakEntitateak) {
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
                                JardueraEntitateak te1 = document.toObject(JardueraEntitateak.class);
                                if (tokiMota.equals("Aurkezpenak")) {
                                    te1.setMota(JardueraEntitateak.jardueraMota.aurkezpena);
                                } else if (tokiMota.equals("Feriak")){
                                    te1.setMota(JardueraEntitateak.jardueraMota.feria);
                                } else if (tokiMota.equals("Konferentziak")) {
                                    te1.setMota(JardueraEntitateak.jardueraMota.konferentzia);
                                } else if (tokiMota.equals("Kumbreak")) {
                                    te1.setMota(JardueraEntitateak.jardueraMota.kumbrea);
                                } else if (tokiMota.equals("Tailerrak")){
                                    te1.setMota(JardueraEntitateak.jardueraMota.tailerrak);
                                }
                                tokiakEntitateak.add(te1);
                            }
                            }

                        }
                });
            }
}