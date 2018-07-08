package com.example.flaus.susea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ListViewTestsActivity extends AppCompatActivity {

    ListView listView;
    Button btnZurueck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_tests);

        // View - Binding
        listView = (ListView) findViewById(R.id.listViewTests);
        btnZurueck = (Button) findViewById(R.id.btnZurueck);




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
