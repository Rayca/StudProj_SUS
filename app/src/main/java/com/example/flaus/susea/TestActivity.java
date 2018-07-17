package com.example.flaus.susea;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.flaus.susea.AuswertungsActivities.AuswertungStudieActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    int alter;
    boolean studie;
    long studienId;
    String studienName;
    long testId;
    String datum;
    Datenbank manager = new Datenbank(this);
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


        // Intent empfangen
        Intent intent = this.getIntent();
        studie = intent.getBooleanExtra("studie", false);
        studienId = intent.getLongExtra("studienId",-1);
        studienName = intent.getStringExtra("studienName");


            Log.d("studie","testActivity : " + studie);




        radioGroup = (RadioGroup) findViewById(R.id.radioGroup_Antworten);

        textViewUeberschrift = (TextView) findViewById(R.id.textView_Ueberschrift);
        textViewUeberschrift.setText("Frage "+ (index+1));



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

    public void naechsteFrage(){

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
            } else if(index == texteFragen.length){
                //Ui ändern für die letzen beiden Fragen (Geschlecht/Alter)
                changeUi();
            } else if (index == texteFragen.length +1) {
                btnWeiter.setEnabled(false);

                //Eingegebne Daten sammeln
                //TODO: evtl Numberpicker statt EditText
                eingabe_alter = (EditText) findViewById(R.id.textEdit_alter);
                //TODO: sicher stellen, dass eine sinnvolle Zahl eingegben wurde
                alter = Integer.parseInt(eingabe_alter.getText().toString());


                //Geschlecht auswerten
                int radioButtonId = radioGroup_geschlecht.getCheckedRadioButtonId();
                String geschlecht ="";

                switch (radioButtonId){
                    case R.id.radioButton_male:
                        geschlecht =  geschlecht + "m";
                        break;
                    case R.id.radioButton_female:
                        geschlecht = geschlecht + "w";
                        break;
                }


                // Datum hinzufügen
                datum = getDatum();

                //gesammelte Daten in die Datenbank schreiben
                testId = manager.insertTest(antworten, alter, geschlecht, datum,studienId);

                Log.d("TJ","test_id vor intent" + testId);
                //Daten an die AuswertungsTestActivity übergeben

                for(int i = 0; i<antworten.length;i++) {
                    Log.d("test", "antworten länge = " + antworten.length);
                    Log.d("test", " antworten = " + antworten[i]);
                }
                // Endscreen
                endScreen();

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


    public void endScreen(){
            LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_letzeSeiteTest);
            textViewFrage.setText("Vielen Dank für Ihre Teilnahme, sie sind fertig! Bitte geben sie das Gerät zurück.");
            layout.setVisibility(View.GONE);
            btnWeiter.setEnabled(true);
            btnWeiter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), AuswertungStudieActivity.class);
                    intent.putExtra("antworten", antworten);
                    intent.putExtra("testId",testId);

                    if(studie) {
                        intent.putExtra("studie", true);
                    } else {
                        intent.putExtra("studie", false);
                    }
                    intent.putExtra("studienId", studienId);
                    intent.putExtra("studienName", studienName);

                    startActivity(intent);
                }
            });
    }


    public String getDatum() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy   HH:mm");
        String dateAndTime = sdf.format(new Date());


        return  dateAndTime;
    }







}
