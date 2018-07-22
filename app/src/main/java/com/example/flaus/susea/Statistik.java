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



    public static double berechneStandardabweichung(Cursor cursor){
        double varianz = 0;
        double standardAbweichung = 0;
        double mittelWert = mittelWert(cursor);

        ArrayList<Integer> scoreList = new ArrayList<>();
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            scoreList.add(cursor.getInt(cursor.getColumnIndex(Datenbank.SPALTE_SCORE)));
            cursor.moveToNext();
        }

        for(int i = 0;i<scoreList.size();i++){
            varianz+=scoreList.get(i) - mittelWert;
        }
        varianz = varianz/scoreList.size();
        standardAbweichung = Math.sqrt(varianz);

        return standardAbweichung;
    }

    public static double mittelWert(Cursor cursor){
        double result;
        int wert = 0;

        ArrayList<Integer> scoreList = new ArrayList<>();
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            scoreList.add(cursor.getInt(cursor.getColumnIndex(Datenbank.SPALTE_SCORE)));
            cursor.moveToNext();
        }


        for(int i = 0;i<scoreList.size();i++){
            wert+=scoreList.get(i);
        }
        result = wert/scoreList.size();
        return result;
    }

    public static int getMedian(Cursor cursor){
        int median=0;

        ArrayList<Integer> scoreList = new ArrayList<>();
        cursor.moveToFirst();
        Log.d("median","scoreList wurde erstellt.");
        while(!cursor.isAfterLast()){
            Log.d("median","scoreList wird gefüllt.");

            scoreList.add(cursor.getInt(cursor.getColumnIndex(Datenbank.SPALTE_SCORE)));
            Log.d("median","scoreList SPALTE: "+cursor.getInt(cursor.getColumnIndex(Datenbank.SPALTE_SCORE)));

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
        if((scoreList.size()%2)==0) {
            Log.d("median","Gerade");
            median = (scoreList.get(scoreList.size() / 2) + (scoreList.get(((scoreList.size() + 1) / 2)-1))) / 2;
            Log.d("median", "Median: " + median);
            return median;
        }
        else{
            Log.d("median","Ungerade");
            median = (scoreList.get(((scoreList.size() + 1) / 2)-1));
            Log.d("median", "Median: " + median);
            return median;
        }
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
