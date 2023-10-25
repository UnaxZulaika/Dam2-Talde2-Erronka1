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
    }
}