package com.example.healtymind;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ImageButton btnCuenta;
    ImageButton btnEstadisticas;
    ImageButton btnActividad;
    ImageButton btnsettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCuenta = findViewById(R.id.btnCuenta);
        btnEstadisticas = findViewById(R.id.btnEstadisticas);
        btnActividad = findViewById(R.id.btnActividad);
        btnsettings = findViewById(R.id.btnsettings);

        btnCuenta.setOnClickListener(v -> {
            Intent intentperfil = new Intent(MainActivity.this, PerfilActivity.class);
            startActivity(intentperfil);
        });
        btnEstadisticas.setOnClickListener(v -> {
            Intent intentestadisticas = new Intent(MainActivity.this, EstadisticasActivity.class);
            startActivity(intentestadisticas);
        });
        btnActividad.setOnClickListener(v -> {
            ActividadDialog dialog = new ActividadDialog(MainActivity.this);
            dialog.show();
        });
        btnsettings.setOnClickListener(v -> {
            Intent intentsettings = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intentsettings);
        });

    }
}