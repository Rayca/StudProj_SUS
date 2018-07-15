package com.example.flaus.susea;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/* Zeigt die Antworten aus einem einzelnen Test an */
public class AnzeigeErgebnisseTest extends AppCompatActivity {
    TextView anzeige_alter;
    TextView anzeige_geschlecht;

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
    }
}
