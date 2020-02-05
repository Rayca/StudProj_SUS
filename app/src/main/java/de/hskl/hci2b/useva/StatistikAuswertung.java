package de.hskl.hci2b.useva;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
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

import de.hskl.hci2b.useva.AuswertungsActivities.AuswertungStudieActivity;

import com.example.flaus.susea.R;

import static de.hskl.hci2b.useva.Datenbank.SPALTE_SCORE;

public class StatistikAuswertung extends AppCompatActivity {

    Toolbar toolbar;
    Datenbank db = new Datenbank(this);
    long studienId;
    String studienName;
    TextView textMittelwert,textStandardAbweichung,textUsability,textLearnability,textMedian,textViewNameStudie, textViewKonfidenzIntervall,textViewMinimum,textViewMaximum;
    Button btnkonfidenzIntervall;
    double standartabweichung=0;
    double mittelwert=0;
    double usability = 0,learnabilty = 0,median = 0;
    int anzahlTests = 0;

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
        btnkonfidenzIntervall = (Button) findViewById(R.id.btnZurStudie);

        //Cursor mit Daten holen
        Cursor scoreCursor = db.selectScoresByStudienId(studienId);
        Cursor learnAbilityCursor = db.selectLearnabilityByStudienId(studienId);
        Cursor usabilityCursor = db.selectUsabilityByStudienId(studienId);


        mittelwert = Statistik.mittelWert(scoreCursor);
        standartabweichung = Statistik.berechneStandardabweichung(scoreCursor, SPALTE_SCORE);
        usability = Statistik.berechneUsability(usabilityCursor);
        learnabilty = Statistik.berechneLearnability(learnAbilityCursor);
        median = Statistik.berechneMedian(scoreCursor, SPALTE_SCORE);

        //TextViews füllen
        textViewNameStudie.setText(studienName);
        textViewNameStudie.setPaintFlags(textViewNameStudie.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        textMittelwert.setText(textMittelwert.getText() +" " + mittelwert);
        textStandardAbweichung.setText(textStandardAbweichung.getText() +" " + standartabweichung);
        textUsability.setText(textUsability.getText()+ " " + usability);
        textLearnability.setText(textLearnability.getText()+" " + learnabilty);
        textMedian.setText(textMedian.getText()+" " + median);



        btnkonfidenzIntervall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double[] intervall = Statistik.berechneKonfiIntervall(mittelwert,standartabweichung, anzahlTests);
                double ergebnis1,ergebnis2;
                ergebnis1 = intervall[0];
                ergebnis2 = intervall[1];
                if (ergebnis1<0){
                    ergebnis1 = 0;
                }else{
                    //Kommastelle wird um 4 stellen nach rechts verschoben
                    ergebnis1=ergebnis1*10000;
                    //Wert wird hinter der Kommastelle abgeschnitten
                    ergebnis1=(int)ergebnis1;
                    //Kommastelle wird um 4 stellen nach links verschoben
                    ergebnis1=ergebnis1/10000;
                    //Ergebnis1 hat jetzt nur noch 4 Nachkommastellen.
                }
                if(ergebnis2>100){
                    ergebnis2 = 100;
                }else{
                    //Kommastelle wird um 4 stellen nach rechts verschoben
                    ergebnis2=ergebnis2*10000;
                    //Wert wird hinter der Kommastelle abgeschnitten
                    ergebnis2=(int)ergebnis2;
                    //Kommastelle wird um 4 stellen nach links verschoben
                    ergebnis2=ergebnis2/10000;
                    //Ergebnis1 hat jetzt nur noch 4 Nachkommastellen
                }
                textViewKonfidenzIntervall.setText("Zu 95% wird der Mittelwert der Grundgesamtheit im Intervall von " + ergebnis1 + " und " + ergebnis2 + " liegen");
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
