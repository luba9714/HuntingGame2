package com.example.huntinggame;

import com.google.android.gms.maps.model.LatLng;

public class Score {
    private int numOfScore;
    private double latitude;
    private double longitude;


    public int getNumOfScore() {
        return numOfScore;

    }

    public void setNumOfScore(int numOfScore) {
        this.numOfScore = numOfScore;
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
