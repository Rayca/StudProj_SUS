package com.example.flaus.susea;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.flaus.susea.Adapter.AdapterTests;

public class ListViewTestsActivity extends AppCompatActivity {

    ListView listView;
    Button btnZurueck;
    Datenbank db = new Datenbank(this);
    long studienId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_tests);

        // View - Binding
        listView = (ListView) findViewById(R.id.listViewTests);
        btnZurueck = (Button) findViewById(R.id.btnZurueck);


        // ListView füllen
        Context context = this;
        int itemLayout = R.layout.test_list_item_layout;
        final Cursor cursor = db.selectAllTestsbyStudienId(studienId);
        final String[] from = new String[]{db.SPALTE_DATUM, db.SPALTE_SCORE};
        int[] to = new int[]{R.id.textViewDatum, R.id.textViewScore};

        final AdapterTests adapterTests = new AdapterTests(context,itemLayout,cursor,from,to,0);

        listView.setAdapter(adapterTests);





        // Funktion für btnZurueck

        btnZurueck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),AuswertungStudieActivity.class);
                startActivity(intent);
            }
        });



    }
}
