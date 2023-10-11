package com.talde2.dam2_erronka1_talde2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PribatasunPolitikak extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pribatasun_politikak);
        //menua izkutatzeko lehenik

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

        //Katalogoa
        Button BtnMenuKatalogoa = findViewById(R.id.idBtnMenuKatalogoa);
        BtnMenuKatalogoa.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PribatasunPolitikak.this, erreserbak.class);
                startActivity(intent);
            }
        });

        //Nire Kontua
        Button BtnMenuKontua = findViewById(R.id.idBtnMenuKontua);
        BtnMenuKontua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PribatasunPolitikak.this, Nire_Kontua.class);

                startActivity(intent);
            }
        });

        //Erreserbak
        Button BtnMenuNireErreserbak = findViewById(R.id.idBtnMenuNireErreserbak);
        BtnMenuNireErreserbak.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PribatasunPolitikak.this, Egindako_Erreserbak.class);
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

        LinearLayout aukerak = findViewById(R.id.idLayOpzioak);
        aukerak.setVisibility(View.INVISIBLE);
        TextView TerminosYCondiciones = (TextView)findViewById(R.id.tvPribatasunPolitikak);
        TerminosYCondiciones.setText(Html.fromHtml("<h1>Oma Aplikazioaren Pribatutasun Politika</h1>\n" +
                "\n" +
                "    <p><strong>Azken Eguneratze Data: 2023-10-28</strong></p>\n" +
                "\n" +
                "    <p>Oma-n, zure pribatutasuna balio eta errespetatzen dugu. Esta Pribatutasun Politika deskribatzen du nola biltzen, erabilten eta zaindu ditugun informazio pertsonala, gure aplikazio mugikorraren bidez emandakoak. Gure aplikazioa erabiliz, onartzen dituzu pribatutasun politikaren terminoak eta baldintzak.</p>\n" +
                "\n" +
                "    <h2>1. Biltzen dugun Informazioa</h2>\n" +
                "\n" +
                "    <h3>1.1. Erregistroko Informazioa</h3>\n" +
                "\n" +
                "    <p>Erregistratzean, gure aplikazioan informazio pertsonala biltzeko aukera izan dezakegu, zure izena, posta elektronikoko helbidea, telefono zenbakia eta beste harreman-informazio batzuk barne.</p>\n" +
                "\n" +
                "    <h3>1.2. Erosketa-Informazioa</h3>\n" +
                "\n" +
                "    <p>Erosketa egin nahi baduzu gure aplikazioan, erosketa prozesatzeko beharrezkoa den informazioa biltzen dugu, hala nola zure kreditu-txartelaren xehetasunak edo fakturazio-informazioa.</p>\n" +
                "\n" +
                "    <h3>1.3. Erabilera-Informazioa</h3>\n" +
                "\n" +
                "    <p>Aplikazioa nola erabilten duzun informazioa biltzen dugu, adibidez, egiten dituzun ekintzak eta erregistratzen diren ekitaldietakoak.</p>\n" +
                "\n" +
                "    <h2>2. Informazioaren Erabilera</h2>\n" +
                "\n" +
                "    <p>Biltzen dugun informazioa honako helburu hauei erabiltzen diogu:</p>\n" +
                "\n" +
                "    <ul>\n" +
                "        <li>Zure erreserbak eta sarrerak prozesatzeko eta kudeatzeko.</li>\n" +
                "        <li>Oma-ren ekitaldi eta zerbitzuak interesatzen dizkizun informazioa eskainitzeko.</li>\n" +
                "        <li>Zure aplikazio-esperientzia hobetzea eta pertsonalizatzea.</li>\n" +
                "        <li>Zure kontuarekin eta erregistratu zaren ekitaldiekin harremanetan jartzea.</li>\n" +
                "        <li>Oma-ren berriak, promozioak eta eguneratzeak bidaltzea, baimen emandakoan horiek jaso nahi badituzu.</li>\n" +
                "    </ul>\n" +
                "\n" +
                "    <h2>3. Informazioa Partekatzea</h2>\n" +
                "\n" +
                "    <p>Ez dugu zure informazio pertsonala hirugarren alderen batekin partekatuko baimenik jasotzen ez badugu, salbu eta ondoko egoeratan:</p>\n" +
                "\n" +
                "    <ul>\n" +
                "        <li>Zure informazioa Oma-ren izenean zerbitzuak eskaintzeko behar duten zerbitzu hornitzaileekin partekatuko dugu (adibidez, ordaintzeko prozesatzaileak).</li>\n" +
                "        <li>Legea, araudiak edo eskakizun legez beharrezkoa denean.</li>\n" +
                "    </ul>\n" +
                "\n" +
                "    <h2>4. Informazioaren Segurtasuna</h2>\n" +
                "\n" +
                "    <p>Toma zaitezke zure informazio pertsonala baimenik gabea, agerian edo aldaketa gabea, atzitzen. Hala ere, kontuan izan interneten edo biltegiratze elektronikoan datuak transmititzen ez direla guztiz seguruak.</p>\n" +
                "\n" +
                "    <h2>5. Acceso y Control de tu Información</h2>\n" +
                "\n" +
                "    <p>Zure informazio pertsonala aplikazioaren bidez oraindik ahalbidetzen dugu. Zure pribatutasun eskubideak erabiliz zure kontua ezabatu nahi baduzu edo oraindik ahalbidetzen dugu, jarri gurekin harremanetan oma@dam2-1erronka-2taldea-aee7d.firebaseapp.com helbidean.</p>\n" +
                "\n" +
                "    <h2>6. Cambios en esta Política de Privacidad</h2>\n" +
                "\n" +
                "    <p>Podemos actualizar esta Pribatutasun Politika edozein momentutan. Aldaketa garrantzitsuen berri emango dizugu politika honetan aplikatzen diren aplikazioaren edo posta elektronikoaren bidez.</p>\n" +
                "\n" +
                "    <h2>7. Contacto</h2>\n" +
                "\n" +
                "    <p>Galderarik edo kezka bat baduzu honi buruzko, harremanetan jar zaitez oma@dam2-1erronka-2taldea-aee7d.firebaseapp.com helbidean.</p>\n" +
                "\n" +
                "    <p>Gure aplikazioa erabiliz, pribatutasun politikaren terminoak eta baldintzak onartzen dituzu. Baldintza horiek onartzen ez badituzu, mesedez, ez erabili gure aplikazioa.</p>\n" +
                "\n" +
                "    <p>Ekitaldiak antolatzeko zure fidantza izateagatik eskertzen dizugu. Zure pribatutasuna zaindu eta esperientzia segurua eta pozgarria eskaintzeko konprometituta gaude.</p>"));

    }
}