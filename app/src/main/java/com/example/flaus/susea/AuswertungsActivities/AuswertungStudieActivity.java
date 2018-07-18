package com.example.flaus.susea.AuswertungsActivities;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
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
import com.example.flaus.susea.R;
import com.example.flaus.susea.StartActivity;
import com.example.flaus.susea.StatistikAuswertung;
import com.example.flaus.susea.TestActivity;
import com.example.flaus.susea.ListViewActivities.ListViewTestsActivity;

/* Übernimmmt die Auswertung und Darstellung der gesammelten Daten eines einzelnen Tests */

public class AuswertungStudieActivity extends AppCompatActivity {


    Datenbank manager = new Datenbank(this);
    TextView textViewNameStudie ,textViewGesamtScore, textViewAnzahlTests;
    Button btnStatistik, btnViewTests, btnZurStartseite;
    FloatingActionButton fab;
    long studienId;
    int score = 0;
    String studienName;
    boolean studie;

    Toolbar toolbar;
    Datenbank db = new Datenbank(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auswertung_studie);


        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Auswertung Studie");




        // View-Binding
        textViewNameStudie = (TextView) findViewById(R.id.textViewNameStudie);
        textViewGesamtScore = (TextView) findViewById(R.id.textViewScore);
        textViewAnzahlTests = (TextView) findViewById(R.id.textViewAnzahlTests);
        btnStatistik = (Button) findViewById(R.id.btnStatistik);
        Button btnNeuerTest = findViewById(R.id.btnNeuerTest);




        // Name und Id der Studie empfangen
        Intent intent = getIntent();
        studienId = intent.getLongExtra("studienId", -1);
        Log.d("Jule", "Id der neuen Studie empfangen:  " + studienId);
        studie = intent.getBooleanExtra("studie",false);
        if(studie){
            //Wenn eine neue Studie erstellt wurde, wird der Name gleich mit dem Intent mitgeschickt
            studienName = intent.getStringExtra("studienName");
            //Und es sind noch keine Tests drin
            textViewAnzahlTests.setText("Noch keine Tests");
        } else { //Ansonten Name und Daten der Studie aus der Datenbank holen
            Cursor c = db.getStudieById(studienId);
            c.moveToFirst();
            studienName = c.getString(1);
            int anzahl_tests = c.getInt(3); //TODO: Nachgucken, ob Anzahl Tests in der Db auch brav erhöht wird
            textViewAnzahlTests.append(" " + anzahl_tests);
        }



        // TextViews füllen
        textViewNameStudie.setText(" " + studienName);


        //Button um einen neuen Test zu starten und der Studie hinzuzufügen
        btnNeuerTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), TestActivity.class);
                i.putExtra("studienId", studienId);
                startActivity(i);
            }
        });

        btnStatistik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: einmal alles mitschicken
                Intent intent = new Intent(getBaseContext(), StatistikAuswertung.class);
                startActivity(intent);
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

            case R.id.actionTestsEinsehen:
                Intent intent1 = new Intent(getBaseContext(),ListViewTestsActivity.class);
                startActivity(intent1);
                return true;

            case R.id.actionAlleStudienEinsehen:
                boolean kommeVonderStartseite = false;
                Intent intent2 = new Intent(getBaseContext(), ListViewStudienActivity.class);
                intent2.putExtra("kommeVonDerStartseite",kommeVonderStartseite);
                startActivity(intent2);
                return true;
            case android.R.id.home:
                // TODO: Hier intent mitgeben, um auf auswertungsseite der studie zurück zu kommen

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }



}







