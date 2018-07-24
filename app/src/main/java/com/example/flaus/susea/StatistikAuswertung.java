package com.example.flaus.susea;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class StatistikAuswertung extends AppCompatActivity {

    Toolbar toolbar;
    Datenbank db = new Datenbank(this);
    long studienId;
    TextView textMittelwert,textStandardAbweichung,textUsability,textLearnability,textMedian;
    double standartabweichung=0;
    double mittelwert=0;
    int usability = 0,learnabilty = 0,median = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistik_auswertung);

        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_theme));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Statistik");

        // Pfeil für den User flow
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        //Studien ID empfangen
        Intent intent = getIntent();
        studienId = intent.getLongExtra("studienId",-1);


        // View-Binding
        textMittelwert = (TextView) findViewById(R.id.textMittelwert);
        textStandardAbweichung = (TextView) findViewById(R.id.textStandardabweichung);
        textUsability = (TextView) findViewById(R.id.textUsability);        // View-Binding
        textLearnability = (TextView) findViewById(R.id.textLearnability);
        textMedian = (TextView) findViewById(R.id.textMedian);

        //Cursor mit Daten holen
        Cursor scoreCursor = db.selectScoresByStudienId(studienId);
        Cursor learnAbilityCursor = db.selectLearnabilityByStudienId(studienId);
        Cursor usabilityCursor = db.selectUsabilityByStudienId(studienId);

        //TextViews füllen
        textMittelwert.setText(" " + Statistik.mittelWert(scoreCursor));
        textStandardAbweichung.setText(" " + Statistik.berechneStandardabweichung(scoreCursor));
        textUsability.setText(" " + Statistik.berechneUsability(usabilityCursor));
        textLearnability.setText(" " + Statistik.berechneLearnability(learnAbilityCursor));
        textMedian.setText(" " + Statistik.berechneMedian(scoreCursor));
    }
}
