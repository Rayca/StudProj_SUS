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
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Auswertung Test");

        // View-Binding
        textViewScore = (TextView) findViewById(R.id.textViewScore);
        textViewGeschlecht = (TextView) findViewById(R.id.textViewGeschlecht);
        textViewAlter = (TextView)findViewById(R.id.textViewAlter);
        textViewAntworten = (TextView) findViewById(R.id.textViewAntworten);
        btnZurStudie = (Button) findViewById(R.id.btnStudie);
        btnZurStartseite = (Button) findViewById(R.id.btnZurStartseite);


        // Intent empfangen
        Intent intent = getIntent();
        testId = intent.getLongExtra("testId",-1);
        studie = intent.getBooleanExtra("studie", false);
        antworten = intent.getIntArrayExtra("antworten");
        studienName = intent.getStringExtra("studienName");
        studienId = intent.getLongExtra("studienId",-1);


        Log.d("studie", "AuswertungTest =" + studie);



        // Button Text ändert sich je nach dem, ob es einzelner Test war oder ob man von einer Studie kommt

        if(studie){
            btnZurStudie.setText("Zurück zur Studien Activity");
        } else {
            btnZurStudie.setText("Test einer Studie zuordnen");
        }


        // Funktion für btnZurStudie

        btnZurStudie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(studie){
                    Intent intent1 = new Intent(getApplicationContext(),AuswertungStudieActivity.class);
                    intent1.putExtra("testId", testId);
                    intent1.putExtra("studie",studie);
                    intent1.putExtra("studienName", studienName);
                    intent1.putExtra("studienId", studienId);
                    startActivity(intent1);
                } else {
                    Intent intent2 = new Intent(getApplicationContext(),ListViewStudienActivity.class);
                    intent2.putExtra("testId", testId);
                    intent2.putExtra("studie",studie);
                    startActivity(intent2);
                }
            }
        });




        // Funktion für btnStartseite
        btnZurStartseite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), StartActivity.class);
                startActivity(intent);
            }
        });



        // TextViews füllen
        textViewScore.setText(" " + Statistik.berechneScore(antworten));


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
