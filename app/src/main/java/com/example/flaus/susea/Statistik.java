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

  static double[] table = new double[] {12.706,4.303,3.182,2.776,2.571,2.447,2.365,2.306,2.262,2.228,2.201,2.179,2.160,2.145,2.131,2.120,2.110,2.101,2.098,2.086,2.080,2.074,2.069,2.064,2.060,2.056,2.052,2.048,2.045,2.042,2.040,2.037,2.035,2.032,2.030,2.028,2.026,2.024,2.023,2.021,2.018,2.015,2.013,2.011,2.009,2.009,2.009,2.009,2.009,2.009,2.009,2.009,2.009,2.009,2.000,2.000,2.000,2.000,2.000,2.000,2.000,2.000,2.000,2.000,1.994,1.994,1.994,1.994,1.994,1.994,1.994,1.994,1.994,1.994,1.990,1.990,1.990,1.990,1.990,1.990,1.990,1.990,1.990,1.990,1.987,1.987,1.987,1.987,1.987,1.987,1.987,1.987,1.987,1.987,1.984,1.984,1.984,1.984,1.984,1.984,1.984,1.984,1.984,1.984,1.984,1.984,1.984,1.984,1.984,1.984,1.984,1.984,1.984,1.984,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976};

  static double[]testTable = new double[500];








    // table befüllen

    //Berechnet Standartabweichung des Scores in einer Studie
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
            varianz+=(scoreList.get(i) - mittelWert)*(scoreList.get(i) - mittelWert);
        }
        varianz = varianz/(scoreList.size()-1);
        standardAbweichung = Math.sqrt(varianz);

        double zwischenErgebnis = standardAbweichung*100;
        zwischenErgebnis = (int) zwischenErgebnis;
        standardAbweichung= zwischenErgebnis/100;
        return standardAbweichung;
    }

    //Berechnet Mittelwert der Scores einer Studie
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

    //Berechnet Learnability Median einer Studie
    public static int berechneLearnability(Cursor cursor){
        int result;
        int wert = 0;

        ArrayList<Integer> scoreList = new ArrayList<>();
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            scoreList.add(cursor.getInt(cursor.getColumnIndex(Datenbank.SPALTE_TEST_LEARNABILITY)));
            cursor.moveToNext();
        }


        for(int i = 0;i<scoreList.size();i++){
            wert+=scoreList.get(i);
        }
        result = wert/scoreList.size();
        return result;

    }

    //Berechnet Usability Median einer Studie
    public static int berechneUsability(Cursor cursor){
        int result;
        int wert = 0;

        ArrayList<Integer> scoreList = new ArrayList<>();
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            scoreList.add(cursor.getInt(cursor.getColumnIndex(Datenbank.SPALTE_TEST_USABILITY)));
            cursor.moveToNext();
        }


        for(int i = 0;i<scoreList.size();i++){
            wert+=scoreList.get(i);
        }
        result = wert/scoreList.size();
        return result;
    }

    //Berechnet Median einer Studie
    public static int berechneMedian(Cursor cursor){
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

    //Berechnet das Usability Ergebnis eines Tests
    public static int berechneUsability(int[]antworten){
        int usability=0;
        for(int i = 0; i<antworten.length;i++){

            usability += antworten[i] - 1;

        }
        Log.d("subscale","Usability - 4 und 10: "+usability +" - " +antworten[3]+" "+antworten[9]);

        usability-=(antworten[3]-1+antworten[9]-1);

        Log.d("subscale","Usability: "+usability);

        usability*=3.125;

        Log.d("subscale","Usability: "+usability);

        return usability;
    }

    public static int berechneMin(Cursor cursor){

        ArrayList<Integer> scoreList = new ArrayList<>();
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            scoreList.add(cursor.getInt(cursor.getColumnIndex(Datenbank.SPALTE_SCORE)));
        }

        Collections.sort(scoreList);
        return scoreList.get(0);
    }
    public static int berechneMax(Cursor cursor){

        ArrayList<Integer> scoreList = new ArrayList<>();
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            scoreList.add(cursor.getInt(cursor.getColumnIndex(Datenbank.SPALTE_SCORE)));
        }

        Collections.sort(scoreList);
        int length = scoreList.size()-1;
        return scoreList.get(length);
    }


    //Berechnet das Learnability Ergebnis eines Tests
    public static int berechneLearnability(int[]antworten){
        double learnability;
        Log.d("subscale","learnability an 4 und 10: "+antworten[3]+" "+antworten[9]);
        learnability=(12.5)*(antworten[3]-1+antworten[9]-1);
        return (int) learnability;
    }

    //Berechnet den Score eines Tests
    public static int berechneScore(int[] antworten){
        double score = 0;



        for(int i = 0; i<antworten.length;i++){

            score += antworten[i] - 1;

        }
        score *= 2.5;
        Log.d("Jule", "Berechneter Score: " + score);
        return (int) score;
    }


    // Berechnet Konfidenzintervall

    public static double[] berechneKonfiIntervall(double mittelwert, double standardabweichung, int anzahlTests) {

        for(int i = 0;i<table.length; i++){
            testTable[i] = table[i];
        }

        for(int i = 200; i<299;i++){
            testTable[i] = 1.972;
        }

        for(int i = 300  ; i<498;i++){
            testTable[i] = 1.968;
        }

        testTable[499] = 1.965;






        double standardError = 0;

        standardError = testTable[anzahlTests] * standardabweichung / Math.sqrt(anzahlTests);


        return new double[]{mittelwert-standardError,mittelwert+standardError};
    }

}
