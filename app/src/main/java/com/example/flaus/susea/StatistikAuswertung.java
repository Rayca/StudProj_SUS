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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.flaus.susea.AuswertungsActivities.AuswertungStudieActivity;
import com.example.flaus.susea.ListViewActivities.ListViewStudienActivity;

public class StatistikAuswertung extends AppCompatActivity {

    Toolbar toolbar;
    Datenbank db = new Datenbank(this);
    long studienId;
    String studienName;
    TextView textMittelwert,textStandardAbweichung,textUsability,textLearnability,textMedian,textViewNameStudie, textViewKonfidenzIntervall;
    Button btnkonfidenzIntervall;
    double standartabweichung=0;
    double mittelwert=0;
    int usability = 0,learnabilty = 0,median = 0, anzahlTests = 0;

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
        anzahlTests = intent.getIntExtra("anzahlTests",0);


        Log.d("thomas", "studieName= " + studienName + " studienId= " + studienId);


        // View-Binding
        textViewNameStudie = (TextView) findViewById(R.id.textViewNameStudie);
        textMittelwert = (TextView) findViewById(R.id.textMittelwert);
        textStandardAbweichung = (TextView) findViewById(R.id.textStandardabweichung);
        textUsability = (TextView) findViewById(R.id.textUsability);        // View-Binding
        textLearnability = (TextView) findViewById(R.id.textLearnability);
        textMedian = (TextView) findViewById(R.id.textMedian);
        textViewKonfidenzIntervall = (TextView) findViewById(R.id.textViewKonfidenzInter);
        btnkonfidenzIntervall = (Button) findViewById(R.id.btnKonfidenzIntervall);

        //Cursor mit Daten holen
        Cursor scoreCursor = db.selectScoresByStudienId(studienId);
        Cursor learnAbilityCursor = db.selectLearnabilityByStudienId(studienId);
        Cursor usabilityCursor = db.selectUsabilityByStudienId(studienId);


        mittelwert = Statistik.mittelWert(scoreCursor);
        standartabweichung = Statistik.berechneStandardabweichung(scoreCursor);
        usability = Statistik.berechneUsability(usabilityCursor);
        learnabilty = Statistik.berechneLearnability(learnAbilityCursor);
        median = Statistik.berechneMedian(scoreCursor);

        //TextViews füllen
        textViewNameStudie.setText(studienName);
        textMittelwert.setText(textMittelwert.getText() +": " + mittelwert);
        textStandardAbweichung.setText(textStandardAbweichung.getText() +": " + standartabweichung);
        textUsability.setText(textUsability.getText()+ ": " + usability);
        textLearnability.setText(textLearnability.getText()+": " + learnabilty);
        textMedian.setText(textMedian.getText()+": " + median);


        btnkonfidenzIntervall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double[] intervall = Statistik.berechneKonfiIntervall(mittelwert,standartabweichung, anzahlTests);
                textViewKonfidenzIntervall.setText("Zu 95% wird der Mittelwert der Grundgesamtheit im Intervall von" + intervall[0] + "und" + intervall[1] + " liegen");
            }
        });



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
