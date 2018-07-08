package com.example.flaus.susea;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import static com.example.flaus.susea.Datenbank.SPALTE_SCORE;

public class AuswertungStudieActivity extends AppCompatActivity {


    Datenbank manager = new Datenbank(this);
    int[] antworten = new int[10];
    TextView anzeige_score;

    long id;
    int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auswertung_studie);



        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_theme));


        // Name und Id der Studie empfangen
        Bundle extras = getIntent().getExtras();
         // antworten = extras.getIntArray("Ergebnisse");
        id = extras.getLong("Test_ID", -1);
        boolean studie = extras.getBoolean("Studie", false);






        //Score berechnen und anzeigen
        anzeige_score = (TextView) findViewById(R.id.textViewScore);
        score = Statistik.berechneScore(antworten);
        anzeige_score.setText(" " + score);


        Log.d("Jule", "Von DB " + manager.getData(SPALTE_SCORE));


    }


}







