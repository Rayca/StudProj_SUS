package com.example.flaus.susea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.flaus.susea.ListViewActivities.ListViewStudienActivity;

public class StartActivity extends AppCompatActivity {

    boolean studie;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("System Usability Scale");

    }

    public void studie_starten(View v){
        studie = true;
        Intent intent = new Intent(this, TypActivity.class);
        intent.putExtra("Studie", studie);
        startActivity(intent);

    }



    public void start(View view){
        studie = false;
        Intent intent = new Intent(this, TypActivity.class);
        intent.putExtra("Studie", studie);
        startActivity(intent);
    }


    public void studienEinsehen(View view){
        Intent intent = new Intent(getBaseContext(), ListViewStudienActivity.class);
        startActivity(intent);
    }
}
