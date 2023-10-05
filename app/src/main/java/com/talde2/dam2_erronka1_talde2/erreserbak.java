package com.talde2.dam2_erronka1_talde2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class erreserbak extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erreserbak);
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
                Intent intent1 = new Intent(erreserbak.this, Nire_Kontua.class);
                startActivity(intent1);
            }
        });

        //RESERBAK
        Button btnErregistratu = findViewById(R.id.idBtnMenuErreserbak);
        btnErregistratu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(erreserbak.this, Egindako_Erreserbak.class);
                startActivity(intent2);
            }
        });
    }

}