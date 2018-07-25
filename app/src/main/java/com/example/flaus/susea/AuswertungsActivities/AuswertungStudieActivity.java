package com.example.flaus.susea.AuswertungsActivities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flaus.susea.Datenbank;
import com.example.flaus.susea.ListViewActivities.ListViewStudienActivity;
import com.example.flaus.susea.ListViewActivities.ListViewTestsActivity;
import com.example.flaus.susea.R;
import com.example.flaus.susea.StartActivity;
import com.example.flaus.susea.Statistik;
import com.example.flaus.susea.StatistikAuswertung;
import com.example.flaus.susea.TestActivity;

/* Übernimmmt die Auswertung und Darstellung der gesammelten Daten eines einzelnen Tests */

public class AuswertungStudieActivity extends AppCompatActivity {


    TextView textViewNameStudie ,textViewGesamtScore, textViewAnzahlTests,textViewInterface,textViewUsabilityScore,textViewLearnabilityScore;
    FloatingActionButton fab;
    long studienId;
    int score = 0;
    String studienName;
    int anzahl_tests;

    Toolbar toolbar;
    Datenbank db = new Datenbank(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auswertung_studie);


        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Studie");

        Intent intent = getIntent();
        studienId = intent.getLongExtra("studienId", -1);
        Log.d("Jule", "Id der neuen Studie empfangen:  " + studienId);
        Cursor c = db.getStudieById(studienId);
        c.moveToFirst();
        anzahl_tests = c.getInt(3);




        // View-Binding
        textViewNameStudie = (TextView) findViewById(R.id.textViewNameStudie);
        textViewGesamtScore = (TextView) findViewById(R.id.textViewScore);
        textViewAnzahlTests = (TextView) findViewById(R.id.textViewAnzahlTests);
        textViewInterface = (TextView) findViewById(R.id.textViewInterfacetyp);
        textViewLearnabilityScore = (TextView) findViewById(R.id.textViewUsability);
        textViewUsabilityScore = (TextView) findViewById(R.id.textViewLearnability);
        Button btnNeuerTest = findViewById(R.id.btnNeuerTest);





        studienName = intent.getStringExtra("studienName");
        studienName = c.getString(1);
        if( anzahl_tests == 0) {
            //Wenn eine neue Studie erstellt wurde, wird der Name gleich mit dem Intent mitgeschickt


            //Und es sind noch keine Tests drin
            textViewAnzahlTests.setText("Anzahl Tests: Noch keine Tests");

        } else { //Ansonten Name und Daten der Studie aus der Datenbank holen
            textViewAnzahlTests.append("" + anzahl_tests);
            textViewGesamtScore.setText("" + c.getInt(4));

            //Cursor mit allen Scores
            Cursor scoreCursor = db.selectScoresByStudienId(studienId);
            int scoreStudie =  (int) Statistik.mittelWert(scoreCursor);
            textViewGesamtScore.setText(""+scoreStudie);
            db.insertScoreStudie(studienId, scoreStudie);

            Cursor usabilityCursor = db.selectUsabilityByStudienId(studienId);
            double usabilityStudie = Statistik.berechneUsability(usabilityCursor);
            textViewUsabilityScore.setText("" + usabilityStudie);

            Cursor learnabilityCursor = db.selectLearnabilityByStudienId(studienId);
            double learnabilityStudie = Statistik.berechneLearnability(learnabilityCursor);
            textViewLearnabilityScore.setText("" + learnabilityStudie);
        }



        // TextViews füllen
        textViewNameStudie.setText(" " + studienName);
        textViewNameStudie.setPaintFlags(textViewNameStudie.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);



        String interfaceString = db.selectInterfaceByStudienId(studienId);
        textViewInterface.setText("" + interfaceString);


        //Button um einen neuen Test zu starten und der Studie hinzuzufügen
        btnNeuerTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), TestActivity.class);
                i.putExtra("studienId", studienId);
                startActivity(i);
            }
        });








    }


@Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);



        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.actionStartseite:
                Intent intent = new Intent(getBaseContext(),StartActivity.class);
                startActivity(intent);
                return true;

            case R.id.actionTestsEinsehen: //TODO Diese Option nicht anzeigen, wenn die Studie leer ist
                if(anzahl_tests == 0){
                    Toast.makeText(AuswertungStudieActivity.this,"Noch keine Tests innerhalb der Studie!",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent1 = new Intent(getBaseContext(), ListViewTestsActivity.class);
                    intent1.putExtra("studienId", studienId);
                    startActivity(intent1);
                }
                return true;

            case R.id.actionAlleStudienEinsehen:
                boolean kommeVonderStartseite = false;
                Intent intent2 = new Intent(getBaseContext(), ListViewStudienActivity.class);
                intent2.putExtra("kommeVonDerStartseite",kommeVonderStartseite);
                startActivity(intent2);
                return true;
            case R.id.actionStatistik:
                //TODO: hier auch noch alle Daten an die Statistik-Klasse mitschicken
                if(anzahl_tests == 0){
                    Toast.makeText(AuswertungStudieActivity.this,"Noch keine Tests innerhalb der Studie!",Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent3 = new Intent(getBaseContext(), StatistikAuswertung.class);
                    intent3.putExtra("studienId", studienId);
                    intent3.putExtra("studienName", studienName);
                    intent3.putExtra("anzahlTests",anzahl_tests);
                    startActivity(intent3);
                }

            return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }



}







