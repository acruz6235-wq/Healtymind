package com.example.healtymind;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText EdtContrasenia;
    private EditText EdtUsuario;
    private Button btningresar;
    private TextView CrarCuenta;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar Firebase
        mAuth = FirebaseAuth.getInstance();

        // Referenciar componentes
        EdtContrasenia = findViewById(R.id.EdtContrasenia);
        EdtUsuario = findViewById(R.id.EdtUsuario);
        btningresar = findViewById(R.id.btningresar);
        CrarCuenta = findViewById(R.id.CrarCuenta);

        // Ir a Crear Cuenta
        CrarCuenta.setOnClickListener(v -> {
            Intent intentperfil = new Intent(Login.this, CrearCuentaActivity.class);
            startActivity(intentperfil);
        });

        // Botón Ingresar
        btningresar.setOnClickListener(v -> {
            loginUsuario();
        });
    }

    private void loginUsuario() {
        String email = EdtUsuario.getText().toString().trim();
        String password = EdtContrasenia.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(Login.this, "Bienvenido " + (user != null ? user.getEmail() : ""), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Login.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }
    }
}