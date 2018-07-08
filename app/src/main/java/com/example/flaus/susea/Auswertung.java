package com.example.flaus.susea;

import android.util.Log;

public class Auswertung {

    public static int berechneStandardabweichung(int[] antworten){

        int standardAbweichung = 0;



        return standardAbweichung;
    }

    public static double berechneCronbachAlpha(int[] antworten){

        double cronbachAlpha = 0;



        return cronbachAlpha;
    }


    public static int berechneScore(int[] antworten){
        int score = 0;

        for(int i = 0; i<antworten.length;i++){
            if(i % 2 == 0){
                score += antworten[i] - 1;
            } else {
                score += 5- antworten[i];
            }
        }
        Log.d("Jule", "Berechneter Score: " + score);
        return score;
    }
}
