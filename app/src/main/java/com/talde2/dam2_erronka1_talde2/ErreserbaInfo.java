package com.talde2.dam2_erronka1_talde2;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.talde2.dam2_erronka1_talde2.Objetuak.JardueraPertsona;

import java.util.ArrayList;

public class ErreserbaInfo extends AppCompatActivity {

    TextView tvInfo;
    ImageView ivArgazkia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erreserba_info);
        int ids[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};
        String documentArray[] = {"Kortezubi", "Baiona", "Bermeo", "Pamplona", "Lekeitio", "Tudela", "Saint jaen de luz",
                "Elantxobe", "Hondarribia", "Zumaia", "B.E.C.", "Guggenheim", "Montehermoso", "Tabakalera",
                "aurkezpena1", "aurkezpena2", "feria1", "feria2", "konferentzia1", "konferentzia2", "kumbre1",
                "kumbre2", "tailerra1", "tailerra2"};

        int id = getIntent().getIntExtra("id", 0);
        String imageName = getIntent().getStringExtra("img");
        String informazioa = getIntent().getStringExtra("informazioa");
        String dbColelction1 = getIntent().getStringExtra("dbColelction1");
        String dbColelction2 = getIntent().getStringExtra("dbColelction2");

        ivArgazkia = findViewById(R.id.ivArgazkia);

        System.out.println("IMAGE NAME: " + imageName);
        int resID = getResources().getIdentifier(imageName, "drawable", getPackageName());

        ivArgazkia.setImageResource(resID);

        tvInfo = findViewById(R.id.tvInfo);

        tvInfo.setText(informazioa);

        String document = documentArray[id];

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Button infBtnAAA = findViewById(R.id.infBtnAAA);
        infBtnAAA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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