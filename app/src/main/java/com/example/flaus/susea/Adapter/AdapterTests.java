package com.example.flaus.susea.Adapter;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


/* Verknüpft die Daten aus der Datenbank, die in dem Cursor entahlten sind
    mit den Layout-Ressourcen des ListViews
 */

public class AdapterTests extends CursorAdapter {


    LayoutInflater mLayoutInflater;
    int itemLayout;
    String[] from;
    int[] to;


    public AdapterTests(Context context, int itemLayout, Cursor cursor, String[] from, int[] to, int flags){
        super(context,cursor,flags);
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
    //Angezeigt wird das Datum, an dem der Test erstellt wurde und daneben der Score des Tests
    public void bindView(View view, Context context, Cursor cursor) {

        String datum = cursor.getString(cursor.getColumnIndexOrThrow(from[0]));
        TextView textViewDatum = (TextView) view.findViewById(to[0]);
        textViewDatum.setText(datum);

        int score = cursor.getInt(cursor.getColumnIndexOrThrow(from[1]));
        TextView textViewScore = (TextView) view.findViewById(to[1]);
        textViewScore.setText(" " + score);

    }
}
