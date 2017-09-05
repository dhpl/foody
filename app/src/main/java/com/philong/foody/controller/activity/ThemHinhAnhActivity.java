package com.philong.foody.controller.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.philong.foody.R;
import com.philong.foody.adapter.AdapterHinhAnh;
import com.philong.foody.model.HinhAnh;

import java.util.ArrayList;
import java.util.List;

public class ThemHinhAnhActivity extends AppCompatActivity{

    private static int REQUEST_PERMISSION_IMAGE = 99;


    private RecyclerView mThemHinhAnhRecyclerView;
    private List<HinhAnh> mHinhAnhList;
    private AdapterHinhAnh mAdapterHinhAnh;
    private FloatingActionButton mFAB;

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, ThemHinhAnhActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_hinh_anh);
        mHinhAnhList = new ArrayList<>();
        //Set view
        mThemHinhAnhRecyclerView = (RecyclerView)findViewById(R.id.them_hinh_anh_recycler_view);
        mFAB = (FloatingActionButton) findViewById(R.id.them_hinh_anh_fab);
        //Runtime permission
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_IMAGE);
        }else{
            mHinhAnhList = getHinhAnh();
        }
        //Set recyclerview
        mThemHinhAnhRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mThemHinhAnhRecyclerView.setNestedScrollingEnabled(false);
        mThemHinhAnhRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapterHinhAnh = new AdapterHinhAnh(mHinhAnhList, this);
        mThemHinhAnhRecyclerView.setAdapter(mAdapterHinhAnh);
        //Set Click FAB
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("HinhAnhChecked", mAdapterHinhAnh.getHinhAnhChecked());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public List<HinhAnh> getHinhAnh(){
        List<HinhAnh> hinhAnhList = new ArrayList<>();
        String []projection ={MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            String data = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            hinhAnhList.add(new HinhAnh(data, false));
            cursor.moveToNext();
        }
        return hinhAnhList;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_PERMISSION_IMAGE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                mHinhAnhList = getHinhAnh();
            }
        }
    }
}
