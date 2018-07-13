package com.example.flaus.susea.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;



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
    public void bindView(View view, Context context, Cursor cursor) {

        String name = cursor.getString(cursor.getColumnIndexOrThrow(from[0]));
        TextView textViewName = (TextView) view.findViewById(to[0]);
        textViewName.setText(name);

        int score = cursor.getInt(cursor.getColumnIndexOrThrow(from[1]));
        TextView textViewScore = (TextView) view.findViewById(to[1]);
        textViewScore.setText(" " + score);

    }
}
