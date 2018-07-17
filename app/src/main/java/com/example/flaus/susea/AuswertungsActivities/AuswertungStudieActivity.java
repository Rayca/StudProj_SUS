package com.example.flaus.susea.AuswertungsActivities;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        btnViewTests = (Button) findViewById(R.id.btnViewTests);
        btnZurStartseite = (Button) findViewById(R.id.btnZurStartseite);
        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);


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
            studienName = c.getString(1);
             //int anzahl_tests = c.getInt(3); //TODO: Nachgucken, ob Anzahl Tests in der Db auch brav erhöht wird
            // textViewAnzahlTests.setText(anzahl_tests);
        }



        // TextViews füllen
        textViewNameStudie.setText(" " + studienName);






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
                Log.d("studID", "StudieId = " + studienId);
                Intent intent = new Intent(getBaseContext(), ListViewTestsActivity.class);
                intent.putExtra("studienId", studienId);
                startActivity(intent);
            }
        });

        btnStatistik.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                int i = manager.selectAllTestsbyStudienId(studienId).getCount();
                if (i < 3) {
                    Toast.makeText(AuswertungStudieActivity.this, "Mindestens 3 Tests für eine Auswertung", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Funktion für fab

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TestActivity.class);
                Log.d("studID", "StudieId = " + studienId);
                intent.putExtra("studienId", studienId);
                intent.putExtra("studienName", studienName);
                intent.putExtra("studie",studie);
                startActivity(intent);
            }
        });




    }


}







