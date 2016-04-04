package com.pruebas.crud;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by fhidalgo on 04/04/2016.
 */
public class ClaseDBAdapter {

    // Definimos constante con el nombre de la tabla
    public static final String C_TABLA = "CLASE" ;

    // Definimos constantes con el nombre de las columnas de la tabla
    public static final String C_COLUMNA_ID  = "_id";
    public static final String C_COLUMNA_NOMBRE = "class_nombre";

    private Context contexto;
    private TiradasDBHelper dbHelper;
    private SQLiteDatabase db;

    // Definimos columnas para lista
    private String[] lista = new String[]{C_COLUMNA_ID, C_COLUMNA_NOMBRE} ;

    public ClaseDBAdapter(Context context) {
        this.contexto = context;
    }

    public ClaseDBAdapter abrir() throws SQLException {
        dbHelper = new TiradasDBHelper(contexto);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void cerrar() {
        dbHelper.close();
    }

    // Devuelve una lista (_id, nombre) con todos los registros
    public Cursor getLista() throws SQLException {
        Cursor c = db.query( true, C_TABLA, lista, null, null, null, null, null, null);
        return c;
    }
}
