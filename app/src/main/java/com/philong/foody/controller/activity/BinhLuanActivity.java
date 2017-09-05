package com.philong.foody.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.philong.foody.R;
import com.philong.foody.adapter.AdapterHinhBinhLuanChecked;
import com.philong.foody.model.HinhAnh;

import java.util.ArrayList;

/**
 * Created by Long on 9/4/2017.
 */

public class BinhLuanActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_THEM_HINH = 1;


    private TextView mTenQuanAnTextView;
    private TextView mDiaChiQuanAnTextView;
    private EditText mTieuDeEditText;
    private EditText mNoiDungEditText;
    private ImageButton mBinhLuanDangImageButton;
    private ImageButton mThemHinhAnhImageButton;
    private ImageButton mThemVideoImageButtom;
    private ImageButton mTagImageButton;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerViewHinhBinhLuan;
    private AdapterHinhBinhLuanChecked mAdapterHinhBinhLuanChecked;
    private  ArrayList<HinhAnh> mHinhAnhs;

    public static Intent newIntent(Context context, String tenQuanAn, String diaChi){
        Intent intent = new Intent(context, BinhLuanActivity.class);
        intent.putExtra("TenQuanAn", tenQuanAn);
        intent.putExtra("DiaChiQuanAn", diaChi);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binh_luan);
        //Set view
        mToolbar = (Toolbar) findViewById(R.id.binh_luan_toolbar);
        mTenQuanAnTextView = (TextView)findViewById(R.id.binh_luan_ten_quan_an_text_view);
        mDiaChiQuanAnTextView = (TextView)findViewById(R.id.binh_luan_dia_chi_quan_an_text_view);
        mTieuDeEditText = (EditText) findViewById(R.id.binh_luan_tieu_de_edit_text);
        mNoiDungEditText = (EditText)findViewById(R.id.binh_luan_noi_dung_edit_text);
        mBinhLuanDangImageButton = (ImageButton) findViewById(R.id.binh_luan_dang_image_button);
        mThemHinhAnhImageButton = (ImageButton) findViewById(R.id.binh_luan_them_hinh_image_button);
        mThemVideoImageButtom = (ImageButton)findViewById(R.id.binh_luan_them_video_image_button);
        mTagImageButton = (ImageButton)findViewById(R.id.binh_luan_tag_image_button);


        if(getSupportActionBar() == null){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if(getIntent() != null){
            mTenQuanAnTextView.setText(getIntent().getStringExtra("TenQuanAn"));
            mDiaChiQuanAnTextView.setText(getIntent().getStringExtra("DiaChiQuanAn"));
        }

        mThemHinhAnhImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(ThemHinhAnhActivity.newIntent(BinhLuanActivity.this), REQUEST_CODE_THEM_HINH);
            }
        });
        //set recyclerview
        mHinhAnhs = new ArrayList<>();
        mRecyclerViewHinhBinhLuan = (RecyclerView) findViewById(R.id.binh_luan_recycler_view);
        mRecyclerViewHinhBinhLuan.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewHinhBinhLuan.setNestedScrollingEnabled(false);
        mRecyclerViewHinhBinhLuan.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CODE_THEM_HINH){
                mHinhAnhs = data.getParcelableArrayListExtra("HinhAnhChecked");
                mAdapterHinhBinhLuanChecked = new AdapterHinhBinhLuanChecked(BinhLuanActivity.this, mHinhAnhs);
                mRecyclerViewHinhBinhLuan.setAdapter(mAdapterHinhBinhLuanChecked);
            }
        }
    }
}
