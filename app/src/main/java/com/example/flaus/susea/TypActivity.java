package com.example.flaus.susea;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.view.View;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.util.ArrayList;

public class TypActivity extends AppCompatActivity {
    String typ;
    AlertDialog studie_erstellen;
    String name_studie;
    Datenbank db;

    // Variabeln zur erstellung der studie
    int anzahlTests = 0;
    int scoreGesamt = 0;
    long studieId = -1;
    boolean studie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typ);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_theme));

        db = new Datenbank(this);

       Intent intent = this.getIntent();
        studie = intent.getBooleanExtra("Studie", false);
        Log.d("studie", "TypActivity =" + studie);

        final ListView listview = findViewById(R.id.list_view_typ);
        String[] typen = {
                "B2B", "B2C", "Web", "Cell", "HW", "Internal SW", "IVR", "Web/IVR"
        };
        final ArrayList<String> typenList = new ArrayList<String>();
        for (int i = 0; i < typen.length; ++i) {
            typenList.add(typen[i]);
        }
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, typenList);
        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = listview.getItemAtPosition(position).toString();

                setTyp(name);

            }
        });

        Button button_next = (Button) findViewById(R.id.button_next);
        button_next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View button_next) {

                if (typ != null) {
                    if ( studie == true) {
                        dialog_studie_erstellen_öffnen();
                    } else {
                        Intent intent1= new Intent(getBaseContext(), TestActivity.class);
                        intent1.putExtra("Name_der_Studie", name_studie);
                        intent1.putExtra("Interfacetyp", typ);
                        intent1.putExtra("studienId",studieId);
                        intent1.putExtra("studie", studie);
                        startActivity(intent1);
                    }

                }else{
                    Toast toast = Toast.makeText(getApplicationContext(),"Bitte einen Typen wählen",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        ImageButton button_help = (ImageButton) findViewById(R.id.button_help);
        button_help.setOnClickListener(new OnClickListener() { //Help Button


            @Override
            public void onClick(View button_help) {
                AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(TypActivity.this);
                dlgBuilder.setTitle("Hilfe");
                dlgBuilder.setMessage("B2B: Enterprise software application such as accounting, HR, CRM and order-management systems\n\n" +
                        "B2C: Public-facing mass-market consumer software: Office Applications, Graphics Apps and Personal Finance Software\n\n" +
                        "Web: Public-facing large-scale websites (airlines, rental cars, retailers, financial service) and intranets\n\n" +
                        "Cell: Cell-phone equipment\n\n" +
                        "HW: Hardware such as phones, modems and Ethernet cards\n\n" +
                        "Internal SW: Internal-productivity software: Customer Service and Network Operations applications. This group likely has overlaps between the B2C and B2B groups.\n\n" +
                        "IVR: Interactive Voice Response Systems (phone- and speech-based)\n\n" +
                        "Web/IVR: A combination of web-based and interactive voice-response systems");
                dlgBuilder.setCancelable(true);
                dlgBuilder.setNeutralButton("Verstanden!", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


                AlertDialog alert = dlgBuilder.create();
                alert.show();
            }
        });


    }

    public void setTyp(String typ){
        this.typ = typ;
    }

    // Öffnet und erstellt den Dialog um der neuen Studie einen Namen zu geben
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
                Log.d("Jule", name_studie);



                // Studie in Datenbank einfügen
                studieId =  db.insertStudie(name_studie,typ,anzahlTests,scoreGesamt);
               Log.d("TJ", "StudieId = " + studieId);
               if(studieId != -1) {
                   Toast.makeText(TypActivity.this, "Studie wurde erfolgreich erstellt!", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(getBaseContext(), AuswertungStudieActivity.class);
                   intent.putExtra("Name_der_Studie", name_studie);
                   intent.putExtra("Interfacetyp", typ);
                   intent.putExtra("studienId",studieId);
                   intent.putExtra("studie", studie);
                   startActivity(intent);

               } else {
                   Toast.makeText(TypActivity.this, "Ups, da lief was schief!",Toast.LENGTH_SHORT).show();
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

}

