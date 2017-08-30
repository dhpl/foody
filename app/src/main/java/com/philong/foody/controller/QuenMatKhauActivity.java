package com.philong.foody.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.philong.foody.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuenMatKhauActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Button mQuenMatKhauButton;

    private EditText mEmailQuenMatKhauEditText;

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, QuenMatKhauActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);

        mAuth = FirebaseAuth.getInstance();
        mQuenMatKhauButton = (Button)findViewById(R.id.quen_mat_khau_button);
        mEmailQuenMatKhauEditText = (EditText)findViewById(R.id.quen_mat_khau_edit_text);

        mQuenMatKhauButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailQuenMatKhauEditText.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(QuenMatKhauActivity.this, "Email không được để trống", Toast.LENGTH_SHORT).show();
                }else {
                    Pattern pattern = Patterns.EMAIL_ADDRESS;
                    Matcher matcher = pattern.matcher(email);
                    if (matcher.find()) {
                        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(QuenMatKhauActivity.this, "Reset mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(QuenMatKhauActivity.this, "Định dạng email không đúng", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
