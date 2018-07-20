package com.example.flaus.susea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flaus.susea.AuswertungsActivities.AuswertungStudieActivity;
import com.example.flaus.susea.AuswertungsActivities.AuswertungTestActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;

/* Implementiert den eigentlichen SUS-Fragebogen */
public class TestActivity extends AppCompatActivity {


    EditText eingabe_alter;
    TextView textViewUeberschrift;
    TextView textViewFrage;
    ProgressBar progessBar;
    RadioGroup radioGroup;
    RadioGroup radioGroup_geschlecht;
    Button btnWeiter;
    int[] antworten = new int[10];
    int index = 0;
    String alter;
    int alterInt;
    boolean studie;
    long studienId;
    Toolbar toolbar;
    String studienName;
    long testId;
    String datum;
    String interfacetyp;
    Datenbank manager = new Datenbank(this);
    //Aussagen des SUS-Fragebogens
    String[] texteFragen = {"Ich benutze die Software gerne regelmäßig.",
            "Die Software wirkt auf mich recht einfach aufgebaut.",
            "Ich finde die Software leicht zu benutzen.",
            "Ich kann die Software ohne Unterstützung durch Fachpersonal direkt benutzen.",
            "Ich finde, dass die angebotenen Funktionen gut in die Software integriert sind.",
            "Ich finde, dass die Software einheitlich aufgebaut ist.",
            "Ich denke, dass die meisten Nutzer sehr schnell mit der Software zurecht kommen.",
            "Ich finde die Software in der Benutzung sehr intuitiv.",
            "Ich weiß bei der Benutzung der Software zu jedem Zeitpunkt, was ich tue.",
            "Ich konnte die Software bedienen ohne zuvor Neues erlernen zu müssen."};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // Toolbar
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Fragen beantworten");


        // Intent empfangen
        Intent intent = this.getIntent();
        studie = intent.getBooleanExtra("neue_studie", false);
        studienId = intent.getLongExtra("studienId",-1);
        studienName = intent.getStringExtra("studienName");
        interfacetyp = intent.getStringExtra("interfacetyp");




        Log.d("studie", "testActivity : " + studie);


        radioGroup = (RadioGroup) findViewById(R.id.radioGroup_Antworten);

        textViewUeberschrift = (TextView) findViewById(R.id.textView_Ueberschrift);
        textViewUeberschrift.setText("Frage " + (index + 1));


        textViewFrage = (TextView) findViewById(R.id.textView_Fragen);
        textViewFrage.setText(texteFragen[index]);

        progessBar = (ProgressBar) findViewById(R.id.progressBar);
        progessBar.setMax(texteFragen.length);

        btnWeiter = (Button) findViewById(R.id.button_naechsteFrage);
        btnWeiter.setEnabled(false);

        radioGroup_geschlecht = (RadioGroup) findViewById(R.id.radioGroup_Geschlecht);


