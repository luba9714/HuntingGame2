package com.example.huntinggame;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScoreBoardManager {
    private String jsonScores="";
    private Score score;
    private Score scores[];
    private ArrayList<String> scores1;
    private List<Integer> topTen;
    private TypeToken type=new TypeToken<Score[]>(){};

    public ScoreBoardManager() {
        loadArray();
        topTen = new ArrayList<>();
        for(Score s : scores){
            topTen.add(s.getNumOfScore());
        }
        Collections.sort(topTen,Collections.reverseOrder());
    }


    public boolean isTopTen(int score, double latitude, double longitude) {
        if (score > topTen.get(9)) {
            for(Score s : scores){
                if(s.getNumOfScore() == topTen.get(9)){
                    s.setNumOfScore(score);
                    s.setLatitude(latitude);
                    s.setLongitude(longitude);
                    break;
                }
            }
            topTen.remove(9);
            topTen.add(score);
            Collections.sort(topTen,Collections.reverseOrder());
            return true;
        }
        return false;
    }

    public void saveArray() {
        jsonScores = new Gson().toJson(scores);
        MSP.getMe().putString("SCORES",jsonScores);
    }

    private void loadArray() {
        jsonScores = MSP.getMe().getString("SCORES","");
        try {
            scores = new Gson().fromJson(jsonScores,type.getType());
        }catch (Exception ex){}
        if(scores == null){
            scores = new Score[10];
            for(int i = 0; i< scores.length; i++){
                scores[i] = new Score();
            }
        }
    }

    public ArrayList<Score> getArray() {
        ArrayList<Score> sorted = new ArrayList<>();
        Collections.addAll(sorted,scores);
        Collections.sort(sorted, new Comparator<Score>() {
            @Override
            public int compare(Score score, Score t1) {

                return t1.getNumOfScore() - score.getNumOfScore();
            }
        });
        return sorted;
    }
}
