package com.example.healtymind;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CrearCuentaActivity extends AppCompatActivity {
    EditText EdtUsuario;
    EditText EdtContrasenia;
    EditText edtEmail;
    EditText edtConfirmarPassword;
    Button btnRegistrar;
    TextView tvIniciarSesion;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        // Inicializar Firebase
        mAuth = FirebaseAuth.getInstance();

        // IMPORTANTE: Inicializar componentes
        EdtUsuario = findViewById(R.id.EdtUsuario);
        EdtContrasenia = findViewById(R.id.EdtContrasenia);
        edtEmail = findViewById(R.id.edtEmail);
        edtConfirmarPassword = findViewById(R.id.edtConfirmarPassword);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        tvIniciarSesion = findViewById(R.id.tvIniciarSesion);

        // Botón Registrar
        btnRegistrar.setOnClickListener(v -> {
            registrarUsuario();
        });

        // Link para volver al login
        tvIniciarSesion.setOnClickListener(v -> {
            Intent intent = new Intent(CrearCuentaActivity.this, Login.class);
            startActivity(intent);
            finish();
        });
    }

    private void registrarUsuario() {
        String nombre = EdtUsuario.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = EdtContrasenia.getText().toString().trim();
        String confirmPassword = edtConfirmarPassword.getText().toString().trim();

        // Validaciones
        if (nombre.isEmpty()) {
            EdtUsuario.setError("Ingresa tu nombre");
            EdtUsuario.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            edtEmail.setError("Ingresa tu email");
            edtEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            EdtContrasenia.setError("Ingresa una contraseña");
            EdtContrasenia.requestFocus();
            return;
        }

        if (password.length() < 6) {
            EdtContrasenia.setError("La contraseña debe tener al menos 6 caracteres");
            EdtContrasenia.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            edtConfirmarPassword.setError("Las contraseñas no coinciden");
            edtConfirmarPassword.requestFocus();
            return;
        }

        // Registrar en Firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(CrearCuentaActivity.this,
                                "Cuenta creada exitosamente", Toast.LENGTH_SHORT).show();

                        // Ir a MainActivity
                        Intent intent = new Intent(CrearCuentaActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(CrearCuentaActivity.this,
                                "Error: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}