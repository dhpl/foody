package com.philong.foody.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long on 9/5/2017.
 */

public class ThucDon {

    private String mMaThucDon;
    private String mTenThucDon;
    private List<MonAn> mMonAnList;
    private ProtocolGetThucDon mProtocolGetThucDon;

    public ThucDon() {

    }

    public String getMaThucDon() {
        return mMaThucDon;
    }

    public void setMaThucDon(String maThucDon) {
        mMaThucDon = maThucDon;
    }

    public String getTenThucDon() {
        return mTenThucDon;
    }

    public void setTenThucDon(String tenThucDon) {
        mTenThucDon = tenThucDon;
    }

    public List<MonAn> getMonAnList() {
        return mMonAnList;
    }

    public void setMonAnList(List<MonAn> monAnList) {
        mMonAnList = monAnList;
    }

    public void getDanhSachThucDonQuanAn(ProtocolGetThucDon protocolGetMonAn, final String maQuanAn){
        mProtocolGetThucDon = protocolGetMonAn;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("thucdonquanans").child(maQuanAn);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                final List<ThucDon> thucDons = new ArrayList<>();
                for(DataSnapshot valueThucDon : dataSnapshot.getChildren()){
                    final ThucDon thucDon  = new ThucDon();
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference()
                            .child("thucdons").child(valueThucDon.getKey());
                    databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshotThucDon) {
                            String key = dataSnapshotThucDon.getKey();
                            thucDon.setMaThucDon(key);
                            thucDon.setTenThucDon(dataSnapshotThucDon.getValue(String.class));
                            final List<MonAn> monAns = new ArrayList<>();
                            for(DataSnapshot valueMonAn : dataSnapshot.child(key).getChildren()){
                                MonAn monAn = valueMonAn.getValue(MonAn.class);
                                monAn.setMamon(valueMonAn.getKey());
                                monAns.add(monAn);
                            }
                            thucDon.setMonAnList(monAns);
                            thucDons.add(thucDon);
                            mProtocolGetThucDon.completeGetThucDon(thucDons);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public interface ProtocolGetThucDon{
        void completeGetThucDon(List<ThucDon> thucDons);
    }
}
