package com.example.healtymind;

public class DiaHabito {
    private int numeroDia;
    private boolean completado;

    public DiaHabito(int numeroDia, boolean completado) {
        this.numeroDia = numeroDia;
        this.completado = completado;
    }

    public int getNumeroDia() { return numeroDia; }
    public boolean isCompletado() { return completado; }
}