package com.example.flaus.susea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


    }


    public void startt(View view){
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }
}
