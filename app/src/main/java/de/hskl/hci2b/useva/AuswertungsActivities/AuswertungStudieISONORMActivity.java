package de.hskl.hci2b.useva.AuswertungsActivities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import de.hskl.hci2b.useva.Datenbank;
import de.hskl.hci2b.useva.ListViewActivities.ISO_ListViewStudienActivity;
import de.hskl.hci2b.useva.ListViewActivities.ISO_ListViewTestsActivity;

import com.example.flaus.susea.R;
import de.hskl.hci2b.useva.StartActivity;
import de.hskl.hci2b.useva.StatistikAuswertungISO;
import de.hskl.hci2b.useva.TestActivity_ISO;

/* Übernimmmt die Auswertung und Darstellung der gesammelten Daten eines einzelnen Tests */

public class AuswertungStudieISONORMActivity extends AppCompatActivity {


    TextView textViewInterfacetyp, textViewNameStudie ,textViewGesamtScore, textViewAnzahlTests,textViewAufgabenangemesseneheit, textViewSelbstbeschreibungsfaehigkeit, textViewSteuerbarkeit, textViewErwartungskonformitaet, textViewFehlertoleranz, textViewIndividualisierbarkeit, textViewLernfoeerdlichkeit;
    long studienId;
    int score = 0;
    String studienName,interfacetyp;
    int anzahl_tests;
    Toolbar toolbar;
    Datenbank db = new Datenbank(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auswertung_studie_isonorm);


        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Studie");

        Intent intent = getIntent();
        studienId = intent.getLongExtra("studienId", -1);
        Log.d("studie", "Id der neuen ISONORM-Studie empfangen:  " + studienId);
        Cursor c = db.getStudieByIdISO(studienId);
        c.moveToFirst();
        anzahl_tests = c.getInt(3);
        interfacetyp = c.getString(2);




        // View-Binding
        textViewNameStudie = (TextView) findViewById(R.id.textViewNameStudie);
        textViewGesamtScore = (TextView) findViewById(R.id.textViewScore);
        textViewAnzahlTests = (TextView) findViewById(R.id.textViewAnzahlTests);
        textViewAufgabenangemesseneheit = (TextView) findViewById(R.id.textView_Aufgabenangemessenheit);
        textViewSelbstbeschreibungsfaehigkeit = (TextView) findViewById(R.id.textView_Selbstbeschreibungsfaehigkeit);
        textViewSteuerbarkeit = (TextView) findViewById(R.id.textView_Steuerbarkeit);
        textViewErwartungskonformitaet = (TextView) findViewById(R.id.textView_Erwatungskonformitaet);
        textViewFehlertoleranz = (TextView) findViewById(R.id.textView_Fehlertoleranz);
        textViewIndividualisierbarkeit = (TextView) findViewById(R.id.textView_Individualisierbarkeit);
        textViewLernfoeerdlichkeit = (TextView) findViewById(R.id.textView_Lernfoerderlichkeit);
        textViewInterfacetyp = (TextView) findViewById(R.id.textViewInterfacetyp);
        Button btnNeuerTest = findViewById(R.id.btnNeuerTest);






