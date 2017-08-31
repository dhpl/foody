package com.philong.foody.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

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

public class QuanAn implements Parcelable{

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
    private List<ChiNhanh> chinhanh;
    private DanhSachQuanAn mDanhSachQuanAn;
    private DatabaseReference mDatabaseReference;


    public QuanAn() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }


    protected QuanAn(Parcel in) {
        giaohang = in.readByte() != 0;
        giodongcua = in.readString();
        giomocua = in.readString();
        tenquanan = in.readString();
        maquanan = in.readString();
        videogioithieu = in.readString();
        luotthich = in.readLong();
        tienich = in.createStringArrayList();
        hinhanhquanan = in.createStringArrayList();
        binhluan = in.createTypedArrayList(BinhLuan.CREATOR);
        chinhanh = in.createTypedArrayList(ChiNhanh.CREATOR);
    }

    public static final Creator<QuanAn> CREATOR = new Creator<QuanAn>() {
        @Override
        public QuanAn createFromParcel(Parcel in) {
            return new QuanAn(in);
        }

        @Override
        public QuanAn[] newArray(int size) {
            return new QuanAn[size];
        }
    };

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

    public List<ChiNhanh> getChinhanh() {
        return chinhanh;
    }

    public void setChinhanh(List<ChiNhanh> chinhanh) {
        this.chinhanh = chinhanh;
    }

    public void getDanhSachQuanAn(final DanhSachQuanAn danhSachQuanAn, final Location locationCurrent){
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
                    //Get chi nhanh quan an
                    List<ChiNhanh> chiNhanhList = new ArrayList<ChiNhanh>();
                    DataSnapshot dataSnapshotChiNhanh = dataSnapshot.child("chinhanhquanans").child(quanAn.getMaquanan());
                    for(DataSnapshot valueChiNhanhQuanAn : dataSnapshotChiNhanh.getChildren()){
                        ChiNhanh chiNhanh = valueChiNhanhQuanAn.getValue(ChiNhanh.class);
                        Location locationQuanAn = new Location("");
                        locationQuanAn.setLatitude(chiNhanh.getLatitude());
                        locationQuanAn.setLongitude(chiNhanh.getLongitude());
                        double khoangCach = locationCurrent.distanceTo(locationQuanAn) / 1000;
                        System.out.println("Khoang cach: " + khoangCach);
                        chiNhanh.setKhoangcach(Math.abs(khoangCach));
                        chiNhanhList.add(chiNhanh);
                    }
                    quanAn.setChinhanh(chiNhanhList);
                    quanAnList.add(quanAn);
                }
                mDanhSachQuanAn.completeDanhSachQuanAn(quanAnList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (giaohang ? 1 : 0));
        parcel.writeString(giodongcua);
        parcel.writeString(giomocua);
        parcel.writeString(tenquanan);
        parcel.writeString(maquanan);
        parcel.writeString(videogioithieu);
        parcel.writeLong(luotthich);
        parcel.writeStringList(tienich);
        parcel.writeStringList(hinhanhquanan);
        parcel.writeTypedList(binhluan);
        parcel.writeTypedList(chinhanh);
    }


    public interface DanhSachQuanAn{
        void completeDanhSachQuanAn(List<QuanAn> quanAnList);
    }
}
