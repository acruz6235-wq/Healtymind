package com.example.healtymind;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;

public class GoogleFitManager {

    private final Context context;
    private static final int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1001;

    // Configurar qué datos queremos leer de Google Fit
    private final FitnessOptions fitnessOptions = FitnessOptions.builder()
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.TYPE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.TYPE_CALORIES_EXPENDED, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.TYPE_SLEEP_SEGMENT, FitnessOptions.ACCESS_READ)
            .build();

    public GoogleFitManager(Context context) {
        this.context = context;
    }

    // Verificar si ya tenemos permisos
    public boolean tienePermisos() {
        return GoogleSignIn.hasPermissions(
                GoogleSignIn.getAccountForExtension(context, fitnessOptions),
                fitnessOptions
        );
    }

    // Solicitar permisos al usuario
    public void solicitarPermisos(Activity activity) {
        GoogleSignIn.requestPermissions(
                activity,
                GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                GoogleSignIn.getAccountForExtension(context, fitnessOptions),
                fitnessOptions
        );
    }

    // Obtener pasos del día
    public void obtenerPasosDeHoy(OnDatosObtenidos callback) {
        Fitness.getHistoryClient(context, GoogleSignIn.getAccountForExtension(context, fitnessOptions))
                .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnSuccessListener(result -> {
                    int pasos = result.isEmpty() ? 0 :
                            result.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();
                    callback.onExito(pasos);
                })
                .addOnFailureListener(e -> {
                    Log.e("GoogleFit", "Error al obtener pasos", e);
                    callback.onError(e.getMessage());
                });
    }

    // Obtener distancia del día (en kilómetros)
    public void obtenerDistanciaDeHoy(OnDatosObtenidos callback) {
        Fitness.getHistoryClient(context, GoogleSignIn.getAccountForExtension(context, fitnessOptions))
                .readDailyTotal(DataType.TYPE_DISTANCE_DELTA)
                .addOnSuccessListener(result -> {
                    float distanciaMetros = result.isEmpty() ? 0 :
                            result.getDataPoints().get(0).getValue(Field.FIELD_DISTANCE).asFloat();
                    float distanciaKm = distanciaMetros / 1000;
                    callback.onExito((int)distanciaKm);
                })
                .addOnFailureListener(e -> {
                    Log.e("GoogleFit", "Error al obtener distancia", e);
                    callback.onError(e.getMessage());
                });
    }

    // Obtener calorías del día
    public void obtenerCaloriasDeHoy(OnDatosObtenidos callback) {
        Fitness.getHistoryClient(context, GoogleSignIn.getAccountForExtension(context, fitnessOptions))
                .readDailyTotal(DataType.TYPE_CALORIES_EXPENDED)
                .addOnSuccessListener(result -> {
                    float calorias = result.isEmpty() ? 0 :
                            result.getDataPoints().get(0).getValue(Field.FIELD_CALORIES).asFloat();
                    callback.onExito((int)calorias);
                })
                .addOnFailureListener(e -> {
                    Log.e("GoogleFit", "Error al obtener calorías", e);
                    callback.onError(e.getMessage());
                });
    }

    // Interface para recibir los datos de forma asíncrona
    public interface OnDatosObtenidos {
        void onExito(int valor);
        void onError(String mensaje);
    }

    public static int getRequestCode() {
        return GOOGLE_FIT_PERMISSIONS_REQUEST_CODE;
    }
