package com.example.flaus.susea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

public class AuswertungTestActivity extends AppCompatActivity {

    TextView textViewScore, textViewGeschlecht, textViewAlter, textViewAntworten;
    Button btnZurStudie, btnZurStartseite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auswertung_test);

        Datenbank mDatenbankManager = new Datenbank(this);

        // View-Binding
        textViewScore = (TextView) findViewById(R.id.textViewScore);
        textViewGeschlecht = (TextView) findViewById(R.id.textViewGeschlecht);
        textViewAlter = (TextView)findViewById(R.id.textViewAlter);
        textViewAntworten = (TextView) findViewById(R.id.textViewAntworten);
        btnZurStudie = (Button) findViewById(R.id.btnStudie);
        btnZurStartseite = (Button) findViewById(R.id.btnZurStartseite);


        // Intent empfangen
        Intent empfangsIntent = getIntent();
        Bundle empfangsBundle = empfangsIntent.getExtras();
        boolean studie = empfangsBundle.getBoolean("Studie",false);
        int[] antworten = empfangsBundle.getIntArray("Ergebnisse");
        long testId = empfangsBundle.getLong("testId",-1);



        // Funktion für btnStartseite
        btnZurStartseite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), StartActivity.class);
                startActivity(intent);
            }
        });



        // TextViews füllen
        //textViewScore.setText(" " + Statistik.berechneScore(antworten));

       // for(int i = 0; i < antworten.length; i++) {
       //     Log.d("TJ2", "antworten: " + antworten[i]);
       // }

       // Log.d("TJ2", "antworten.length: " + antworten.length);
        Log.d("TJ2", "test ID : " + testId);




    }
}
