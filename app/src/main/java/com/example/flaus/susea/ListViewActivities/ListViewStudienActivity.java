package com.example.flaus.susea.ListViewActivities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.flaus.susea.Adapter.AdapterStudien;
import com.example.flaus.susea.Datenbank;
import com.example.flaus.susea.R;
import com.example.flaus.susea.StartActivity;

public class ListViewStudienActivity extends AppCompatActivity {

    ListView listView;
    Button btnZurueck;
    Datenbank db = new Datenbank(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_studien);

        // View Binding
        listView = (ListView) findViewById(R.id.listView);
        btnZurueck = (Button) findViewById(R.id.btnZurueck);


        // listView füllen

        Context context = this;
        int itemLayout = R.layout.studie_list_item_layout;
        final Cursor cursor = db.selectAllStudien();
        final String[] from = new String[]{db.SPALTE_STUDIE_NAME, db.SPALTE_STUDIE_SCORE};
        int[] to = new int[]{R.id.textViewName, R.id.textViewScore};

        final AdapterStudien adapterStudien = new AdapterStudien(context,itemLayout,cursor,from,to,0);

        listView.setAdapter(adapterStudien);




        // btnZurueck

        btnZurueck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), StartActivity.class);
                startActivity(intent);
            }
        });

    }
}
