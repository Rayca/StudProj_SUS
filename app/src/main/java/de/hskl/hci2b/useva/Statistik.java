package de.hskl.hci2b.useva;

import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.lang.*;

//GANZ VIEL MATHEMATIK UND STATISTIK !! DANGER !!
/* Stellt die mathematischen Funktionen zu Verfügung um Kennzahlen für Studien zu berechnen */
public class Statistik {

  static double[] table = new double[] {12.706,4.303,3.182,2.776,2.571,2.447,2.365,2.306,2.262,2.228,2.201,2.179,2.160,2.145,2.131,2.120,2.110,2.101,2.098,2.086,2.080,2.074,2.069,2.064,2.060,2.056,2.052,2.048,2.045,2.042,2.040,2.037,2.035,2.032,2.030,2.028,2.026,2.024,2.023,2.021,2.018,2.015,2.013,2.011,2.009,2.009,2.009,2.009,2.009,2.009,2.009,2.009,2.009,2.009,2.000,2.000,2.000,2.000,2.000,2.000,2.000,2.000,2.000,2.000,1.994,1.994,1.994,1.994,1.994,1.994,1.994,1.994,1.994,1.994,1.990,1.990,1.990,1.990,1.990,1.990,1.990,1.990,1.990,1.990,1.987,1.987,1.987,1.987,1.987,1.987,1.987,1.987,1.987,1.987,1.984,1.984,1.984,1.984,1.984,1.984,1.984,1.984,1.984,1.984,1.984,1.984,1.984,1.984,1.984,1.984,1.984,1.984,1.984,1.984,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.980,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976,1.976};

  static double[]testTable = new double[500];








    // table befüllen

