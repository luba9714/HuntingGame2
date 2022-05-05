package com.example.huntinggame.activities;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.huntinggame.GameManager;
import com.example.huntinggame.LocationApp;
import com.example.huntinggame.R;
import com.google.android.material.textview.MaterialTextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Activity_SensorsGame extends AppCompatActivity implements SensorEventListener {
    private ImageView main_IMG_11,main_IMG_12,main_IMG_13,main_IMG_14,main_IMG_15,main_IMG_21,main_IMG_22,
            main_IMG_23,main_IMG_24,main_IMG_25,main_IMG_31,main_IMG_32,main_IMG_33,main_IMG_34,main_IMG_35,main_IMG_41,
            main_IMG_42,main_IMG_43,main_IMG_44,main_IMG_45,main_IMG_51,main_IMG_52,main_IMG_53,main_IMG_54,main_IMG_55,
            main_IMG_61,main_IMG_62,main_IMG_63,main_IMG_64,main_IMG_65;
    private ImageView main_IMG_heart1,main_IMG_heart2,main_IMG_heart3;
    private ImageView imagesMat[][];
    private ImageView hearts[];
    private float x,y,z;
    private NumberFormat formatter =new DecimalFormat("#0.00");
    private MaterialTextView main_LBL_score;
    private enum TIMER_STATUS {RUNNING,PAUSE}
    private TIMER_STATUS timerStatus;
    private GameManager game;
    private int counter=1;
    private SensorManager sensorManager;
    private Sensor sensor;
    private Random ran=new Random();
    private List<String> givenList = Arrays.asList("UP", "DOWN", "LEFT", "RIGHT");
    private Handler handler=new Handler();
    private final int delay= 1000;
    private LocationApp location;

    private Runnable r= new Runnable() {
        @Override
        public void run() {
            counter++;
            if(counter>=10){
                imagesMat[game.getRowSnitch()][game.getColSnitch()].setImageResource(0);
            }
            score();
            runAway();
            goHunt();
            if(game.isEnd()){
                Intent intent=new Intent(Activity_SensorsGame.this, Activity_EndGame.class);
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


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        x=sensorEvent.values[0];
        y=sensorEvent.values[1];
        z=sensorEvent.values[2];
        Log.d("ccc", "messege "+x+" "+ y +" "+z);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_sensors);
        location=new LocationApp();
        location.setLocationSettings(this);
        main_LBL_score=findViewById(R.id.main_LBL_score);
        game=new GameManager(6,5);
        imagesMat=new ImageView[game.getMaxRow()][game.getMaxCol()];
        setFindViewsByIdInHeartArray();
        setFindViewsByIdInMat();
        //sensor
        sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        startTimer();




    }
    private void goHunt() {
        String direct=givenList.get(ran.nextInt(givenList.size()));

        if (game.getRowHunter() > 0 && direct.equals("UP")) {
            imagesMat[game.getRowHunter()][game.getColHunter()].setImageResource(0);
            if(game.getRowHunter() == game.getRowSnitch() && game.getColHunter() == game.getColSnitch()&& counter<10){
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
            if(game.getRowHunter() == game.getRowSnitch() && game.getColHunter() == game.getColSnitch()&& counter<10){
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
            if(game.getRowHunter() == game.getRowSnitch() && game.getColHunter() == game.getColSnitch() && counter<10){
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
            if(game.getRowHunter() == game.getRowSnitch() && game.getColHunter() == game.getColSnitch()&& counter<10 ){
                imagesMat[game.getRowSnitch()][game.getColSnitch()].setImageResource(R.drawable.ic_snitch);
            }

            if (game.getRowHunter() == game.getRowHaunted() && game.getColHunter() + 1 == game.getColHaunted()) {// a catch
                setHauntedAfterCatch();
            }
            game.setColHunter(game.getColHunter() + 1);
            imagesMat[game.getRowHunter()][game.getColHunter()].setImageResource(R.drawable.ic_voldemort);
        }

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
    public void runAway(){

        if (game.getRowHaunted() > 0 && y<7 && y>3 ) {
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
        if (game.getRowHaunted() < game.getMaxRow() - 1 && y>9 && z<0 ) {
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
        if (game.getColHaunted() > 0 && x>5) {
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
        if (game.getColHaunted() < game.getMaxCol() - 1 && x<-5) {
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
    private void setSnitch() {
        Random rS=new Random();
        imagesMat[game.getRowSnitch()][game.getColSnitch()].setImageResource(0);
        game.setScore(game.getScore()+20);
        do {
            game.setRowSnitch(rS.nextInt(game.getMaxRow()-1));
            game.setColSnitch(rS.nextInt(game.getMaxCol()-1));
        }while(game.checkIfSnitchErased());
        imagesMat[game.getRowSnitch()][game.getColSnitch()].setImageResource(R.drawable.ic_snitch);
        imagesMat[game.getRowHaunted()][game.getColHaunted()].setImageResource(R.drawable.ic_harry);

    }



    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }



    @Override
    protected void onStart() {
        super.onStart();
        if(timerStatus== TIMER_STATUS.PAUSE){
            startTimer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(timerStatus== TIMER_STATUS.RUNNING){
            stopTimer();
            timerStatus= TIMER_STATUS.PAUSE;
        }
    }

    private void score(){
        game.setScore(game.getScore()+1);
        main_LBL_score.setText(""+game.getScore());
    }

    //Timer
    private void startTimer(){
        timerStatus= TIMER_STATUS.RUNNING;
        handler.postDelayed(r,delay);
    }
    private void stopTimer(){
        timerStatus= TIMER_STATUS.PAUSE;
        handler.removeCallbacks(r);
    }


    private void setFindViewsByIdInHeartArray() {
        hearts=new ImageView[3];
        main_IMG_heart1=findViewById(R.id.main_IMG_heart1);
        main_IMG_heart2=findViewById(R.id.main_IMG_heart2);
        main_IMG_heart3=findViewById(R.id.main_IMG_heart3);
        hearts[0]=main_IMG_heart1;
        hearts[1]=main_IMG_heart2;
        hearts[2]=main_IMG_heart3;
    }

    private void setFindViewsByIdInMat() {
        main_IMG_11=findViewById(R.id.main_IMG_11);
        imagesMat[0][0]=main_IMG_11;
        main_IMG_12=findViewById(R.id.main_IMG_12);
        imagesMat[0][1]=main_IMG_12;
        main_IMG_13=findViewById(R.id.main_IMG_13);
        imagesMat[0][2]=main_IMG_13;
        main_IMG_14=findViewById(R.id.main_IMG_14);
        imagesMat[0][3]=main_IMG_14;
        main_IMG_15=findViewById(R.id.main_IMG_15);
        imagesMat[0][4]=main_IMG_15;

        main_IMG_21=findViewById(R.id.main_IMG_21);
        imagesMat[1][0]=main_IMG_21;
        main_IMG_22=findViewById(R.id.main_IMG_22);
        imagesMat[1][1]=main_IMG_22;
        main_IMG_23=findViewById(R.id.main_IMG_23);
        imagesMat[1][2]=main_IMG_23;
        main_IMG_24=findViewById(R.id.main_IMG_24);
        imagesMat[1][3]=main_IMG_24;//Haunted=harry
        game.setRowHaunted(1);
        game.setColHaunted(3);
        main_IMG_25=findViewById(R.id.main_IMG_25);
        imagesMat[1][4]=main_IMG_25;

        main_IMG_31=findViewById(R.id.main_IMG_31);
        imagesMat[2][0]=main_IMG_31;//snitch
        game.setRowSnitch(2);
        game.setColSnitch(0);
        main_IMG_32=findViewById(R.id.main_IMG_32);
        imagesMat[2][1]=main_IMG_32;
        main_IMG_33=findViewById(R.id.main_IMG_33);
        imagesMat[2][2]=main_IMG_33;
        main_IMG_34=findViewById(R.id.main_IMG_34);
        imagesMat[2][3]=main_IMG_34;
        main_IMG_35=findViewById(R.id.main_IMG_35);
        imagesMat[2][4]=main_IMG_35;

        main_IMG_41=findViewById(R.id.main_IMG_41);
        imagesMat[3][0]=main_IMG_41;
        main_IMG_42=findViewById(R.id.main_IMG_42);
        imagesMat[3][1]=main_IMG_42;
        main_IMG_43=findViewById(R.id.main_IMG_43);
        imagesMat[3][2]=main_IMG_43;//voldemort
        game.setColHunter(2);
        game.setRowHunter(3);
        main_IMG_44=findViewById(R.id.main_IMG_44);
        imagesMat[3][3]=main_IMG_44;
        main_IMG_45=findViewById(R.id.main_IMG_45);
        imagesMat[3][4]=main_IMG_45;

        main_IMG_51=findViewById(R.id.main_IMG_51);
        imagesMat[4][0]=main_IMG_51;
        main_IMG_52=findViewById(R.id.main_IMG_52);
        imagesMat[4][1]=main_IMG_52;
        main_IMG_53=findViewById(R.id.main_IMG_53);
        imagesMat[4][2]=main_IMG_53;
        main_IMG_54=findViewById(R.id.main_IMG_54);
        imagesMat[4][3]=main_IMG_54;
        main_IMG_55=findViewById(R.id.main_IMG_55);
        imagesMat[4][4]=main_IMG_55;

        main_IMG_61=findViewById(R.id.main_IMG_61);
        imagesMat[5][0]=main_IMG_61;
        main_IMG_62=findViewById(R.id.main_IMG_62);
        imagesMat[5][1]=main_IMG_62;
        main_IMG_63=findViewById(R.id.main_IMG_63);
        imagesMat[5][2]=main_IMG_63;
        main_IMG_64=findViewById(R.id.main_IMG_64);
        imagesMat[5][3]=main_IMG_64;
        main_IMG_65=findViewById(R.id.main_IMG_65);
        imagesMat[5][4]=main_IMG_65;

    }
}