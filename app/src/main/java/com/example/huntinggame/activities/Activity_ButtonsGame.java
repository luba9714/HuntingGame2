package com.example.huntinggame.activities;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;


import android.widget.ImageView;

import com.example.huntinggame.GameManager;
import com.example.huntinggame.LocationApp;
import com.example.huntinggame.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.textview.MaterialTextView;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Activity_ButtonsGame extends AppCompatActivity {
    private ImageView main_IMG_11,main_IMG_12,main_IMG_13,main_IMG_21,main_IMG_22,
            main_IMG_23,main_IMG_31,main_IMG_32,main_IMG_33,main_IMG_41, main_IMG_42,main_IMG_43,main_IMG_51,main_IMG_52,main_IMG_53;
    private ImageView main_IMG_up,main_IMG_down,main_IMG_left,main_IMG_right;
    private ImageView main_IMG_heart1,main_IMG_heart2,main_IMG_heart3;
    private ImageView imagesMat[][];
    private ImageView hearts[];
    private MaterialTextView main_LBL_score;
    private enum TIMER_STATUS {RUNNING,PAUSE}
    private TIMER_STATUS timerStatus;
    private GameManager game;
    private LocationApp location;
    private int counter=0;
    private Random ran=new Random();
    private List<String> givenList = Arrays.asList("UP", "DOWN", "LEFT", "RIGHT");
    private Handler handler=new Handler();
    private final int delay= 1000;
    private Runnable r= new Runnable() {
        @Override
        public void run() {
            counter++;
            if(counter>10){
                imagesMat[game.getRowSnitch()][game.getColSnitch()].setImageResource(0);
            }
            score();
            runAway();
            goHunt();
            if(game.isEnd()){
                Intent intent=new Intent(Activity_ButtonsGame.this, Activity_EndGame.class);
                intent.putExtra("SCORE",main_LBL_score.getText()+"");
                intent.putExtra("LATITUDE",location.getLatitude());
                intent.putExtra("LONGITUDE",location.getLongitude());
                startActivity(intent);
                finish();
                handler.removeCallbacks(this);
            }
            handler.postDelayed(this,delay);
        }
    };
//    private final LocationListener mLocationListener = new LocationListener() {
//        @Override
//        public void onLocationChanged(final LocationApp location) {
//            latitude = location.getLatitude();
//            longitude = location.getLongitude();
//        }
//    };

//    private void setLocationSettings() {
//        Criteria locationCriteria = new Criteria();
//        locationCriteria.setAccuracy(Criteria.ACCURACY_FINE);
//        locationCriteria.setAltitudeRequired(false);
//        locationCriteria.setBearingRequired(false);
//        locationCriteria.setCostAllowed(true);
//        locationCriteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
//        locationManager = (LocationManager) Activity_ButtonsGame.this.getSystemService(LOCATION_SERVICE);
//        @SuppressLint("MissingPermission")
//        ActivityResultLauncher<String[]> locationPermissionRequest =
//                registerForActivityResult(new ActivityResultContracts
//                                .RequestMultiplePermissions(), result -> {
//                            Boolean fineLocationGranted = result.getOrDefault(
//                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
//                            Boolean coarseLocationGranted = result.getOrDefault(
//                                    Manifest.permission.ACCESS_COARSE_LOCATION, false);
//                            if (fineLocationGranted != null && fineLocationGranted) {
//                                // Precise location access granted.
//                                // Then update all the time and at every meters change.
//                                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
//                                        0, mLocationListener);
//                                String providerName = locationManager.getBestProvider(locationCriteria, true);
//                                location = locationManager.getLastKnownLocation(providerName);
//                                if (location == null) {
//                                    latitude = defaultLocation.latitude;
//                                    longitude = defaultLocation.longitude;
//                                } else {
//                                    latitude = location.getLatitude();
//                                    longitude = location.getLongitude();
//                                }
//                                LatLng curLoc = new LatLng(latitude, longitude);
//                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
//                                // Only approximate location access granted.
//                                // Then update every limited time and at every limited meters change.
//                                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
//                                        LOCATION_REFRESH_DISTANCE, mLocationListener);
//                                String providerName = locationManager.getBestProvider(locationCriteria, true);
//                                location = locationManager.getLastKnownLocation(providerName);
//                                latitude = location.getLatitude();
//                                longitude = location.getLongitude();
//                                LatLng curLoc = new LatLng(latitude, longitude);
//                            } else {
//                                // No location access granted.
//                                // Then we can't use the location track and the 1st score location functionality is disabled.
//                                // That's why we have set the default location to Ness Ziona(could be anywhere else just need default).
//                            }
//                        }
//                );
//        // check whether the app already has the permissions,
//        // and whether the app needs to show a permission rationale dialog.
//        locationPermissionRequest.launch(new String[]{
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//        });
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons);
        location=new LocationApp();
        location.setLocationSettings(this);
