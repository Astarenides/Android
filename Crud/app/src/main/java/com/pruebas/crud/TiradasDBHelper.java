package com.pruebas.crud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by fhidalgo on 30/03/2016.
 */
public class TiradasDBHelper extends SQLiteOpenHelper {

    private static int version = 3;
    private static String name = "Tiradas" ;
    private static SQLiteDatabase.CursorFactory factory = null;

    public TiradasDBHelper (Context context) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(this.getClass().toString(), "Creando base de datos...");

        db.execSQL( "CREATE TABLE PERSONAJES(" +
                " _id INTEGER PRIMARY KEY," +
                " pj_nombre TEXT NOT NULL, " +
                " pj_raza TEXT, " +
                " pj_observaciones TEXT)" );

        db.execSQL( "CREATE UNIQUE INDEX pj_nombre ON PERSONAJES(pj_nombre ASC)" );

        Log.i(this.getClass().toString(), "Tabla PERSONAJES creada");

        /*
        * Insertamos datos iniciales
        */
        db.execSQL("INSERT INTO PERSONAJES(_id, pj_nombre) VALUES(1,'Dijkstra')");
        db.execSQL("INSERT INTO PERSONAJES(_id, pj_nombre) VALUES(2,'Bokoto')");
        db.execSQL("INSERT INTO PERSONAJES(_id, pj_nombre) VALUES(3,'Fox')");
        db.execSQL("INSERT INTO PERSONAJES(_id, pj_nombre) VALUES(4,'Katia')");

        Log.i(this.getClass().toString(), "Datos iniciales PERSONAJES insertados");

        Log.i(this.getClass().toString(), "Base de datos creada");

        // Aplicamos las sucesivas actualizaciones
        upgrade_2(db);
        upgrade_3(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Actualización a versión 2
        if (oldVersion < 2) {
            upgrade_2(db);
        }

        // Actualización a versión 3
        if (oldVersion < 3) {
            upgrade_3(db);
        }
    }

    private void upgrade_2(SQLiteDatabase db) {
        // Upgrade versión 2: definir algunos datos de ejemplo
        db.execSQL("UPDATE PERSONAJES SET pj_observaciones = 'Tiene todo el equipo necesario' WHERE _id = 1");
        Log.i(this.getClass().toString(), "Actualización versión 2 finalizada");
    }

    private void upgrade_3(SQLiteDatabase db) {
        // Upgrade versión 3: Incluir pj_dead
        db.execSQL("ALTER TABLE PERSONAJES ADD pj_dead VARCHAR2(1) NOT NULL DEFAULT 'N'");
        Log.i(this.getClass().toString(), "Actualización versión 3 finalizada");
    }
}
