package com.example.healtymind;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;


public class NotificationActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "canal_musica_v4";
    private static final int PERMISSION_REQUEST_CODE = 123;

    private Button btnNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        crearCanalNotificaciones();
        pedirPermisoNotificaciones();

        btnNotification = findViewById(R.id.btnNotification);
        btnNotification.setOnClickListener(view -> mostrarNotificacion());
    }

    private void crearCanalNotificaciones() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel canal = new NotificationChannel(
                    CHANNEL_ID,

                    "NOTIFICATION TEST",
                    NotificationManager.IMPORTANCE_HIGH
            );
            canal.setDescription("Canal para notificaciones de música y alertas");
            canal.enableVibration(true);

            Uri sonido = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sonido);
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            canal.setSound(sonido, audioAttributes);

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(canal);
            }
        }
    }

    private void pedirPermisoNotificaciones() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        PERMISSION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permiso de notificaciones concedido", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permiso de notificaciones denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void mostrarNotificacion() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Necesitas el permiso para notificaciones", Toast.LENGTH_LONG).show();
                return;
            }
        }

        Uri sonido = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sonido);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(android.R.drawable.ic_dialog_alert);

        builder.setContentTitle("Nueva NOTIFICACION DE HEALTYMIND");
        builder.setContentText("TEST DE NOTIFICACION ");
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setAutoCancel(true);
        builder.setSound(sonido);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(99, builder.build());
    }
}