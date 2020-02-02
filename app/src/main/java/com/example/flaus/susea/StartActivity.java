package com.example.flaus.susea;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.flaus.susea.ListViewActivities.ListViewStudienActivity;
import com.example.flaus.susea.ListViewActivities.ISO_ListViewStudienActivity;

//Controller, der nur vorgibt, was als nächstes getan werden soll
public class StartActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_theme));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Usability Evaluation");



    }

    /*Startet eine neue Studie und schickt einen Intent an TypActivity ab, in der
     der Interfacetyp der neuen Studie ausgewählt werden soll
    Das Boolean neue_studie wird als Flag mitgeschickt, dass eine neue Studie erstellt
    werden soll und keine vorhandene aus der Db geladen werden soll */
    public void studie_starten(View v){
        Intent intent = new Intent(this, TypActivity.class);
        startActivity(intent);

    }

    //Startet einen einzelnen  Test
    public void start(View view){
        Intent intent = new Intent(this, TypActivity.class);
        startActivity(intent);
    }

    //Lässt alle Studie in der DB als Liste anzeigen
    public void SUS_StudienEinsehen(View view){
        Intent intent = new Intent(getBaseContext(), ListViewStudienActivity.class);
        startActivity(intent);
    }

    public void ISO_StudienEinsehen(View view){
        Intent intent = new Intent(getBaseContext(), ISO_ListViewStudienActivity.class);
        startActivity(intent);
    }
}
