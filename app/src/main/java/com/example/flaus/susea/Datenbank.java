package com.example.flaus.susea;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class Datenbank extends SQLiteOpenHelper {
    public static final int DATENBANK_VERSION = 5;
    public static final String DATENBANK_NAMEN = "Datenbank.db";

    //Konstanten für die Test-Datenbank für SUS
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
    public static final String SPALTE_TEST_USABILITY = "Test_Usability";
    public static final String SPALTE_TEST_LEARNABILITY = "Test_Learnability";

    //Konstanten für die Test-Datenbank für ISONORM
    public static final String TABELLE_TEST_ISO = "Einzelner_Test_ISO";
    public static final String SPALTE_TEST_ID_ISO = "Test_ID_ISO";
    public static final String SPALTE_DATUM_ISO = "Test_Datum_ISO";
    public static final String SPALTE_FRAGE1_ISO = "ISO_Frage_1_Antwort";
    public static final String SPALTE_FRAGE2_ISO = "ISO_Frage_2_Antwort";
    public static final String SPALTE_FRAGE3_ISO = "ISO_Frage_3_Antwort";
    public static final String SPALTE_FRAGE4_ISO = "ISO_Frage_4_Antwort";
    public static final String SPALTE_FRAGE5_ISO = "ISO_Frage_5_Antwort";
    public static final String SPALTE_FRAGE6_ISO = "ISO_Frage_6_Antwort";
    public static final String SPALTE_FRAGE7_ISO = "ISO_Frage_7_Antwort";
    public static final String SPALTE_FRAGE8_ISO = "ISO_Frage_8_Antwort";
    public static final String SPALTE_FRAGE9_ISO = "ISO_Frage_9_Antwort";
    public static final String SPALTE_FRAGE10_ISO = "ISO_Frage_10_Antwort";
    public static final String SPALTE_FRAGE11_ISO = "ISO_Frage_11_Antwort";
    public static final String SPALTE_FRAGE12_ISO = "ISO_Frage_12_Antwort";
    public static final String SPALTE_FRAGE13_ISO = "ISO_Frage_13_Antwort";
    public static final String SPALTE_FRAGE14_ISO = "ISO_Frage_14_Antwort";
    public static final String SPALTE_FRAGE15_ISO = "ISO_Frage_15_Antwort";
    public static final String SPALTE_FRAGE16_ISO = "ISO_Frage_16_Antwort";
    public static final String SPALTE_FRAGE17_ISO = "ISO_Frage_17_Antwort";
    public static final String SPALTE_FRAGE18_ISO = "ISO_Frage_18_Antwort";
    public static final String SPALTE_FRAGE19_ISO = "ISO_Frage_19_Antwort";
    public static final String SPALTE_FRAGE20_ISO = "ISO_Frage_20_Antwort";
    public static final String SPALTE_FRAGE21_ISO = "ISO_Frage_21_Antwort";
    public static final String SPALTE_ALTER_ISO = "ISO_Proband_Alter";
    public static final String SPALTE_GESCHLECHT_ISO = "ISO_Proband_Geschlecht";
    public static final String SPALTE_SCORE_ISO = "ISO_Test_Score";
    public static final String SPALTE_INTERFACE_TYP_TEST_ISO = "ISO_Test_Interfacetyp";
    public static final String SPALTE_TEST_AUFGABENANGEMESSENHEIT = "ISO_Test_Aufgabenangemessenheit";
    public static final String SPALTE_TEST_SELBSTBESCHREIBUNGSFAEHIGKEIT = "ISO_Test_Selbstbeschreibungsfaehigkeit";
    public static final String SPALTE_TEST_STEUERBARKEIT = "ISO_Test_Steuerbarkeit";
    public static final String SPALTE_TEST_ERWARTUNGSKONFORMITAET = "ISO_Test_Erwartungskonformitaet";
    public static final String SPALTE_TEST_FEHLERTOLERANZ = "ISO_Test_Fehlertoleranz";
    public static final String SPALTE_TEST_INDIVIDUALISIERBARKEIT = "ISO_Test_Individualisierbarkeit";
    public static final String SPALTE_TEST_LERNFOERDLICHKEIT = "ISO_Test_Lernfoerderlichkeit";

    //Konstanten für die Studien-Datenbank
    public static final String TABELLE_STUDIE = "Studie";
    public static final String SPALTE_STUDIE_ID = "Studie_ID";
    public static final String SPALTE_STUDIE_NAME = "Studie_Name";
    public static final String SPALTE_INTERFACE_TYP_STUDIE = "Studie_Interfacetyp";
    public static final String SPALTE_ANZAHL_TESTS = "Studie_Anzahl_Tests";
    public static final String SPALTE_STUDIE_SCORE = "Studie_Score_gesamt";
    public static final String SPALTE_STUDIE_USABILITY = "Studie_Usability_gesamt";
    public static final String SPALTE_STUDIE_LEARNABILITY = "Studie_Learnability_gesamt";

    //Konstanten für die Studien-Datenbank für ISONORM
    public static final String TABELLE_STUDIE_ISO = "Studie_ISO";
    public static final String SPALTE_STUDIE_ID_ISO = "Studie_ID_ISO";
    public static final String SPALTE_STUDIE_NAME_ISO = "Studie_Name_ISO";
    public static final String SPALTE_INTERFACE_TYP_STUDIE_ISO = "Studie_Interfacetyp_ISO";
    public static final String SPALTE_ANZAHL_TESTS_ISO = "Studie_Anzahl_Tests_ISO";
    public static final String SPALTE_STUDIE_SCORE_ISO = "Studie_Score_gesamt_ISO";
    public static final String SPALTE_STUDIE_AUSFGABENANGEMESSENHEIT= "Studie_Aufgabenangemessenheit_gesamt";
    public static final String SPALTE_STUDIE_SELBSTBESCHREIBUNGSFAEHIGKEIT= "Studie_Selbstbeschreibungsfaehigkeit_gesamt";
    public static final String SPALTE_STUDIE_STEUERBARKEIT= "Studie_Steuerbarkeit_gesamt";
    public static final String SPALTE_STUDIE_ERWARTUNGSKONFORMITAET= "Studie_Erwartungskonformitaet_gesamt";
    public static final String SPALTE_STUDIE_FEHLERTOLERANZ= "Studie_Fehlertoleranz_gesamt";
    public static final String SPALTE_STUDIE_INDIVIDUALISIERBARKEIT= "Studie_Individualisierbarkeit_gesamt";
    public static final String SPALTE_STUDIE_LENRFOERDERLICHKEIT= "Studie_Lernfoerderlichkeit_gesamt";

    public Datenbank(Context cxt) {
        super(cxt, DATENBANK_NAMEN, null, DATENBANK_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL( //Tabelle zum Speichern der einzelnen ISONORM Tests
                "CREATE TABLE " + TABELLE_TEST_ISO + " (" +
                        SPALTE_TEST_ID_ISO + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        SPALTE_DATUM_ISO + " DATE, " +
                        SPALTE_FRAGE1_ISO + " INTEGER, " +
                        SPALTE_FRAGE2_ISO + " INTEGER," +
                        SPALTE_FRAGE3_ISO + " INTEGER," +
                        SPALTE_FRAGE4_ISO + " INTEGER," +
                        SPALTE_FRAGE5_ISO + " INTEGER," +
                        SPALTE_FRAGE6_ISO + " INTEGER," +
                        SPALTE_FRAGE7_ISO + " INTEGER," +
                        SPALTE_FRAGE8_ISO + " INTEGER," +
                        SPALTE_FRAGE9_ISO + " INTEGER," +
                        SPALTE_FRAGE10_ISO + " INTEGER," +
                        SPALTE_FRAGE11_ISO + " INTEGER," +
                        SPALTE_FRAGE12_ISO + " INTEGER," +
                        SPALTE_FRAGE13_ISO + " INTEGER," +
                        SPALTE_FRAGE14_ISO + " INTEGER," +
                        SPALTE_FRAGE15_ISO + " INTEGER," +
                        SPALTE_FRAGE16_ISO + " INTEGER," +
                        SPALTE_FRAGE17_ISO + " INTEGER," +
                        SPALTE_FRAGE18_ISO + " INTEGER," +
                        SPALTE_FRAGE19_ISO + " INTEGER," +
                        SPALTE_FRAGE20_ISO + " INTEGER," +
                        SPALTE_FRAGE21_ISO + " INTEGER," +
                        SPALTE_ALTER_ISO + " INTEGER," +
                        SPALTE_GESCHLECHT_ISO + " TEXT," +
                        SPALTE_SCORE_ISO + " INTEGER," +
                        SPALTE_STUDIE_ID + " INTEGER," +
                        SPALTE_INTERFACE_TYP_TEST_ISO + " TEXT," +
                        SPALTE_TEST_AUFGABENANGEMESSENHEIT + " INTEGER," +
                        SPALTE_TEST_SELBSTBESCHREIBUNGSFAEHIGKEIT + " INTEGER," +
                        SPALTE_TEST_STEUERBARKEIT + " INTEGER," +
                        SPALTE_TEST_ERWARTUNGSKONFORMITAET + " INTEGER," +
                        SPALTE_TEST_FEHLERTOLERANZ + " INTEGER," +
                        SPALTE_TEST_INDIVIDUALISIERBARKEIT + " INTEGER," +
                        SPALTE_TEST_LERNFOERDLICHKEIT + " INTEGER" +
                        ")"
        );

        sqLiteDatabase.execSQL( //Tabelle für einzelne SUS_Tests
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
                        SPALTE_INTERFACE_TYP_TEST + " TEXT," +
                        SPALTE_TEST_USABILITY + " TEXT," +
                        SPALTE_TEST_LEARNABILITY + " TEXT" +
                        ")"
        );

        sqLiteDatabase.execSQL( //Tabelle für SUS Stduie
                "CREATE TABLE " + TABELLE_STUDIE + " (" +
                        SPALTE_STUDIE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        SPALTE_STUDIE_NAME + " TEXT," +
                        SPALTE_INTERFACE_TYP_STUDIE + " TEXT," +
                        SPALTE_ANZAHL_TESTS + " INTEGER," +
                        SPALTE_STUDIE_SCORE + " INTEGER," +
                        SPALTE_STUDIE_USABILITY + " INTEGER," +
                        SPALTE_STUDIE_LEARNABILITY + " INTEGER" +
                        ")"
        );

        sqLiteDatabase.execSQL( //Tabelle für ISONORM Stduie
                "CREATE TABLE " + TABELLE_STUDIE_ISO + " (" +
                        SPALTE_STUDIE_ID_ISO + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        SPALTE_STUDIE_NAME_ISO + " TEXT," +
                        SPALTE_INTERFACE_TYP_STUDIE_ISO + " TEXT," +
                        SPALTE_ANZAHL_TESTS_ISO + " INTEGER," +
                        SPALTE_STUDIE_SCORE_ISO + " INTEGER," +
                        SPALTE_STUDIE_AUSFGABENANGEMESSENHEIT + " INTEGER," +
                        SPALTE_STUDIE_SELBSTBESCHREIBUNGSFAEHIGKEIT + " INTEGER," +
                        SPALTE_STUDIE_STEUERBARKEIT + " INTEGER," +
                        SPALTE_STUDIE_ERWARTUNGSKONFORMITAET + " INTEGER," +
                        SPALTE_STUDIE_FEHLERTOLERANZ + " INTEGER," +
                        SPALTE_STUDIE_INDIVIDUALISIERBARKEIT + " INTEGER," +
                        SPALTE_STUDIE_LENRFOERDERLICHKEIT + " INTEGER" +
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



    /*------------------------------------------------------------------------------------------
      Funktionen für die TABELLE_STUDIEN
      ---------------------------------------------------------------------------------------------*/
     //Gibt alle Studien in der Tabelle Studien in einem Cursor zurück
    public Cursor selectAllStudien() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+SPALTE_STUDIE_ID+" as _id, "+SPALTE_STUDIE_NAME+", "+SPALTE_ANZAHL_TESTS+ " , "+ SPALTE_STUDIE_SCORE + " FROM " + TABELLE_STUDIE, null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor selectAllStudienISO() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+SPALTE_STUDIE_ID_ISO+" as _id, "+SPALTE_STUDIE_NAME_ISO+", "+SPALTE_ANZAHL_TESTS_ISO+ " , "+ SPALTE_STUDIE_SCORE_ISO + " FROM " + TABELLE_STUDIE_ISO, null);
        cursor.moveToFirst();
        return cursor;
    }

    //Gibt eine einzene Studie als Cursor zurück
    public Cursor getStudieById(long id){
        Log.d("Jule", "DB: übergebene Id " + id );
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABELLE_STUDIE + " WHERE " + SPALTE_STUDIE_ID  + " = " + id, null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor getStudieByIdISO(long id){
        Log.d("Jule", "DB: übergebene Id " + id );
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABELLE_STUDIE_ISO + " WHERE " + SPALTE_STUDIE_ID_ISO  + " = " + id, null);
        cursor.moveToFirst();
        return cursor;
    }

    //Fügt eine neue Studie in die DB ein
    // Die ID der Studie wird automatisch beim Einfügen erzeugt und als long zurückgegeben
    public long insertStudieISO(String name, String interfaceTyp, int anzahlTests, int scoreGesamt){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues neueZeile = new ContentValues();
        neueZeile.put(SPALTE_STUDIE_NAME_ISO,name);
        neueZeile.put(SPALTE_INTERFACE_TYP_STUDIE_ISO, interfaceTyp);
        neueZeile.put(SPALTE_ANZAHL_TESTS_ISO,anzahlTests);
        neueZeile.put(SPALTE_STUDIE_SCORE_ISO,scoreGesamt);
        neueZeile.put(SPALTE_STUDIE_AUSFGABENANGEMESSENHEIT, 0); // Als Anfansgwert
        neueZeile.put(SPALTE_STUDIE_SELBSTBESCHREIBUNGSFAEHIGKEIT, 0); //Als Anfangswert
        neueZeile.put(SPALTE_STUDIE_STEUERBARKEIT, 0); //Als Anfangswert
        neueZeile.put(SPALTE_STUDIE_ERWARTUNGSKONFORMITAET, 0); //Als Anfangswert
        neueZeile.put(SPALTE_STUDIE_FEHLERTOLERANZ, 0); //Als Anfangswert
        neueZeile.put(SPALTE_STUDIE_INDIVIDUALISIERBARKEIT, 0); //Als Anfangswert
        neueZeile.put(SPALTE_STUDIE_LENRFOERDERLICHKEIT, 0); //Als Anfangswert

        long id = db.insert(TABELLE_STUDIE_ISO,null,neueZeile);
        Log.d("Jule", "Studie_Id = " + id);


        return id;

    }

    public long insertStudie(String name, String interfaceTyp, int anzahlTests, int scoreGesamt){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues neueZeile = new ContentValues();
        neueZeile.put(SPALTE_STUDIE_NAME,name);
        neueZeile.put(SPALTE_INTERFACE_TYP_STUDIE, interfaceTyp);
        neueZeile.put(SPALTE_ANZAHL_TESTS,anzahlTests);
        neueZeile.put(SPALTE_STUDIE_SCORE,scoreGesamt);
        neueZeile.put(SPALTE_STUDIE_USABILITY, 0); // Als Anfansgwert
        neueZeile.put(SPALTE_STUDIE_LEARNABILITY, 0); //Als Anfangswert

        long id = db.insert(TABELLE_STUDIE,null,neueZeile);
        Log.d("Jule", "Studie_Id = " + id);


        return id;

    }


    public String selectInterfaceByStudienId(long studienId){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT "+SPALTE_INTERFACE_TYP_STUDIE+" FROM "+ TABELLE_STUDIE + " WHERE " + SPALTE_STUDIE_ID + " = " + studienId+"";
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        String ausgabe = cursor.getString(0);
        return ausgabe;
    }


      /*------------------------------------------------------------------------------------------
    Funktionen für die TABELLE_TEST
    ---------------------------------------------------------------------------------------------*/

      //Gibt einen einzelnen Test in einem Cursor zurück
      public Cursor getTestById(long id){
          SQLiteDatabase db = this.getWritableDatabase();
          Cursor cursor = db.rawQuery("SELECT * FROM "+ TABELLE_TEST + " WHERE " + SPALTE_TEST_ID  + " = " + id, null);
          cursor.moveToFirst();
          return cursor;
      }


    //Fügt einen neuen Test in die DB ein
    // Die ID des Tests wird automatisch beim Einfügen erzeugt und als long zurückgegeben
    //TODO:: int usabilty und int learnability und int score in der Doku beschreiben?
    public long insertTest(int[] antworten, int alter, String geschlecht, String datum, long studienId, int score,int usability,int learnability) {
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
        neueZeile.put(SPALTE_SCORE, score);
        neueZeile.put(SPALTE_TEST_USABILITY,usability);
        neueZeile.put(SPALTE_TEST_LEARNABILITY,learnability);
        Log.d("jule", "score, der in die db geschrieben wird" +score);

        //Neuen Test in die DB einfügen
        long id = db.insert(TABELLE_TEST, null, neueZeile);
        Log.d("Jule", id + "");

        //Wichtig, dass die Anzahl Tests in der zugehörigen Studie erhöht wird.
        if(studienId != -1) {
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABELLE_TEST + " WHERE " + SPALTE_STUDIE_ID + " = " + studienId, null); //addierte alle Zeilen auf, in denen die tsud id gleich ist
            cursor.moveToFirst();
            Log.d("Jule", "Id bei insert-Test: " + id);
            int anzahl_tests = cursor.getCount();
            Log.d("Jule", "Anzahl Tests in der Studie:  " + anzahl_tests);
            ContentValues neue_anz_tests = new ContentValues();
            neue_anz_tests.put(SPALTE_ANZAHL_TESTS, anzahl_tests);
            String[] arg = new String[]{Long.toString(studienId)};
            //Anzahl der Tests in der Spalte SPALTE_ANZAHL_TESTS in der Zeile mit der passenden StudienID
            db.update(TABELLE_STUDIE, neue_anz_tests, SPALTE_STUDIE_ID + " = ?", arg);
        }

        return id;

    }

    //Test für ISONORM in die DB eintragen
    // Die ID des Tests wird automatisch beim Einfügen erzeugt und als long zurückgegeben
    //TODO:: int usabilty und int learnability und int score in der Doku beschreiben?
    public long insertTestISO(int[] antworten, int alter, String geschlecht, String datum, long studienId, int score_gesamt,int aufgabenangemessenheit,int selbstbeschreibungsfaehigkeit, int steuerbarkeit, int erwartungskonformitaet,  int fehlertoleranz, int individualisierbarkeit, int lernfoerderlichkeit) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues neueZeile = new ContentValues();
        neueZeile.put(SPALTE_FRAGE1_ISO, antworten[0]);
        neueZeile.put(SPALTE_FRAGE2_ISO, antworten[1]);
        neueZeile.put(SPALTE_FRAGE3_ISO, antworten[2]);
        neueZeile.put(SPALTE_FRAGE4_ISO, antworten[3]);
        neueZeile.put(SPALTE_FRAGE5_ISO, antworten[4]);
        neueZeile.put(SPALTE_FRAGE6_ISO, antworten[5]);
        neueZeile.put(SPALTE_FRAGE7_ISO, antworten[6]);
        neueZeile.put(SPALTE_FRAGE8_ISO, antworten[7]);
        neueZeile.put(SPALTE_FRAGE9_ISO, antworten[8]);
        neueZeile.put(SPALTE_FRAGE10_ISO, antworten[9]);
        neueZeile.put(SPALTE_FRAGE11_ISO, antworten[10]);
        neueZeile.put(SPALTE_FRAGE12_ISO, antworten[11]);
        neueZeile.put(SPALTE_FRAGE13_ISO, antworten[12]);
        neueZeile.put(SPALTE_FRAGE14_ISO, antworten[13]);
        neueZeile.put(SPALTE_FRAGE15_ISO, antworten[14]);
        neueZeile.put(SPALTE_FRAGE16_ISO, antworten[15]);
        neueZeile.put(SPALTE_FRAGE17_ISO, antworten[16]);
        neueZeile.put(SPALTE_FRAGE18_ISO, antworten[17]);
        neueZeile.put(SPALTE_FRAGE19_ISO, antworten[18]);
        neueZeile.put(SPALTE_FRAGE20_ISO, antworten[19]);
        neueZeile.put(SPALTE_FRAGE21_ISO, antworten[20]);
        neueZeile.put(SPALTE_ALTER_ISO, alter);
        neueZeile.put(SPALTE_GESCHLECHT_ISO, geschlecht);
        neueZeile.put(SPALTE_DATUM_ISO, datum);
        neueZeile.put(SPALTE_STUDIE_ID_ISO,studienId);
        neueZeile.put(SPALTE_SCORE_ISO, score_gesamt);
        neueZeile.put(SPALTE_TEST_AUFGABENANGEMESSENHEIT, aufgabenangemessenheit);
        neueZeile.put(SPALTE_TEST_SELBSTBESCHREIBUNGSFAEHIGKEIT,selbstbeschreibungsfaehigkeit);
        neueZeile.put(SPALTE_TEST_STEUERBARKEIT, steuerbarkeit);
        neueZeile.put(SPALTE_TEST_ERWARTUNGSKONFORMITAET, erwartungskonformitaet);
        neueZeile.put(SPALTE_TEST_FEHLERTOLERANZ, fehlertoleranz);
        neueZeile.put(SPALTE_TEST_INDIVIDUALISIERBARKEIT, individualisierbarkeit);
        neueZeile.put(SPALTE_TEST_LERNFOERDLICHKEIT, lernfoerderlichkeit);
        Log.d("jule", "score, der in die db geschrieben wird" +score_gesamt);

        //Neuen Test in die DB einfügen
        long id = db.insert(TABELLE_TEST_ISO, null, neueZeile);
        Log.d("Jule", id + "");

        //Wichtig, dass die Anzahl Tests in der zugehörigen Studie erhöht wird.
        if(studienId != -1) {
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABELLE_TEST_ISO + " WHERE " + SPALTE_STUDIE_ID_ISO + " = " + studienId, null); //addierte alle Zeilen auf, in denen die tsud id gleich ist
            cursor.moveToFirst();
            Log.d("Jule", "Id bei insert-Test: " + id);
            int anzahl_tests = cursor.getCount();
            Log.d("Jule", "Anzahl Tests in der Studie:  " + anzahl_tests);
            ContentValues neue_anz_tests = new ContentValues();
            neue_anz_tests.put(SPALTE_ANZAHL_TESTS_ISO, anzahl_tests);
            String[] arg = new String[]{Long.toString(studienId)};
            //Anzahl der Tests in der Spalte SPALTE_ANZAHL_TESTS in der Zeile mit der passenden StudienID
            db.update(TABELLE_STUDIE_ISO, neue_anz_tests, SPALTE_STUDIE_ID_ISO + " = ?", arg);
        }

        return id;

    }

    //Gibt alle Tests, die zu einer bestimmten Studie gehören, in einem Cursor zurück
    public Cursor selectAllTestsbyStudienId (long studienId){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT "+SPALTE_TEST_ID+" as _id, "+SPALTE_DATUM+", "+SPALTE_SCORE+" FROM " + TABELLE_TEST + " WHERE " + SPALTE_STUDIE_ID + " = "+studienId+"";

        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor selectTestsByStudienIdSorted( long studienId){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT "+SPALTE_TEST_ID+" as _id, "+SPALTE_DATUM+", "+SPALTE_SCORE+" FROM " + TABELLE_TEST + " WHERE " + SPALTE_STUDIE_ID + " = "+studienId+"" + " ORDER BY " + SPALTE_DATUM + " DESC ";

        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor selectScoresByStudienId(long studienId){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT "+SPALTE_SCORE+" FROM "+ TABELLE_TEST + " WHERE " + SPALTE_STUDIE_ID + " = " + studienId+"";
        Log.d("median","StudienId = "+studienId);
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor selectScoresByStudienIdISO(long studienId){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT "+SPALTE_SCORE_ISO+" FROM "+ TABELLE_TEST_ISO + " WHERE " + SPALTE_STUDIE_ID_ISO + " = " + studienId+"";
        Log.d("median","StudienId = "+studienId);
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        return cursor;
    }
    public Cursor selectUsabilityByStudienId(long studienId){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT "+SPALTE_TEST_USABILITY+" FROM "+ TABELLE_TEST + " WHERE " + SPALTE_STUDIE_ID + " = " + studienId+"";
        Log.d("median","StudienId = "+studienId);
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        return cursor;
    }
    public Cursor selectLearnabilityByStudienId(long studienId){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT "+SPALTE_TEST_LEARNABILITY+" FROM "+ TABELLE_TEST + " WHERE " + SPALTE_STUDIE_ID + " = " + studienId+"";
        Log.d("median","StudienId = "+studienId);
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        return cursor;
    }

    //Funktionen für die Dialogprinzipien

    public Cursor selectAufgabenangemessenheit(long studienId){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT "+SPALTE_TEST_AUFGABENANGEMESSENHEIT+" FROM "+ TABELLE_TEST_ISO + " WHERE " + SPALTE_STUDIE_ID_ISO + " = " + studienId+"";
        Log.d("median","StudienId = "+studienId);
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor selectSelbstbeschreibungsfaehigkeit(long studienId){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT "+SPALTE_TEST_SELBSTBESCHREIBUNGSFAEHIGKEIT+" FROM "+ TABELLE_TEST_ISO + " WHERE " + SPALTE_STUDIE_ID_ISO + " = " + studienId+"";
        Log.d("median","StudienId = "+studienId);
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor selectSteuerbarkeit(long studienId){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT "+SPALTE_TEST_STEUERBARKEIT+" FROM "+ TABELLE_TEST_ISO + " WHERE " + SPALTE_STUDIE_ID_ISO + " = " + studienId+"";
        Log.d("median","StudienId = "+studienId);
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor selectErwartungskonformiteat(long studienId){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT "+SPALTE_TEST_ERWARTUNGSKONFORMITAET+" FROM "+ TABELLE_TEST_ISO + " WHERE " + SPALTE_STUDIE_ID_ISO + " = " + studienId+"";
        Log.d("median","StudienId = "+studienId);
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor selectFehlertoleranz(long studienId){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT "+SPALTE_TEST_FEHLERTOLERANZ+" FROM "+ TABELLE_TEST_ISO + " WHERE " + SPALTE_STUDIE_ID_ISO + " = " + studienId+"";
        Log.d("median","StudienId = "+studienId);
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor selectIndividualisierbarkeit(long studienId){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT "+SPALTE_TEST_INDIVIDUALISIERBARKEIT+" FROM "+ TABELLE_TEST_ISO + " WHERE " + SPALTE_STUDIE_ID_ISO + " = " + studienId+"";
        Log.d("median","StudienId = "+studienId);
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor selectLernfoerderlichkeit(long studienId){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT "+SPALTE_TEST_LERNFOERDLICHKEIT+" FROM "+ TABELLE_TEST_ISO + " WHERE " + SPALTE_STUDIE_ID_ISO + " = " + studienId+"";
        Log.d("median","StudienId = "+studienId);
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        return cursor;
    }

    public void insertScoreStudie(long studienID, int score){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues neueZeile = new ContentValues();
        neueZeile.put(SPALTE_STUDIE_SCORE , score);
        String[] arg = new String[]{Long.toString(studienID)};
        db.update(TABELLE_STUDIE, neueZeile, SPALTE_STUDIE_ID + " = ?", arg);


    }







}