    //Berechnet Standartabweichung des Scores in einer Studie
    public static double berechneStandardabweichung(Cursor cursor, String Spalte){
        double varianz= 0,varianzkorr = 0;
        double standardAbweichung = 0,standardAbweichungkorr = 0;
        double mittelWert = mittelWertISO(cursor, Spalte);

        ArrayList<Double> scoreList = new ArrayList<>();
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            scoreList.add(cursor.getDouble(cursor.getColumnIndex(Spalte)));
            cursor.moveToNext();
        }
        Log.d("Jule","SCORELIST LÄNGE: "+scoreList.size());
        Log.d("Jule","CURSOR LÄNGE: "+cursor.getCount());
        Log.d("Jule","MITTELWERT: "+mittelWert);
        cursor.moveToFirst();
        for(int i = 0;i<scoreList.size();i++){
            varianz+=(scoreList.get(i) - mittelWert)*(scoreList.get(i) - mittelWert);
        }
        varianzkorr=varianz;
        varianz = (double) varianz/(scoreList.size());
        varianzkorr = (double) varianzkorr/(scoreList.size()-1);
        Log.d("Jule","VARIANZ: "+varianz);
        Log.d("Jule","VARIANZ KORRIGIERT: "+varianzkorr);
        standardAbweichung = Math.sqrt(varianz);
        standardAbweichungkorr = Math.sqrt(varianzkorr);
        Log.d("Jule","Standardabweichung: "+standardAbweichung);
        Log.d("Jule","Standardabweichung korrigiert: "+standardAbweichungkorr);
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
        result = (double) wert/scoreList.size();
        return result;
    }

    public static double mittelWertISO(Cursor cursor, String Spalte){
        double result;
        double wert = 0;

        ArrayList<Double> scoreList = new ArrayList<>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            scoreList.add(cursor.getDouble(cursor.getColumnIndex(Spalte)));
            cursor.moveToNext();
        }


        for(int i = 0;i<scoreList.size();i++){
            wert+=scoreList.get(i);
        }
        result = (double) wert/scoreList.size();
        Log.d("Jule","Wert: "+wert+", scorelist.size: "+scoreList.size()+" ,result: "+result);
        return result;
    }

    //Berechnet Learnability Median einer Studie
    public static double berechneLearnability(Cursor cursor){
        double result;
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
        result = (double) wert/scoreList.size();
        return result;

    }

    //Berechnet Usability Median einer Studie
    public static double berechneUsability(Cursor cursor){
        double result;
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
        result = (double) wert/scoreList.size();
        return result;
    }

    //Funktionen für ISONORM
    public static double berechneAufgabenangemessenheit(Cursor cursor){
        double result;
        int wert = 0;

        ArrayList<Integer> scoreList = new ArrayList<>();
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            scoreList.add(cursor.getInt(cursor.getColumnIndex(Datenbank.SPALTE_TEST_AUFGABENANGEMESSENHEIT)));
            cursor.moveToNext();
        }


        for(int i = 0;i<scoreList.size();i++){
            wert+=scoreList.get(i);
        }
        result = (double) wert/scoreList.size();
        return result;
    }


    public static double berechneSelbstbeschreibungsfaehigkeit(Cursor cursor){
        double result;
        int wert = 0;

        ArrayList<Integer> scoreList = new ArrayList<>();
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            scoreList.add(cursor.getInt(cursor.getColumnIndex(Datenbank.SPALTE_TEST_SELBSTBESCHREIBUNGSFAEHIGKEIT)));
            cursor.moveToNext();
        }


        for(int i = 0;i<scoreList.size();i++){
            wert+=scoreList.get(i);
        }
        result = (double) wert/scoreList.size();
        return result;
    }

    public static double berechneSteuerbarkeit(Cursor cursor){
        double result;
        int wert = 0;

        ArrayList<Integer> scoreList = new ArrayList<>();
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            scoreList.add(cursor.getInt(cursor.getColumnIndex(Datenbank.SPALTE_TEST_STEUERBARKEIT)));
            cursor.moveToNext();
        }


        for(int i = 0;i<scoreList.size();i++){
            wert+=scoreList.get(i);
        }
        result = (double) wert/scoreList.size();
        return result;
    }


    public static double berechneErwartungskonf(Cursor cursor){
        double result;
        int wert = 0;

        ArrayList<Integer> scoreList = new ArrayList<>();
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            scoreList.add(cursor.getInt(cursor.getColumnIndex(Datenbank.SPALTE_TEST_ERWARTUNGSKONFORMITAET)));
            cursor.moveToNext();
        }


        for(int i = 0;i<scoreList.size();i++){
            wert+=scoreList.get(i);
        }
        result = (double) wert/scoreList.size();
        return result;
    }

    public static double berechneFehlertoleranz(Cursor cursor){
        double result;
        int wert = 0;

        ArrayList<Integer> scoreList = new ArrayList<>();
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            scoreList.add(cursor.getInt(cursor.getColumnIndex(Datenbank.SPALTE_TEST_FEHLERTOLERANZ)));
            cursor.moveToNext();
        }


        for(int i = 0;i<scoreList.size();i++){
            wert+=scoreList.get(i);
        }
        result = (double) wert/scoreList.size();
        return result;
    }


    public static double berechneIndividualisierbarkeit(Cursor cursor){
        double result;
        int wert = 0;

        ArrayList<Integer> scoreList = new ArrayList<>();
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            scoreList.add(cursor.getInt(cursor.getColumnIndex(Datenbank.SPALTE_TEST_INDIVIDUALISIERBARKEIT)));
            cursor.moveToNext();
        }


        for(int i = 0;i<scoreList.size();i++){
            wert+=scoreList.get(i);
        }
        result = (double) wert/scoreList.size();
        return result;
    }

    public static double berechneLernfoerderlichkeit(Cursor cursor){
        double result;
        int wert = 0;

        ArrayList<Integer> scoreList = new ArrayList<>();
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            scoreList.add(cursor.getInt(cursor.getColumnIndex(Datenbank.SPALTE_TEST_LERNFOERDERLICHKEIT)));
            cursor.moveToNext();
        }


        for(int i = 0;i<scoreList.size();i++){
            wert+=scoreList.get(i);
        }
        result = (double) wert/scoreList.size();
        return result;
    }

    //Berechnet Median einer Studie
    public static double berechneMedian(Cursor cursor, String Spalte){
        double median=0;

        ArrayList<Double> scoreList = new ArrayList<>();
        cursor.moveToFirst();
        Log.d("median","scoreList wurde erstellt.");
        while(!cursor.isAfterLast()){
            Log.d("median","scoreList wird gefüllt.");

            scoreList.add(cursor.getDouble(cursor.getColumnIndex(Spalte)));
            Log.d("median","scoreList SPALTE: "+cursor.getDouble(cursor.getColumnIndex(Spalte)));

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


    //Funktion zum berechnen des Wertes für Dialogprinzip für ISONORM

    public static double berechneMittelwert3(int a, int b, int c){
        double mittelwert = (double) (a+b+c)/3;
        return  mittelwert;
    }

    public static double berechneGesamtScore(int[] antworten){
        int wert =0 ;
        for (int i =0; i < antworten.length; i++){
            wert = wert + antworten[i];
        }

        double gesamtScore = (double) wert/antworten.length;
        return gesamtScore;
    }

}
