package com.example.healtymind;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;
import java.util.Calendar;

public class Desafio_Activity extends AppCompatActivity {

    private MaterialCardView cardMensual;
    private TextView tvTituloMes, tvDescRetoMes, tvTagMensual, tvRetoDiario1, tvRetoDiario2;
    private ImageView imgFestividad;
    private CheckBox chkRetoMensual, chkDiario1, chkDiario2;

    public static class DesafioMes {
        String titulo, descripcion, colorHex, drawableName;
        public DesafioMes(String titulo, String descripcion, String colorHex, String drawableName) {
            this.titulo = titulo;
            this.descripcion = descripcion;
            this.colorHex = colorHex;
            this.drawableName = drawableName;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desafio);

        cardMensual = findViewById(R.id.cardMensual);
        tvTituloMes = findViewById(R.id.tvTituloMes);
        tvDescRetoMes = findViewById(R.id.tvDescRetoMes);
        tvTagMensual = findViewById(R.id.tvTagMensual);
        imgFestividad = findViewById(R.id.imgFestividad);
        tvRetoDiario1 = findViewById(R.id.tvRetoDiario1);
        tvRetoDiario2 = findViewById(R.id.tvRetoDiario2);
        chkRetoMensual = findViewById(R.id.chkRetoMensual);
        chkDiario1 = findViewById(R.id.chkDiario1);
        chkDiario2 = findViewById(R.id.chkDiario2);

        configurarContenidoMensual();

        tvRetoDiario1.setText("Meditar 5 minutos antes de iniciar el día.");
        tvRetoDiario2.setText("Escribir una cosa positiva que sucedió hoy.");

        gestionarCheckboxes();
    }

    private void configurarContenidoMensual() {
        int mes = Calendar.getInstance().get(Calendar.MONTH);
        DesafioMes[] lista = new DesafioMes[12];
        lista[0] = new DesafioMes("El Regalo de la Intención", "Escribe 3 propósitos de bienestar.", "#81D4FA", "ic_reyes_magos");
        lista[1] = new DesafioMes("Amor Propio Primero", "Escribe una carta de agradecimiento.", "#B388FF", "ic_san_valentin");
        lista[2] = new DesafioMes("Equilibrio y Empatía", "Dedica un pensamiento positivo.", "#9C27B0", "ic_dia_mujer");
        lista[3] = new DesafioMes("Reconecta con tu Alegría", "Realiza una actividad creativa.", "#FFEB3B", "ic_dia_nino");
        lista[4] = new DesafioMes("Raíces de Gratitud", "Expresa tu agradecimiento.", "#E1BEE7", "ic_dia_madre");
        lista[5] = new DesafioMes("Sabiduría Compartida", "Reflexiona sobre un consejo.", "#F8BBD0", "ic_dia_padre");
        lista[6] = new DesafioMes("Legado de Calma", "Lee una historia antigua.", "#EF9A9A", "ic_dia_abuelos");
        lista[7] = new DesafioMes("Desconexión Mental", "Pasa 3 horas sin celular.", "#FF9800", "ic_juventud");
        lista[8] = new DesafioMes("Libertad de Pensamiento", "Libérate de un hábito limitante.", "#2E7D32", "ic_independencia");
        lista[9] = new DesafioMes("Armonía Interior", "Escucha música que te de paz.", "#795548", "ic_musica");
        lista[10] = new DesafioMes("Honrar la Memoria", "Reflexiona sobre un valor positivo.", "#B0BEC5", "ic_dia_muertos");
        lista[11] = new DesafioMes("El Arte de Dar-se", "Realiza un acto de bondad.", "#D32F2F", "ic_navidad");

        DesafioMes actual = lista[mes];
        tvTituloMes.setText(actual.titulo);
        tvDescRetoMes.setText(actual.descripcion);
        int color = Color.parseColor(actual.colorHex);
        cardMensual.setStrokeColor(color);
        tvTagMensual.setTextColor(color);

        int resID = getResources().getIdentifier(actual.drawableName, "drawable", getPackageName());
        if (resID != 0) imgFestividad.setImageResource(resID);
    }

    private void gestionarCheckboxes() {
        SharedPreferences prefs = getSharedPreferences("HealthyMindPrefs", MODE_PRIVATE);
        configurarComportamiento(chkRetoMensual, "chk_mensual", prefs);
        configurarComportamiento(chkDiario1, "chk_diario1", prefs);
        configurarComportamiento(chkDiario2, "chk_diario2", prefs);
    }

    private void configurarComportamiento(CheckBox chk, String key, SharedPreferences prefs) {
        if (prefs.getBoolean(key, false)) {
            chk.setChecked(true);
            chk.setEnabled(false);
        }

        chk.setOnClickListener(v -> {
            if (chk.isChecked()) {
                chk.setEnabled(false);
                int diaHoy = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(key, true);
                editor.putBoolean("dia_" + diaHoy + "_completado", true); // Esto marca el calendario
                editor.apply();
                Toast.makeText(this, "¡Reto cumplido!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}