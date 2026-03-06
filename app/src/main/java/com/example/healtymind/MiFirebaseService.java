package com.example.healtymind;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MiFirebaseService extends FirebaseMessagingService {

    private static final String CHANNEL_ID = "canal_recordatorio_v5";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String titulo = remoteMessage.getNotification() != null
                ? remoteMessage.getNotification().getTitle()
                : "¡Buenos Dias, Laura!";

        String mensaje = remoteMessage.getNotification() != null
                ? remoteMessage.getNotification().getBody()
                : "¡Tomate 5 minutos para una sesion de meditacion el dia de hoy!";

        crearCanalNotificaciones();
        mostrarNotificacion(titulo, mensaje);
    }

    private void crearCanalNotificaciones() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel canal = new NotificationChannel(
                    CHANNEL_ID,
                    "Notificaciones de Recordatorio",
                    NotificationManager.IMPORTANCE_HIGH
            );
            Uri sonido = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sonido);
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            canal.setSound(sonido, audioAttributes);

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(canal);
            }
        }
    }

    private void mostrarNotificacion(String titulo, String mensaje) {
        Uri sonido = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sonido);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.imagen_notificacion)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.imagen_notificacion))
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(sonido);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(99, builder.build());
        }
    }
}