package com.example.flaus.susea.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


/* Verknüpft die Daten aus der Datenbank, die in dem Cursor entahlten sind
    mit den Layout-Ressourcen des ListViews
 */

public class AdapterStudien extends CursorAdapter {


    LayoutInflater mLayoutInflater;
    int itemLayout;
    String[] from;
    int[] to;

    public AdapterStudien(Context context, int itemLayout, Cursor c,String[]from, int[] to, int flags){
        super(context,c,flags);
        mLayoutInflater = LayoutInflater.from(context);
        this.itemLayout = itemLayout;
        this.from = from;
        this.to = to;

    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View v = mLayoutInflater.inflate(itemLayout,parent,false);

        return v;
    }

    @Override
    //Angezeigt werden in der Liste einmal der Name der Studie und darunter die Anzahl Tests
    public void bindView(View view, Context context, Cursor cursor) {

        String name = cursor.getString(cursor.getColumnIndexOrThrow(from[0]));
        TextView textViewName = view.findViewById(to[0]);
        textViewName.setText(name);

        int anzahl_tests = cursor.getInt(cursor.getColumnIndexOrThrow(from[1]));
        TextView textViewAnzTests = view.findViewById(to[1]);
        textViewAnzTests.setText("Anzahl Tests: " + anzahl_tests);

        double score = cursor.getDouble(cursor.getColumnIndexOrThrow(from[2]));
        Log.d("Jule", "Score im Binding:" + score);

        TextView textViewScore = view.findViewById(to[2]);
        textViewScore.setText(("Score: " + String.format("%.2f", score)));

    }
}
