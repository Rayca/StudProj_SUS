package com.example.flaus.susea;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import static com.example.flaus.susea.Datenbank.SPALTE_SCORE;

public class AuswertungStudieActivity extends AppCompatActivity {


    Datenbank manager = new Datenbank(this);
    int[] antworten = new int[10];
    TextView anzeige_score;
    ImageButton start;
    long id;
    int score =0;


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
        anzeige_score = (TextView) findViewById(R.id.textView2);
        score = berechneScore(antworten);
        anzeige_score.setText(" " + score);


        Log.d("Jule", "Von DB " + manager.getData(SPALTE_SCORE));







        start = (ImageButton) findViewById(R.id.button_home);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), StartActivity.class);
                startActivity(intent);
            }
        });
    }




    public int berechneStandardabweichung(int[] antworten){

        int standardAbweichung = 0;



        return standardAbweichung;
    }



    public int berechneScore(int[] antworten){
        int score = 0;

        for(int i = 0; i<antworten.length;i++){
            score += antworten[i] - 1;;
        }
        score *= 2.5;
        Log.d("TJ", "Berechneter Score: " + score);
        manager.setScore(id,score);
        return score;
    }

    public boolean loadStudie(){
        Bundle intent = getIntent().getExtras();
        boolean studie = intent.getBoolean("Studie", false);

        return studie;

    }
}







