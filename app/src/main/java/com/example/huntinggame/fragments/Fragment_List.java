package com.example.huntinggame.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.huntinggame.R;
import com.example.huntinggame.Score;

import java.util.ArrayList;


public class Fragment_List extends Fragment {
    private ListView fragment_list_LSV_list;
    private ArrayList<String> scoreListStrings=new ArrayList<>();
    private ArrayList<Score> scoreList;

    private ArrayAdapter<String> arrayAdapter;
    private CallBack_MapFocus callBack_mapFocus;

    public void setCallBack_MapFocus(CallBack_MapFocus callBack_mapFocus) {
        this.callBack_mapFocus = callBack_mapFocus;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup cotainer, Bundle savedInstanceState){
        View view =inflater.inflate(R.layout.fragment_list,cotainer,false);
        findView(view);
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, this.scoreListStrings);
        fragment_list_LSV_list.setAdapter(arrayAdapter);
        setClick();
        return view;
    }
    public void setClick(){
        fragment_list_LSV_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(callBack_mapFocus !=null) {
                    callBack_mapFocus.setClickOnMap(scoreList.get(position).getLatitude(),scoreList.get(position).getLongitude());
                }
            }
        });
    }
    private void findView(View view) {
        fragment_list_LSV_list=view.findViewById(R.id.fragment_list_LSV_list);
    }


    public void setList(ArrayList<Score> list) {
        scoreList = list;
        for (Score i : list) {
            this.scoreListStrings.add(String.valueOf(i.getNumOfScore()));
        }
    }
}
