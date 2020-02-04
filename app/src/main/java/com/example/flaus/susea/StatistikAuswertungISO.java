package com.example.flaus.susea;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.flaus.susea.AuswertungsActivities.AuswertungStudieActivity;

import static com.example.flaus.susea.Datenbank.SPALTE_SCORE_ISO;

public class StatistikAuswertungISO extends AppCompatActivity {

    Toolbar toolbar;
    Datenbank db = new Datenbank(this);
    long studienId;
    String studienName;
    TextView textMittelwert,textStandardAbweichung,textMedian,textViewNameStudie,textViewMinimum,textViewMaximum;
    Button btnZurStudie;
    double standartabweichung=0;
    double mittelwert=0;
    double median = 0;
    int anzahlTests = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistik_auswertung_iso);

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
        textMedian = (TextView) findViewById(R.id.textMedian);
        btnZurStudie = (Button) findViewById(R.id.btnZurStudie);

        //Cursor mit Daten holen
        Cursor scoreCursor = db.selectScoresByStudienIdISO_sorted(studienId);


        mittelwert = Statistik.mittelWertISO(scoreCursor ,SPALTE_SCORE_ISO);
        standartabweichung = Statistik.berechneStandardabweichung(scoreCursor, SPALTE_SCORE_ISO);
        median = Statistik.berechneMedian(scoreCursor, SPALTE_SCORE_ISO);

        //TextViews füllen
        textViewNameStudie.setText(studienName);
        textViewNameStudie.setPaintFlags(textViewNameStudie.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        textMittelwert.setText(textMittelwert.getText() +" " + mittelwert);
        textStandardAbweichung.setText(textStandardAbweichung.getText() +" " + standartabweichung);
        textMedian.setText(textMedian.getText()+" " + median);
        int index = anzahlTests-1;
        double minimum = scoreCursor.getDouble(index);
        textViewMinimum.setText("" + minimum);
        textViewMaximum.setText("" + scoreCursor.getDouble(0));



        btnZurStudie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //TODO: INtent and AuswertungStudieISO Schicken
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

    @Override
    public void onBackPressed(){

    }


}
