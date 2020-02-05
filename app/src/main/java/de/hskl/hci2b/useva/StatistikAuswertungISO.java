package de.hskl.hci2b.useva;

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

import de.hskl.hci2b.useva.AuswertungsActivities.AuswertungStudieISONORMActivity;

import com.example.flaus.susea.R;

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
        textViewMinimum = (TextView) findViewById(R.id.textViewMinimum);
        textViewMaximum = (TextView) findViewById(R.id.textViewMaximum);
        btnZurStudie = (Button) findViewById(R.id.btnZurStudie);

        //Cursor mit Daten holen
        Cursor scoreCursor = db.selectScoresByStudienIdISO_sorted(studienId);
        scoreCursor.moveToFirst();


        mittelwert = Statistik.mittelWertISO(scoreCursor , Datenbank.SPALTE_SCORE_ISO);
        if (anzahlTests<2){
            standartabweichung = 0;
            textStandardAbweichung.setText(textStandardAbweichung.getText() +" zu wenige Tests");
        }
        else{
            standartabweichung = Statistik.berechneStandardabweichung(scoreCursor, Datenbank.SPALTE_SCORE_ISO);
            textStandardAbweichung.setText(textStandardAbweichung.getText() +" " + String.format("%.2f",standartabweichung));
        }
        median = Statistik.berechneMedian(scoreCursor, Datenbank.SPALTE_SCORE_ISO);

        //TextViews füllen
        textViewNameStudie.setText(studienName);
        textViewNameStudie.setPaintFlags(textViewNameStudie.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        textMittelwert.setText(textMittelwert.getText() +" " + String.format("%.2f",mittelwert));
        textMedian.setText(textMedian.getText()+" " + String.format("%.2f",median));
        scoreCursor.moveToLast();
        textViewMinimum.setText("" + String.format("%.2f",scoreCursor.getDouble(0)));
        scoreCursor.moveToFirst();
        textViewMaximum.setText("" + String.format("%.2f",scoreCursor.getDouble(0)));
        scoreCursor.moveToFirst();



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
                Intent intent = new Intent(getBaseContext(), AuswertungStudieISONORMActivity.class);
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
        NavUtils.navigateUpFromSameTask(this);
        Intent intent = new Intent(getBaseContext(), AuswertungStudieISONORMActivity.class);
        intent.putExtra("studienName", studienName);
        intent.putExtra("studienId", studienId);
        startActivity(intent);
    }


}
