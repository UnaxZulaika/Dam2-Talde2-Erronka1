package com.talde2.dam2_erronka1_talde2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class erreserbaOrdainketa extends AppCompatActivity {

    private EditText editTextFecha;
    private RadioGroup radioGroup;
    private RadioButton rbKredituTxartela;
    private RadioButton rbSukurtsala;
    private Button regBtnErregistratu;
    private TextView txvKredituTxartela;
    private TextView kredituTxartela;
    private EditText kredituTxartelData;
    private EditText kredituTxartelSegurtasuna;
    private Spinner spinnerPrezioak;
    private TextView txvAzkenPrezioa;
    private double azkenPrezioa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erreserba_ordainketa);

        String kokalekua = getIntent().getStringExtra("kokalekua");
        double prezioa = getIntent().getDoubleExtra("prezioa", 0);
        double prezioa10 = getIntent().getDoubleExtra("prezioa10", 0);
        double prezioa20 = getIntent().getDoubleExtra("prezioa20", 0);
        String imageName = getIntent().getStringExtra("img");

        //LEHIOAK PASATZERA
        //datos a pasar a todas las pantallas del menu (para nire kontua)
        String izena = getIntent().getStringExtra("USER_izena");
        String abizena = getIntent().getStringExtra("USER_abizena");
        String nan = getIntent().getStringExtra("USER_nan");
        String email = getIntent().getStringExtra("USER_email");
        String mugikorra = getIntent().getStringExtra("USER_mugikorra");
        String erabiltzaileMota = getIntent().getStringExtra("USER_erabiltzaileMota");


        editTextFecha = findViewById(R.id.editTextFecha);
        radioGroup = findViewById(R.id.radioGroup);
        rbKredituTxartela = findViewById(R.id.RBkredituTxartela);
        rbSukurtsala = findViewById(R.id.RBsukurtsala);
        regBtnErregistratu = findViewById(R.id.regBtnErreserbaBukatu);
        txvKredituTxartela = findViewById(R.id.txvKredituTxartela);
        kredituTxartela = findViewById(R.id.kredituTxartela);
        kredituTxartelData = findViewById(R.id.kredituTxartelData);
        kredituTxartelSegurtasuna = findViewById(R.id.kredituTxartelSegurtasun);
        spinnerPrezioak = findViewById(R.id.spinnerPrezioak);
        txvAzkenPrezioa = findViewById(R.id.txvAzkenPrezioa);

        txvKredituTxartela.setVisibility(View.INVISIBLE);
        kredituTxartela.setVisibility(View.INVISIBLE);
        kredituTxartelData.setVisibility(View.INVISIBLE);
        kredituTxartelSegurtasuna.setVisibility(View.INVISIBLE);

        ArrayList<String> aukerak = new ArrayList<>();
        if (prezioa >= 1) {
            aukerak.add("Prezioa (Pertsona kopurua: 1): " + prezioa + "€");
        }
        if (prezioa10 >= 1) {
            aukerak.add("Prezioa (Pertsona kopurua: 10): " + prezioa10 + "€");
        }
        if (prezioa20 >= 1) {
            aukerak.add("Prezioa (Pertsona kopurua: 20): " + prezioa20 + "€");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, aukerak);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrezioak.setAdapter(adapter);

        spinnerPrezioak.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Obtén la opción seleccionada
                String opcionSeleccionada = (String) spinnerPrezioak.getSelectedItem();

                // Actualiza el TextView en función de la opción seleccionada
                if (opcionSeleccionada.contains("Prezioa (Pertsona kopurua: 1): " + prezioa + "€")) {
                    txvAzkenPrezioa.setText("Prezioa: " + prezioa + "€");
                    azkenPrezioa = prezioa;
                } else if (opcionSeleccionada.contains("Prezioa (Pertsona kopurua: 10): " + prezioa10 + "€")) {
                    txvAzkenPrezioa.setText("Prezioa: " + prezioa10 + "€");
                    azkenPrezioa = prezioa10;
                } else if (opcionSeleccionada.contains("Prezioa (Pertsona kopurua: 20): " + prezioa20 + "€")) {
                    txvAzkenPrezioa.setText("Prezioa: " + prezioa20 + "€");
                    azkenPrezioa = prezioa20;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        kredituTxartelData.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            // 2 karaketereko 'lenght'a dagoenean automatikoki '/' ipiniko du.
            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                // Egiaztatu lehenengo 2 zenbakiak sartu diren eta ez dagoen '/'
                if (input.length() == 2 && !input.contains("/")) {
                    // Gehitu automatikoki '/' karakterea lehenengo 2 zenbakien ondoren
                    kredituTxartelData.setText(input + "/");
                    kredituTxartelData.setSelection(kredituTxartelData.getText().length());
                }
            }
        });

        // Listener DatePickerrean click egiten denerako
        editTextFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                erakutsiEgutegia();
            }
        });

        // Bisibilitatea kontrolatzeko listenerra
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Kreditu txartelaren aukera badago aukeratuta, hurrengo kanpo desbedinak erakutsiko dira
                if (checkedId == R.id.RBkredituTxartela) {
                    // Kreditu txartelaren aukera aukeratzean, editText eta TextView-a erakusten du
                    txvKredituTxartela.setVisibility(View.VISIBLE);
                    kredituTxartela.setVisibility(View.VISIBLE);
                    kredituTxartelData.setVisibility(View.VISIBLE);
                    kredituTxartelSegurtasuna.setVisibility(View.VISIBLE);
                    // Sukurtsala aukera badago aukeratuta, hurrengo kanpo desbedinak ez dira erakutsiko
                } else if (checkedId == R.id.RBsukurtsala) {
                    txvKredituTxartela.setVisibility(View.INVISIBLE);
                    kredituTxartela.setVisibility(View.INVISIBLE);
                    kredituTxartelData.setVisibility(View.INVISIBLE);
                    kredituTxartelSegurtasuna.setVisibility(View.INVISIBLE);
                }
            }
        });

        regBtnErregistratu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aukeratutako data konprobatu
                String aukeratutakoData = editTextFecha.getText().toString();
                if (!TextUtils.isEmpty(aukeratutakoData)) {
                    // RadioButton aukeratuta dagoen konprobatu
                    if (radioGroup.getCheckedRadioButtonId() == 0) {

                    } else if (rbKredituTxartela.isChecked()) {
                            String txartelZenabkia = kredituTxartela.getText().toString();
                            String iraungintzaData = kredituTxartelData.getText().toString();
                            String ccv = kredituTxartelSegurtasuna.getText().toString();

                        String erreserbaData = editTextFecha.getText().toString();

                            // Kreditu txartelaren egiaztapenak
                            if (txartelZenbakiKonprobaketa(txartelZenabkia)) {
                                if (iraungitzeDataKonprobaketa(iraungintzaData)) {
                                    if (esCCVValido(ccv)) {
                                        Intent intent = new Intent(erreserbaOrdainketa.this, erreserbaBukatuta.class);
                                        System.out.println("PREZIOA: " + azkenPrezioa);
                                        System.out.println("KOKALEKUA: " + kokalekua);
                                        System.out.println("ERRESERBA DATA: " + erreserbaData);
                                        intent.putExtra("prezioa", azkenPrezioa);
                                        intent.putExtra("kokalekua", kokalekua);
                                        intent.putExtra("erreserbaData", erreserbaData);
                                        intent.putExtra("img", imageName);
                                        variables_de_usuario(intent, izena, abizena, nan, email, mugikorra, erabiltzaileMota);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Kreditu-txartelaren CCV baliogabea", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Kreditu-txartelaren iraungitze-data baliogabea", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Kreditu-txartelaren zenbakia ez da baliozkoa", Toast.LENGTH_SHORT).show();
                            }
                        } else if (rbSukurtsala.isChecked()) {
                        Intent intent = new Intent(erreserbaOrdainketa.this, erreserbaBukatuta.class);
                        String erreserbaData = editTextFecha.getText().toString();
                        System.out.println("PREZIOA: " + azkenPrezioa);
                        System.out.println("KOKALEKUA: " + kokalekua);
                        System.out.println("ERRESERBA DATA: " + erreserbaData);
                        intent.putExtra("prezioa", azkenPrezioa);
                        intent.putExtra("kokalekua", kokalekua);
                        intent.putExtra("erreserbaData", erreserbaData);
                        intent.putExtra("img", imageName);
                        variables_de_usuario(intent, izena, abizena, nan, email, mugikorra, erabiltzaileMota);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Aukeratu ordainketa-metodo bat", Toast.LENGTH_SHORT).show();
                        return; // Salir del método si no se seleccionó un RadioButton
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Aukeratu data", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }

    private boolean txartelZenbakiKonprobaketa(String txartelZenbakia) {
        // Kendu zuriuneak eta gidoiak txartel-zenbakitik
        txartelZenbakia = txartelZenbakia.replaceAll("\\s", "").replaceAll("-", "");

        // Ohiko esamoldea erabiltzen du txartel-zenbakiaren formatua egiaztatzeko
        String regex = "^(\\d{13,19})$";
        if (!txartelZenbakia.matches(regex)) {
            return false;
        }

        return true;
    }

    private boolean iraungitzeDataKonprobaketa(String iraungitzeData) {
        // Dataren luzera 5 karaktere direla egiaztatu
        if (iraungitzeData.length() != 5) {
            return false;
        }

        // Atera hilabetea, barra diagonala eta urtea
        String hilabeteStr = iraungitzeData.substring(0, 2);
        String barraDiagonal = iraungitzeData.substring(2, 3);
        String urteaStr = iraungitzeData.substring(3);

        try {
            int hilabetea = Integer.parseInt(hilabeteStr);
            int urtea = Integer.parseInt(urteaStr);

            // Hilabetea 1etik 12ra bitarteko tartean dagoela egiaztatzea
            if (hilabetea < 1 || hilabetea > 12) {
                return false;
            }
            // Urtea 1etik 31ra bitarteko tartean dagoela egiaztatzea
            if (urtea < 1 || urtea > 31) {
                return false;
            }
            // Posizio egokian barra diagonal bat dagoela egiaztatzea
            if (!barraDiagonal.equals("/")) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    private boolean esCCVValido(String ccv) {
        // Zuriuneak eta zenbakizkoak ez diren karaktereak ezabatzen ditu
        ccv = ccv.replaceAll("\\s", "").replaceAll("[^0-9]", "");

        // Egiaztatu CCV 3 edo 4 zenbaki-digitu dituen
        if (ccv.length() == 3 || ccv.length() == 4) {
            return true;
        } else {
            return false;
        }
    }

    private void erakutsiEgutegia() {
        final Calendar calendario = Calendar.getInstance();
        int urtea = calendario.get(Calendar.YEAR);
        int hilabetea = calendario.get(Calendar.MONTH);
        int eguna = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Aukeratutako data editTextean agertzen da
                String aukeratutakoData =  dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;

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

    private boolean dataBaliozkatu(String aukeratuData) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date dataGaur = Calendar.getInstance().getTime();
        try {
            Date aukeratutakoData = sdf.parse(aukeratuData);
            return aukeratutakoData.before(dataGaur);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
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
