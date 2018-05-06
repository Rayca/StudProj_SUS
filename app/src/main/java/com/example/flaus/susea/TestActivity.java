package com.example.flaus.susea;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {
    TextView ueberschrift;
    TextView frage;
    ProgressBar progessBar;
    int index = 0;
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



        ueberschrift = (TextView) findViewById(R.id.textView_Ueberschrift);
        ueberschrift.setText("Frage "+ (index+1));



        frage = (TextView) findViewById(R.id.textView_Fragen);
        frage.setText(texteFragen[index]);

        progessBar = (ProgressBar) findViewById(R.id.progressBar);
        progessBar.setMax(texteFragen.length-1);

        Button next = (Button) findViewById(R.id.button_naechsteFrage);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            naechsteFrage();
            }
        });



    }

    public void naechsteFrage(){
        index = index +1;
        if(index < texteFragen.length) {
            ueberschrift.setText("Frage " + (index + 1));
            frage.setText(texteFragen[index]);
            progessBar.incrementProgressBy(1);
        }

    }
}
