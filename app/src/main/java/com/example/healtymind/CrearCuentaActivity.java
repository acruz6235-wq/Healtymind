package com.example.healtymind;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class CrearCuentaActivity extends AppCompatActivity {
    EditText EdtUsuario, EdtContrasenia, edtEmail, edtConfirmarPassword;
    Button btnRegistrar;
    TextView tvIniciarSesion;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        // ESTO FALTABA - conectar los campos
        EdtUsuario = findViewById(R.id.EdtUsuario);
        EdtContrasenia = findViewById(R.id.EdtContrasenia);
        edtEmail = findViewById(R.id.edtEmail);
        edtConfirmarPassword = findViewById(R.id.edtConfirmarPassword);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        tvIniciarSesion = findViewById(R.id.tvIniciarSesion);

        mAuth = FirebaseAuth.getInstance();

        btnRegistrar.setOnClickListener(v -> {
            registrarUsuario();
        });

        tvIniciarSesion.setOnClickListener(v -> {
            startActivity(new Intent(CrearCuentaActivity.this, Login.class));
        });
    }

    private void registrarUsuario() {
        String nombre = EdtUsuario.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = EdtContrasenia.getText().toString().trim();
        String confirmar = edtConfirmarPassword.getText().toString().trim();

        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty() || confirmar.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmar)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Cuenta creada exitosamente", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CrearCuentaActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}