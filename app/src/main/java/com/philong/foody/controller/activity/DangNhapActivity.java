package com.philong.foody.controller.activity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.philong.foody.R;

public class DangNhapActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener
        , View.OnClickListener {

    private static final int RC_SIGN_IN_GOOGLE = 99;

    private static final int RC_SIGN_IN_FACEBOOK = 100;


    private Button mDangNhapEmail;
    private SignInButton mSignInButton;
    private LoginButton mLoginButton;
    private TextView mDangKyTextView;
    private TextView mQuenMatKhauTextView;
    private EditText mEmailEditText;
    private EditText mMatKhauEditText;

    private String mEmail;
    private String mMatKhau;


    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    //Facebook
    private CallbackManager mCallbackManager;


    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, DangNhapActivity.class);
        return intent;
    }

    public static Intent newIntent(Context context, String email, String matKhau){
        Intent intent = new Intent(context, DangNhapActivity.class);
        intent.putExtra("Email", email);
        intent.putExtra("MatKhau", matKhau);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        //Get View
        mDangNhapEmail = (Button)findViewById(R.id.dang_nhap_button);
        mSignInButton = (SignInButton)findViewById(R.id.dang_nhap_google_button);
        mLoginButton = (LoginButton)findViewById(R.id.dang_nhap_facebook_button);
        mDangNhapEmail = (Button)findViewById(R.id.dang_nhap_button);
        mQuenMatKhauTextView = (TextView)findViewById(R.id.dang_nhap_quen_mat_khau_text_view);
        mDangKyTextView = (TextView)findViewById(R.id.dang_nhap_dang_ky_text_view);
        mEmailEditText = (EditText)findViewById(R.id.dang_nhap_email_edit_text);
        mMatKhauEditText = (EditText)findViewById(R.id.dang_nhap_mat_khau_edit_text);
        mDangNhapEmail.setOnClickListener(this);
        mQuenMatKhauTextView.setOnClickListener(this);
        mDangKyTextView.setOnClickListener(this);
        mSignInButton.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
        //Google
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
        mAuth = FirebaseAuth.getInstance();
        //Facebook
        mCallbackManager = CallbackManager.Factory.create();
        mLoginButton.setReadPermissions("email", "public_profile");
        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        if(getIntent() != null){
            mEmail = getIntent().getStringExtra("Email");
            mMatKhau = getIntent().getStringExtra("MatKhau");
            mEmailEditText.setText(mEmail);
            mMatKhauEditText.setText(mMatKhau);
        }
        signOutGoogle();
        signOutFacebook();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //UpdateUI
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN_GOOGLE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }else{

            }
        }else{
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id){
            case R.id.dang_nhap_google_button:
                signInGoogle();
                break;
            case R.id.dang_nhap_button:
                String email = mEmailEditText.getText().toString().trim();
                String matKhau = mMatKhauEditText.getText().toString().trim();
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(matKhau)){
                    Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show();
                }else {
                    mAuth.signInWithEmailAndPassword(email, matKhau)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                                            @Override
                                            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                                                checkSignIn(firebaseAuth);
                                            }
                                        });
                                    }else{
                                        Toast.makeText(DangNhapActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                break;
            case R.id.dang_nhap_quen_mat_khau_text_view:
                startActivity(QuenMatKhauActivity.newIntent(this));
                break;
            case R.id.dang_nhap_dang_ky_text_view:
                startActivity(DangKyActivity.newIntent(this));
                break;

        }
    }


    private void signInGoogle(){
        Intent signInIntent =  Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN_GOOGLE);
    }

    private void signOutGoogle(){
        mAuth.signOut();
    }

    private void signOutFacebook(){
        LoginManager.getInstance().logOut();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount googleSignInAccount){
        AuthCredential credential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                        }else{

                        }
                    }
                });
    }

    private void checkSignIn(FirebaseAuth firebaseAuth){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            startActivity(MainActivity.newIntent(this));
        }
    }

    //Facebook
    private void handleFacebookAccessToken(AccessToken token){
        AuthCredential authCredential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                                @Override
                                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                                    checkSignIn(firebaseAuth);
                                }
                            });
                        }else{
                            Toast.makeText(DangNhapActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
