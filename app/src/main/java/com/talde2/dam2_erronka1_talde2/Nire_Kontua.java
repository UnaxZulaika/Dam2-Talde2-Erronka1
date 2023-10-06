package com.talde2.dam2_erronka1_talde2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Nire_Kontua extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nire_kontua);

        //desabilitatu testua zartzea edit textean

            //izena
            EditText nrkntEditIzena = findViewById(R.id.nrkntEditIzena);
            nrkntEditIzena.setEnabled(false); nrkntEditIzena.setInputType(0);

            //nan
            EditText nrkntEditNan = findViewById(R.id.nrkntEditNan);
            nrkntEditNan.setEnabled(false); nrkntEditNan.setInputType(0);

            //abizenak
            EditText nrkntEditAbizenak = findViewById(R.id.nrkntEditAbizenak);
            nrkntEditAbizenak.setEnabled(false); nrkntEditAbizenak.setInputType(0);

            //posta
            EditText nrkntEditPosta = findViewById(R.id.nrkntEditPosta);
            nrkntEditPosta.setEnabled(false); nrkntEditPosta.setInputType(0);

            //mugikorra
            EditText nrkntEditMugikorra = findViewById(R.id.nrkntEditMugikorra);
            nrkntEditMugikorra.setEnabled(false); nrkntEditMugikorra.setInputType(0);

            //pasahitza1
            EditText nrkntEditPasahitza = findViewById(R.id.nrkntEditPasahitza);
            nrkntEditPasahitza.setEnabled(false); nrkntEditPasahitza.setInputType(0);

            //Pasahitza2
            EditText nrkntEditPasahitza2 = findViewById(R.id.nrkntEditPasahitza2);
            nrkntEditPasahitza2.setEnabled(false); nrkntEditPasahitza2.setInputType(0);

            //Erabiltzaile mota
            EditText nrkntEditErbltzMta = findViewById(R.id.nrkntEditErbltzMta);
            nrkntEditErbltzMta.setEnabled(false); nrkntEditErbltzMta.setInputType(0);

        //fin desabilitatu dit text idaztea

        //menua izkutatzeko lehenik
        LinearLayout opzioak = findViewById(R.id.idLayOpzioak);
        opzioak.setVisibility(View.INVISIBLE);

        //MENUA
        Button btnMenuItx = findViewById(R.id.idBtnMenuItxi);
        Button btnMenuZabaldu = findViewById(R.id.IdBtnMenuZabaldu);
        LinearLayout Opzioak = findViewById(R.id.idLayOpzioak);

        //open
        btnMenuZabaldu.setOnClickListener(new View.OnClickListener(){
              @Override
              public void onClick(View v) {
                  LinearLayout opzioak = findViewById(R.id.idLayOpzioak);
                  opzioak.setVisibility(View.VISIBLE);
              }
          }

        );

        //close
        btnMenuItx.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout Opzioak = findViewById(R.id.idLayOpzioak);
                Opzioak.setVisibility(View.INVISIBLE);
            }
        });


        //LEHIOAK PASATZERA

        //NIRE KONTUA
        Button btnNireKontua = findViewById(R.id.idBtnMenuKontua);
        btnNireKontua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Nire_Kontua.this, Nire_Kontua.class);
                startActivity(intent1);
            }
        });

        //RESERBAK
        Button btnErregistratu = findViewById(R.id.idBtnMenuErreserbak);
        btnErregistratu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Nire_Kontua.this, Egindako_Erreserbak.class);
                startActivity(intent2);
            }
        });
    }
}