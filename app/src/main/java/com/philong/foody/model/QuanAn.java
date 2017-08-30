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

    public void getDanhSachQuanAn(final DanhSachQuanAn danhSachQuanAn){
        mDanhSachQuanAn = danhSachQuanAn;
        final List<QuanAn> quanAnList = new ArrayList<>();

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshotRoot = dataSnapshot.child("quanans");
                for(DataSnapshot dataSnapShotQuanAn : dataSnapshotRoot.getChildren()){
                    DataSnapshot dataSnapshotHinhAnh = dataSnapshot.child("hinhanhquanans").child(dataSnapShotQuanAn.getKey());
                    QuanAn quanAn = dataSnapShotQuanAn.getValue(QuanAn.class);
                    quanAn.setMaquanan(dataSnapShotQuanAn.getKey());
                    List<String> hinhAnhList = new ArrayList<>();
                    for(DataSnapshot dataSnapshotHinhQuanAn : dataSnapshotHinhAnh.getChildren()){
                        hinhAnhList.add(dataSnapshotHinhQuanAn.getValue(String.class));
                    }
                    quanAn.setHinhanhquanan(hinhAnhList);
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
