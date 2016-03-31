package com.pruebas.crud;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by fhidalgo on 30/03/2016.
 */
public class TiradasCursorAdapter extends CursorAdapter {

    private TiradasDBAdapter dbAdapter = null;

    public TiradasCursorAdapter (Context context, Cursor c){
        super(context, c);
        dbAdapter = new TiradasDBAdapter(context);
        dbAdapter.abrir();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        TextView tv = (TextView) view ;

        tv.setText(cursor.getString(cursor.getColumnIndex(TiradasDBAdapter.C_COLUMNA_NOMBRE)));

        if (cursor.getString(cursor.getColumnIndex(TiradasDBAdapter.C_COLUMNA_DEAD)).equals("S")) {
            tv.setTextColor(Color.GRAY);
        } else {
            tv.setTextColor(Color.BLACK);
        }
    }
}
