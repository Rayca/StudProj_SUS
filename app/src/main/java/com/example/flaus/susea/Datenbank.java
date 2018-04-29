package com.example.flaus.susea;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Datenbank extends SQLiteOpenHelper {
    public static final int DATENBANK_VERSION = 1;
    public static final String DATENBANK_NAMEN = "Datenbank.db";
    //Konstanten für die Test-Datenbank
    public static final String TABELLE_TEST = "Einzelner_Test";
    public static final String SPALTE_TEST_ID = "Test_ID";
    public static final String SPALTE_DATUM = "Test_Datum";
    public static final String SPALTE_FRAGE1 = "Frage_1_Antwort";
    public static final String SPALTE_FRAGE2 = "Frage_2_Antwort";
    public static final String SPALTE_FRAGE3 = "Frage_3_Antwort";
    public static final String SPALTE_FRAGE4 = "Frage_4_Antwort";
    public static final String SPALTE_FRAGE5 = "Frage_5_Antwort";
    public static final String SPALTE_FRAGE6 = "Frage_6_Antwort";
    public static final String SPALTE_FRAGE7 = "Frage_7_Antwort";
    public static final String SPALTE_FRAGE8 = "Frage_8_Antwort";
    public static final String SPALTE_FRAGE9 = "Frage_9_Antwort";
    public static final String SPALTE_FRAGE10 = "Frage_10_Antwort";
    public static final String SPALTE_ALTER = "Proband_Alter";
    public static final String SPALTE_GESCHLECHT = "Proband_Geschlecht";
    public static final String SPALTE_SCORE = "Test_Score";

    //Konstanten für die Studien-Datenbank
    public static final String TABELLE_STUDIE = "Studie";
    public static final String SPALTE_STUDIE_ID = "Studie_ID";
    public static final String SPALTE_STUDIE_NAME = "Studie_Name";
    public static final String SPALTE_INTERFACE = "Studie_Interfacetyp";
    public static final String SPALTE_ANZAHL_TESTS = "Studie_Anzahl_Tests";
    public static final String SPALTE_STUDIE_SCORE = "Studie_Score_gesamt";

    public Datenbank(Context cxt) {
        super(cxt, DATENBANK_NAMEN, null, DATENBANK_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + TABELLE_TEST + " (" +
                        SPALTE_TEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        SPALTE_DATUM + " DATE, " +
                        SPALTE_FRAGE1 + " INTEGER, " +
                        SPALTE_FRAGE2 + " INTEGER," +
                        SPALTE_FRAGE3 + " INTEGER," +
                        SPALTE_FRAGE4 + " INTEGER," +
                        SPALTE_FRAGE5 + " INTEGER," +
                        SPALTE_FRAGE6 + " INTEGER," +
                        SPALTE_FRAGE7 + " INTEGER," +
                        SPALTE_FRAGE8 + " INTEGER," +
                        SPALTE_FRAGE9 + " INTEGER," +
                        SPALTE_FRAGE10 + " INTEGER," +
                        SPALTE_ALTER + " INTEGER," +
                        SPALTE_GESCHLECHT + " TEXT," +
                        SPALTE_SCORE + " INTEGER," +
                        SPALTE_STUDIE_ID + " INTEGER" +
                        ")"
        );

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + TABELLE_STUDIE + " (" +
                        SPALTE_STUDIE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        SPALTE_STUDIE_NAME + " TEXT," +
                        SPALTE_INTERFACE + " TEXT," +
                        SPALTE_ANZAHL_TESTS + " INTEGER," +
                        SPALTE_STUDIE_SCORE + " INTEGER" +
                        ")"
        );
   }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABELLE_TEST);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABELLE_STUDIE);
        onCreate(sqLiteDatabase);
    }

    /*------------------------------------------------------------------------------------------
    Funktionen für die DB
    ---------------------------------------------------------------------------------------------*/


    public void Test_einfuegen (int[] antworten, int alter, String geschlecht, int score){
        ContentValues neueZeile = new ContentValues();
        neueZeile.put(SPALTE_TEST_ID, 0);
        neueZeile.put(SPALTE_DATUM, "2018-04-29");
        neueZeile.put(SPALTE_FRAGE1, antworten[0]);
        neueZeile.put(SPALTE_FRAGE2, antworten[1]);
        neueZeile.put(SPALTE_FRAGE3, antworten[2]);
        neueZeile.put(SPALTE_FRAGE4, antworten[3]);
        neueZeile.put(SPALTE_FRAGE5, antworten[4]);
        neueZeile.put(SPALTE_FRAGE6, antworten[5]);
        neueZeile.put(SPALTE_FRAGE7, antworten[6]);
        neueZeile.put(SPALTE_FRAGE8, antworten[7]);
        neueZeile.put(SPALTE_FRAGE9, antworten[8]);
        neueZeile.put(SPALTE_FRAGE10, antworten[9]);
        neueZeile.put(SPALTE_ALTER, alter);
        neueZeile.put(SPALTE_GESCHLECHT, geschlecht);
        neueZeile.put(SPALTE_SCORE, score);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABELLE_TEST, null, neueZeile);
    }

    //NUR ZUM TEST!!!
    public Cursor zeile_auslesen(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELLE_TEST, null);
        return  cursor;
    }


}
