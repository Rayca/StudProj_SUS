package com.example.flaus.susea.AuswertungsActivities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.flaus.susea.Datenbank;
import com.example.flaus.susea.ListViewActivities.ListViewTestsActivity;
import com.example.flaus.susea.R;
import com.example.flaus.susea.StartActivity;

/* Zeigt die Antworten aus einem einzelnen Test an */
//TODO: Jede Menge zu tun
public class AuswertungTestActivity extends AppCompatActivity {



    Button btnZurStudie;

    Toolbar toolbar;
    long studienId, test_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auswertung_test);
        Datenbank db = new Datenbank(this);

        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Auswertung Test");

        // Pfeil für den User flow
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);



        Intent intent = getIntent();
        test_id = intent.getLongExtra("testID", -1);
        studienId = intent.getLongExtra("studienId", -1);
        Cursor c = db.getTestById(test_id);


        //View-Binding
       /* anzeige_alter = findViewById(R.id.textView_alter);
        anzeige_geschlecht = findViewById(R.id.textView_geschlecht);
        TextView anzeige_score = findViewById(R.id.textView_ergScore);
        TextView f1 = findViewById(R.id.textView_ergF1);
        TextView f2 = findViewById(R.id.textView_ergF2);
        TextView f3 = findViewById(R.id.textView_ergF3);
        TextView f4 = findViewById(R.id.textView_ergF4);
        TextView f5 = findViewById(R.id.textView_ergF5);
        TextView f6 = findViewById(R.id.textView_ergF6);
        TextView f7 = findViewById(R.id.textView_ergF7);
        TextView f8 = findViewById(R.id.textView_ergF8);
        TextView f9 = findViewById(R.id.textView_ergF9);
        TextView f10 = findViewById(R.id.textView_ergF10); */


       btnZurStudie = findViewById(R.id.btnZurStudie);

       btnZurStudie.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getBaseContext(),AuswertungStudieActivity.class);
               intent.putExtra("studienId",studienId);
               startActivity(intent);
           }
       });




        //Die TextViews alle füllen
       /* int alter = c.getInt(12);
        anzeige_alter.append(alter + "");
        String geschlecht = c.getString(13);
        anzeige_geschlecht.append(geschlecht);
        anzeige_score.append(c.getString(14));
        f1.append(c.getInt(2) + "");
        f2.append(c.getInt(3) + "");
        f3.append(c.getInt(4) + "");
        f4.append(c.getInt(5) + "");
        f5.append(c.getInt(6) + "");
        f6.append(c.getInt(7) + "");
        f7.append(c.getInt(8) + "");
        f8.append(c.getInt(9) + "");
        f9.append(c.getInt(10) + "");
        f10.append(c.getInt(11) + ""); */

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_studie_listview,menu);

        return true;
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                Intent intent = new Intent(getBaseContext(), ListViewTestsActivity.class);
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



}

