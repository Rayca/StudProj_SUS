package com.example.flaus.susea;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import static com.example.flaus.susea.Datenbank.SPALTE_SCORE;

public class AuswertungActivity extends AppCompatActivity {


    Datenbank manager = new Datenbank(this);
    int[] antworten = new int[10];
    TextView anezige_score;
    ImageButton start;
    long id;
    int score =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auswertung);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_theme));

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);

        Bundle extras = getIntent().getExtras();
        id = extras.getLong("Test_ID", -1);
        boolean studie = extras.getBoolean("Studie", false);
        String studie_name = extras.getString("Name_der_Studie");
        final String interfacetyp = extras.getString("Interfacetyp");
        boolean test_ende = extras.getBoolean("TestTest_abgeschlossen", false);


        //Button um zurück auf den "Home-Screen" zu kommen
        start =  findViewById(R.id.button_home);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), StartActivity.class);
                startActivity(intent);
            }
        });

        anezige_score = findViewById(R.id.anzeige_score);
        //Nach dem Erstellen der Studie den Screen mit Default-Werten füllen:
        //TODO Werte aus der DB holen
        if(!test_ende){
            anezige_score.setText("Noch kein Wert");
        }
        //Anzahl Tests:
        TextView anzeige_anzahl_tests =  findViewById(R.id.anzeige_anzahl_tests);
        //TODO Wert aus der DB holen
        anzeige_anzahl_tests.setText("0");

        TextView anzeiege_standrasabweichung = findViewById(R.id.anzeige_standardabweichung);
        //TODO Wert aus der DB holen
        anzeiege_standrasabweichung.setText("0");



        //Wenn ein Test abgeschlossen wurde, alle Werte der Studie aktualisieren
        if(test_ende){
            aktualisiereStudie();
        }
       /* //Wenn es keine Studie ist, sondern nur ein einzelner Test, dann wird hier auch nur der SUS des einzelnen Tests berechnte und angezeitgt
        //TODO: hier das XML Layout File setzen, für den einzelnen Test
        if (!studie) {
            antworten = extras.getIntArray("Ergebnisse"); // Nur zum Überprüfen, ob die richtigen Werte im Array übergeben werden
            for (int i = 0; i < antworten.length; i++) {
                Log.d("Jule", "Antwort " + (i + 1) + " : " + antworten[i] + "\n");
            }
*/

        //Score berechnen und anzeigen
       if(test_ende) {
           score = Auswertung.berechneScore(antworten);
           manager.setScore(id, score);

           Log.d("Jule", "Übergebener Score " + score);
           anezige_score.setText(score + "");

       }
        Log.d("Jule", "Von DB " + manager.getData(SPALTE_SCORE));

        //Click auf den Floating Action Button startet einen neuen Test in der Studie
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: ID der Studie noch mitschicken
                    Intent intent = new Intent(getBaseContext(), TestActivity.class);
                    startActivity(intent);
                }
            });

    }

    // Wenn es eine Studie ist, dann hier die Werte aus der DB ziehen und anzeigen.
        //TODO: Alle Test, die zu der aktuellen Studie gehören(passende ID) aus der DB holen und Werte daruas berechnen


    public void aktualisiereStudie(){
        //TODO hier alle Werte neu berechnen und neu anzeigen
    }



}







