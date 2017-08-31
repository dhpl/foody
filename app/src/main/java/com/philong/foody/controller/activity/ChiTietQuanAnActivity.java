package com.philong.foody.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.philong.foody.R;
import com.philong.foody.model.QuanAn;

public class ChiTietQuanAnActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ImageView mHinhAnhImageView;
    private TextView mTenQuanAnTextView;
    private TextView mDiaChiQuanAnTextView;
    private TextView mThoiGianHoatDongTextView;
    private TextView mTrangThaiHoatDongTextView;
    private TextView mTaiAnhTextView;
    private TextView mCheckInTextView;
    private TextView mBinhLuanTextView;
    private TextView mLuuLaiTextView;
    private TextView mChiaSeTextView;
    private TextView mToolbarTextView;
    private QuanAn mQuanAn;


    public static Intent newIntent(Context context, QuanAn quanAn){
        Intent intent = new Intent(context, ChiTietQuanAnActivity.class);
        intent.putExtra("QuanAn", quanAn);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_quan_an);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        if(getSupportActionBar() == null){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle("");

        }
        //Get view
        mToolbarTextView = (TextView)findViewById(R.id.chi_tiet_quan_an_ten_toolbar_n_text_view);
        mHinhAnhImageView = (ImageView)findViewById(R.id.chi_tiet_quan_an_hinh_anh_image_view);
        mTenQuanAnTextView = (TextView)findViewById(R.id.chi_tiet_quan_an_ten_text_view);
        mDiaChiQuanAnTextView = (TextView)findViewById(R.id.chi_tiet_quan_an_dia_chi_text_view);
        mThoiGianHoatDongTextView = (TextView)findViewById(R.id.chi_tiet_quan_an_thoi_gian_dong_mo_cua_text_view);
        mTrangThaiHoatDongTextView = (TextView)findViewById(R.id.chi_tiet_quan_an_trang_thai_text_view);
        mTaiAnhTextView = (TextView)findViewById(R.id.chi_tiet_quan_an_tai_anh_text_view);
        mCheckInTextView = (TextView)findViewById(R.id.chi_tiet_quan_an_check_in_text_view);
        mBinhLuanTextView = (TextView)findViewById(R.id.chi_tiet_quan_an_binh_luan_text_view);
        mLuuLaiTextView = (TextView)findViewById(R.id.chi_tiet_quan_an_luu_lai_text_view);
        mChiaSeTextView = (TextView)findViewById(R.id.chi_tiet_quan_an_chia_se_text_view);
        //Get quan an from intent
        mToolbarTextView.setText("Foody");
        if(getIntent() != null){
            mQuanAn = getIntent().getParcelableExtra("QuanAn");
            mTenQuanAnTextView.setText(mQuanAn.getTenquanan());
            mDiaChiQuanAnTextView.setText(mQuanAn.getChinhanh().get(0).getDiachi());
            mThoiGianHoatDongTextView.setText("Thời gian hoạt động: " + String.valueOf(mQuanAn.getGiomocua()) + " - " + mQuanAn.getGiodongcua());
            mTaiAnhTextView.setText(String.valueOf(mQuanAn.getHinhanhquanan().size()));
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(mQuanAn.getHinhanhquanan().get(0));
            setImageView(storageReference, mHinhAnhImageView);
        }
    }


    public void setImageView(StorageReference storageReference, final ImageView imageView){
        final long ONE_MEGABYTE = 1024 * 1024;
        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(bitmap);
            }
        });
    }
}
