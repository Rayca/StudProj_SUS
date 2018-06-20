package com.example.flaus.susea;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
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

import org.w3c.dom.Text;

public class TestActivity extends AppCompatActivity {


    EditText eingabe_alter;
    TextView ueberschrift;
    TextView frage;
    ProgressBar progessBar;
    RadioGroup radioGroup;
    RadioGroup radioGroup_geschlecht;
    Button next;
    int[] antworten = new int[10];
    int index = 0;
    int alter;
    Datenbank manager = new Datenbank(this);
    String[] texteFragen = {"Ich benutze die Software gerne regelmäßig.",
            "Die Software wirkt auf mich recht einfach aufgebaut.",
            "Ich finde die Software leicht zu benutzen.",
            "Ich kann die Software ohne Unterstützung durch Fachpersonal direkt benutzen.",
            "Ich finde, dass die angebotenen Funktionen gut in die Software integriert sind.",
            "Ich finde, dass die Software einheitlich ausfgebaut ist.",
            "Ich denke, dass die meisten Nutzer sehr schnell mit der Software zurecht kommen.",
            "Ich finde die Software in der Benutzung sehr intuitiv.",
            "Ich weiß bei der benutzung der Software zu jedem Zeitpunkt, was ich tue.",
            "Ich konnte die Software bedienen ohne zuvor Neues erlernen zu müssen."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ActionBar actionBar = getSupportActionBar();
       /* GradientDrawable background = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{ContextCompat.getColor(this, R.color.imst),
                        ContextCompat.getColor(this, R.color.alp),
                        ContextCompat.getColor(this, R.color.aing),
                        ContextCompat.getColor(this, R.color.bw),
                        ContextCompat.getColor(this, R.color.bg)}); */
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_theme));


        radioGroup = (RadioGroup) findViewById(R.id.radioGroup_Antworten);

        ueberschrift = (TextView) findViewById(R.id.textView_Ueberschrift);
        ueberschrift.setText("Frage "+ (index+1));



        frage = (TextView) findViewById(R.id.textView_Fragen);
        frage.setText(texteFragen[index]);

        progessBar = (ProgressBar) findViewById(R.id.progressBar);
        progessBar.setMax(texteFragen.length);

        next = (Button) findViewById(R.id.button_naechsteFrage);
        next.setEnabled(false);

        radioGroup_geschlecht = (RadioGroup) findViewById(R.id.radioGroup_Geschlecht);


        // Erst wenn man eine Antwort ausgewählt hat, kann man zur nächsten frage weiterkommen
        //Und auch erst mit dem Klick auf den Button wird die ausgewählte Antwort gespeichert
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                next.setEnabled(true);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            naechsteFrage();
            }
        });

        //Erst wenn eine Anwtort aufgewählt wurde, wird der Button enabled
        radioGroup_geschlecht.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                next.setEnabled(true);
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
            ueberschrift.setText("Frage " + (index + 1));
            progessBar.incrementProgressBy(1);
            if (index < texteFragen.length) {
                frage.setText(texteFragen[index]);
                next.setEnabled(false);
            } else if(index == texteFragen.length){
                //Ui ändern für die letzen beiden Fragen (Geschlecht/Alter)
                changeUi();
            } else if (index == texteFragen.length +1) {
                next.setEnabled(false);
                //Text des Button ändern
                next.setText("Test beenden");
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

                //gesammelte Daten in die Datenbank schreiben
                manager.insertTest(antworten, alter, geschlecht);

                //Daten an die AuswertungsActivity übergeben
                Intent intent = new Intent(this, AuswertungActivity.class);
                intent.putExtra("Ergebnisse", antworten);
                startActivity(intent);
                //TODO: "AbschlussScreen"
            } else {
                //irgendeine Fehlermeldung, weil dann ist wirklich was schief gelaufen
            }




    }

    //Ändert das UI für die letzte Seite des Fragebogens zur Erhebung von Alter und Geschlecht
        public void changeUi(){
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_letzeSeiteTest);
        TextView zustimmung = (TextView) findViewById(R.id.textView_Zustimmung);
        TextView ablehnung = (TextView) findViewById(R.id.textView_Ablehnung);

        frage.setText("Bitten geben Sie noch Ihr Alter und Geschlecht an.");

        layout.setVisibility(View.VISIBLE);
        radioGroup.setVisibility(View.GONE);
        zustimmung.setVisibility(View.GONE);
        ablehnung.setVisibility(View.GONE);

    }
}
