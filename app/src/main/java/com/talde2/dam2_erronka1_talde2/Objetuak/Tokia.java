package com.talde2.dam2_erronka1_talde2.Objetuak;

public class Tokia {
    private int code;
    private String kokalekua;
    private String img;
    private String informazioa;
    private double balorazioa;

    public Tokia(int kodea, String ubikazioa, String irudia, String deskripzioa) {
        this.code = kodea;
        this.kokalekua = ubikazioa;
        this.img = irudia;
        this.informazioa = deskripzioa;
    }
    public Tokia() {
        this.code = 0;
        this.kokalekua = "";
        this.img = "";
        this.informazioa = "";
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getKokalekua() {
        return kokalekua;
    }

    public void setKokalekua(String kokalekua) {
        this.kokalekua = kokalekua;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getInformazioa() {

        return informazioa;
    }

    public void setInformazioa(String informazioa) {

        this.informazioa = informazioa;
    }

    public double getBalorazioa() {
        return balorazioa;
    }

    public void setBalorazioa(double balorazioa) {
        this.balorazioa = balorazioa;
    }
}

