package com.philong.foody.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.philong.foody.R;
import com.philong.foody.adapter.AdapterHinhBinhLuanChecked;
import com.philong.foody.controller.service.PushImage;
import com.philong.foody.model.BinhLuan;
import com.philong.foody.model.HinhAnh;
import com.philong.foody.model.QuanAn;

import java.util.ArrayList;
import java.util.List;

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
    private ImageButton mPostImageButton;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerViewHinhBinhLuan;
    private AdapterHinhBinhLuanChecked mAdapterHinhBinhLuanChecked;
    private ArrayList<HinhAnh> mHinhAnhs;
    private QuanAn mQuanAn;
    private BinhLuan mBinhLuan;
    private List<String> mHinhAnhBinhLuanList;
    private RelativeLayout mRelativeLayout;

    public static Intent newIntent(Context context, QuanAn quanAn){
        Intent intent = new Intent(context, BinhLuanActivity.class);
        intent.putExtra("QuanAnBinhLuan", quanAn);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binh_luan);
        mHinhAnhs = new ArrayList<>();
        mHinhAnhBinhLuanList = new ArrayList<>();
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
        mPostImageButton = (ImageButton) findViewById(R.id.binh_luan_dang_image_button);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relative_layout);

        if(getSupportActionBar() == null){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if(getIntent() != null){
            mQuanAn = getIntent().getParcelableExtra("QuanAnBinhLuan");
            mTenQuanAnTextView.setText(mQuanAn.getTenquanan());
            mDiaChiQuanAnTextView.setText(mQuanAn.getChinhanh().get(0).getDiachi());
        }

        mThemHinhAnhImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(ThemHinhAnhActivity.newIntent(BinhLuanActivity.this), REQUEST_CODE_THEM_HINH);
            }
        });
        //set recyclerview

        mRecyclerViewHinhBinhLuan = (RecyclerView) findViewById(R.id.binh_luan_recycler_view);
        mRecyclerViewHinhBinhLuan.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewHinhBinhLuan.setNestedScrollingEnabled(false);
        mRecyclerViewHinhBinhLuan.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        // Post binh luận;
        mBinhLuan = new BinhLuan();
        mPostImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tieuDe = mTieuDeEditText.getText().toString().trim();
                String noiDung = mNoiDungEditText.getText().toString().trim();
                if(!TextUtils.isEmpty(tieuDe) && !TextUtils.isEmpty(noiDung)){
                    mBinhLuan.setTieude(tieuDe);
                    mBinhLuan.setNoidung(noiDung);
                    mBinhLuan.setChamdiem(0.0);
                    mBinhLuan.setLuotthich(0);
                    mBinhLuan.setMauser(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    themBinhLuan(mBinhLuan, mHinhAnhs, mQuanAn.getMaquanan());
                    Animation anim = AnimationUtils.loadAnimation(BinhLuanActivity.this, R.anim.animation_post);
                    mRelativeLayout.startAnimation(anim);
                    mTieuDeEditText.setText("");
                    mNoiDungEditText.setText("");
                    mTieuDeEditText.requestFocus();
                }else{
                    Toast.makeText(BinhLuanActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                for(HinhAnh h : mHinhAnhs){
                    mHinhAnhBinhLuanList.add(h.getPath());
                }
            }
        }
    }

    public void themBinhLuan(BinhLuan binhLuan, final ArrayList<HinhAnh> hinhAnhs, String maQuanAn){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("binhluans");
        final String key = databaseReference.child(maQuanAn).push().getKey();
        databaseReference.child(maQuanAn).child(key).setValue(binhLuan).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                   if(hinhAnhs.size() > 0){
                       for(HinhAnh hinhAnh : hinhAnhs){
                           startService(PushImage.newIntent(BinhLuanActivity.this, hinhAnh, key));
                       }
                   }
                }
            }
        });
    }
}
