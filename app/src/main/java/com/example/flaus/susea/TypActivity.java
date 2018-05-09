package com.example.flaus.susea;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.ListView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TypActivity extends AppCompatActivity {
    String typ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typ);


        final ListView listview = findViewById(R.id.list_view_typ);
        String[] typen = {
                "B2B", "B2C", "Web", "Cell", "HW", "Internal SW", "IVR", "Web/IVR"
        };
        final ArrayList<String> typenList = new ArrayList<String>();
        for (int i = 0; i < typen.length; ++i) {
            typenList.add(typen[i]);
        }
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, typenList);
        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = listview.getItemAtPosition(position).toString();

                setTyp(name);





            }
        });

        Button button_next = (Button) findViewById(R.id.button_next);
        button_next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View button_next) {

                if (typ != null) {
                    Intent intent = new Intent(getBaseContext(), TestActivity.class);
                    intent.putExtra("typ", typ);
                    startActivity(intent);
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(),"Bitte einen Typen wählen",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        Button button_help = (Button) findViewById(R.id.button_help);
        button_help.setOnClickListener(new OnClickListener() { //Help Button


            @Override
            public void onClick(View button_help) {
                AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(TypActivity.this);
                dlgBuilder.setTitle("Hilfe");
                dlgBuilder.setMessage("B2B: Enterprise software application such as accounting, HR, CRM and order-management systems\n\n" +
                        "B2C: Public-facing mass-market consumer software: Office Applications, Graphics Apps and Personal Finance Software\n\n" +
                        "Web: Public-facing large-scale websites (airlines, rental cars, retailers, financial service) and intranets\n\n" +
                        "Cell: Cell-phone equipment\n\n" +
                        "HW: Hardware such as phones, modems and Ethernet cards\n\n" +
                        "Internal SW: Internal-productivity software: Customer Service and Network Operations applications. This group likely has overlaps between the B2C and B2B groups.\n\n" +
                        "IVR: Interactive Voice Response Systems (phone- and speech-based)\n\n" +
                        "Web/IVR: A combination of web-based and interactive voice-response systems");
                dlgBuilder.setCancelable(true);
                dlgBuilder.setNeutralButton("Verstanden!", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


                AlertDialog alert = dlgBuilder.create();
                alert.show();
            }
        });


    }

    public void setTyp(String typ){
        this.typ = typ;
    }

}

