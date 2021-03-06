package de.hskl.hci2b.useva;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.view.View.OnClickListener;

import de.hskl.hci2b.useva.AuswertungsActivities.AuswertungStudieActivity;
import de.hskl.hci2b.useva.AuswertungsActivities.AuswertungStudieISONORMActivity;
import de.hskl.hci2b.useva.ListViewActivities.ListViewStudienActivity;

import com.example.flaus.susea.R;

import java.util.ArrayList;

public class TypActivity extends AppCompatActivity {
    String typ;
    AlertDialog studie_erstellen;
    String name_studie;
    Datenbank db;
    Button button_next;

        // Variabeln zur erstellung der neue_studie
    int anzahlTests = 0;
    double scoreGesamt = 0;
    long studienId = -1;
    Toolbar toolbar;
    boolean kommeVonListeStudien;
    RadioGroup typFragebogen;
    String fragebogen;


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
        //final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, typenList);
        final ArrayAdapter adapter = new ArrayAdapter(this, R.layout.customlistviewitem, typenList);
        listview.setAdapter(adapter);


        //Auswahl eines Interfacetypen aus der Liste
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Übergibt den ausgewählten Interfacetyp als name
                String name = listview.getItemAtPosition(position).toString();
                setTyp(name);

            }
        });

        //Auswahl des Fragebogens
        typFragebogen = (RadioGroup) findViewById(R.id.radioGroup_fragebogen);

        button_next = findViewById(R.id.button_next);
        button_next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View button_next) {

                if (typ != null) {
                    int radioButtonID = typFragebogen.getCheckedRadioButtonId();
                    switch (radioButtonID) {
                        case R.id.radioButton_ISONORM:
                            fragebogen="I";
                            break;
                        case R.id.radioButton_SUS:
                            fragebogen="S";
                            break;
                    }
                    Log.d("Jule", "Ausgewählter Fragebogen " + fragebogen);
                    dialog_studie_erstellen_öffnen();
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

    //Öffnet und erstellt den Dialog um der neuen Studie einen Namen zu geben
    //Bestätigt man den Dialog wird eine neue Studie in die DB eingefügt und
    //man gelangt zur nächsten Activity (Übersicht über die Studie)

    protected void resumecreate(){
        Toast.makeText(TypActivity.this, "Studie wurde erfolgreich erstellt!", Toast.LENGTH_SHORT).show();

        if(fragebogen.equals("S")) {
            // Studie in Datenbank einfügen
            Log.d("studie", "SUS !!!!");
            studienId =  db.insertStudie(name_studie,typ,anzahlTests,scoreGesamt);//Schickt den Intent ab, um eine Studie mit SUS zu starten
            Intent intent = new Intent(getBaseContext(), AuswertungStudieActivity.class);
            intent.putExtra("studienName", name_studie);
            intent.putExtra("Interfacetyp", typ);
            intent.putExtra("studienId", studienId);
            startActivity(intent);
        } else if(fragebogen.equals("I")){
            Log.d("studie", "ISO !!!!");
            // Studie in Datenbank einfügen
            studienId =  db.insertStudieISO(name_studie,typ,anzahlTests,scoreGesamt);//Schickt den Intent ab, um eine Studie mit ISONORM zu starten
            Log.d("studie", "StudienID_TypActivity: "+studienId);
            Intent intent = new Intent(getBaseContext(), AuswertungStudieISONORMActivity.class);
            intent.putExtra("studienName", name_studie);
            intent.putExtra("Interfacetyp", typ);
            intent.putExtra("studienId", studienId);
            startActivity(intent);
        }
        //TODO: hier sollte noch eine gute Fehlermeldung kommen
    }

    public void dialog_studie_erstellen_öffnen(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Log.d("Jule", "Funktion zum Erstellen des Dialogs wird aufgerufen");
        builder.setTitle("Neue Studie erstellen");
        builder.setMessage("Bitte einen Namen für die Studie eingeben");
        final TextInputLayout studieinput = new TextInputLayout(this);
        final EditText eingabe_name = new EditText(this);
        studieinput.addView(eingabe_name);
        builder.setPositiveButton("Studie erstellen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                name_studie = eingabe_name.getText().toString();
                Log.d("Jule", "\""+name_studie+"\"");
                Log.d("studID", "StudieId = " + studienId);
                if(name_studie.isEmpty() != true) {
                    resumecreate();
                } else {
                    Toast.makeText(TypActivity.this, "Bitte geben Sie der Studie einen Namen!",Toast.LENGTH_SHORT).show();
                }
            }

        });
        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Dialog wird einfach geschlossen, sonst passiert nichts
                studie_erstellen.dismiss();
            }
        });
        builder.setView(studieinput);
        studie_erstellen = builder.create();
        studie_erstellen.show();
        eingabe_name.setFocusableInTouchMode(true);
        eingabe_name.requestFocus();
        eingabe_name.setSingleLine(true);
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        studie_erstellen.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface v, int keyCode, KeyEvent event) {

                // Wenn Enter-Button gedrückt wird
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    name_studie = eingabe_name.getText().toString();
                    Log.d("Jule", "\""+name_studie+"\"");
                    Log.d("studID", "StudieId = " + studienId);
                    if(name_studie.isEmpty() != true) {
                        resumecreate();
                    } else {
                        Toast.makeText(TypActivity.this, "Bitte geben Sie der Studie einen Namen!",Toast.LENGTH_SHORT).show();
                    }
                }
                else if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_BACK)) {
                    //Dialog wird einfach geschlossen, sonst passiert nichts
                    studie_erstellen.dismiss();
                }
                return false;
            }
        });
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

