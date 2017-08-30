package com.philong.foody.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.philong.foody.R;
import com.philong.foody.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {


    private Toolbar mToolbar;
    private RadioGroup mMainRadioGroup;
    private RadioButton mRadioAnGi;
    private RadioButton mRadioODau;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private boolean isClose = false;

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mMainRadioGroup = (RadioGroup)findViewById(R.id.main_radio_group);
        mRadioAnGi = (RadioButton)findViewById(R.id.main_radio_an_gi);
        mRadioODau = (RadioButton)findViewById(R.id.main_radio_o_dau);
        mRadioODau.setChecked(true);
        if(getSupportActionBar() == null){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle("");
        }
        mViewPager = (ViewPager)findViewById(R.id.main_view_pager);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position == 0){
                    mRadioODau.setChecked(true);
                    mRadioAnGi.setChecked(false);
                }else{
                    mRadioAnGi.setChecked(true);
                    mRadioODau.setChecked(false);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mRadioODau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(0);
            }
        });
        mRadioAnGi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(1);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(isClose){
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            super.onBackPressed();
        }else{
            Toast.makeText(this, "Bấm thêm 1 lần nữa để thoát", Toast.LENGTH_SHORT).show();
            isClose = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isClose = false;
                }
            }, 2000);
        }
    }
}
