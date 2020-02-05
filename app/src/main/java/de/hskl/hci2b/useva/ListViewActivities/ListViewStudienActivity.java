package de.hskl.hci2b.useva.ListViewActivities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import de.hskl.hci2b.useva.Adapter.AdapterStudien;
import de.hskl.hci2b.useva.AuswertungsActivities.AuswertungStudieActivity;
import de.hskl.hci2b.useva.Datenbank;
import com.example.flaus.susea.R;
import de.hskl.hci2b.useva.StartActivity;
import de.hskl.hci2b.useva.TypActivity;

//Füllt und verwaltet das ListView, mit dem alle Studien aus der DB als Liste angezeigt werden können
public class ListViewStudienActivity extends AppCompatActivity {

    ListView listView;
    Button btnZurueck;
    Toolbar toolbar;
    boolean kommeVonDerStartseite;
    Datenbank db = new Datenbank(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_studien);

        // Intent
        Intent intent = getIntent();
         kommeVonDerStartseite = intent.getBooleanExtra("kommeVonDerStartseite",true);


        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Alle SUS Studien");
        // Pfeil Action

        ActionBar ab = getSupportActionBar();
        if(kommeVonDerStartseite){
            ab.setDisplayHomeAsUpEnabled(true);
           // MenuItem item = (MenuItem) findViewById(R.id.actionStartseite);
           // item.setVisible(false);
        } else {
            ab.setDisplayHomeAsUpEnabled(false);
        }







        // View Binding
        listView = findViewById(R.id.listView);




        // listView füllen
        final Context context = this;
        int itemLayout = R.layout.studie_list_item_layout;
        final Cursor cursor = db.selectAllStudien();
        final String[] from = new String[]{Datenbank.SPALTE_STUDIE_NAME, Datenbank.SPALTE_ANZAHL_TESTS, Datenbank.SPALTE_STUDIE_SCORE};
        int[] to = new int[]{R.id.textView_StudieName, R.id.textView_AnzahlTests, R.id.textView_StudieScore};

        final AdapterStudien adapterStudien = new AdapterStudien(context,itemLayout,cursor,from,to,0);

        listView.setAdapter(adapterStudien);






        //Auswahl einer Studie aus der Liste
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, AuswertungStudieActivity.class);
                //ID der Studie raussuchen, die angeklickt wurde
                cursor.moveToPosition(position);
                long studie_id = cursor.getLong(0);
                intent.putExtra("studienId", studie_id);
                String studienName = cursor.getString(1);
                cursor.close();
                Log.d("thomas", "studienName: " + studienName);
                intent.putExtra("studienName",studienName);
                startActivity(intent);



            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_studie_listview,menu);
        if(kommeVonDerStartseite){
            MenuItem item = menu.findItem(R.id.actionStartseite);
            item.setVisible(false);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionStartseite:
                Intent intent = new Intent(getBaseContext(), StartActivity.class);
                startActivity(intent);
                return true;


            case R.id.actionNeueStudieanlegen:
                Intent intent1 = new Intent(getBaseContext(), TypActivity.class);
                boolean kommeVonListeStudien = true;
                intent1.putExtra("kommeVonListeStudien",kommeVonListeStudien);
                startActivity(intent1);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed(){

    }
}
