package com.pruebas.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * Created by fhidalgo on 04/04/2016.
 */
public class Personaje {

    private Context context;

    private Long id;
    private String nombre;
    private String raza;
    private String observaciones;
    private boolean dead;
    private Long clase_id;

    public Personaje(Context context) {
        this.context = context;
    }

    public Personaje(Context context, Long id, String nombre, String raza, String observaciones, boolean dead, Long clase_id){
        this.context = context;
        this.id = id;
        this.nombre = nombre;
        this.raza = raza;
        this.observaciones = observaciones;
        this.dead = dead;
        this.clase_id = clase_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClase_id() {
        return clase_id;
    }

    public void setClase_id(Long clase_id) {
        this.clase_id = clase_id;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static Personaje find (Context context, long id){
        PersonajesDBAdapter dbAdapter = new PersonajesDBAdapter(context);
        Cursor c = dbAdapter.getRegistro(id);
        Personaje personaje = Personaje.cursorToPersonaje(context, c);
        c.close();
        return personaje;
    }

    public static Personaje cursorToPersonaje(Context context, Cursor c) {
        Personaje personaje = null;
        if (c != null) {
            personaje = new Personaje(context);

            personaje.setId(c.getLong(c.getColumnIndex(PersonajesDBAdapter.C_COLUMNA_ID)));
            personaje.setNombre(c.getString(c.getColumnIndex(PersonajesDBAdapter.C_COLUMNA_NOMBRE)));
            personaje.setRaza(c.getString(c.getColumnIndex(PersonajesDBAdapter.C_COLUMNA_RAZA)));
            personaje.setObservaciones(c.getString(c.getColumnIndex(PersonajesDBAdapter.C_COLUMNA_OBSERVACIONES)));
            personaje.setDead(c.getString(c.getColumnIndex(PersonajesDBAdapter.C_COLUMNA_DEAD)).equals("S"));
            personaje.setClase_id(c.getLong(c.getColumnIndex(PersonajesDBAdapter.C_COLUMNA_CLASE)));
        }
        return personaje;
    }

    private ContentValues toContentValues() {
        ContentValues reg = new ContentValues();

        reg.put(PersonajesDBAdapter.C_COLUMNA_ID, this.getId());
        reg.put(PersonajesDBAdapter.C_COLUMNA_NOMBRE, this.getNombre());
        reg.put(PersonajesDBAdapter.C_COLUMNA_RAZA, this.getRaza());
        reg.put(PersonajesDBAdapter.C_COLUMNA_OBSERVACIONES, this.getObservaciones());
        reg.put(PersonajesDBAdapter.C_COLUMNA_DEAD, (this.isDead())?"S":"N");
        reg.put(PersonajesDBAdapter.C_COLUMNA_CLASE, this.getClase_id());

        return reg;
    }

    public long save() {
        PersonajesDBAdapter dbAdapter = new PersonajesDBAdapter(this.getContext());

        // comprobamos si estamos insertando o actualizando según esté o no relleno el identificador
        if ((this.getId() == null) || (!dbAdapter.exists(this.getId()))) {
            long nuevoId = dbAdapter.insert(this.toContentValues());

            if (nuevoId != -1) {
                this.setId(nuevoId);
            }
        } else {
            dbAdapter.update(this.toContentValues());
        }

        return this.getId();
    }

    public long delete() {
        // borramos el registro
        PersonajesDBAdapter dbAdapter = new PersonajesDBAdapter(this.getContext());
        return dbAdapter.delete(this.getId());
    }
}
