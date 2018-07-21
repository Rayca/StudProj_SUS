package com.example.flaus.susea.ListViewActivities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.flaus.susea.Adapter.AdapterAntworten;
import com.example.flaus.susea.Datenbank;
import com.example.flaus.susea.R;

/* Zeigt die Antworten aus einem einzelnen Test an */
//TODO: Jede Menge zu tun
public class ListViewAntwortenTest extends AppCompatActivity {
    TextView anzeige_alter;
    TextView anzeige_geschlecht;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anezige_ergebnisse_test);
        Datenbank db = new Datenbank(this);

        //View-Binding
        anzeige_alter = findViewById(R.id.textView_alter);
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
        TextView f10 = findViewById(R.id.textView_ergF10);


        Intent intent = getIntent();
        long test_id = intent.getLongExtra("testID", -1);
        Cursor c = db.getTestById(test_id);

        //Die TextViews alle füllen
        int alter = c.getInt(12);
        anzeige_alter.append(alter + "");
        String geschlecht = c.getString(13);
        anzeige_geschlecht.append(geschlecht);
        anzeige_score.append(c.getString(14));
        f1.append(c.getInt(0) + "");
        f2.append(c.getInt(1) + "");
        f3.append(c.getInt(2) + "");
        f4.append(c.getInt(3) + "");
        f5.append(c.getInt(4) + "");
        f6.append(c.getInt(5) + "");
        f7.append(c.getInt(6) + "");
        f8.append(c.getInt(7) + "");
        f9.append(c.getInt(8) + "");
        f10.append(c.getInt(9) + "");



    }
}

