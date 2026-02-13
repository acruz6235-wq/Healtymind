package com.example.healtymind;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ImageButton btnCuenta;
    ImageButton btnEstadisticas;
    ImageButton btnActividad;
    ImageButton btnsettings;
    Button moment;

    // Agregar GoogleFitManager
    private GoogleFitManager googleFitManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCuenta = findViewById(R.id.btnCuenta);
        btnEstadisticas = findViewById(R.id.btnEstadisticas);
        btnActividad = findViewById(R.id.btnActividad);
        btnsettings = findViewById(R.id.btnsettings);
        moment = findViewById(R.id.moment);

        // Inicializar GoogleFitManager
        googleFitManager = new GoogleFitManager(this);

        // Verificar y solicitar permisos de Google Fit
        verificarPermisos();

        btnCuenta.setOnClickListener(v -> {
            Intent intentperfil = new Intent(MainActivity.this, PerfilActivity.class);
            startActivity(intentperfil);
        });
        btnEstadisticas.setOnClickListener(v -> {
            Intent intentestadisticas = new Intent(MainActivity.this, EstadisticasActivity.class);
            startActivity(intentestadisticas);
        });
        btnActividad.setOnClickListener(v -> {
            Intent intentactividad = new Intent(MainActivity.this, ActividadActivity.class);
            startActivity(intentactividad);
        });
        btnsettings.setOnClickListener(v -> {
            Intent intentsettings = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intentsettings);
        });
        moment.setOnClickListener(v -> {
            Intent intentmoment = new Intent(MainActivity.this, Login.class);
            startActivity(intentmoment);
        });
    }

    private void verificarPermisos() {
        if (!googleFitManager.tienePermisos()) {
            // No tiene permisos, solicitarlos
            googleFitManager.solicitarPermisos(this);
        } else {
            // Ya tiene permisos, obtener datos
            obtenerDatosGoogleFit();
        }
    }

    private void obtenerDatosGoogleFit() {
        // Obtener pasos del día
        googleFitManager.obtenerPasosDeHoy(new GoogleFitManager.OnDatosObtenidos() {
            @Override
            public void onExito(int pasos) {
                Toast.makeText(MainActivity.this,
                        "Pasos de hoy: " + pasos,
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String mensaje) {
                Toast.makeText(MainActivity.this,
                        "Error: " + mensaje,
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Obtener distancia del día
        googleFitManager.obtenerDistanciaDeHoy(new GoogleFitManager.OnDatosObtenidos() {
            @Override
            public void onExito(int distanciaKm) {
                Toast.makeText(MainActivity.this,
                        "Distancia: " + distanciaKm + " km",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String mensaje) {
                // Manejar error
            }
        });

        // Obtener calorías del día
        googleFitManager.obtenerCaloriasDeHoy(new GoogleFitManager.OnDatosObtenidos() {
            @Override
            public void onExito(int calorias) {
                Toast.makeText(MainActivity.this,
                        "Calorías: " + calorias,
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String mensaje) {
                // Manejar error
            }
        });
    }

    // Manejar respuesta cuando el usuario autoriza/rechaza los permisos
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GoogleFitManager.getRequestCode()) {
            if (resultCode == RESULT_OK) {
                // Usuario autorizó, obtener datos
                obtenerDatosGoogleFit();
            } else {
                Toast.makeText(this,
                        "Necesitas autorizar Google Fit para usar esta función",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
