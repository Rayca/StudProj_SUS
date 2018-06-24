package com.example.flaus.susea;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class AuswertungActivity extends AppCompatActivity {


    int[] antworten = new int[10];
    TextView anezige_score;
    ImageButton start;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auswertung);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_theme));

        Bundle extras = getIntent().getExtras();
        antworten = extras.getIntArray("Ergebnisse");


        // Nur zum Überprüfen, ob die richtigen Werte im Array übergeben werden
        for(int i = 0; i< antworten.length; i++){
            Log.d("Jule", "Antwort "+ (i+1) +" : " + antworten[i] + "\n");
        }

        //Score berechnen und anzeigen
        anezige_score = (TextView) findViewById(R.id.anzeige_score);
        int score = berechneScore(antworten);
        anezige_score.setText(score+ "");

        start = (ImageButton) findViewById(R.id.button_home);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), StartActivity.class);
                startActivity(intent);
            }
        });
    }


    public int berechneScore(int[] antworten){
        int score = 0;

        for(int i = 0; i<antworten.length;i++){
                if(i % 2 == 0){
                    score += antworten[i] - 1;
                } else {
                     score += 5- antworten[i];
                }
        }
        return score;
    }


    public int berechneStandardabweichung(int[] antworten){

        int standardAbweichung = 0;



        return standardAbweichung;
    }


    public double berechneCronbachAlpha(int[] antworten){

        double cronbachAlpha = 0;



        return cronbachAlpha;
    }


}







