package com.pruebas.fragments.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pruebas.fragments.pojo.Contacto;

import java.util.ArrayList;

/**
 * Created by fhidalgo on 20/05/2016.
 */
public class BaseDatos extends SQLiteOpenHelper {

    private Context context;

    public BaseDatos(Context context) {
        super(context, ConstantesBaseDatos.DATA_BASE_NAME, null, ConstantesBaseDatos.DATA_BASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCrearTablaContacto = "CREATE TABLE " + ConstantesBaseDatos.TABLE_CONTACTS + "(" +
                                        ConstantesBaseDatos.TABLE_CONTACTS_ID           + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                        ConstantesBaseDatos.TABLE_CONTACTS_NOMBRE       + " TEXT, " +
                                        ConstantesBaseDatos.TABLE_CONTACTS_TELEFONO     + " TEXT, " +
                                        ConstantesBaseDatos.TABLE_CONTACTS_EMAIL        + " TEXT" +
                                        ")";
        String queryCrearTablaContactoLikes = "CREATE TABLE " + ConstantesBaseDatos.TABLE_LIKES_CONTACTS + "(" +
                                            ConstantesBaseDatos.TABLE_LIKES_CONTACTS_ID             + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                            ConstantesBaseDatos.TABLE_LIKES_CONTACTS_ID_CONTACTO    + " INTEGER, " +
                                            ConstantesBaseDatos.TABLE_LIKES_CONTACTS_LIKES          + " INTEGER, " +
                                            "FOREIGN KEY (" + ConstantesBaseDatos.TABLE_LIKES_CONTACTS_ID_CONTACTO + ") " +
                                            "REFERENCES " + ConstantesBaseDatos.TABLE_CONTACTS + "(" + ConstantesBaseDatos.TABLE_CONTACTS_ID + ")" +
                                            ")";
        db.execSQL(queryCrearTablaContacto);
        db.execSQL(queryCrearTablaContactoLikes);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ConstantesBaseDatos.TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + ConstantesBaseDatos.TABLE_LIKES_CONTACTS);
        onCreate(db);
    }

    public ArrayList<Contacto> obtenerTodosLosContactos() {
        ArrayList<Contacto> contactos = new ArrayList<>();
        String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);
        while(registros.moveToNext()){
            Contacto contactoActual = new Contacto();
            contactoActual.setId(registros.getInt(0));
            contactoActual.setNombre(registros.getString(1));
            contactoActual.setTelefono(registros.getString(2));
            contactoActual.setEmail(registros.getString(3));
            contactoActual.setLikes(0); //TODO añadir los likes del contacto de la otra tabla
            contactos.add(contactoActual);
        }
        registros.close();
        db.close();
        return contactos;
    }

    public void insertarContacto(ContentValues contentValues) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ConstantesBaseDatos.TABLE_CONTACTS, null, contentValues);
        db.close();
    }

    public void insertarLikeContacto(ContentValues contentValues) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ConstantesBaseDatos.TABLE_LIKES_CONTACTS, null, contentValues);
        db.close();
    }

    public int obtenerLikesContacto(Contacto contacto) {
        int likes = 0;

        String query =  "SELECT COUNT(" + ConstantesBaseDatos.TABLE_LIKES_CONTACTS_LIKES + ") " +
                "FROM " + ConstantesBaseDatos.TABLE_LIKES_CONTACTS + " " +
                "WHERE " + ConstantesBaseDatos.TABLE_LIKES_CONTACTS_ID_CONTACTO + " = " + contacto.getId();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);
        while (registros.moveToNext()){
            likes += registros.getInt(0);
        }
        registros.close();
        db.close();
        return likes;
    }
}
