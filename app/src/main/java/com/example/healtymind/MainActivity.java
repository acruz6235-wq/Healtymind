package com.example.healtymind;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    ImageButton btnCuenta;
    ImageButton btnEstadisticas;
    ImageButton btnActividad;
    ImageButton btnsettings;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCuenta = findViewById(R.id.btnCuenta);
        btnEstadisticas = findViewById(R.id.btnEstadisticas);
        btnActividad = findViewById(R.id.btnActividad);
        btnsettings = findViewById(R.id.btnsettings);
        textView = findViewById(R.id.textView);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String token = task.getResult();
                        Log.d("FCM Token", token);
                    } else {
                        Log.e("FCM Token", "Error: " + task.getException()); // ✅ ver el error
                    }
                });

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
            Intent intentsettings = new Intent(MainActivity.this, Racha_Activity.class);
            startActivity(intentsettings);
        });
        textView.setOnClickListener(v -> {
            Intent intentsettings = new Intent(MainActivity.this, NotificationActivity.class);
            startActivity(intentsettings);
        });

    }
}