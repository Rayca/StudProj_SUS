package com.example.flaus.susea.AuswertungsActivities;

import android.content.Intent;
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

import com.example.flaus.susea.AuswertungsActivities.AuswertungStudieActivity;
import com.example.flaus.susea.Datenbank;
import com.example.flaus.susea.ListViewActivities.ListViewStudienActivity;
import com.example.flaus.susea.R;
import com.example.flaus.susea.StartActivity;
import com.example.flaus.susea.Statistik;


/* Übernimmmt die Auswertung und Darstellung der gesammelten Daten eines einzelnen Tests */
public class AuswertungTestActivity extends AppCompatActivity {

    TextView textViewScore, textViewGeschlecht, textViewAlter, textViewAntworten;
    Button btnZurStudie, btnZurStartseite;
    long testId;
    long studienId = -1;
    int[] antworten = new int[10];
    boolean studie;
    String studienName;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auswertung_test);

        Datenbank mDatenbankManager = new Datenbank(this);

        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_theme));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tests der Studie ");

        // View-Binding
        textViewScore = (TextView) findViewById(R.id.textViewScore);
        textViewGeschlecht = (TextView) findViewById(R.id.textViewGeschlecht);
        textViewAlter = (TextView)findViewById(R.id.textViewAlter);
        textViewAntworten = (TextView) findViewById(R.id.textViewAntworten);



        // Intent empfangen
        Intent intent = getIntent();
        testId = intent.getLongExtra("testId",-1);
        studie = intent.getBooleanExtra("neue_studie", false);
        antworten = intent.getIntArrayExtra("antworten");
        studienName = intent.getStringExtra("studienName");
        studienId = intent.getLongExtra("studienId",-1);


        Log.d("studie", "LöschenAuswertungTest =" + studie);
















        // TextViews füllen
        textViewScore.append(" " + Statistik.berechneScore(antworten));


        // TODO : geschlecht und alter angeben und antworten einzelner fragen
        // TODO : Button zurück zur StudienActivity mit funktion versetzen. intent muss alles mitgeben



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();
        if(itemId==R.id.actionStartseite){
            Intent intent = new Intent(getBaseContext(),StartActivity.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }








}
