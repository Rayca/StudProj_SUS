package com.example.flaus.susea.ListViewActivities;

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

import com.example.flaus.susea.Adapter.AdapterTests;
import com.example.flaus.susea.AuswertungsActivities.AuswertungStudieActivity;
import com.example.flaus.susea.Datenbank;
import com.example.flaus.susea.R;
import com.example.flaus.susea.StartActivity;

//Füllt und verwaltet das ListView, mit dem alle Tests aus der DB als Liste angezeigt werden können
public class ListViewTestsActivity extends AppCompatActivity {

    ListView listView;
    Datenbank db = new Datenbank(this);
    Toolbar toolbar;
    long studienId;
    String studienName;
    boolean studie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_tests);


        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_theme));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tests der Studie ");

        // Pfeil für den User flow
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        // View - Binding
        listView = (ListView) findViewById(R.id.listViewTests);


        // Intent empfangen
        Intent intent = getIntent();
        studienId = intent.getLongExtra("studienId", -1);
        studienName = intent.getStringExtra("studienName");
        studie = intent.getBooleanExtra("studie",false);
        Log.d("studID", "Empfangene Studien-ID: "+ studienId);


        // ListView füllen
        final Context context = this;
        int itemLayout = R.layout.test_list_item_layout;
        final Cursor cursor = db.selectAllTestsbyStudienId(studienId);
        Log.d("Jule", " Länge des Cursor " + cursor.getCount());
        final String[] from = new String[]{db.SPALTE_DATUM, db.SPALTE_SCORE};
        int[] to = new int[]{R.id.textView_TestDatum, R.id.textView_TestScore};

        final AdapterTests adapterTests = new AdapterTests(context,itemLayout,cursor,from,to,0);

        listView.setAdapter(adapterTests);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(context, ListViewAntwortenTest.class);
                cursor.moveToPosition(position);
                long test_id = cursor.getLong(0); //Test-Id aus dem Cursor holen
                intent1.putExtra("testID", test_id);
                startActivity(intent1);
            }
        });





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_studie_listview,menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.home:
                Intent intent = new Intent(getBaseContext(),AuswertungStudieActivity.class);
                intent.putExtra("studienId", studienId);
                intent.putExtra("studie",studie);
                intent.putExtra("studienName", studienName);
                startActivity(intent);
                return true;

            default:
                Intent intent1 = new Intent(getBaseContext(),AuswertungStudieActivity.class);
                intent1.putExtra("studienId", studienId);
                intent1.putExtra("studie",studie);
                intent1.putExtra("studienName", studienName);
                startActivity(intent1);
                return true;


        }
    }
}
