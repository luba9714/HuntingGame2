package com.example.huntinggame.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.huntinggame.R;
import com.example.huntinggame.Score;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class Fragment_Map extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ArrayList<Score> scores;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup cotainer, Bundle savedInstanceState){
        View view =inflater.inflate(R.layout.fragment_map,cotainer,false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        scores=new ArrayList<>();


        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        initMap();


    }
    public void setListScores(ArrayList<Score> scoreList) {

        this.scores=scoreList;
    }

    private void initMap() {
        //init map
        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                mMap = googleMap;
                for(Score s : scores){
                    if(!(s.getLatitude() == 0 && s.getLongitude() ==0)) {
                        LatLng latLng = new LatLng(s.getLatitude(), s.getLongitude());
                        googleMap.addMarker(new MarkerOptions().position(latLng)
                                .title("LocationApp of " + s.getNumOfScore()));
                    }
                }
            }
        });
    }

    public void setLocation(double latitude,double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
        mMap.moveCamera(cameraUpdate);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }



}
