package com.example.healtymind;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Racha_Activity extends AppCompatActivity {

    RecyclerView rvCalendario;
    TextView tvRachaSuperior, tvRachaCard, tvMesAnio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_racha);

        rvCalendario = findViewById(R.id.rvCalendario);
        tvRachaSuperior = findViewById(R.id.tvRachaSuperior);
        tvRachaCard = findViewById(R.id.tvRachaCard);
        tvMesAnio = findViewById(R.id.tvMesAnio);

        rvCalendario.setLayoutManager(new GridLayoutManager(this, 7));
        cargarDatosRacha();
    }

    private void cargarDatosRacha() {
        SharedPreferences prefs = getSharedPreferences("HealthyMindPrefs", MODE_PRIVATE);
        Calendar calendar = Calendar.getInstance();

        int maxDias = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        List<DiaHabito> listaDias = new ArrayList<>();
        int diasConRacha = 0;

        for (int i = 1; i <= maxDias; i++) {
            // Buscamos si el día i está marcado como completado
            boolean completado = prefs.getBoolean("dia_" + i + "_completado", false);
            if (completado) diasConRacha++;
            listaDias.add(new DiaHabito(i, completado));
        }
        Habito_Adaptater adapter = new Habito_Adaptater(listaDias);
        rvCalendario.setAdapter(adapter);

        String textoRacha = diasConRacha + " días";
        tvRachaSuperior.setText(textoRacha);
        tvRachaCard.setText(textoRacha);
        tvMesAnio.setText("Marzo 2026");
    }
}