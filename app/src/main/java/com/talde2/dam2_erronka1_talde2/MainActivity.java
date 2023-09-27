package com.talde2.dam2_erronka1_talde2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // db.collection("prueba").document("texto").set(mensaje);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Sortu intent bat login hasteko
                Intent intent = new Intent(MainActivity.this, login.class);
                startActivity(intent);
                finish(); // Honek MainActivity ixten du, erabiltzaileak Atzera botoia erabiliz bertara itzultzea nahi ez baduzu.
            }
        }, 2000); // zenbat denbora tardatzen duen aldatzen 1sg = 1000
    }
}