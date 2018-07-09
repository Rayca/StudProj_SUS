package com.example.flaus.susea;

import android.util.Log;

public class Statistik {

    public static int berechneStandardabweichung(int[] antworten){

        int standardAbweichung = 0;



        return standardAbweichung;
    }

    public static double berechneCronbachAlpha(int[] antworten){

        double cronbachAlpha = 0;



        return cronbachAlpha;
    }


    public static int berechneScore(int[] antworten){
        double score = 0;



        for(int i = 0; i<antworten.length;i++){

            score += antworten[i] - 1;

        }
        score *= 2.5;
        Log.d("Jule", "Berechneter Score: " + score);
        return (int) score;
    }
}
