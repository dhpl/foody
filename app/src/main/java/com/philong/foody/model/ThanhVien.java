package com.philong.foody.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Long on 8/30/2017.
 */

public class ThanhVien {

    private String hinhanh;
    private String hoten;
    private String mathanhvien;

    private DatabaseReference mDatabaseReference;

    public ThanhVien() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("thanhviens");
    }

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
}
