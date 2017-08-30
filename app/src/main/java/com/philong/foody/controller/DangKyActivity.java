package com.philong.foody.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.philong.foody.R;

public class DangKyActivity extends AppCompatActivity{

    private Button mDangKyButton;
    private EditText mEmailEditText;
    private EditText mMatKhauEditText;
    private EditText mNhapLaiMatKhauEditText;

    private String mEmail;
    private String mMatKhau;
    private String mNhapLaiMatKhau;

    //Firebase
    private FirebaseAuth mAuth;


    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, DangKyActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        //Get view
        mDangKyButton = (Button)findViewById(R.id.dang_ky_button);
        mEmailEditText = (EditText)findViewById(R.id.dang_ky_email_edit_text);
        mMatKhauEditText = (EditText)findViewById(R.id.dang_ky_mat_khau_edit_text);
        mNhapLaiMatKhauEditText = (EditText)findViewById(R.id.dang_ky_nhap_lai_mat_khau_edit_text);
        //Firebase
        mAuth = FirebaseAuth.getInstance();
        //Register
        mDangKyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEmail = mEmailEditText.getText().toString().trim();
                mMatKhau = mMatKhauEditText.getText().toString().trim();
                mNhapLaiMatKhau = mNhapLaiMatKhauEditText.getText().toString().trim();
                if(TextUtils.isEmpty(mEmail) || TextUtils.isEmpty(mMatKhau) || TextUtils.isEmpty(mNhapLaiMatKhau)){
                    Toast.makeText(DangKyActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                }else{
                    if(!mMatKhau.equals(mNhapLaiMatKhau)){
                        Toast.makeText(DangKyActivity.this, "Mật khẩu phải trùng nhau", Toast.LENGTH_SHORT).show();
                    }else{
                        mAuth.createUserWithEmailAndPassword(mEmail, mMatKhau)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                                                @Override
                                                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                                                    checkLogin(firebaseAuth);
                                                }
                                            });
                                        }else{
                                            Toast.makeText(DangKyActivity.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
    }



    private void checkLogin(FirebaseAuth firebaseAuth){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            startActivity(DangNhapActivity.newIntent(this, mEmail, mMatKhau));
        }
    }
}
