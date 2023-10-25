package com.talde2.dam2_erronka1_talde2.Objetuak;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class JardueraPertsona extends Tokia implements Parcelable {

    protected JardueraPertsona(Parcel in) {
        prezioa = in.readDouble();
        prezioa10 = in.readDouble();
        prezioa20 = in.readDouble();
    }

    public static final Creator<JardueraPertsona> CREATOR = new Creator<JardueraPertsona>() {
        @Override
        public JardueraPertsona createFromParcel(Parcel in) {
            return new JardueraPertsona(in);
        }

        @Override
        public JardueraPertsona[] newArray(int size) {
            return new JardueraPertsona[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeDouble(prezioa);
        parcel.writeDouble(prezioa10);
        parcel.writeDouble(prezioa20);
    }

    public enum jardueraMota {
        aisialdia, ruta
    }
    private jardueraMota mota;
    private double prezioa;
    private double prezioa10;
    private double prezioa20;

    public JardueraPertsona(int kodea, String ubikazioa, String irudia, String deskripzioa, double sarreraPrezioa, double prezioa10pertsona, double prezioa20pertsona) {
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
