package com.philong.foody.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.philong.foody.R;

public class MainActivity extends AppCompatActivity {

    private boolean isClose = false;

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
