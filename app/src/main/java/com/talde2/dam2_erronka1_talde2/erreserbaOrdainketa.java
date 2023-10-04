package com.talde2.dam2_erronka1_talde2;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class erreserbaOrdainketa extends AppCompatActivity {

    private EditText editTextFecha;
    private RadioGroup radioGroup;
    private RadioButton rbKredituTxartela;
    private RadioButton rbSukurtsala;
    private TextView txvKredituTxartela;
    private TextView kredituTxartela;
    private EditText kredituTxartelData;
    private EditText kredituTxartelSegurtasuna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erreserba_ordainketa);

        editTextFecha = findViewById(R.id.editTextFecha);
        radioGroup = findViewById(R.id.radioGroup);
        rbKredituTxartela = findViewById(R.id.RBkredituTxartela);
        rbSukurtsala = findViewById(R.id.RBsukurtsala);
        txvKredituTxartela = findViewById(R.id.txvKredituTxartela);
        kredituTxartela = findViewById(R.id.kredituTxartela);
        kredituTxartelData = findViewById(R.id.kredituTxartelData);
        kredituTxartelSegurtasuna = findViewById(R.id.kredituTxartelSegurtasun);

        txvKredituTxartela.setVisibility(View.INVISIBLE);
        kredituTxartela.setVisibility(View.INVISIBLE);
        kredituTxartelData.setVisibility(View.INVISIBLE);
        kredituTxartelSegurtasuna.setVisibility(View.INVISIBLE);

        // Listener DatePickerrean click egiten denerako
        editTextFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarCalendario();
            }
        });

        // Bisibilitatea kontrolatzeko listenerra
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.RBkredituTxartela) {
                    // Kreditu txartelaren aukera aukeratzean, editText eta TextView-a erakusten du
                    txvKredituTxartela.setVisibility(View.VISIBLE);
                    kredituTxartela.setVisibility(View.VISIBLE);
                    kredituTxartelData.setVisibility(View.VISIBLE);
                    kredituTxartelSegurtasuna.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.RBsukurtsala) {
                    // Kreditu txartelaren aukera aukeratzean, editText eta TextView-a eskutatzen du
                    txvKredituTxartela.setVisibility(View.INVISIBLE);
                    kredituTxartela.setVisibility(View.INVISIBLE);
                    kredituTxartelData.setVisibility(View.INVISIBLE);
                    kredituTxartelSegurtasuna.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void mostrarCalendario() {
        final Calendar calendario = Calendar.getInstance();
        int urtea = calendario.get(Calendar.YEAR);
        int hilabetea = calendario.get(Calendar.MONTH);
        int eguna = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Aukeratutako data editTextean agertzen da
                String aukeratutakoData = "0" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;

                // Data baliozko egun batean dagoen konproabtzen du.
                if (dataBaliozkatu(aukeratutakoData)) {
                    // Errore mezua
                    Toast.makeText(getApplicationContext(), "Aukeratu den data ez da baliozkoa.", Toast.LENGTH_SHORT).show();
                    editTextFecha.setText(""); // EditText garbitu.
                } else {
                    editTextFecha.setText(aukeratutakoData);
                }
            }
        }, urtea, hilabetea, eguna);

        datePickerDialog.show();
    }

    private boolean dataBaliozkatu(String fechaSeleccionada) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date dataGaur = Calendar.getInstance().getTime();
        try {
            Date aukeratutakoData = sdf.parse(fechaSeleccionada);
            return aukeratutakoData.before(dataGaur);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}
