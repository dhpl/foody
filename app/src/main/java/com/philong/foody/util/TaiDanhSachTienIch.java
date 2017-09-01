package com.philong.foody.util;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.philong.foody.model.QuanAn;
import com.philong.foody.model.TienIch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long on 9/1/2017.
 */

public class TaiDanhSachTienIch {

    private DanhSachTienIch mProtocol;

    public void getDanhSachTienIch(QuanAn quanAn, DanhSachTienIch danhSachTienIch){
        mProtocol = danhSachTienIch;
        List<String> maTienIchList = new ArrayList<>();
        maTienIchList = quanAn.getTienich();
        if(maTienIchList != null){
            for(String maTienIch : quanAn.getTienich()){
                if(maTienIch != null){
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("quanlytienichs").child(maTienIch);
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            TienIch tienIch = dataSnapshot.getValue(TienIch.class);
                            if(tienIch != null){
                                mProtocol.compleTeDanhSachTienIch(tienIch);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

        }


    }



}
