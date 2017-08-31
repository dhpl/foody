package com.philong.foody.controller.activity;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.philong.foody.R;

public class SplashScreenActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks
        , GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_CODE_PERMISSION_LOCATION = 123;


    private TextView mEmailTextView;
    private TextView mVersionTextView;

    private GoogleApiClient mGoogleApiClient;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mEmailTextView = (TextView)findViewById(R.id.splash_screen_email_text);
        mVersionTextView = (TextView)findViewById(R.id.splash_screen_version_text);
        //Sharepreference
        mSharedPreferences = getSharedPreferences("Location", MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        //Get Location

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();



        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            mVersionTextView.setText("Version " + String.valueOf(packageInfo.versionName));
            mEmailTextView.setText("dieuhoangphilong@gmail.com");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        updatePermisionLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void updatePermisionLocation(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkPermissionLocation()){
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    System.out.println("Latitude: " + location.getLatitude());
                    System.out.println("Longitude: " + location.getLongitude());
                    mEditor.putString("Latitude", String.valueOf(location.getLatitude()));
                    mEditor.putString("Longitude", String.valueOf(location.getLongitude()));
                    mEditor.commit();
                }
            }else{
                Toast.makeText(this, "Cần quyên location", Toast.LENGTH_SHORT).show();
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

    public boolean checkPermissionLocation(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_PERMISSION_LOCATION);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case REQUEST_CODE_PERMISSION_LOCATION:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                   updatePermisionLocation();
                }
                return;
        }
    }
}
