package com.example.flaus.susea.ListViewActivities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.flaus.susea.Adapter.AdapterAntworten;
import com.example.flaus.susea.Adapter.AdapterTests;
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

        Intent intent = getIntent();
        long test_id = intent.getLongExtra("testID", -1);
        Cursor c = db.getTestById(test_id);
        int alter = c.getInt(12);
        String geschlecht = c.getString(13);

        //TODO: Irgendwie braucht der CursorAdapter eine Spalte als ID O.o
        int itemLayout = R.layout.antworten_list_item_layout;
        final String[] from = new String[]{db.SPALTE_FRAGE1,db.SPALTE_FRAGE2, db.SPALTE_FRAGE3, db.SPALTE_FRAGE4, db.SPALTE_FRAGE5, db.SPALTE_FRAGE6, db.SPALTE_FRAGE7, db.SPALTE_FRAGE8, db.SPALTE_FRAGE9, db.SPALTE_FRAGE10};
        int[] to = new int[]{R.id.textViewAntwort, R.id.textViewAntwort};

        AdapterAntworten adapter = new AdapterAntworten(this,itemLayout,c,from,to,0);
        listView.setAdapter(adapter);

    }
}
