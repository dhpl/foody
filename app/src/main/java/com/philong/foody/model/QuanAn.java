package com.philong.foody.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long on 8/30/2017.
 */

public class QuanAn {

    private boolean giaohang;
    private String giodongcua;
    private String giomocua;
    private String tenquanan;
    private String maquanan;
    private String videogioithieu;
    private long luotthich;
    private List<String> tienich;
    private List<String> hinhanhquanan;
    private List<BinhLuan> binhluan;
    private DanhSachQuanAn mDanhSachQuanAn;
    private DatabaseReference mDatabaseReference;


    public QuanAn() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public boolean isGiaohang() {
        return giaohang;
    }

    public void setGiaohang(boolean giaohang) {
        this.giaohang = giaohang;
    }

    public String getGiodongcua() {
        return giodongcua;
    }

    public void setGiodongcua(String giodongcua) {
        this.giodongcua = giodongcua;
    }

    public String getGiomocua() {
        return giomocua;
    }

    public void setGiomocua(String giomocua) {
        this.giomocua = giomocua;
    }

    public String getTenquanan() {
        return tenquanan;
    }

    public void setTenquanan(String tenquanan) {
        this.tenquanan = tenquanan;
    }

    public String getMaquanan() {
        return maquanan;
    }

    public void setMaquanan(String maquanan) {
        this.maquanan = maquanan;
    }

    public List<String> getTienich() {
        return tienich;
    }

    public void setTienich(List<String> tienich) {
        this.tienich = tienich;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public String getVideogioithieu() {
        return videogioithieu;
    }

    public void setVideogioithieu(String videogioithieu) {
        this.videogioithieu = videogioithieu;
    }

    public List<String> getHinhanhquanan() {
        return hinhanhquanan;
    }

    public void setHinhanhquanan(List<String> hinhanhquanan) {
        this.hinhanhquanan = hinhanhquanan;
    }

    public List<BinhLuan> getBinhluan() {
        return binhluan;
    }

    public void setBinhluan(List<BinhLuan> binhluan) {
        this.binhluan = binhluan;
    }

    public void getDanhSachQuanAn(final DanhSachQuanAn danhSachQuanAn){
        mDanhSachQuanAn = danhSachQuanAn;
        final List<QuanAn> quanAnList = new ArrayList<>();

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshotQuanAn = dataSnapshot.child("quanans");
                for(DataSnapshot valueQuanAn : dataSnapshotQuanAn.getChildren()){
                    DataSnapshot dataSnapshotHinhAnh = dataSnapshot.child("hinhanhquanans").child(valueQuanAn.getKey());
                    QuanAn quanAn = valueQuanAn.getValue(QuanAn.class);
                    quanAn.setMaquanan(valueQuanAn.getKey());
                    //Get hinh
                    List<String> hinhAnhList = new ArrayList<>();
                    for(DataSnapshot valueHinhAnh : dataSnapshotHinhAnh.getChildren()){
                        hinhAnhList.add(valueHinhAnh.getValue(String.class));
                    }
                    quanAn.setHinhanhquanan(hinhAnhList);
                    //Get Binh Luan
                    DataSnapshot dataSnapshotBinhLuan = dataSnapshot.child("binhluans").child(quanAn.getMaquanan());
                    List<BinhLuan> binhLuanList = new ArrayList<>();
                    for(DataSnapshot valueBinhLuan : dataSnapshotBinhLuan.getChildren()){
                        BinhLuan binhLuan = valueBinhLuan.getValue(BinhLuan.class);
                        binhLuan.setMabinhluan(valueBinhLuan.getKey());
                        //Get thanh vien
                        ThanhVien thanhVien  = dataSnapshot.child("thanhviens").child(String.valueOf(binhLuan.getMauser())).getValue(ThanhVien.class);
                        binhLuan.setThanhvien(thanhVien);
                        //Get danh sach hinh anh binh luan
                        List<String> hinhAnhBinhLuanList = new ArrayList<String>();
                        DataSnapshot dataSnapshotHinhAnhBinhLuan = dataSnapshot.child("hinhanhbinhluans").child(binhLuan.getMabinhluan());
                        for(DataSnapshot valueHinhAnhBinhLuan : dataSnapshotHinhAnhBinhLuan.getChildren()){
                            hinhAnhBinhLuanList.add(valueHinhAnhBinhLuan.getValue(String.class));
                        }
                        binhLuan.setHinhanhbinhluans(hinhAnhBinhLuanList);
                        binhLuanList.add(binhLuan);
                    }
                    quanAn.setBinhluan(binhLuanList);
                    quanAnList.add(quanAn);
                }
                mDanhSachQuanAn.completeDanhSachQuanAn(quanAnList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public interface DanhSachQuanAn{
        void completeDanhSachQuanAn(List<QuanAn> quanAnList);
    }
}
