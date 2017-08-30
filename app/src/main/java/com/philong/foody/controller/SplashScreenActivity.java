package com.philong.foody.controller;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

import com.philong.foody.R;

public class SplashScreenActivity extends AppCompatActivity {

    private TextView mEmailTextView;
    private TextView mVersionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mEmailTextView = (TextView)findViewById(R.id.splash_screen_email_text);
        mVersionTextView = (TextView)findViewById(R.id.splash_screen_version_text);

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            mVersionTextView.setText("Version " + String.valueOf(packageInfo.versionName));
            mEmailTextView.setText("dieuhoangphilong@gmail.com");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(DangNhapActivity.newIntent(SplashScreenActivity.this));
                finish();
            }
        }, 2000);
    }
}
