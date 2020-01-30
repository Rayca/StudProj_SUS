package com.example.flaus.susea;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Integer.valueOf;

/* Implementiert den eigentlichen SUS-Fragebogen */
public class TestActivity_ISO extends AppCompatActivity {


    EditText eingabe_alter;
    TextView textViewUeberschrift;
    TextView textViewFrageLinks;
    TextView textViewFrageRechts;
    ProgressBar progessBar;
    RadioGroup radioGroup;
    RadioGroup radioGroup_geschlecht;
    Button btnWeiter;
    int[] antworten = new int[21];
    int index = 0;
    String alter;
    int alterInt;
    boolean studie;
    long studienId = -1;
    Toolbar toolbar;
    String studienName;
    long testId;
    String datum;
    String interfacetyp;
    Datenbank manager = new Datenbank(this);
    //Fragen aus dem ISONORM Fragebogen
    String[] texteFragen_links = {"bietet nicht alle Funktionen, um die anfallenden Aufgaben effizient zu bewältigen.",
            "erfordert überflüssige Eingaben.",
            "ist schlecht auf die Anforderungen der Arbeit zugeschnitten",
            "liefert in unzureichendem Maße Informationen darüber, welche Eingaben zuverlässig oder nötig sind.",
            "bietet auf Verlangen keine situationsspezifischen Erklärungen, die konkret weiterhelfen.",
            "erzwingt eine unnötig starre Einhaltung von Berarbeitungsschritten.",
            "ermöglicht keinen leichten Wechsel zwischen einzelnen Menüs oder Masken",
            "erzwingt unnötige Unterbrechungen der Arbeit.",
            "erschwert die Orientierung durch eine uneinheitliche Gestaltung.",
            "informiert in unzureichendem Maße über das, was er gerade macht.",
            "lässt sich nicht durchgehend nach einem einheitlichen Prinzip bedienen.",
            "liefert schlecht verständliche Fehlermeldungen.",
            "erdordert bei Fehlern im Großen und Ganzen keinen Korrekturaufwand.",
            "gibt keine konkreten Hinweise zur Fehlerbehebung.",
            "lässt sich von mir schwer erweitern, wenn für mich neue Aufgaben entstehen",
            "lässt sich von mir schlecht an meine persänliche, individuelle Art der Arbeitserledigung anpassen.",
            "lässt sich - im Rahmen ihrem Leistungsumfangs von mir schlecht für unterschiedliche Aufgaben passend einrichten.",
            "erfordert viel Zeit zum Erlernen.",
            "erfordert, dass man sich viele Details merken muss",
            "ist schlecht ohen fremde Hilfe oder Handbuch erlernbar"};

