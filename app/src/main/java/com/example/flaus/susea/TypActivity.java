package com.example.flaus.susea;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.example.flaus.susea.AuswertungsActivities.AuswertungStudieActivity;
import com.example.flaus.susea.ListViewActivities.ListViewStudienActivity;
import com.example.flaus.susea.ListViewActivities.ListViewTestsActivity;

import java.util.ArrayList;

public class TypActivity extends AppCompatActivity {
    String typ;
    AlertDialog studie_erstellen;
    String name_studie;
    Datenbank db;
    Button button_next, btnAbbrechen;

    // Variabeln zur erstellung der neue_studie
    int anzahlTests = 0;
    int scoreGesamt = 0;
    long studienId = -1;
    Toolbar toolbar;
    boolean kommeVonListeStudien;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typ);

        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_theme));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Interfacetyp auswählen");

        // Pfeil für den User flow
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        db = new Datenbank(this);

        //Intent Empfangen und Daten daraus lesen
       Intent intent = this.getIntent();
       kommeVonListeStudien = intent.getBooleanExtra("kommeVonListeStudien",false);


        // Auswahlliste Interfacetypen füllen
        final ListView listview = findViewById(R.id.list_view_typ);
        String[] typen = {
                "Hardware", "Software", "Mobile", "Tablet", "Enterprise Software", "Webseite"
        };
        final ArrayList<String> typenList = new ArrayList<String>();
        for (int i = 0; i < typen.length; ++i) {
            typenList.add(typen[i]);
        }
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, typenList);
        listview.setAdapter(adapter);


        //Auswahl einer Interfacetypen aus der Liste
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // TODO: Schriftfarbe des ausgewählten items zu weiß ändern
                //Übergibt den ausgewählten Interfacetyp als name
                String name = listview.getItemAtPosition(position).toString();
                setTyp(name);

            }
        });

        button_next = findViewById(R.id.button_next);
        button_next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View button_next) {

                if (typ != null) {
                        dialog_studie_erstellen_öffnen();
                         //Schickt den Intent ab, gefüllt mit allen wichtigen Daten für die Anzeige der Studie
                     /*   Intent intent1= new Intent(getBaseContext(), TestActivity.class);
                        intent1.putExtra("studienName", name_studie);
                        intent1.putExtra("interfacetyp", typ);
                        intent1.putExtra("studienId",studienId); //TODO müssen wir die wirklich als -1 mitschicken?
                        startActivity(intent1); */
                    }

                else{ //Wenn kein Interfacetyp vorher ausgewählt wurde
                    Toast toast = Toast.makeText(getApplicationContext(),"Bitte einen Typen wählen",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });








    }

    public void setTyp(String typ){
        this.typ = typ;
    }

    // Öffnet und erstellt den Dialog um der neuen Studie einen Namen zu geben
    //Bestätigt man den Dialog wird eine neue Studie in die DB eingefügt und
    //man gelangt zur nächsten Activity (Übersicht über die Studie)
    public void dialog_studie_erstellen_öffnen(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Log.d("Jule", "Funktion zum Erstellen des Dialogs wird aufgerufen");
        builder.setTitle("Neue Studie erstellen");
        builder.setMessage("Bitte einen Namen für die Studie eingeben");

        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_studie_erstellen, null);
        builder.setView(v);

        final EditText eingabe_name = (EditText) v.findViewById(R.id.editText_name);

        builder.setPositiveButton("Studie erstellen", new DialogInterface.OnClickListener() {
            @Override
            //TODO: sicherstellen, dass auch etwas eingegeben wurde
            public void onClick(DialogInterface dialogInterface, int i) {
                name_studie = eingabe_name.getText().toString();
                Log.d("Jule", "\""+name_studie+"\"");


               Log.d("studID", "StudieId = " + studienId);
               if(name_studie.isEmpty() != true) {


                   // Studie in Datenbank einfügen
                   studienId =  db.insertStudie(name_studie,typ,anzahlTests,scoreGesamt);

                   Toast.makeText(TypActivity.this, "Studie wurde erfolgreich erstellt!", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(getBaseContext(), AuswertungStudieActivity.class);
                   intent.putExtra("studienName", name_studie);
                   intent.putExtra("Interfacetyp", typ);
                   intent.putExtra("studienId", studienId);
                   startActivity(intent);

               } else {
                   Toast.makeText(TypActivity.this, "Bitte geben Sie der Studie einen Namen!",Toast.LENGTH_SHORT).show();
               }
            }

        });
        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Dialog wird einfach geschlossen, sonst passiert nichts
            }
        });

        studie_erstellen = builder.create();
        studie_erstellen.show();


        return;
    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                Log.d("thomas", "home wurde gedrückt");
                if(kommeVonListeStudien){
                    Intent intent = new Intent(getBaseContext(), ListViewStudienActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getBaseContext(), StartActivity.class);
                    startActivity(intent);

                }

                return true;




            default:
                Log.d("thomas", "home wurde gedrückt");
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }



}

