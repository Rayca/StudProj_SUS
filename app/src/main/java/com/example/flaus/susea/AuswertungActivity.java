package com.example.flaus.susea;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AuswertungActivity extends AppCompatActivity {


    int[] antworten = new int[10];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auswertung);
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







