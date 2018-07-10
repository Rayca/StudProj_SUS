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
    long testId, studienId;
    int[] antworten = new int[10];
    boolean studie;
    String studienName;


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
        Intent intent = getIntent();
        testId = intent.getLongExtra("testId",-1);
        studie = intent.getBooleanExtra("studie", false);
        antworten = intent.getIntArrayExtra("antworten");
        studienName = intent.getStringExtra("studienName");
        studienId = intent.getLongExtra("studienId",-1);


        Log.d("studie", "AuswertungTest =" + studie);



        // Button Text ändert sich je nach dem, ob es einzelner Test war oder ob man von einer Studie kommt

        if(studie){
            btnZurStudie.setText("Zurück zur Studien Activity");
            Intent intent1 = new Intent(getApplicationContext(),AuswertungStudieActivity.class);
            intent1.putExtra("testId", testId);
            intent1.putExtra("studie",studie);
            intent1.putExtra("studienName", studienName);
            intent1.putExtra("studienId", studienId);
        } else {
            btnZurStudie.setText("Test einer Studie zuordnen");
            // TODO: Intent zur ListView Activity mit Studien, sodass man einzelnen Test zuordnen kann
        }




        // Funktion für btnStartseite
        btnZurStartseite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), StartActivity.class);
                startActivity(intent);
            }
        });



        // TextViews füllen
        textViewScore.setText(" " + Statistik.berechneScore(antworten));


        // TODO : geschlecht und alter angeben und antworten einzelner fragen
        // TODO : Button zurück zur StudienActivity mit funktion versetzen. intent muss alles mitgeben



    }
}