//        MediaPlayer song = MediaPlayer.create();
//        song.start();

        main_LBL_score=findViewById(R.id.main_LBL_score);
        game=new GameManager(5,3);
        imagesMat=new ImageView[game.getMaxRow()][game.getMaxCol()];
        hearts=new ImageView[3];
        setFindViewsByIdInMat();
        setFindViewsByIdInHeartArray();
        setFindViewsByIdForDirections();
        setClick();
        startTimer();
    }
    private void setClick() {
        main_IMG_up.setOnClickListener(View->game.setDirection("UP"));
        main_IMG_down.setOnClickListener(View-> game.setDirection("DOWN"));
        main_IMG_left.setOnClickListener(View-> game.setDirection("LEFT"));
        main_IMG_right.setOnClickListener(View->game.setDirection("RIGHT"));
    }

    private void setHauntedAfterCatch() {
        imagesMat[1][1].setImageResource(R.drawable.ic_harry);
        game.setRowHaunted(1);
        game.setColHaunted(1);
        if(game.getLives()>1){
            hearts[game.getLives()-1].setImageResource(0);
            game.loseLives();
        }else {
            imagesMat[game.getRowHaunted()][game.getColHaunted()].setImageResource(0);
            game.setIsEnd(true);
        }
    }
    private void setSnitch() {
        Random rS=new Random();
        imagesMat[game.getRowSnitch()][game.getColSnitch()].setImageResource(0);
        game.setScore(game.getScore()+20);
        do {
            game.setRowSnitch(rS.nextInt(game.getMaxRow()-1));
            game.setColSnitch(rS.nextInt(game.getMaxCol()-1));
        }while((game.getColSnitch()==game.getColHaunted() && game.getRowSnitch()==game.getRowHunter())
                || (game.getRowSnitch()==game.getRowHunter() && game.getColSnitch()==game.getColHunter()));
        imagesMat[game.getRowSnitch()][game.getColSnitch()].setImageResource(R.drawable.ic_snitch);
        imagesMat[game.getRowHaunted()][game.getColHaunted()].setImageResource(R.drawable.ic_harry);

    }

    private void goHunt() {
        String direct=givenList.get(ran.nextInt(givenList.size()));


        if (game.getRowHunter() > 0 && direct.equals("UP")) {
            imagesMat[game.getRowHunter()][game.getColHunter()].setImageResource(0);
            if(game.getRowHunter() == game.getRowSnitch() && game.getColHunter() == game.getColSnitch()){
                imagesMat[game.getRowSnitch()][game.getColSnitch()].setImageResource(R.drawable.ic_snitch);
            }

            if (game.getRowHunter() - 1 == game.getRowHaunted() && game.getColHunter() == game.getColHaunted()) {// a catch
                setHauntedAfterCatch();
            }
            game.setRowHunter(game.getRowHunter() - 1);
            imagesMat[game.getRowHunter()][game.getColHunter()].setImageResource(R.drawable.ic_voldemort);

        }
        if (game.getRowHunter() < game.getMaxRow() - 1 && direct.equals("DOWN") ) {
            imagesMat[game.getRowHunter()][game.getColHunter()].setImageResource(0);
            if(game.getRowHunter() == game.getRowSnitch() && game.getColHunter() == game.getColSnitch()){
                imagesMat[game.getRowSnitch()][game.getColSnitch()].setImageResource(R.drawable.ic_snitch);
            }

            if (game.getRowHunter() + 1 == game.getRowHaunted() && game.getColHunter() == game.getColHaunted()) {// a catch
                setHauntedAfterCatch();
            }
            game.setRowHunter(game.getRowHunter() + 1);
            imagesMat[game.getRowHunter()][game.getColHunter()].setImageResource(R.drawable.ic_voldemort);

        }
        if (game.getColHunter() > 0 && direct.equals("LEFT")) {
            imagesMat[game.getRowHunter()][game.getColHunter()].setImageResource(0);
            if(game.getRowHunter() == game.getRowSnitch() && game.getColHunter() == game.getColSnitch()){
                imagesMat[game.getRowSnitch()][game.getColSnitch()].setImageResource(R.drawable.ic_snitch);
            }

            if (game.getRowHunter() == game.getRowHaunted() && game.getColHunter() - 1 == game.getColHaunted()) {// a catch
                setHauntedAfterCatch();
            }
            game.setColHunter(game.getColHunter() - 1);
            imagesMat[game.getRowHunter()][game.getColHunter()].setImageResource(R.drawable.ic_voldemort);

        }
        if (game.getColHunter() < game.getMaxCol() - 1 && direct.equals("RIGHT")) {
            imagesMat[game.getRowHunter()][game.getColHunter()].setImageResource(0);
            if(game.getRowHunter() == game.getRowSnitch() && game.getColHunter() == game.getColSnitch()){
                imagesMat[game.getRowSnitch()][game.getColSnitch()].setImageResource(R.drawable.ic_snitch);
            }

            if (game.getRowHunter() == game.getRowHaunted() && game.getColHunter() + 1 == game.getColHaunted()) {// a catch
                setHauntedAfterCatch();
            }
            game.setColHunter(game.getColHunter() + 1);
            imagesMat[game.getRowHunter()][game.getColHunter()].setImageResource(R.drawable.ic_voldemort);
        }

    }
    public void runAway(){

        if (game.getRowHaunted() > 0 && game.getDirection().equals("UP")) {
            imagesMat[game.getRowHaunted()][game.getColHaunted()].setImageResource(0);
            if(game.ifACatchWhenUp()){
                setHauntedAfterCatch();

            }else if(game.getRowSnitch()==game.getRowHaunted()-1 && game.getColSnitch()==game.getColHaunted()) {
                game.setRowHaunted(game.getRowHaunted() + 1);
                setSnitch();

            }else{
                game.setRowHaunted(game.getRowHaunted() - 1);
                imagesMat[game.getRowHaunted()][game.getColHaunted()].setImageResource(R.drawable.ic_harry);
            }
        }
        if (game.getRowHaunted() < game.getMaxRow() - 1 && game.getDirection().equals("DOWN") ) {
            imagesMat[game.getRowHaunted()][game.getColHaunted()].setImageResource(0);
            if(game.ifACatchWhenDown()){
                setHauntedAfterCatch();

            }else if(game.getRowSnitch()==game.getRowHaunted()+1 && game.getColSnitch()==game.getColHaunted()) {
                game.setRowHaunted(game.getRowHaunted() + 1);
                setSnitch();
            }else {
                game.setRowHaunted(game.getRowHaunted() + 1);
                imagesMat[game.getRowHaunted()][game.getColHaunted()].setImageResource(R.drawable.ic_harry);
            }
        }
        if (game.getColHaunted() > 0 && game.getDirection().equals("LEFT")) {
            imagesMat[game.getRowHaunted()][game.getColHaunted()].setImageResource(0);
            if(game.ifACatchWhenLeft()){
                setHauntedAfterCatch();

            }else if(game.getRowSnitch()==game.getRowHaunted() && game.getColSnitch()==game.getColHaunted()-1) {
                game.setColHaunted(game.getColHaunted() - 1);
                setSnitch();
            }else {
                game.setColHaunted(game.getColHaunted() - 1);
                imagesMat[game.getRowHaunted()][game.getColHaunted()].setImageResource(R.drawable.ic_harry);
            }
        }
        if (game.getColHaunted() < game.getMaxCol() - 1 && game.getDirection().equals("RIGHT")) {
            imagesMat[game.getRowHaunted()][game.getColHaunted()].setImageResource(0);
            if(game.ifACatchWhenRight()){
                setHauntedAfterCatch();
            }else if(game.getRowSnitch()==game.getRowHaunted() && game.getColSnitch()==game.getColHaunted()+1) {
                game.setColHaunted(game.getColHaunted() + 1);
                setSnitch();
            }else {
                game.setColHaunted(game.getColHaunted() + 1);
                imagesMat[game.getRowHaunted()][game.getColHaunted()].setImageResource(R.drawable.ic_harry);
            }
        }
    }



    //Timer
    private void startTimer(){
        timerStatus=TIMER_STATUS.RUNNING;
        handler.postDelayed(r,delay);
    }
    private void stopTimer(){
        timerStatus=TIMER_STATUS.PAUSE;
        handler.removeCallbacks(r);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(timerStatus==TIMER_STATUS.PAUSE){
            startTimer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(timerStatus==TIMER_STATUS.RUNNING){
            stopTimer();
            timerStatus=TIMER_STATUS.PAUSE;
        }
    }

    private void score(){
        game.setScore(game.getScore()+1);
        main_LBL_score.setText(""+game.getScore());
    }
    private void setFindViewsByIdInHeartArray() {
        main_IMG_heart1=findViewById(R.id.main_IMG_heart1);
        main_IMG_heart2=findViewById(R.id.main_IMG_heart2);
        main_IMG_heart3=findViewById(R.id.main_IMG_heart3);
        hearts[0]=main_IMG_heart1;
        hearts[1]=main_IMG_heart2;
        hearts[2]=main_IMG_heart3;
    }
    private void setFindViewsByIdForDirections() {
        main_IMG_up=findViewById(R.id.main_IMG_up);
        main_IMG_down=findViewById(R.id.main_IMG_down);
        main_IMG_left=findViewById(R.id.main_IMG_left);
        main_IMG_right=findViewById(R.id.main_IMG_right);
    }
    private void setFindViewsByIdInMat() {
        main_IMG_11=findViewById(R.id.main_IMG_11);
        imagesMat[0][0]=main_IMG_11;
        main_IMG_12=findViewById(R.id.main_IMG_12);
        imagesMat[0][1]=main_IMG_12;
        main_IMG_13=findViewById(R.id.main_IMG_13);
        imagesMat[0][2]=main_IMG_13;

        main_IMG_21=findViewById(R.id.main_IMG_21);
        imagesMat[1][0]=main_IMG_21;
        main_IMG_22=findViewById(R.id.main_IMG_22);
        imagesMat[1][1]=main_IMG_22;//Haunted=harry
        game.setColHaunted(1);
        game.setRowHaunted(1);
        main_IMG_23=findViewById(R.id.main_IMG_23);
        imagesMat[1][2]=main_IMG_23;

        main_IMG_31=findViewById(R.id.main_IMG_31);
        imagesMat[2][0]=main_IMG_31;//snitch
        game.setRowSnitch(2);
        game.setColSnitch(0);

        main_IMG_32=findViewById(R.id.main_IMG_32);
        imagesMat[2][1]=main_IMG_32;//Hunter

        main_IMG_33=findViewById(R.id.main_IMG_33);
        imagesMat[2][2]=main_IMG_33;

        main_IMG_41=findViewById(R.id.main_IMG_41);
        imagesMat[3][0]=main_IMG_41;
        main_IMG_42=findViewById(R.id.main_IMG_42);
        imagesMat[3][1]=main_IMG_42;
        main_IMG_43=findViewById(R.id.main_IMG_43);
        imagesMat[3][2]=main_IMG_43;//voldemort
        game.setColHunter(2);
        game.setRowHunter(3);
        main_IMG_51=findViewById(R.id.main_IMG_51);
        imagesMat[4][0]=main_IMG_51;
        main_IMG_52=findViewById(R.id.main_IMG_52);
        imagesMat[4][1]=main_IMG_52;
        main_IMG_53=findViewById(R.id.main_IMG_53);
        imagesMat[4][2]=main_IMG_53;

    }
}