    String[] texteFragen_rechts = {"bietet alle Funktionen, um die anfallenden Aufgaben effizient zu bewältigen.",
            "erfordert keine überflüssigen Eingaben.",
            "ist gut auf die Anforderungen der Arbeit zugeschnitten.",
            "liefert in zureichendem Maße Informationen darüber, welche Eingaben zulässig oder nötig sind.",
            "bietet auf Verlangen situationsspezifische Erklärungen, die konkret weiterhelfen.",
            "Ich finde, dass die Software einheitlich aufgebaut ist.",
            "Ich denke, dass die meisten Nutzer sehr schnell mit der Software zurecht kommen.",
            "Ich finde die Software in der Benutzung sehr intuitiv.",
            "Ich weiß bei der Benutzung der Software zu jedem Zeitpunkt, was ich tue.",
            "Ich konnte die Software bedienen ohne zuvor Neues erlernen zu müssen."};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_iso);

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


        textViewFrageLinks = (TextView) findViewById(R.id.textView_FrageLinks);
        textViewFrageLinks.setText(texteFragen_links[index]);

        textViewFrageRechts = (TextView) findViewById(R.id.textView_FrageRechts);
        textViewFrageRechts.setText(texteFragen_rechts[index]);

        progessBar = (ProgressBar) findViewById(R.id.progressBar);
        progessBar.setMax(texteFragen_links.length);

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
    /* Je nachdem, welcher der RadioButtons angeklickt wurde, wird der ausgewählte Wert 1-5
    an der passenden Stelle im Array abgelegt
     */

        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        switch (radioButtonID) {
            case R.id.radioButton1:
                antworten[index] = -3;
                break;
            case R.id.radioButton2:
                antworten[index] = -2;
                break;
            case R.id.radioButton3:
                antworten[index] = -1;
                break;
            case R.id.radioButton4:
                antworten[index] = 0;
                break;
            case R.id.radioButton5:
                antworten[index] = 1;
                break;
            case R.id.radioButton6:
                antworten[index] = 2;
                break;
            case R.id.radioButton7:
                antworten[index] = 3;
                break;


        }
        //Setzt wieder alle RadioButtons auf unchecked
        radioGroup.clearCheck();
        index = index + 1;
        textViewUeberschrift.setText("Frage " + (index + 1));
        progessBar.incrementProgressBy(1);
        if (index < texteFragen_links.length) {
            textViewFrageLinks.setText(texteFragen_links[index]);
            textViewFrageRechts.setText(texteFragen_rechts[index]);
            btnWeiter.setEnabled(false);
        } else if (index == texteFragen_links.length) {
            //Ui ändern für die letzen beiden Fragen (Geschlecht/Alter)
            changeUi();

        } else if (index > texteFragen_links.length) {
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

                //Berechnung der Werte für die einzelnen Dialogprinzipinen
                int score_gesamt = Statistik.berechneGesamtScore(antworten);
                int aufgabenangemessenheit = Statistik.berechneMittelwert3(antworten[1], antworten[2], antworten[3]);
                int selbstbeschreibungsfaehigkeit = Statistik.berechneMittelwert3(antworten[4], antworten[5], antworten[6]);
                int steuerbarkeit = Statistik.berechneMittelwert3(antworten[7], antworten[8], antworten[9]);
                int erwartungskonformitaet = Statistik.berechneMittelwert3(antworten[10], antworten[11], antworten[12]);
                int fehlertoleranz = Statistik.berechneMittelwert3(antworten[13], antworten[14], antworten[15]);
                int individualisierbarkeit = Statistik.berechneMittelwert3(antworten[16], antworten[17], antworten[18]);
                int lernfoerderlichkeit = Statistik.berechneMittelwert3(antworten[19], antworten[20], antworten[21]);


                //gesammelte Daten in die Datenbank schreiben
                testId = manager.insertTestISO(antworten, alterInt, geschlecht, datum, studienId, score_gesamt, aufgabenangemessenheit, selbstbeschreibungsfaehigkeit, steuerbarkeit, erwartungskonformitaet, fehlertoleranz, individualisierbarkeit, lernfoerderlichkeit);

                Log.d("TJ", "test_id vor intent" + testId);
                Log.d("studId", "StudieIdISO in TestActivityISO = " + studienId);
                //Daten an die AuswertungsTestActivity übergeben

                for (int i = 0; i < antworten.length; i++) {
                    Log.d("test", "antworten länge = " + antworten.length);
                    Log.d("test", " antworten = " + antworten[i]);
                }
                // Endscreen

                endScreen();
            }else{
                Toast.makeText(TestActivity_ISO.this, "Bitte geben Sie Ihr Alter ein.", Toast.LENGTH_SHORT).show();
            }

        }






    }

    //Ändert das UI für die letzte Seite des Fragebogens zur Erhebung von Alter und Geschlecht
    public void changeUi(){
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_letzeSeiteTest);
        //TextView zustimmung = (TextView) findViewById(R.studienId.textView_Zustimmung);
        //TextView ablehnung = (TextView) findViewById(R.studienId.textView_Ablehnung);
        LinearLayout layoutZustimmung = (LinearLayout) findViewById(R.id.layoutZustimmung);


        textViewUeberschrift.setText("Bitte geben Sie noch Ihr Alter und Geschlecht an.");
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
            textViewUeberschrift.setText("Vielen Dank für Ihre Teilnahme, Sie sind fertig! Bitte geben Sie das Gerät zurück.");
            layout.setVisibility(View.GONE);
            btnWeiter.setEnabled(true);
            btnWeiter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), AuswertungStudieActivity.class);
                    intent.putExtra("antworten", antworten);
                    intent.putExtra("testId",testId);


                        //Das sind alles Werte, die man für die Auswertung einer Studie braucht
                        intent.putExtra("studienId", studienId);
                        Log.d("Jule", "Id, die mitgeschickt wird: " + studienId);
                        intent.putExtra("studienName", studienName);
                        intent.putExtra("interfacetyp", interfacetyp);
                        startActivity(intent);


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
