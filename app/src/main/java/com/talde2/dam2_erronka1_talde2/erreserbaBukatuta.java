package com.talde2.dam2_erronka1_talde2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class erreserbaBukatuta extends AppCompatActivity {

    private Button btnNireErreserbak;
    private TextView idtxtErreserbaEskerrak;
    private TextView idtxtErreserbaInfo;
    private TextView idtxtErreserbaOndo;
    private ImageView erreserbaBukatutaImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erreserba_bukatuta);

        String kokalekua = getIntent().getStringExtra("kokalekua");
        double prezioa = getIntent().getDoubleExtra("prezioa", 0);
        String erreserbaData = getIntent().getStringExtra("erreserbaData");
        String imageName = getIntent().getStringExtra("img");

        //LEHIOAK PASATZERA
        //datos a pasar a todas las pantallas del menu (para nire kontua)
        String izena = getIntent().getStringExtra("USER_izena");
        String abizena = getIntent().getStringExtra("USER_abizena");
        String nan = getIntent().getStringExtra("USER_nan");
        String email = getIntent().getStringExtra("USER_email");
        String mugikorra = getIntent().getStringExtra("USER_mugikorra");
        String erabiltzaileMota = getIntent().getStringExtra("USER_erabiltzaileMota");

        idtxtErreserbaEskerrak = findViewById(R.id.idtxtErreserbaEskerrak);
        idtxtErreserbaInfo = findViewById(R.id.idtxtErreserbaInfo);
        idtxtErreserbaOndo = findViewById(R.id.idtxtErreserbaOndo);

        erreserbaBukatutaImg = findViewById(R.id.erreserbaBukatutaImg);

        int resID = getResources().getIdentifier(imageName, "drawable", getPackageName());
        erreserbaBukatutaImg.setImageResource(resID);

        String txtEskerrak = "Zure erreserba arrakastatsua izan da! Eskerrik asko gugan konfiatzeagatik.\nHemen daukazu erreserbaren informazioa:";
        String info = "Kokalekua: " + kokalekua + "\n";
        info += "Prezioa: " + prezioa + "€\n";
        info += "Erreserba data: " + erreserbaData + "\n\n";
        String ondo = "Zure eskaera ondo gorde da.";

        idtxtErreserbaEskerrak.setText(txtEskerrak);
        idtxtErreserbaInfo.setText(info);
        idtxtErreserbaOndo.setText(ondo);



        btnNireErreserbak = findViewById(R.id.btnNireErreserbak);
        btnNireErreserbak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Asegúrate de que el usuario esté autenticado y obtén su correo electrónico.
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    String userEmail = currentUser.getEmail();

                    // Genera un ID aleatorio para el documento.
                    String documentId = UUID.randomUUID().toString(); // Puedes utilizar la clase UUID para generar un ID aleatorio.

                    // Crea un mapa con los datos que deseas almacenar en el documento.
                    Map<String, Object> erreserbaInfo = new HashMap<>();
                    erreserbaInfo.put("userEmail", userEmail);
                    erreserbaInfo.put("kokalekua", kokalekua);
                    erreserbaInfo.put("prezioa", prezioa);
                    erreserbaInfo.put("erreserbaData", erreserbaData);

                    // Accede a la instancia de Firebase Firestore y agrega el documento a la colección "Erreserbak".
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("Erreserbak")
                            .document(documentId) // Utiliza el ID aleatorio para el documento
                            .set(erreserbaInfo)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // La operación fue exitosa.
                                    // Puedes mostrar un mensaje al usuario si lo deseas.
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    e.printStackTrace();
                                    // Agrega un registro o notificación para identificar el error.
                                }
                            });
                    Intent intent = new Intent(erreserbaBukatuta.this, erreserbak.class);
                    variables_de_usuario(intent, izena, abizena, nan, email, mugikorra, erabiltzaileMota);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
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