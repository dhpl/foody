package com.philong.foody.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Long on 8/30/2017.
 */

public class ThanhVien implements Parcelable{

    private String hinhanh;
    private String hoten;
    private String mathanhvien;

    private DatabaseReference mDatabaseReference;

    public ThanhVien() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("thanhviens");
    }

    protected ThanhVien(Parcel in) {
        hinhanh = in.readString();
        hoten = in.readString();
        mathanhvien = in.readString();
    }

    public static final Creator<ThanhVien> CREATOR = new Creator<ThanhVien>() {
        @Override
        public ThanhVien createFromParcel(Parcel in) {
            return new ThanhVien(in);
        }

        @Override
        public ThanhVien[] newArray(int size) {
            return new ThanhVien[size];
        }
    };

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getMathanhvien() {
        return mathanhvien;
    }

    public void setMathanhvien(String mathanhvien) {
        this.mathanhvien = mathanhvien;
    }

    public void themThanhVien(ThanhVien thanhVien, String uid){

        mDatabaseReference.child(uid).setValue(thanhVien);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(hinhanh);
        parcel.writeString(hoten);
        parcel.writeString(mathanhvien);
    }
}
