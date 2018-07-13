package com.example.flaus.susea.AuswertungsActivities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.flaus.susea.Datenbank;
import com.example.flaus.susea.R;
import com.example.flaus.susea.StartActivity;
import com.example.flaus.susea.TestActivity;
import com.example.flaus.susea.ListViewActivities.ListViewTestsActivity;

public class AuswertungStudieActivity extends AppCompatActivity {


    Datenbank manager = new Datenbank(this);
    TextView textViewNameStudie ,textViewGesamtScore, textViewAnzahlTests;
    Button btnStatistik, btnViewTests, btnZurStartseite;
    FloatingActionButton fab;
    long studienId;
    int score = 0;
    String studienName;
    boolean studie;


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
        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);


        // Name und Id der Studie empfangen
        Intent intent = getIntent();
        studienId = intent.getLongExtra("studienId", -1);
        studienName = intent.getStringExtra("studienName");
        studie = intent.getBooleanExtra("studie",false);

        // TextViews füllen

        textViewNameStudie.setText(" " + studienName);


Log.d("studie", "AuswertungsstudieActivity =" + studie);



        // Funktion für btnZurStartseite

        btnZurStartseite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),StartActivity.class);
                startActivity(intent);
            }
        });




        // Funktion für btnViewTests

        btnViewTests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ListViewTestsActivity.class);
                intent.putExtra("studienId",studienId);
                startActivity(intent);
            }
        });




        // Funktion für fab

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TestActivity.class);
                intent.putExtra("studienID", studienId);
                intent.putExtra("studienName", studienName);
                intent.putExtra("studie",studie);
                startActivity(intent);
            }
        });




    }


}







