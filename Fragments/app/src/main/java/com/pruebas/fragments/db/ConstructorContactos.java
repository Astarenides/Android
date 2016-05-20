package com.pruebas.fragments.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.pruebas.fragments.pojo.Contacto;

import java.util.ArrayList;

/**
 * Created by fhidalgo on 20/05/2016.
 */
public class ConstructorContactos {

    private static final int LIKE = 1;
    private Context context;

    public ConstructorContactos (Context context) {
        this.context = context;
    }

    public ArrayList<Contacto> obtenerDatos() {
        BaseDatos db = new BaseDatos(context);
        insertarContactosDummy(db);
        return db.obtenerTodosLosContactos();
    }

    public void insertarContactosDummy (BaseDatos db) {
        ContentValues values = new ContentValues();
        values.put(ConstantesBaseDatos.TABLE_CONTACTS_NOMBRE, "Fco. Javier Hidalgo");
        values.put(ConstantesBaseDatos.TABLE_CONTACTS_TELEFONO, "987654321");
        values.put(ConstantesBaseDatos.TABLE_CONTACTS_EMAIL, "astarenides@hotmail.com");
        db.insertarContacto(values);

        values = new ContentValues();
        values.put(ConstantesBaseDatos.TABLE_CONTACTS_NOMBRE, "Pedro Sanchez");
        values.put(ConstantesBaseDatos.TABLE_CONTACTS_TELEFONO, "132456789");
        values.put(ConstantesBaseDatos.TABLE_CONTACTS_EMAIL, "pedrosanchez@hotmail.com");
        db.insertarContacto(values);

        values = new ContentValues();
        values.put(ConstantesBaseDatos.TABLE_CONTACTS_NOMBRE, "Mireya Martinez");
        values.put(ConstantesBaseDatos.TABLE_CONTACTS_TELEFONO, "123987456");
        values.put(ConstantesBaseDatos.TABLE_CONTACTS_EMAIL, "mireyamartinez@hotmail.com");
        db.insertarContacto(values);
    }

    public void darLikeContacto(Contacto contacto){
        BaseDatos db = new BaseDatos(context);
        ContentValues values = new ContentValues();
        values.put(ConstantesBaseDatos.TABLE_LIKES_CONTACTS_ID_CONTACTO, contacto.getId());
        values.put(ConstantesBaseDatos.TABLE_LIKES_CONTACTS_LIKES, LIKE);
        db.insertarLikeContacto(values);
    }

    public int obtenerLikesContacto(Contacto contacto){
        BaseDatos db = new BaseDatos(context);
        return db.obtenerLikesContacto(contacto);
    }
}
