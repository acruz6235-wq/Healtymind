package com.example.healtymind;

public class Recorrido {
    private String fecha;
    private float distanciaKm;
    private int pasos;
    private float calorias;

    // Constructor vacío (requerido por Firebase)
    public Recorrido() {}

    // Constructor con parámetros
    public Recorrido(String fecha, float distanciaKm, int pasos, float calorias) {
        this.fecha = fecha;
        this.distanciaKm = distanciaKm;
        this.pasos = pasos;
        this.calorias = calorias;
    }

    // Getters
    public String getFecha() { return fecha; }
    public float getDistanciaKm() { return distanciaKm; }
    public int getPasos() { return pasos; }
    public float getCalorias() { return calorias; }

    // Setters (necesarios para Firebase)
    public void setFecha(String fecha) { this.fecha = fecha; }
    public void setDistanciaKm(float distanciaKm) { this.distanciaKm = distanciaKm; }
    public void setPasos(int pasos) { this.pasos = pasos; }
    public void setCalorias(float calorias) { this.calorias = calorias; }
}