package com.example.gestioncuentabar;
import android.os.Bundle;
import android.view.View;
import android.graphics.Color;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText etCuenta;

    AutoCompleteTextView actvCamarero;

    CheckBox cbPropina;

    SeekBar sbPropina;

    TextView tvPorcentaje;
    TextView tvResultado;

    RadioGroup rgPago;

    RatingBar rbValoracion;

    Button btnCalcular;


    String[] camareros = {
            "Nadia",
            "Andrés",
            "María",
            "Cristina",
            "Abraham",
            "Sandra",
            "Alejo",
            "Laura"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etCuenta = findViewById(R.id.etCuenta);

        actvCamarero = findViewById(R.id.nCamarero);

        cbPropina = findViewById(R.id.cbPropina);

        sbPropina = findViewById(R.id.sbPropina);

        tvPorcentaje = findViewById(R.id.tvPorcentaje);
        tvResultado = findViewById(R.id.tvResultado);

        rgPago = findViewById(R.id.rgPago);

        rbValoracion = findViewById(R.id.rbValoracion);

        btnCalcular = findViewById(R.id.btnCalcular);



        ArrayAdapter<String> adaptador = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                camareros
        );

        actvCamarero.setAdapter(adaptador);

        actvCamarero.setThreshold(3);



        sbPropina.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress, boolean fromUser) {

                        tvPorcentaje.setText("Propina: " + progress + "%");

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }

                });



        btnCalcular.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                String camarero = nCamarero.getText().toString().trim();


                boolean camareroValido = false;

                for (int i = 0; i < camareros.length; i++) {

                    if (camareros[i].equalsIgnoreCase(camarero)) {

                        camareroValido = true;

                    }

                }

                if (!camareroValido) {

                    tvResultado.setTextColor(Color.RED);
                    tvResultado.setText("Selecciona un camarero de la lista");
                    return;

                }



                String textoCuenta = etCuenta.getText().toString().trim();



                if (textoCuenta.isEmpty()) {

                    tvResultado.setTextColor(Color.RED);
                    tvResultado.setText("Falta introducir el valor de la cuenta");
                    return;

                }



                double cuenta;

                try {

                    cuenta = Double.parseDouble(
                            textoCuenta.replace(',', '.')
                    );

                } catch (NumberFormatException e) {

                    tvResultado.setTextColor(Color.RED);
                    tvResultado.setText("Introduce un valor numérico válido");
                    return;

                }



                if (cuenta <= 0) {

                    tvResultado.setTextColor(Color.RED);
                    tvResultado.setText("La cuenta debe ser mayor que cero");
                    return;

                }



                double propina = 0;

                if (cbPropina.isChecked()) {

                    int porcentaje = sbPropina.getProgress();

                    propina = cuenta * porcentaje / 100;

                }



                double total = cuenta + propina;



                String metodoPago = "No seleccionado";

                int seleccionado = rgPago.getCheckedRadioButtonId();

                if (seleccionado == R.id.rbEfectivo) {

                    metodoPago = "Efectivo";

                } else if (seleccionado == R.id.rbTarjeta) {

                    metodoPago = "Tarjeta";

                }



                float valoracion = rbValoracion.getRating();



                tvResultado.setTextColor(Color.BLACK);



                tvResultado.setText(

                        "Camarero: " + camarero + "\n" +

                                "Cuenta: " +
                                String.format(Locale.getDefault(),
                                        "%.2f", cuenta) + " €\n" +

                                "Propina: " +
                                String.format(Locale.getDefault(),
                                        "%.2f", propina) + " €\n" +

                                "Total: " +
                                String.format(Locale.getDefault(),
                                        "%.2f", total) + " €\n" +

                                "Método de pago: " + metodoPago + "\n" +

                                "Valoración: " + valoracion + " estrellas"

                );

            }

        });

    }

}