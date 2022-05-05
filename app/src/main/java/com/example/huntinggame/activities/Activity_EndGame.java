package com.example.huntinggame.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.huntinggame.R;
import com.example.huntinggame.Score;
import com.example.huntinggame.ScoreBoardManager;
import com.example.huntinggame.fragments.CallBack_MapFocus;
import com.example.huntinggame.fragments.Fragment_List;
import com.example.huntinggame.fragments.Fragment_Map;


public class Activity_EndGame extends AppCompatActivity   {
    private FrameLayout endGame_FRM_Board,endGame_FRM_map;
    private Button endGame_BTN_finish,endGame_BTN_playAgain;
    private Fragment_List list;
    private Fragment_Map map;
    private String scoreNum ="";
    private Score score;
    private double latitude=0;
    private double longitude=0;
    private ScoreBoardManager scoreBoardManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endgame);
        scoreBoardManager=new ScoreBoardManager();

        if(getIntent().getExtras()!=null) {
            scoreNum = getIntent().getStringExtra("SCORE");
            latitude = getIntent().getDoubleExtra("LATITUDE", 1.0);
            longitude = getIntent().getDoubleExtra("LONGITUDE", 2.0);
            scoreBoardManager.isTopTen(Integer.valueOf(scoreNum),latitude,longitude);
        }
        findViews();
        setButtons();

        list=new Fragment_List();
        list.setList(scoreBoardManager.getArray());
        list.setCallBack_MapFocus(callBack_mapFocus);
        getSupportFragmentManager().beginTransaction().add(R.id.endGame_FRM_Board, list).commit();

        map=new Fragment_Map();
        map.setListScores(scoreBoardManager.getArray());
        getSupportFragmentManager().beginTransaction().add(R.id.endGame_FRM_map,map).commit();
    }


    private CallBack_MapFocus callBack_mapFocus=new CallBack_MapFocus() {
        @Override
        public void setClickOnMap(double latitude, double longitude) {
            map.setLocation(latitude, longitude);
        }
    };



    private void setButtons() {
        endGame_BTN_finish.setOnClickListener(view -> finish());
        endGame_BTN_playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Activity_EndGame.this, Activity_StartGame.class));
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    @Override
    protected void onStop() {
        super.onStop();
        scoreBoardManager.saveArray();
    }

    private void findViews() {
        endGame_FRM_Board=findViewById(R.id.endGame_FRM_Board);
        endGame_FRM_map=findViewById(R.id.endGame_FRM_map);
        endGame_BTN_finish=findViewById(R.id.endGame_BTN_finish);
        endGame_BTN_playAgain=findViewById(R.id.endGame_BTN_playAgain);
    }


}
