package com.philong.foody.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Long on 8/31/2017.
 */

public class BinhLuan implements Parcelable {

    private double chamdiem;
    private long luotthich;
    private ThanhVien thanhvien;
    private String noidung;
    private String tieude;
    private String mabinhluan;
    private String mauser;
    private List<String> hinhanhbinhluans;

    public BinhLuan() {
    }


    protected BinhLuan(Parcel in) {
        chamdiem = in.readDouble();
        luotthich = in.readLong();
        thanhvien = in.readParcelable(ThanhVien.class.getClassLoader());
        noidung = in.readString();
        tieude = in.readString();
        mabinhluan = in.readString();
        mauser = in.readString();
        hinhanhbinhluans = in.createStringArrayList();
    }

    public static final Creator<BinhLuan> CREATOR = new Creator<BinhLuan>() {
        @Override
        public BinhLuan createFromParcel(Parcel in) {
            return new BinhLuan(in);
        }

        @Override
        public BinhLuan[] newArray(int size) {
            return new BinhLuan[size];
        }
    };

    public double getChamdiem() {
        return chamdiem;
    }

    public void setChamdiem(double chamdiem) {
        this.chamdiem = chamdiem;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public ThanhVien getThanhvien() {
        return thanhvien;
    }

    public void setThanhvien(ThanhVien thanhvien) {
        this.thanhvien = thanhvien;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }



    public List<String> getHinhanhbinhluans() {
        return hinhanhbinhluans;
    }

    public void setHinhanhbinhluans(List<String> hinhanhbinhluans) {
        this.hinhanhbinhluans = hinhanhbinhluans;
    }

    public String getMabinhluan() {
        return mabinhluan;
    }

    public void setMabinhluan(String mabinhluan) {
        this.mabinhluan = mabinhluan;
    }

    public String getMauser() {
        return mauser;
    }

    public void setMauser(String mauser) {
        this.mauser = mauser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(chamdiem);
        parcel.writeLong(luotthich);
        parcel.writeParcelable(thanhvien, i);
        parcel.writeString(noidung);
        parcel.writeString(tieude);
        parcel.writeString(mabinhluan);
        parcel.writeString(mauser);
        parcel.writeStringList(hinhanhbinhluans);
    }
}
