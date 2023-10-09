package com.talde2.dam2_erronka1_talde2.Objetuak;

public class JardueraPertsona extends Tokia {

    private enum jardueraMota {
        aisialdia, ruta
    }
    private String jardueraInfo;
    private double sarreraPrezioa;
    private double prezioa10pertsona;
    private double prezioa20pertsona;

    public JardueraPertsona(String kodea, String ubikazioa, String irudia, String deskripzioa, String jardueraInfo, double sarreraPrezioa, double prezioa10pertsona, double prezioa20pertsona) {
        super(kodea, ubikazioa, irudia, deskripzioa);
        this.jardueraInfo = jardueraInfo;
        this.sarreraPrezioa = sarreraPrezioa;
        this.prezioa10pertsona = prezioa10pertsona;
        this.prezioa20pertsona = prezioa20pertsona;
    }

    public String getJardueraInfo() {
        return jardueraInfo;
    }

    public void setJardueraInfo(String jardueraInfo) {
        this.jardueraInfo = jardueraInfo;
    }

    public double getSarreraPrezioa() {
        return sarreraPrezioa;
    }

    public void setSarreraPrezioa(double sarreraPrezioa) {
        this.sarreraPrezioa = sarreraPrezioa;
    }

    public double getPrezioa10pertsona() {
        return prezioa10pertsona;
    }

    public void setPrezioa10pertsona(double prezioa10pertsona) {
        this.prezioa10pertsona = prezioa10pertsona;
    }

    public double getPrezioa20pertsona() {
        return prezioa20pertsona;
    }

    public void setPrezioa20pertsona(double prezioa20pertsona) {
        this.prezioa20pertsona = prezioa20pertsona;
    }
}
