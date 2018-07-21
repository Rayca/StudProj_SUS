package com.example.flaus.susea;

import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    public static int getMedian(Cursor cursor){
        int median=0;

        ArrayList<Integer> scoreList = new ArrayList<>();
        cursor.moveToFirst();
        Log.d("median","scoreList wurde erstellt.");
        while(!cursor.isAfterLast()){
            Log.d("median","scoreList wird gefüllt.");

            scoreList.add(cursor.getColumnIndex(Datenbank.SPALTE_SCORE));
            Log.d("median","scoreList SPALTE: "+cursor.getColumnIndex(Datenbank.SPALTE_SCORE));

            cursor.moveToNext();
        }
        Log.d("median","List.size = "+ scoreList.size());


        for(int i = 0;i<scoreList.size();i++){
            Log.d("median","Unsortiert: "+i+ ". = "+scoreList.get(i));
        }
        Collections.sort(scoreList);

        for(int i = 0;i<scoreList.size();i++){
            Log.d("median","Sortiert: "+i+ ". = "+scoreList.get(i));
        }

        median= (scoreList.get(scoreList.size()/2)+scoreList.get(scoreList.size()/2+1))/2;
        Log.d("median","Median: "+median);
        return median;
    }

    public static int berechneUsability(int[]antworten){
        int usability=0;
        for(int i = 0; i<antworten.length;i++){

            usability += antworten[i] - 1;

        }
        usability-=(antworten[3]+antworten[9]);
        usability*=3.125;

        return usability;
    }

    public static int berechneLearnability(int[]antworten){
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
