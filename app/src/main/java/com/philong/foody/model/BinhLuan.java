package com.philong.foody.model;

import java.util.List;

/**
 * Created by Long on 8/31/2017.
 */

public class BinhLuan {

    private double chamdiem;
    private long luotthich;
    private ThanhVien thanhvien;
    private String noidung;
    private String tieude;
    private String mabinhluan;
    private long mauser;
    private List<String> hinhanhbinhluans;

    public BinhLuan() {
    }

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

    public long getMauser() {
        return mauser;
    }

    public void setMauser(long mauser) {
        this.mauser = mauser;
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
}
