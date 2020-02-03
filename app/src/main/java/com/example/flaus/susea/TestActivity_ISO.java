package com.example.flaus.susea;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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
import com.example.flaus.susea.AuswertungsActivities.AuswertungStudieISONORMActivity;

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
            "bietet von sich aus keine situationsspezifischen Erklärungen, die konkret weiterhelfen.",
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
            "bietet von sich aus situationsspezifische Erklärungen, die konkret weiterhelfen",
            "erzwingt keine unnätig starre Einhaltung von Bearbeitungsschritten.",
            "ermöglicht einen leichten Wechsel zwischen einzelnen Menüs oder Masken",
            "erzwingt keine unnötigen Unterbrechungen der Arbeit.",
            "erleichtert die Orientierung durch eine einheitliche Gestaltung.",
            "informiert in ausreichendem Maße über das, was es gerade macht.",
            "lässt siich durchgehend nach einem einheitlichen Prinzip bedienen.",
            "liefert gut verständliche Fehlermeldungen.",
            "erfordert bei Fehlern im Großen und Ganzen einen geringen Korrekturaufwand",
            "gibt konkrete Hinweise  zur Fehlerbehebung.",
            "lässt sich von mir leicht erweitern, wenn für mich neue Aufgaben entstehen.",
            "lässt sich von mit gut an meine persönliche, induviduelle Art der Arbeitserledigung anpassen.",
            "lässt sich - im Rahmen ihres Leistungsumfangs von mir gut für unterschiedliche Aufgaben passend einrichten.",
            "erfordert wenig Zeit zum Erlernen.",
            "erfordert nicht, dass man sich viele Details merken muss.",
            "ist gut ohne fremde Hilfe oder Handbuch erlernbar."};



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


        radioGroup = (RadioGroup) findViewById(R.id.radioGroup_AntwortenISO);

        //TODO: Frage + Nummer wieder anzeigen
       // textViewUeberschrift = (TextView) findViewById(R.id.textView_Ueberschrift);
        //textViewUeberschrift.setText("Frage " + (index + 1));


        textViewFrageLinks = (TextView) findViewById(R.id.textView_FrageLinks);
        textViewFrageLinks.setText(texteFragen_links[index]);

        textViewFrageRechts = (TextView) findViewById(R.id.textView_FrageRechts);
        textViewFrageRechts.setText(texteFragen_rechts[index]);

        progessBar = (ProgressBar) findViewById(R.id.progressBarISO);
        progessBar.setMax(texteFragen_links.length);

        btnWeiter = (Button) findViewById(R.id.button_naechsteFrageISO);
        btnWeiter.setEnabled(false);

        radioGroup_geschlecht = (RadioGroup) findViewById(R.id.radioGroup_GeschlechtISO);


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
            case R.id.radioButton1ISO:
                antworten[index] = -3;
                break;
            case R.id.radioButton2ISO:
                antworten[index] = -2;
                break;
            case R.id.radioButton3ISO:
                antworten[index] = -1;
                break;
            case R.id.radioButton4ISO:
                antworten[index] = 0;
                break;
            case R.id.radioButton5ISO:
                antworten[index] = 1;
                break;
            case R.id.radioButton6ISO:
                antworten[index] = 2;
                break;
            case R.id.radioButton7ISO:
                antworten[index] = 3;
                break;


        }
        //Setzt wieder alle RadioButtons auf unchecked
        radioGroup.clearCheck();
        index = index + 1;
        //textViewUeberschrift.setText("Frage " + (index + 1));
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
            eingabe_alter = (EditText) findViewById(R.id.textEdit_alterISO);
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
                    case R.id.radioButton_maleISO:
                        geschlecht = geschlecht + "m";
                        break;
                    case R.id.radioButton_femaleISO:
                        geschlecht = geschlecht + "w";
                        break;
                }


                // Datum hinzufügen
                datum = getDatum();

                //Berechnung der Werte für die einzelnen Dialogprinzipinen
                double score_gesamt = Statistik.berechneGesamtScore(antworten);
                double aufgabenangemessenheit = Statistik.berechneMittelwert3(antworten[0], antworten[1], antworten[2]);
                double selbstbeschreibungsfaehigkeit = Statistik.berechneMittelwert3(antworten[3], antworten[4], antworten[5]);
                double steuerbarkeit = Statistik.berechneMittelwert3(antworten[6], antworten[7], antworten[8]);
                double erwartungskonformitaet = Statistik.berechneMittelwert3(antworten[9], antworten[10], antworten[11]);
                double fehlertoleranz = Statistik.berechneMittelwert3(antworten[12], antworten[13], antworten[14]);
                double individualisierbarkeit = Statistik.berechneMittelwert3(antworten[15], antworten[16], antworten[17]);
                double lernfoerderlichkeit = Statistik.berechneMittelwert3(antworten[18], antworten[19], antworten[20]);


                //gesammelte Daten in die Datenbank schreiben
                testId = manager.insertTestISO(antworten, alterInt, geschlecht, datum, studienId, score_gesamt, aufgabenangemessenheit, selbstbeschreibungsfaehigkeit, steuerbarkeit, erwartungskonformitaet, fehlertoleranz, individualisierbarkeit, lernfoerderlichkeit);
                manager.updateISOStudie(studienId);
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
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_letzeSeiteTestISO);
        //TextView zustimmung = (TextView) findViewById(R.studienId.textView_Zustimmung);
        //TextView ablehnung = (TextView) findViewById(R.studienId.textView_Ablehnung);
        ConstraintLayout layoutZustimmung = (ConstraintLayout) findViewById(R.id.layoutZustimmungISO);


        //textViewUeberschrift.setText("Bitte geben Sie noch Ihr Alter und Geschlecht an.");
        btnWeiter.setText("Test beenden");

        layoutZustimmung.setVisibility((View.GONE));
        layout.setVisibility(View.VISIBLE);
        radioGroup.setVisibility(View.GONE);
        //textViewUeberschrift.setVisibility(View.GONE);
        //zustimmung.setVisibility(View.GONE);
        //ablehnung.setVisibility(View.GONE);

    }


    //Schaltet das UI um, sodass das EndScreen angezeigt wird
    //Außerdem wird hier der Intent anbeschickt, der zur Auswertung der Studie weiterleitet
    public void endScreen(){
            LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_letzeSeiteTestISO);
            //textViewUeberschrift.setText("Vielen Dank für Ihre Teilnahme, Sie sind fertig! Bitte geben Sie das Gerät zurück.");
            layout.setVisibility(View.GONE);
            btnWeiter.setEnabled(true);
            btnWeiter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), AuswertungStudieISONORMActivity.class);
                    intent.putExtra("antworten", antworten);
                    intent.putExtra("testId",testId);


                        //Das sind alles Werte, die man für die Auswertung einer Studie braucht
                        intent.putExtra("studienId", studienId);
                        Log.d("Jule", "StudienId, die mitgeschickt wird: " + studienId);
                        Log.d("Jule", "TestId, die mitgeschickt wird: " + testId);
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
