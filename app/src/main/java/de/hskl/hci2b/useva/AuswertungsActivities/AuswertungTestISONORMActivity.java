package de.hskl.hci2b.useva.AuswertungsActivities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.hskl.hci2b.useva.Datenbank;
import de.hskl.hci2b.useva.ListViewActivities.ISO_ListViewTestsActivity;

import com.example.flaus.susea.R;
import de.hskl.hci2b.useva.StartActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* Zeigt die Antworten aus einem einzelnen Test an */
public class AuswertungTestISONORMActivity extends AppCompatActivity {



    Button btnZurStudie;

    Toolbar toolbar;
    long studienId, test_id;
    String studienName;

    private String parseDate (String source) throws ParseException{
        Date date = new SimpleDateFormat("dd mm yyyy   hh:mm").parse(source);
        String formattedDate = new SimpleDateFormat("dd.MM.yyyy").format(date);
        return formattedDate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auswertung_test_isonorm);
        Datenbank db = new Datenbank(this);

        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Auswertung Test");

        // Pfeil für den User flow
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        //View-Binding
        TextView anzeige_alter = findViewById(R.id.textViewAlter);
        TextView anzeige_geschlecht = findViewById(R.id.textViewGeschlecht);
        TextView anzeige_score = findViewById(R.id.textViewScore);
        TextView f1 = findViewById(R.id.textViewF1);
        TextView f2 = findViewById(R.id.textViewF2);
        TextView f3 = findViewById(R.id.textViewF3);
        TextView f4 = findViewById(R.id.textViewF4);
        TextView f5 = findViewById(R.id.textViewF5);
        TextView f6 = findViewById(R.id.textViewF6);
        TextView f7 = findViewById(R.id.textViewF7);
        TextView f8 = findViewById(R.id.textViewF8);
        TextView f9 = findViewById(R.id.textViewF9);
        TextView f10 = findViewById(R.id.textViewF10);
        TextView f11 = findViewById(R.id.textViewF11);
        TextView f12 = findViewById(R.id.textViewF12);
        TextView f13 = findViewById(R.id.textViewF13);
        TextView f14 = findViewById(R.id.textViewF14);
        TextView f15 = findViewById(R.id.textViewF15);
        TextView f16= findViewById(R.id.textViewF16);
        TextView f17 = findViewById(R.id.textViewF17);
        TextView f18 = findViewById(R.id.textViewF18);
        TextView f19 = findViewById(R.id.textViewF19);
        TextView f20 = findViewById(R.id.textViewF20);
        TextView f21 = findViewById(R.id.textViewF21);
        TextView anzeige_auf = findViewById(R.id.textViewAuf);
        TextView anzeige_sel = findViewById(R.id.textViewSel);
        TextView anzeige_ste = findViewById(R.id.textViewSteu);
        TextView anzeige_erw = findViewById(R.id.textViewErw);
        TextView anzeige_feh = findViewById(R.id.textViewFeh);
        TextView anzeige_ind = findViewById(R.id.textViewInd);
        TextView anzeige_ler = findViewById(R.id.textViewLer);
        TextView anzeige_dat = findViewById(R.id.textViewDatum);
        TextView anzeige_name = findViewById(R.id.textViewStudName);
        TextView anzeige_studiengang = findViewById(R.id.textViewStudiengang);
        TextView anzeige_semester = findViewById(R.id.textViewSemester);


        Intent intent = getIntent();
        test_id = intent.getLongExtra("testID", -1);
        studienId = intent.getLongExtra("studienId", -1);
        studienName = intent.getStringExtra("studienName");
        Cursor c = db.getTestByIdISO(test_id);


       btnZurStudie = findViewById(R.id.btnZurStudie);

       btnZurStudie.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getBaseContext(),AuswertungStudieISONORMActivity.class);
               intent.putExtra("studienId",studienId);
               startActivity(intent);
           }
       });




        //Die TextViews alle füllen mit den Daten aus dem Cursor
        anzeige_alter.setText("Alter: " + c.getInt(23));
        anzeige_geschlecht.setText("Geschlecht: "+ c.getString(24));
        try{
            anzeige_dat.setText("Datum: "+ parseDate(c.getString(1)));
        }
        catch (ParseException e){Log.e("Jule","PARSING ERROR: "+e.getMessage());}
        anzeige_score.setText("Score: "+ String.format("%.2f",c.getDouble(25)));
        anzeige_name.setText(studienName);
        f1.setText("Frage 1:  " + c.getInt(2));
        f2.setText("Frage 2:  " + c.getInt(3));
        f3.setText("Frage 3:  " + c.getInt(4));
        f4.setText("Frage 4:  " + c.getInt(5));
        f5.setText("Frage 5:  " + c.getInt(6));
        f6.setText("Frage 6:  " + c.getInt(7));
        f7.setText("Frage 7:  " + c.getInt(8));
        f8.setText("Frage 8:  " + c.getInt(9));
        f9.setText("Frage 9:  " + c.getInt(10));
        f10.setText("Frage 10:  " + c.getInt(11));
        f11.setText("Frage 11:  " + c.getInt(12));
        f12.setText("Frage 12:  " + c.getInt(13));
        f13.setText("Frage 13:  " + c.getInt(14));
        f14.setText("Frage 14:  " + c.getInt(15));
        f15.setText("Frage 15:  " + c.getInt(16));
        f16.setText("Frage 16:  " + c.getInt(17));
        f17.setText("Frage 17:  " + c.getInt(18));
        f18.setText("Frage 18:  " + c.getInt(19));
        f19.setText("Frage 19:  " + c.getInt(20));
        f20.setText("Frage 20:  " + c.getInt(21));
        f21.setText("Frage 21:  " + c.getInt(22));
        anzeige_auf.setText("Aufgabenangemessenheit: " + String.format("%.2f",c.getDouble(28)));
        anzeige_sel.setText("Selbstbeschreibungsfähigkeit: " + String.format("%.2f",c.getDouble(29)));
        anzeige_ste.setText("Steuerbarkeit: " + String.format("%.2f",c.getDouble(30)));
        anzeige_erw.setText("Erwartungskonformität: " + String.format("%.2f",c.getDouble(31)));
        anzeige_feh.setText("Fehlertoleranz: " + String.format("%.2f",c.getDouble(32)));
        anzeige_ind.setText("Individualisierbarkeit: " + String.format("%.2f",c.getDouble(33)));
        anzeige_ler.setText("Lernförderlichkeit: " + String.format("%.2f",c.getDouble(34)));
        anzeige_studiengang.setText("Studiengang: "+ c.getString(35));
        anzeige_semester.setText("Semester: "+ c.getString(36));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_studie_listview,menu);

        MenuItem item1 = menu.findItem(R.id.actionNeueStudieanlegen);
        item1.setVisible(false);

        return true;
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                Intent intent = new Intent(getBaseContext(), ISO_ListViewTestsActivity.class);
                intent.putExtra("studienId",studienId);
                startActivity(intent);
                Log.d("thomas", "pfeil wurde gedrückt");
                return true;

            case R.id.actionStartseite:
                Intent intent1 = new Intent(getBaseContext(),StartActivity.class);
                startActivity(intent1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){

    }

}

