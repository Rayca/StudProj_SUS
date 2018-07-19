package com.example.flaus.susea;

import android.util.Log;

//GANZ VIEL MATHEMATIK UND STATISTIK !! DANGER !!
/* Stellt die mathematischen Funktionen zu Verfügung um Kennzahlen für Studien zu berechnen */
public class Statistik {

    public static double berechneStandardabweichung(int[] antworten){
        double varianz = 0;
        double standardAbweichung = 0;
        double mittelWert = mittelWert(antworten);
        for(int i = 0;i<antworten.length;i++){
            varianz+=antworten[i] - mittelWert;
        }
        varianz = varianz/antworten.length;
        standardAbweichung = Math.sqrt(varianz);

        return standardAbweichung;
    }

    public static double mittelWert(int[] antworten){
        double result;
        int wert = 0;
        for(int i = 0;i<antworten.length;i++){
            wert+=antworten[i];
        }
        result = wert/antworten.length;
        return result;
    }

    public static int median(int[]antworten){
        int median=0;
        return median;
    }

    public static int usability(int[]antworten){
        int usability=0;
        for(int i = 0; i<antworten.length;i++){

            usability += antworten[i] - 1;

        }
        usability-=(antworten[3]+antworten[9]);
        usability*=3.125;

        return usability;
    }

    public static int learnability(int[]antworten){
        double learnability;
        learnability=(12.5)*(antworten[3]+antworten[9]);
        return (int) learnability;
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
