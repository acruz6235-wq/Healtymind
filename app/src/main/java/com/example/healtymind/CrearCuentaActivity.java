package com.example.healtymind;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.healtymind.R;

public class CrearCuentaActivity extends AppCompatActivity {
    EditText EdtUsuario;
    EditText EdtContrasenia;
    EditText edtEmail;
    EditText edtConfirmarPassword;
    Button btnRegistrar;
    TextView tvIniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);


        btnRegistrar.setOnClickListener(v -> {
            Intent iniciar = new Intent(CrearCuentaActivity.this, MainActivity.class);
            startActivity(iniciar);
        });
        tvIniciarSesion.setOnClickListener(v -> {
            Intent intentperfil = new Intent(CrearCuentaActivity.this, Login.class);
            startActivity(intentperfil);
        });
    }
}