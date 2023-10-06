package com.talde2.dam2_erronka1_talde2.Objetuak;

public class Erabiltzaile {

    private String izena;
    private String nan;
    private String Abizenak;
    private String email;
    private String mugikorra;
    private String erabiltzaileMota;

    public Erabiltzaile(String izena, String nan, String abizenak, String email, String mugikorra, String erabiltzaileMota) {
        this.izena = izena;
        this.nan = nan;
        Abizenak = abizenak;
        this.email = email;
        this.mugikorra = mugikorra;
        this.erabiltzaileMota = erabiltzaileMota;
    }

    public String getIzena() {
        return izena;
    }

    public void setIzena(String izena) {
        this.izena = izena;
    }

    public String getNan() {
        return nan;
    }

    public void setNan(String nan) {
        this.nan = nan;
    }

    public String getAbizenak() {
        return Abizenak;
    }

    public void setAbizenak(String abizenak) {
        Abizenak = abizenak;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMugikorra() {
        return mugikorra;
    }

    public void setMugikorra(String mugikorra) {
        this.mugikorra = mugikorra;
    }

    public String getErabiltzaileMota() {
        return erabiltzaileMota;
    }

    public void setErabiltzaileMota(String erabiltzaileMota) {
        this.erabiltzaileMota = erabiltzaileMota;
    }

    @Override
    public String toString() {
        return "erabiltzaileak{" +
                "izena='" + izena + '\'' +
                ", nan='" + nan + '\'' +
                ", Abizenak='" + Abizenak + '\'' +
                ", email='" + email + '\'' +
                ", mugikorra='" + mugikorra + '\'' +
                ", erabiltzaileMota='" + erabiltzaileMota + '\'' +
                '}';
    }
}
