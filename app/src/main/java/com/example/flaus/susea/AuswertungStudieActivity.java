package com.example.flaus.susea;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import static com.example.flaus.susea.Datenbank.SPALTE_SCORE;

public class AuswertungStudieActivity extends AppCompatActivity {


    Datenbank manager = new Datenbank(this);
    TextView textViewNameStudie ,textViewGesamtScore, textViewAnzahlTests;
    Button btnStatistik, btnViewTests, btnZurStartseite;
    long studienId;
    int score = 0;
    String studieName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auswertung_studie);



        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_theme));

        // View-Binding
        textViewNameStudie = (TextView) findViewById(R.id.textViewNameStudie);
        textViewGesamtScore = (TextView) findViewById(R.id.textViewScore);
        textViewAnzahlTests = (TextView) findViewById(R.id.textViewAnzahlTests);
        btnStatistik = (Button) findViewById(R.id.btnStatistik);
        btnViewTests = (Button) findViewById(R.id.btnViewTests);
        btnZurStartseite = (Button) findViewById(R.id.btnZurStartseite);


        // Name und Id der Studie empfangen
        Intent intent = getIntent();

        studienId = intent.getLongExtra("studienId", -1);
        studieName = intent.getStringExtra("Name_der_Studie");


        // TextViews füllen

        textViewNameStudie.setText(" " + studieName);





        // Funktion für btnZurStartseite

        btnZurStartseite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),StartActivity.class);
                startActivity(intent);
            }
        });

        // Funktion für btbViewTests

        btnViewTests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ListViewTestsActivity.class);
                startActivity(intent);
            }
        });




    }


}







