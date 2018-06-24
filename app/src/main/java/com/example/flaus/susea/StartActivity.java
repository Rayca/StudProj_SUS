package com.example.flaus.susea;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class StartActivity extends AppCompatActivity {

    boolean studie = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_theme));

    }

    public void studie_starten(View v){

        studie = true;
        Intent intent = new Intent(this, TypActivity.class);
        intent.putExtra("Studie", studie);
        startActivity(intent);

    }



    public void start(View view){
        Intent intent = new Intent(this, TypActivity.class);
        intent.putExtra("Studie", studie);
        startActivity(intent);
    }
}
