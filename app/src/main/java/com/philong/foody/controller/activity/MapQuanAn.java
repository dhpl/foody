package com.philong.foody.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.philong.foody.R;
import com.philong.foody.util.DownloadJson;

import java.util.List;

/**
 * Created by Long on 9/4/2017.
 */

public class MapQuanAn extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap mGoogleMap;
    private MapFragment mMapFragment;
    private Location mLocationCurrent;
    private Location mLocationQuanAn;
    private SharedPreferences mSharedPreferences;
    //AIzaSyDTUTcEQB0-a_9ja-LLbeCCqcDDnAdwu6Y
    //https://maps.googleapis.com/maps/api/directions/json?origin=Toronto&destination=Montreal&key=YOUR_API_KEY


    public static Intent newIntent(Context context, double lat, double lng){
        Intent intent = new Intent(context, MapQuanAn.class);
        intent.putExtra("LatQuanAn", lat);
        intent.putExtra("LngQuanAn", lng);
        return intent;
    }


    @Override
    public void onCreate(Bundle bundle) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(bundle);
        setContentView(R.layout.activity_map_quan_an);
        mSharedPreferences = getSharedPreferences("Location", MODE_PRIVATE);
        mLocationCurrent = new Location("c");
        mLocationCurrent.setLatitude(Double.parseDouble(mSharedPreferences.getString("Latitude", "")));
        mLocationCurrent.setLongitude(Double.parseDouble(mSharedPreferences.getString("Longitude", "")));
        if(getIntent() != null){
            mLocationQuanAn = new Location("q");
            mLocationQuanAn.setLatitude(getIntent().getDoubleExtra("LatQuanAn", 0.0));
            mLocationQuanAn.setLongitude(getIntent().getDoubleExtra("LngQuanAn", 0.0));

        }
        mMapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        LatLng currentPoint = new LatLng(mLocationCurrent.getLatitude(), mLocationCurrent.getLongitude());
        LatLng quanAnPoint = new LatLng(mLocationQuanAn.getLatitude(), mLocationQuanAn.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentPoint);
        mGoogleMap.addMarker(markerOptions);
        mGoogleMap.addMarker(new MarkerOptions().position(quanAnPoint));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentPoint, 14);
        mGoogleMap.animateCamera(cameraUpdate);
        final PolylineOptions polylineOptions = new PolylineOptions();
        new DownloadJson(new DownloadJson.GetPolyLine() {
            @Override
            public void compleGetPolyLine(List<LatLng> latLngs) {
                for(LatLng latLng : latLngs){
                    polylineOptions.add(latLng);
                }
                Polyline polyline = mGoogleMap.addPolyline(polylineOptions);
            }
        }).execute("https://maps.googleapis.com/maps/api/directions/json?origin="+ mLocationCurrent.getLatitude() + "," + mLocationCurrent.getLongitude()
                + "&destination=" + mLocationQuanAn.getLatitude() + "," + mLocationQuanAn.getLongitude() + "&key=" + "AIzaSyC-eJjaraDQTRojyNkpIDICvi-W0ME1DOs");



    }
}
