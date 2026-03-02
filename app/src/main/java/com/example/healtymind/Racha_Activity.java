package com.example.healtymind;

import android.os.Bundle;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class Racha_Activity extends AppCompatActivity {

    // Variables para la vista
    RecyclerView rvCalendario;
    TextView tvRachaSuperior, tvRachaCard, tvMesAnio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_racha);

        // 1. Vincular los IDs del XML
        // Asegúrate de haber cambiado los IDs en tu XML como hablamos
        rvCalendario = findViewById(R.id.rvCalendario);
        tvRachaSuperior = findViewById(R.id.tvRachaSuperior); // El de arriba
        tvRachaCard = findViewById(R.id.tvRachaCard);       // El de la tarjeta azul
        tvMesAnio = findViewById(R.id.tvMesAnio);

        // 2. Configurar el RecyclerView en 7 columnas
        rvCalendario.setLayoutManager(new GridLayoutManager(this, 7));

        // 3. Crear la lista y calcular la racha dinámicamente
        List<DiaHabito> listaDias = new ArrayList<>();
        int diasCompletados = 0;

        for (int i = 1; i <= 30; i++) {
            // Lógica: Los primeros 13 días están hechos (true)
            boolean estaHecho = (i <= 13);

            if (estaHecho) {
                diasCompletados++; // Sumamos a la racha
            }

            listaDias.add(new DiaHabito(i, estaHecho));
        }

        // 4. Sincronizar los textos con el conteo real
        String rachaTexto = diasCompletados + " días";
        tvRachaSuperior.setText(rachaTexto);
        tvRachaCard.setText(rachaTexto);
        tvMesAnio.setText("Noviembre 2024");

        // 5. Conectar el Adaptador
        Habito_Adaptater adaptador = new Habito_Adaptater(listaDias);
        rvCalendario.setAdapter(adaptador);
    }
}