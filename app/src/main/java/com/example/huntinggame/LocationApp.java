package com.example.huntinggame;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.huntinggame.activities.Activity_ButtonsGame;
import com.google.android.gms.maps.model.LatLng;

public class LocationApp {
    private android.location.Location location;
    private LocationManager locationManager;
    private double latitude;
    private double longitude;
    private final LatLng defaultLocation = new LatLng(32.324070, 34.858021);
    private float LOCATION_REFRESH_DISTANCE = 100;
    private long LOCATION_REFRESH_TIME = 15000;

    public final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final android.location.Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
    };

    public void setLocationSettings(AppCompatActivity appCompatActivity) {
        Criteria locationCriteria = new Criteria();
        locationCriteria.setAccuracy(Criteria.ACCURACY_FINE);
        locationCriteria.setAltitudeRequired(false);
        locationCriteria.setBearingRequired(false);
        locationCriteria.setCostAllowed(true);
        locationCriteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
        locationManager = (LocationManager) appCompatActivity.getSystemService(Context.LOCATION_SERVICE);
        @SuppressLint("MissingPermission")
        ActivityResultLauncher<String[]> locationPermissionRequest =
                appCompatActivity.registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION, false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                // Precise location access granted.
                                // Then update all the time and at every meters change.
                                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                                        0, mLocationListener);
                                String providerName = locationManager.getBestProvider(locationCriteria, true);
                                location = locationManager.getLastKnownLocation(providerName);
                                if (location == null) {
                                    latitude = defaultLocation.latitude;
                                    longitude = defaultLocation.longitude;
                                } else {
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                }
                                LatLng curLoc = new LatLng(latitude, longitude);
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                // Only approximate location access granted.
                                // Then update every limited time and at every limited meters change.
                                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                                        LOCATION_REFRESH_DISTANCE, mLocationListener);
                                String providerName = locationManager.getBestProvider(locationCriteria, true);
                                location = locationManager.getLastKnownLocation(providerName);
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                LatLng curLoc = new LatLng(latitude, longitude);
                            } else {
                                // No location access granted.
                                // Then we can't use the location track and the 1st score location functionality is disabled.
                                // That's why we have set the default location to Ness Ziona(could be anywhere else just need default).
                            }
                        }
                );
        // check whether the app already has the permissions,
        // and whether the app needs to show a permission rationale dialog.
        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