        studienName = intent.getStringExtra("studienName");
        studienName = c.getString(1);
        if( anzahl_tests == 0) {
            //Wenn eine neue Studie erstellt wurde, wird der Name gleich mit dem Intent mitgeschickt


            //Und es sind noch keine Tests drin
            textViewAnzahlTests.setText("Noch keine Tests");

        } else { //Ansonten Name und Daten der Studie aus der Datenbank holen



            textViewAnzahlTests.append("" + anzahl_tests);
            textViewGesamtScore.setText("" + String.format("%.2f",c.getDouble(4)));


            //Cursor mit allen Scores
            //Cursor scoreCursor = db.selectScoresByStudienIdISO(studienId);
            //double scoreStudie = Statistik.mittelWertISO(scoreCursor,Datenbank.SPALTE_SCORE_ISO);
            //Log.d ("Jule",scoreStudie+"");
            //scoreCursor.close();
            //textViewGesamtScore.setText(""+scoreStudie);
            //db.insertScoreStudie(studienId, scoreStudie);

            //Cursor aufgabenangemessenheitCursor = db.selectAufgabenangemessenheit(studienId);
            //double studieAufgabenangemessenheit = Statistik.berechneAufgabenangemessenheit(aufgabenangemessenheitCursor);
            //aufgabenangemessenheitCursor.close();
            textViewAufgabenangemesseneheit.setText("" + String.format("%.2f",c.getDouble(5)));

            //Cursor selbstbeschreibungsfaehigkeitCursor = db.selectSelbstbeschreibungsfaehigkeit(studienId);
            //double studieSelbstbeschreibungsfaehigkeit = Statistik.berechneSelbstbeschreibungsfaehigkeit(selbstbeschreibungsfaehigkeitCursor);
            //selbstbeschreibungsfaehigkeitCursor.close();
            textViewSelbstbeschreibungsfaehigkeit.setText("" + String.format("%.2f",c.getDouble(6)));

            //Cursor steuerbarkeitCursor = db.selectSteuerbarkeit(studienId);
            //double studieSteuerbarkeit = Statistik.berechneSteuerbarkeit(steuerbarkeitCursor);
            //steuerbarkeitCursor.close();
            textViewSteuerbarkeit.setText("" + String.format("%.2f",c.getDouble(7)));

            //Cursor erwartungskonfCursor = db.selectErwartungskonformiteat(studienId);
            //double studieErwartungskonf = Statistik.berechneErwartungskonf(erwartungskonfCursor);
            //erwartungskonfCursor.close();
            textViewErwartungskonformitaet.setText("" + String.format("%.2f",c.getDouble(8)));

            //Cursor fehlertoleranzCursor = db.selectFehlertoleranz(studienId);
            //double studieFehlertoleranz = Statistik.berechneFehlertoleranz(fehlertoleranzCursor);
            //fehlertoleranzCursor.close();
            textViewFehlertoleranz.setText("" + String.format("%.2f",c.getDouble(9)));

            //Cursor indiviCursor = db.selectIndividualisierbarkeit(studienId);
            //double studieIndividualisierbarkeit = Statistik.berechneIndividualisierbarkeit(indiviCursor);
            //indiviCursor.close();
            textViewIndividualisierbarkeit.setText("" + String.format("%.2f",c.getDouble(10)));

            //Cursor lernfCursor = db.selectLernfoerderlichkeit(studienId);
            //double studieLernfoerderlichkeit = Statistik.berechneLernfoerderlichkeit(lernfCursor);
            //lernfCursor.close();
            textViewLernfoeerdlichkeit.setText("" + String.format("%.2f",c.getDouble(11)));

            textViewInterfacetyp.setText(" " + c.getString(2));
        }



        // TextViews füllen
        textViewNameStudie.setText("" + studienName);
        textViewNameStudie.setPaintFlags(textViewNameStudie.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);



        //Button um einen neuen Test zu starten und der Studie hinzuzufügen
        btnNeuerTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), TestActivity_ISO.class);
                i.putExtra("studienId", studienId);
                i.putExtra("interfacetyp",interfacetyp);
                startActivity(i);
            }
        });





        c.close();


    }


@Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);



        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.actionStartseite:
                Intent intent = new Intent(getBaseContext(),StartActivity.class);
                startActivity(intent);
                return true;

            case R.id.actionTestsEinsehen:
                if(anzahl_tests == 0){
                    Toast.makeText(AuswertungStudieISONORMActivity.this,"Noch keine Tests innerhalb der Studie!",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent1 = new Intent(getBaseContext(), ISO_ListViewTestsActivity.class);
                    intent1.putExtra("studienId", studienId);

                    intent1.putExtra("studienName", studienName);
                    startActivity(intent1);
                }
                return true;

            case R.id.actionAlleStudienEinsehen:
                boolean kommeVonderStartseite = false;
                Intent intent2 = new Intent(getBaseContext(), ISO_ListViewStudienActivity.class);
                intent2.putExtra("kommeVonDerStartseite",kommeVonderStartseite);
                startActivity(intent2);
                return true;
            case R.id.actionStatistik:
                //TODO: hier auch noch alle Daten an die Statistik-Klasse mitschicken
                if(anzahl_tests == 0){
                    Toast.makeText(AuswertungStudieISONORMActivity.this,"Noch keine Tests innerhalb der Studie!",Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent3 = new Intent(getBaseContext(), StatistikAuswertungISO.class);
                    intent3.putExtra("studienId", studienId);
                    intent3.putExtra("anzahlTests",anzahl_tests);
                    intent3.putExtra("studienName", studienName);
                    startActivity(intent3);
                }

            return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getBaseContext(),StartActivity.class);
        startActivity(intent);
    }

}







