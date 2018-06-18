package com.example.flaus.susea;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TestActivity extends AppCompatActivity {


    TextView ueberschrift;
    TextView frage;
    ProgressBar progessBar;
    RadioGroup radioGroup;
    Button next;
    int[] antworten = new int[10];
    int index = 0;
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
            } else {

                //TODO: UI ändern um Geschlecht und Alter noch abzufragen
                changeUi();
                //Gesammelte Daten in die Datenbank schreiben
                //TODO: Aktuelles Datum noch in die DB schreiben
                manager.insertTest(antworten, 10, "w");


                //Daten an die AuswertungsActivity übergeben
                Intent intent = new Intent(this, AuswertungActivity.class);
                intent.putExtra("Ergebnisse", antworten);
                //startActivity(intent);
                //TODO: "AbschlussScreen"
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
