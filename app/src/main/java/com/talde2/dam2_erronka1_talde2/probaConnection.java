package com.talde2.dam2_erronka1_talde2;

public class probaConnection {

    private String izena;
    private boolean ondoDabil;

    private int nota;

    public probaConnection(String izena, boolean ondoDabil, int nota) {
        this.izena = izena;
        this.ondoDabil = ondoDabil;
        this.nota = nota;
    }

    public String getIzena() {
        return izena;
    }

    public void setIzena(String izena) {
        this.izena = izena;
    }

    public boolean isOndoDabil() {
        return ondoDabil;
    }

    public void setOndoDabil(boolean ondoDabil) {
        this.ondoDabil = ondoDabil;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }
}
