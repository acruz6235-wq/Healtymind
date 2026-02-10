package com.example.healtymind;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class EstadisticasActivity extends AppCompatActivity {

    private BarChart barChart;
    private DatabaseReference databaseRef;
    private FirebaseAuth mAuth;
    private TextInputEditText edtDistancia;
    private Button btnAgregar;
    private Button btnResetear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            Toast.makeText(this, "Debes iniciar sesión primero", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EstadisticasActivity.this, Login.class);
            startActivity(intent);
            finish();
            return;
        }

        barChart = findViewById(R.id.barChart);
        edtDistancia = findViewById(R.id.edtDistancia);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnResetear = findViewById(R.id.btnResetear);

        String userId = currentUser.getUid();
        databaseRef = FirebaseDatabase.getInstance()
                .getReference("usuarios")
                .child(userId)
                .child("recorridos");

        btnAgregar.setOnClickListener(v -> agregarRecorrido());
        btnResetear.setOnClickListener(v -> confirmarReseteo());

        cargarDatosEnTiempoReal();
    }

    private void agregarRecorrido() {
        String distanciaStr = edtDistancia.getText().toString().trim();

        if (distanciaStr.isEmpty()) {
            Toast.makeText(this, "Ingresa una distancia", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            float distancia = Float.parseFloat(distanciaStr);

            if (distancia <= 0) {
                Toast.makeText(this, "La distancia debe ser mayor a 0", Toast.LENGTH_SHORT).show();
                return;
            }

            String fechaActual = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
            int pasos = (int) (distancia * 1300);
            float calorias = distancia * 60;

            Recorrido nuevoRecorrido = new Recorrido(fechaActual, distancia, pasos, calorias);
            String recorridoId = databaseRef.push().getKey();

            if (recorridoId != null) {
                databaseRef.child(recorridoId).setValue(nuevoRecorrido)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(EstadisticasActivity.this, "¡Recorrido agregado!", Toast.LENGTH_SHORT).show();
                            edtDistancia.setText("");
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(EstadisticasActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        });
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Ingresa un número válido", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmarReseteo() {
        new AlertDialog.Builder(this)
                .setTitle("Resetear todos los datos")
                .setMessage("¿Estás seguro de que quieres eliminar todos los recorridos?")
                .setPositiveButton("Sí, eliminar", (dialog, which) -> resetearDatos())
                .setNegativeButton("Cancelar", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void resetearDatos() {
        databaseRef.removeValue()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Todos los datos eliminados", Toast.LENGTH_SHORT).show();
                    barChart.clear();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void cargarDatosEnTiempoReal() {
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<BarEntry> entries = new ArrayList<>();
                ArrayList<String> labels = new ArrayList<>();

                int index = 0;
                for (DataSnapshot data : snapshot.getChildren()) {
                    Recorrido recorrido = data.getValue(Recorrido.class);
                    if (recorrido != null) {
                        entries.add(new BarEntry(index, recorrido.getDistanciaKm()));
                        labels.add(recorrido.getFecha());
                        index++;
                    }
                }

                if (entries.isEmpty()) {
                    barChart.clear();
                    barChart.getDescription().setText("¡Agrega tu primer recorrido!");
                    barChart.getDescription().setTextSize(14f);
                    barChart.getDescription().setTextColor(Color.GRAY);
                    barChart.invalidate();
                } else {
                    configurarGrafica(entries, labels);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EstadisticasActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configurarGrafica(ArrayList<BarEntry> entries, ArrayList<String> labels) {
        BarDataSet dataSet = new BarDataSet(entries, "Distancia (km)");
        dataSet.setColor(Color.rgb(233, 30, 99));
        dataSet.setValueTextSize(12f);
        dataSet.setValueTextColor(Color.BLACK);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setLabelRotationAngle(-45);

        barChart.getDescription().setText("Mis recorridos");
        barChart.getDescription().setTextSize(12f);
        barChart.animateY(1000);
        barChart.invalidate();
    }
}