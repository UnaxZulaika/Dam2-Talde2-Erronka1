package com.talde2.dam2_erronka1_talde2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

public class ErreserbaInfo extends AppCompatActivity {

    TextView tvKokalekua;
    TextView tvInfo;
    ImageView ivArgazkia;

    RatingBar rbBalorazioak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erreserba_info);

        String documentArray[] = {"", "Kortezubi", "Baiona", "Bermeo", "Pamplona", "Lekeitio", "Tudela", "Saint jaen de luz",
                "Elantxobe", "Hondarribia", "Zumaia", "B.E.C.", "Guggenheim", "Montehermoso", "Tabakalera",
                "aurkezpena1", "aurkezpena2", "feria1", "feria2", "konferentzia1", "konferentzia2", "kumbre1",
                "kumbre2", "tailerra1", "tailerra2"};

        // erreserbak activity-tik pasatzen den informazio guztia
        int id = getIntent().getIntExtra("id", 0);
        String imageName = getIntent().getStringExtra("img");
        String kokalekua = getIntent().getStringExtra("kokalekua");
        String informazioa = getIntent().getStringExtra("informazioa");
        int balorazioa = getIntent().getIntExtra("balorazioa", 0);
        String dbColelction1 = getIntent().getStringExtra("dbColelction1");
        String dbColelction2 = getIntent().getStringExtra("dbColelction2");

        // argazkia ipintzen du
        ivArgazkia = findViewById(R.id.ivArgazkia);
        int resID = getResources().getIdentifier(imageName, "drawable", getPackageName());
        ivArgazkia.setImageResource(resID);

        // Kokalekua ipintzen du
        tvKokalekua = findViewById(R.id.tvKokalekua2);
        String txtKokalekua = kokalekua + ", " + documentArray[id];
        if (kokalekua.equals(documentArray[id])) {
            txtKokalekua = kokalekua;
        }
        tvKokalekua.setText(txtKokalekua);

        //Informazioa ipintzen du
        tvInfo = findViewById(R.id.tvInfo);
        tvInfo.setText(informazioa);

        // Balorazioa ipintzen du
        rbBalorazioak = findViewById(R.id.rbBalorazioak);
        rbBalorazioak.setRating(balorazioa);
        rbBalorazioak.setIsIndicator(true);

        // Erreserbaren Ordainketara eramaten duen botoia
        Button infBtnErreserbaEgin = findViewById(R.id.infBtnErreserbaEgin);
        infBtnErreserbaEgin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ErreserbaInfo.this, erreserbaOrdainketa.class);
                startActivity(intent);
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
                Intent intent = new Intent(ErreserbaInfo.this, Nire_Kontua.class);

                variables_de_usuario(intent, izena, abizena, nan, email, mugikorra, erabiltzaileMota);

                startActivity(intent);
            }
        });

        //RESERBAK
        Button btnErregistratu = findViewById(R.id.idBtnMenuNireErreserbak);
        btnErregistratu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ErreserbaInfo.this, Egindako_Erreserbak.class);

                variables_de_usuario(intent, izena, abizena, nan, email, mugikorra, erabiltzaileMota);

                startActivity(intent);
            }
        });

        //PRIBATASUN POLITIKAK
        Button idBtnPribatutasunPolitikak = findViewById(R.id.idBtnPribatutasunPolitikak);
        idBtnPribatutasunPolitikak.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ErreserbaInfo.this, PribatasunPolitikak.class);

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
}