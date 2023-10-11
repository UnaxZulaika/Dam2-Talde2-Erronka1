package com.talde2.dam2_erronka1_talde2.Objetuak;

public class Tokia {
    private String kodea;
    private String ubikazioa;
    private String irudia;
    private String deskripzioa;
    private double balorazioa;

    public Tokia(String kodea, String ubikazioa, String irudia, String deskripzioa) {
        this.kodea = kodea;
        this.ubikazioa = ubikazioa;
        this.irudia = irudia;
        this.deskripzioa = deskripzioa;
    }
    public Tokia() {
        this.kodea = "";
        this.ubikazioa = "";
        this.irudia = "";
        this.deskripzioa = "";
    }

    public String getKodea() {
        return kodea;
    }

    public void setKodea(String kodea) {
        this.kodea = kodea;
    }

    public String getUbikazioa() {
        return ubikazioa;
    }

    public void setUbikazioa(String ubikazioa) {
        this.ubikazioa = ubikazioa;
    }

    public String getIrudia() {
        return irudia;
    }

    public void setIrudia(String irudia) {
        this.irudia = irudia;
    }

    public String getDeskripzioa() {

        return deskripzioa;
    }

    public void setDeskripzioa(String deskripzioa) {

        this.deskripzioa = deskripzioa;
    }

    public double getBalorazioa() {
        return balorazioa;
    }

    public void setBalorazioa(double balorazioa) {
        this.balorazioa = balorazioa;
    }
}

