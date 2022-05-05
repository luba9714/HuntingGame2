package com.example.huntinggame.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.huntinggame.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class Activity_StartGame extends AppCompatActivity {
    private MaterialButton name_BTN_buttonsGame;
    private MaterialButton name_BTN_sensorsGame;
    private MaterialButton name_BTN_scoreTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        name_BTN_buttonsGame=findViewById(R.id.name_BTN_buttonsGame);
        name_BTN_sensorsGame=findViewById(R.id.name_BTN_sensorsGame);
        name_BTN_scoreTable=findViewById(R.id.name_BTN_scoreTable);

        name_BTN_buttonsGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Activity_StartGame.this, Activity_ButtonsGame.class));
                finish();
            }
        });

        name_BTN_sensorsGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Activity_StartGame.this, Activity_SensorsGame.class));
                finish();
            }
        });
        name_BTN_scoreTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Activity_StartGame.this, Activity_EndGame.class));
                finish();
            }
        });



    }
}
