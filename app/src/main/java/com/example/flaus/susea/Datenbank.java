package com.example.flaus.susea;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Datenbank extends SQLiteOpenHelper {
    public static final int DATENBANK_VERSION = 3;
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
    public static final String SPALTE_INTERFACE_TYP_TEST = "Test_Interfacetyp";

    //Konstanten für die Studien-Datenbank
    public static final String TABELLE_STUDIE = "Studie";
    public static final String SPALTE_STUDIE_ID = "Studie_ID";
    public static final String SPALTE_STUDIE_NAME = "Studie_Name";
    public static final String SPALTE_INTERFACE_TYP_STUDIE = "Studie_Interfacetyp";
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
                        SPALTE_STUDIE_ID + " INTEGER," +
                        SPALTE_INTERFACE_TYP_TEST + " TEXT" +
                        ")"
        );

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + TABELLE_STUDIE + " (" +
                        SPALTE_STUDIE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        SPALTE_STUDIE_NAME + " TEXT," +
                        SPALTE_INTERFACE_TYP_STUDIE + " TEXT," +
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




    //NUR ZUM TEST!!!
    public Cursor selectAllTests(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELLE_TEST, null);
        return  cursor;
    }


    public Cursor selectAllStudien() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+SPALTE_STUDIE_ID+" as _id, "+SPALTE_STUDIE_NAME+", "+SPALTE_STUDIE_SCORE+" FROM " + TABELLE_STUDIE, null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor getStudieById(long id){
        Log.d("Jule", "DB: übergebene Id " + id );
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABELLE_STUDIE + " WHERE " + SPALTE_STUDIE_ID  + " = " + id, null);
        cursor.moveToFirst();
        return cursor;
    }


    public Cursor getTestById(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABELLE_TEST + " WHERE " + SPALTE_TEST_ID  + " = " + id, null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor selectAllTestsbyStudienId (long studienId){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT "+SPALTE_TEST_ID+" as _id, "+SPALTE_DATUM+", "+SPALTE_SCORE+" FROM " + TABELLE_TEST + " WHERE " + SPALTE_STUDIE_ID + " = "+studienId+"";

        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        return cursor;
    }



    public long insertTest(int[] antworten, int alter, String geschlecht, String datum) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues neueZeile = new ContentValues();
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
        neueZeile.put(SPALTE_DATUM, datum);

        long id = db.insert(TABELLE_TEST, null, neueZeile);
        Log.d("Jule", id + "");
        return id;

    }

    public long insertTest(int[] antworten, int alter, String geschlecht, String datum, long studienId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues neueZeile = new ContentValues();
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
        neueZeile.put(SPALTE_DATUM, datum);
        neueZeile.put(SPALTE_STUDIE_ID,studienId);

        long id = db.insert(TABELLE_TEST, null, neueZeile);
        Log.d("Jule", id + "");
        return id;

    }







    public String getData(String string){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT "+string+" FROM " + TABELLE_TEST,null);

        if(c!=null){
            c.moveToFirst();
            String data = c.getString(0);
            Log.d("jule",data);
            return data;
        }

        return null;
    }





    public void setScore(long id, int score){

        SQLiteDatabase db = getWritableDatabase();
        //Cursor c = db.rawQuery("SELECT * FROM " + TABELLE_TEST+" WHERE "+SPALTE_TEST_ID+" = "+studienId,null);
        ContentValues neueZeile = new ContentValues();
        neueZeile.put(SPALTE_SCORE,score);
        String[] arg = new String[]{Long.toString(id)};
        Log.d("Jule", "In die DB geschr. "+score+" "+id);
        db.update(TABELLE_TEST,neueZeile,SPALTE_TEST_ID + " = ?",arg);
    }





    public long insertStudie(String name, String interfaceTyp, int anzahlTests, int scoreGesamt){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues neueZeile = new ContentValues();
        neueZeile.put(SPALTE_STUDIE_NAME,name);
        neueZeile.put(SPALTE_INTERFACE_TYP_STUDIE, interfaceTyp);
        neueZeile.put(SPALTE_ANZAHL_TESTS,anzahlTests);
        neueZeile.put(SPALTE_STUDIE_SCORE,scoreGesamt);

        long id = db.insert(TABELLE_STUDIE,null,neueZeile);
        Log.d("Jule", "Studie_Id = " + id);


        return id;

    }

}
