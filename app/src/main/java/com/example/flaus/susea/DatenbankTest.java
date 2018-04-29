package com.example.flaus.susea;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


//Die Activity war nur zum Test der DB gedacht
public class DatenbankTest extends AppCompatActivity {


    Datenbank manager = new Datenbank(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datenbank_test);

        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] testdaten = {1,2,3,4,5,6,7,8,9,10};
                manager.Test_einfuegen(testdaten, 12, "m", 100 );
                testdaten();
            }
        });



    }

    public void testdaten(){
        Cursor c = manager.zeile_auslesen();
        c.moveToFirst();
        int id = c.getInt(0);
        String datum = c.getString(1);
        String antworten = "";
        for ( int i = 2; i < 12; i ++){
            antworten = antworten + c.getInt(i);
        }
        int alter = c.getInt(12);
        String g = c.getString(13);
        int score = c.getInt(14);

        TextView v = (TextView) findViewById(R.id.textView);
        v.setText(id + datum + antworten + alter + g + score);
    }
}
