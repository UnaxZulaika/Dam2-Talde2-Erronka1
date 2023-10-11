package com.talde2.dam2_erronka1_talde2.Objetuak;

public class JardueraEntitateak extends Tokia {
    public enum jardueraMota{
        konferentzia, kumbrea, presentazioa, feria, tailerrak
    }
    private jardueraMota mota;
    private double prezioTxikia;
    private double prezioErdia;
    private double prezioHandia;
    private double prezioOsoHandia;

    public JardueraEntitateak(String kodea, String ubikazioa, String irudia, String deskripzioa, String jardueraInfo, double prezioTxikia, double prezioErdia, double prezioHandia, double prezioOsoHandia) {
        super(kodea, ubikazioa, irudia, deskripzioa);
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

    public jardueraMota getMota() {
        return mota;
    }

    public void setMota(jardueraMota mota) {
        this.mota = mota;
    }
}

