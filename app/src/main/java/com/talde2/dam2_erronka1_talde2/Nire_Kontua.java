package com.talde2.dam2_erronka1_talde2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Nire_Kontua extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nire_kontua);

        //menua izkutatzeko lehenik
        LinearLayout opzioak = findViewById(R.id.idLayOpzioak);
        opzioak.setVisibility(View.INVISIBLE);

        //pasatutako datuak
        String izena = getIntent().getStringExtra("USER_izena");
        String abizena = getIntent().getStringExtra("USER_abizena");
        String nan = getIntent().getStringExtra("USER_nan");
        String email = getIntent().getStringExtra("USER_email");
        String mugikorra = getIntent().getStringExtra("USER_mugikorra");
        String erabiltzaileMota = getIntent().getStringExtra("USER_erabiltzaileMota");

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

        //MENUA
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
                opzioak.setVisibility(View.VISIBLE);
            }
        });


        //NIRE KONTUA
        Button btnNireKontua = findViewById(R.id.idBtnMenuKontua);
        btnNireKontua.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Nire_Kontua.this, Nire_Kontua.class);

                intent.putExtra("USER_izena", izena);
                intent.putExtra("USER_abizena", abizena);
                intent.putExtra("USER_nan", nan);
                intent.putExtra("USER_email", email);
                intent.putExtra("USER_mugikorra", mugikorra);
                intent.putExtra("USER_erabiltzaileMota", erabiltzaileMota);

                startActivity(intent);
            }
        });

        //RESERBAK
        Button btnErregistratu = findViewById(R.id.idBtnMenuErreserbak);
        btnErregistratu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Nire_Kontua.this, Egindako_Erreserbak.class);

                intent.putExtra("USER_izena", izena);
                intent.putExtra("USER_abizena", abizena);
                intent.putExtra("USER_nan", nan);
                intent.putExtra("USER_email", email);
                intent.putExtra("USER_mugikorra", mugikorra);
                intent.putExtra("USER_erabiltzaileMota", erabiltzaileMota);

                startActivity(intent);
            }
        });

        //PRIBATASUN POLITIKAK
        Button idBtnPribatutasunPolitikak = findViewById(R.id.idBtnPribatutasunPolitikak);
        idBtnPribatutasunPolitikak.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Nire_Kontua.this, PribatasunPolitikak.class);

                intent.putExtra("USER_izena", izena);
                intent.putExtra("USER_abizena", abizena);
                intent.putExtra("USER_nan", nan);
                intent.putExtra("USER_email", email);
                intent.putExtra("USER_mugikorra", mugikorra);
                intent.putExtra("USER_erabiltzaileMota", erabiltzaileMota);

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