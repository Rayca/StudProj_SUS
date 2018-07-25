package com.example.flaus.susea;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.flaus.susea.AuswertungsActivities.AuswertungStudieActivity;
import com.example.flaus.susea.ListViewActivities.ListViewStudienActivity;

public class StatistikAuswertung extends AppCompatActivity {

    Toolbar toolbar;
    Datenbank db = new Datenbank(this);
    long studienId;
    String studienName;
    TextView textMittelwert,textStandardAbweichung,textUsability,textLearnability,textMedian,textViewNameStudie;
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
        getSupportActionBar().setTitle("Statistische Auswertung");

        // Pfeil für den User flow
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        //Studien ID empfangen
        Intent intent = getIntent();
        studienId = intent.getLongExtra("studienId",-1);
        studienName = intent.getStringExtra("studienName");

        Log.d("thomas", "studieName= " + studienName + " studienId= " + studienId);


        // View-Binding
        textViewNameStudie = (TextView) findViewById(R.id.textViewNameStudie);
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
        textViewNameStudie.setText(studienName);
        textMittelwert.setText(textMittelwert.getText() +": " + Statistik.mittelWert(scoreCursor));
        textStandardAbweichung.setText(textStandardAbweichung.getText() +": " + Statistik.berechneStandardabweichung(scoreCursor));
        textUsability.setText(textUsability.getText()+ ": " + Statistik.berechneUsability(usabilityCursor));
        textLearnability.setText(textLearnability.getText()+": " + Statistik.berechneLearnability(learnAbilityCursor));
        textMedian.setText(textMedian.getText()+": " + Statistik.berechneMedian(scoreCursor));
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                Log.d("thomas", "home wurde gedrückt");
                Intent intent = new Intent(getBaseContext(), AuswertungStudieActivity.class);
                intent.putExtra("studienName", studienName);
                intent.putExtra("studienId", studienId);
                startActivity(intent);

                return true;




            default:
                Log.d("thomas", "home wurde gedrückt");
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }




}
