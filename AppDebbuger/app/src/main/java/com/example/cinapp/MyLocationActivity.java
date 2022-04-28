package com.example.cinapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyLocationActivity extends Activity implements LocationListener {

    private Location location;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        updateLocationText(location);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        updateLocationText(location);
    }

    @SuppressLint("SetTextI18n")
    private void updateLocationText(Location location) {
        this.location = location;
        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
    }
}
