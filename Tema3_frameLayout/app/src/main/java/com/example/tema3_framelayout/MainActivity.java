package com.example.tema3_framelayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Pantalla
    TextView pantalla;

    // Botones numéricos
    Button boton0, boton1, boton2, boton3, boton4;
    Button boton5, boton6, boton7, boton8, boton9;

    // Botones de operaciones
    Button botonSuma, botonMenos, botonMultiplicar;
    Button botonDividir, botonIgual, botonPunto;

    // Botones para borrar
    Button botonC, botonAC;

    // Variables para los cálculos
    double numero1, numero2, resultado;
    String operacion = "";
    String numeroActual = "0";
    boolean nuevoNumero = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ejerciciogrid);

        // Pantalla
        pantalla = findViewById(R.id.pantalla);

        // Botones numéricos
        boton0 = findViewById(R.id.boton0);
        boton1 = findViewById(R.id.boton1);
        boton2 = findViewById(R.id.boton2);
        boton3 = findViewById(R.id.boton3);
        boton4 = findViewById(R.id.boton4);
        boton5 = findViewById(R.id.boton5);
        boton6 = findViewById(R.id.boton6);
        boton7 = findViewById(R.id.boton7);
        boton8 = findViewById(R.id.boton8);
        boton9 = findViewById(R.id.boton9);

        // Botones de operaciones
        botonSuma = findViewById(R.id.botonSuma);
        botonMenos = findViewById(R.id.botonMenos);
        botonMultiplicar = findViewById(R.id.botonMultiplicar);
        botonDividir = findViewById(R.id.botonDividir);
        botonIgual = findViewById(R.id.botonIgual);
        botonPunto = findViewById(R.id.botonPunto);

        // Botones C y AC
        botonC = findViewById(R.id.botonC);
        botonAC = findViewById(R.id.botonAC);

        // Eventos de los números
        boton0.setOnClickListener(v -> pulsarNumero("0"));
        boton1.setOnClickListener(v -> pulsarNumero("1"));
        boton2.setOnClickListener(v -> pulsarNumero("2"));
        boton3.setOnClickListener(v -> pulsarNumero("3"));
        boton4.setOnClickListener(v -> pulsarNumero("4"));
        boton5.setOnClickListener(v -> pulsarNumero("5"));
        boton6.setOnClickListener(v -> pulsarNumero("6"));
        boton7.setOnClickListener(v -> pulsarNumero("7"));
        boton8.setOnClickListener(v -> pulsarNumero("8"));
        boton9.setOnClickListener(v -> pulsarNumero("9"));

        // Decimal
        botonPunto.setOnClickListener(v -> pulsarNumero("."));

        // Operaciones
        botonSuma.setOnClickListener(v -> pulsarOperacion("+"));
        botonMenos.setOnClickListener(v -> pulsarOperacion("-"));
        botonMultiplicar.setOnClickListener(v -> pulsarOperacion("*"));
        botonDividir.setOnClickListener(v -> pulsarOperacion("/"));

        // Igual
        botonIgual.setOnClickListener(v -> calcular());

        // Borrar último carácter
        botonC.setOnClickListener(v -> borrarUltimo());

        // Borrar todo
        botonAC.setOnClickListener(v -> borrarTodo());
    }

    // Introduce un número en la pantalla
    public void pulsarNumero(String numero) {

        if (nuevoNumero) {
            if (numero.equals(".")) {
                numeroActual = "0.";
            } else {
                numeroActual = numero;
            }

            nuevoNumero = false;

        } else {

            // No permitir dos puntos decimales
            if (numero.equals(".") && numeroActual.contains(".")) {
                return;
            }

            // Evitar ceros al principio
            if (!numero.equals(".") && numeroActual.equals("0")) {
                numeroActual = numero;
            } else {
                numeroActual += numero;
            }
        }

        pantalla.setText(numeroActual);
    }

    // Guarda el primer número y la operación
    public void pulsarOperacion(String op) {

        if (!nuevoNumero) {
            numero1 = Double.parseDouble(numeroActual);
        }

        operacion = op;
        nuevoNumero = true;

        pantalla.setText(numeroActual + " " + operacion);
    }

    // Calcula el resultado
    public void calcular() {

        if (operacion.isEmpty() || nuevoNumero) {
            return;
        }

        numero2 = Double.parseDouble(numeroActual);

        switch (operacion) {

            case "+":
                resultado = numero1 + numero2;
                break;

            case "-":
                resultado = numero1 - numero2;
                break;

            case "*":
                resultado = numero1 * numero2;
                break;

            case "/":
                if (numero2 == 0) {
                    pantalla.setText("Error");
                    numeroActual = "0";
                    operacion = "";
                    nuevoNumero = true;
                    return;
                }

                resultado = numero1 / numero2;
                break;
        }

        // Mostrar el resultado sin decimales innecesarios
        if (resultado == (long) resultado) {
            numeroActual = String.valueOf((long) resultado);
        } else {
            numeroActual = String.valueOf(resultado);
        }

        pantalla.setText(numeroActual);

        operacion = "";
        nuevoNumero = true;
    }

    // Borra el último carácter
    public void borrarUltimo() {

        if (nuevoNumero) {
            return;
        }

        if (numeroActual.length() > 1) {
            numeroActual = numeroActual.substring(
                    0, numeroActual.length() - 1
            );
        } else {
            numeroActual = "0";
            nuevoNumero = true;
        }

        // Si queda solo un signo decimal, restaurar el cero
        if (numeroActual.isEmpty()) {
            numeroActual = "0";
            nuevoNumero = true;
        }

        pantalla.setText(numeroActual);
    }

    // Borra toda la calculadora
    public void borrarTodo() {

        numero1 = 0;
        numero2 = 0;
        resultado = 0;

        operacion = "";
        numeroActual = "0";
        nuevoNumero = true;

        pantalla.setText("0");
    }
}