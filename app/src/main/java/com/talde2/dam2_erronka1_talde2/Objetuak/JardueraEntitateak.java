package com.talde2.dam2_erronka1_talde2.Objetuak;

public class JardueraEntitateak extends Tokia {
    public enum jardueraMota{
        konferentzia, kumbrea, aurkezpena, feria, tailerrak
    }
    private jardueraMota mota;
    private double prezioT;
    private double prezioE;
    private double prezioH;
    private double prezioOH;

    public JardueraEntitateak(String kodea, String ubikazioa, String irudia, String deskripzioa, double prezioTxikia, double prezioErdia, double prezioHandia, double prezioOsoHandia) {
        super(kodea, ubikazioa, irudia, deskripzioa);
        this.prezioT = prezioTxikia;
        this.prezioE = prezioErdia;
        this.prezioH = prezioHandia;
        this.prezioOH = prezioOsoHandia;
    }
    public JardueraEntitateak() {
        super();
        this.prezioT = 0;
        this.prezioE = 0;
        this.prezioH = 0;
        this.prezioOH = 0;
    }

    public double getPrezioT() {
        return prezioT;
    }

    public void setPrezioT(double prezioT) {
        this.prezioT = prezioT;
    }

    public double getPrezioE() {
        return prezioE;
    }

    public void setPrezioE(double prezioE) {
        this.prezioE = prezioE;
    }

    public double getPrezioH() {
        return prezioH;
    }

    public void setPrezioH(double prezioH) {
        this.prezioH = prezioH;
    }

    public double getPrezioOH() {
        return prezioOH;
    }

    public void setPrezioOH(double prezioOH) {
        this.prezioOH = prezioOH;
    }

    public jardueraMota getMota() {
        return mota;
    }

    public void setMota(jardueraMota mota) {
        this.mota = mota;
    }
}

