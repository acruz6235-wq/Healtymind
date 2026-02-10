package com.example.healtymind;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    private Button Cerrasecion;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAuth = FirebaseAuth.getInstance();

        Cerrasecion = findViewById(R.id.Cerrasecion);

        Cerrasecion.setOnClickListener(v -> {
            cerrarSesion();
        });
    }

    private void cerrarSesion() {
        mAuth.signOut();

        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();

        // Ir a Login y limpiar el stack de activities
        Intent intent = new Intent(SettingsActivity.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}