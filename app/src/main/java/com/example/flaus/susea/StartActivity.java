package com.example.flaus.susea;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.flaus.susea.ListViewActivities.ListViewStudienActivity;

//Controller, der nur vorgibt, was als nächstes getan werden soll
public class StartActivity extends AppCompatActivity {

    boolean neue_studie; //Wird gebraucht um später entscheiden zu können, ob gerade eine neue Studie eingefügt wurde
    boolean studie;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ActionBar actionBar = getSupportActionBar();
        //actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_theme));



    }

    /*Startet eine neue Studie und schickt einen Intent an TypActivity ab, in der
     der Interfacetyp der neuen Studie ausgewählt werden soll
    Das Boolean neue_studie wird als Flag mitgeschickt, dass eine neue Studie erstellt
    werden soll und keine vorhandene aus der Db geladen werden soll */
    public void studie_starten(View v){
        neue_studie = true;
        Intent intent = new Intent(this, TypActivity.class);
        intent.putExtra("Studie", neue_studie);
        startActivity(intent);

    }

    //Startet einen einzelnen  Test
    public void start(View view){
        neue_studie = false;
        Intent intent = new Intent(this, TypActivity.class);
        intent.putExtra("Studie", neue_studie);
        startActivity(intent);
    }

    //Lässt alle Studie in der DB als Liste anzeigen
    public void studienEinsehen(View view){
        Intent intent = new Intent(getBaseContext(), ListViewStudienActivity.class);
        startActivity(intent);
    }
}
