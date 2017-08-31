package com.philong.foody.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Long on 8/31/2017.
 */

public class ChiNhanh implements Parcelable{

    private String diachi;
    private double latitude;
    private double longitude;
    private double khoangcach;

    public ChiNhanh() {

    }

    protected ChiNhanh(Parcel in) {
        diachi = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        khoangcach = in.readDouble();
    }

    public static final Creator<ChiNhanh> CREATOR = new Creator<ChiNhanh>() {
        @Override
        public ChiNhanh createFromParcel(Parcel in) {
            return new ChiNhanh(in);
        }

        @Override
        public ChiNhanh[] newArray(int size) {
            return new ChiNhanh[size];
        }
    };

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getKhoangcach() {
        return khoangcach;
    }

    public void setKhoangcach(double khoangcach) {
        this.khoangcach = khoangcach;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(diachi);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeDouble(khoangcach);
    }
}
