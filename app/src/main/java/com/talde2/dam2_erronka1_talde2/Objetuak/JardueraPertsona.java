package com.talde2.dam2_erronka1_talde2.Objetuak;

public class JardueraPertsona extends Tokia {

    public enum jardueraMota {
        aisialdia, ruta
    }
    private jardueraMota mota;
    private double prezioa;
    private double prezioa10;
    private double prezioa20;

    public JardueraPertsona(String kodea, String ubikazioa, String irudia, String deskripzioa, double sarreraPrezioa, double prezioa10pertsona, double prezioa20pertsona) {
        super(kodea, ubikazioa, irudia, deskripzioa);
        this.prezioa = sarreraPrezioa;
        this.prezioa10 = prezioa10pertsona;
        this.prezioa20 = prezioa20pertsona;
    }
    public JardueraPertsona() {
        super();
        this.prezioa = 0;
        this.prezioa10 = 0;
        this.prezioa20 = 0;
    }


    public double getPrezioa() {
        return prezioa;
    }

    public void setPrezioa(double prezioa) {
        this.prezioa = prezioa;
    }

    public double getPrezioa10() {

        return prezioa10;
    }

    public void setPrezioa10(double prezioa10) {
        this.prezioa10 = prezioa10;
    }

    public double getPrezioa20() {
        return prezioa20;
    }
    public void setPrezioa20(double prezioa20) {
        this.prezioa20 = prezioa20;
    }

    public jardueraMota getMota() {
        return mota;
    }

    public void setMota(jardueraMota mota) {
        this.mota = mota;
    }

}
