package com.talde2.dam2_erronka1_talde2.Objetuak;

public class JardueraEntitateak {
    private enum jardueraMota{
        konferentzia, kumbrea, presentazioa, feria, tailerrak
    }

    private double prezioTxikia;
    private double prezioErdia;
    private double prezioHandia;
    private double prezioOsoHandia;

    public JardueraEntitateak(double prezioTxikia, double prezioErdia, double prezioHandia, double prezioOsoHandia) {
        this.prezioTxikia = prezioTxikia;
        this.prezioErdia = prezioErdia;
        this.prezioHandia = prezioHandia;
        this.prezioOsoHandia = prezioOsoHandia;
    }

    public double getPrezioTxikia() {
        return prezioTxikia;
    }

    public void setPrezioTxikia(double prezioTxikia) {
        this.prezioTxikia = prezioTxikia;
    }

    public double getPrezioErdia() {
        return prezioErdia;
    }

    public void setPrezioErdia(double prezioErdia) {
        this.prezioErdia = prezioErdia;
    }

    public double getPrezioHandia() {
        return prezioHandia;
    }

    public void setPrezioHandia(double prezioHandia) {
        this.prezioHandia = prezioHandia;
    }

    public double getPrezioOsoHandia() {
        return prezioOsoHandia;
    }

    public void setPrezioOsoHandia(double prezioOsoHandia) {
        this.prezioOsoHandia = prezioOsoHandia;
    }
}