        // Erst wenn man eine Antwort ausgewählt hat, kann man zur nächsten textViewFrage weiterkommen
        //Und auch erst mit dem Klick auf den Button wird die ausgewählte Antwort gespeichert
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                btnWeiter.setEnabled(true);
            }
        });
        btnWeiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                naechsteFrage();
            }
        });

        //Erst wenn eine Anwtort aufgewählt wurde, wird der Button enabled
        radioGroup_geschlecht.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                btnWeiter.setEnabled(true);
            }
        });


    }

    public void naechsteFrage() {
    /* Je nachdem, welcher der RadioButtons angeklcikt wurde, wird der ausgewählte Wert 1-5
    an der passenden Stelle im Array abgelegt
     */

        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        switch (radioButtonID) {
            case R.id.radioButton1:
                antworten[index] = 1;
                break;
            case R.id.radioButton2:
                antworten[index] = 2;
                break;
            case R.id.radioButton3:
                antworten[index] = 3;
                break;
            case R.id.radioButton4:
                antworten[index] = 4;
                break;
            case R.id.radioButton5:
                antworten[index] = 5;
                break;

        }
        //Setzt wieder alle RadioButtons auf unchecked
        radioGroup.clearCheck();
        index = index + 1;
        textViewUeberschrift.setText("Frage " + (index + 1));
        progessBar.incrementProgressBy(1);
        if (index < texteFragen.length) {
            textViewFrage.setText(texteFragen[index]);
            btnWeiter.setEnabled(false);
        } else if (index == texteFragen.length) {
            //Ui ändern für die letzen beiden Fragen (Geschlecht/Alter)
            changeUi();

        } else if (index > texteFragen.length) {
            btnWeiter.setEnabled(false);

            //Eingegebne Daten sammeln
            //TODO: evtl Numberpicker statt EditText
            eingabe_alter = (EditText) findViewById(R.id.textEdit_alter);
            //TODO: sicher stellen, dass eine sinnvolle Zahl eingegben wurde
            alter = eingabe_alter.getText().toString();
            Log.d("alter","alter: \""+alter+"\"");
            try {
                alterInt = valueOf(alter);
            }catch (NumberFormatException e){
                alter = "";
            }
            if(alter!="") {
                //Geschlecht auswerten
                int radioButtonId = radioGroup_geschlecht.getCheckedRadioButtonId();
                String geschlecht = "";

                switch (radioButtonId) {
                    case R.id.radioButton_male:
                        geschlecht = geschlecht + "m";
                        break;
                    case R.id.radioButton_female:
                        geschlecht = geschlecht + "w";
                        break;
                }


                // Datum hinzufügen
                datum = getDatum();

                //TODO: Das ist hier nur zum Test, nicht vergessen, nachher zu löschen
                int score = Statistik.berechneScore(antworten);

                //gesammelte Daten in die Datenbank schreiben
                testId = manager.insertTest(antworten, alterInt, geschlecht, datum, studienId, score);

                Log.d("TJ", "test_id vor intent" + testId);
                Log.d("studId", "StudieId in TestActivity = " + studienId);
                //Daten an die AuswertungsTestActivity übergeben

                for (int i = 0; i < antworten.length; i++) {
                    Log.d("test", "antworten länge = " + antworten.length);
                    Log.d("test", " antworten = " + antworten[i]);
                }
                // Endscreen

                //Test ob median funktion funktioniert.
                // Statistik.median(antworten);

                endScreen();
            }else{
                Toast.makeText(TestActivity.this, "Bitte geben Sie Ihr Alter ein.", Toast.LENGTH_SHORT).show();
            }

        }






    }

    //Ändert das UI für die letzte Seite des Fragebogens zur Erhebung von Alter und Geschlecht
    public void changeUi(){
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_letzeSeiteTest);
        //TextView zustimmung = (TextView) findViewById(R.studienId.textView_Zustimmung);
        //TextView ablehnung = (TextView) findViewById(R.studienId.textView_Ablehnung);
        LinearLayout layoutZustimmung = (LinearLayout) findViewById(R.id.layoutZustimmung);


        textViewFrage.setText("Bitte geben Sie noch Ihr Alter und Geschlecht an.");
        btnWeiter.setText("Test beenden");

        layoutZustimmung.setVisibility((View.GONE));
        layout.setVisibility(View.VISIBLE);
        radioGroup.setVisibility(View.GONE);
        textViewUeberschrift.setVisibility(View.GONE);
        //zustimmung.setVisibility(View.GONE);
        //ablehnung.setVisibility(View.GONE);

    }


    //Schaltet das UI um, sodass das EndScreen angezeigt wird
    //Außerdem wird hier der Intent anbeschickt, der zur Auswertung der Studie weiterleitet
    public void endScreen(){
            LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_letzeSeiteTest);
            textViewFrage.setText("Vielen Dank für Ihre Teilnahme, Sie sind fertig! Bitte geben Sie das Gerät zurück.");
            layout.setVisibility(View.GONE);
            btnWeiter.setEnabled(true);
            btnWeiter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), AuswertungStudieActivity.class);
                    intent.putExtra("antworten", antworten);
                    intent.putExtra("testId",testId);

                    if(studie) {
                        //Das sind alles Werte, die man für die Auswertung einer Studie braucht
                        intent.putExtra("neue_studie", true);
                        intent.putExtra("studienId", studienId);
                        Log.d("Jule", "Id, die mitgeschickt wird: " + studienId);
                        intent.putExtra("studienName", studienName);
                        intent.putExtra("interfacetyp", interfacetyp);
                        startActivity(intent);
                    } else {
                        //Das sind die Werte, die mitgeschickt werden, wenn nur ein einzelner Test gestartet wurde
                        Intent intent1 = new Intent( getBaseContext(), AuswertungTestActivity.class);
                        intent1.putExtra("testId",testId);
                        intent1.putExtra("neue_studie", false);
                        intent1.putExtra("antworten", antworten);
                        intent1.putExtra("interfacetyp", interfacetyp);
                        startActivity(intent1);

                    }

                }
            });
    }


    //Hilfsfunktion um das aktuelle Datum und Uhrzeit vom System zu bekommen
    public String getDatum() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy   HH:mm");
        String dateAndTime = sdf.format(new Date());


        return  dateAndTime;
    }







}
