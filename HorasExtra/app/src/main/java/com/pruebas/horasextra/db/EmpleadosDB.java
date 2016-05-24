package com.pruebas.horasextra.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pruebas.horasextra.model.Empleado;

import java.util.ArrayList;

/**
 * Created by fhidalgo on 23/05/2016.
 */
public class EmpleadosDB {

    private SQLiteDatabase db;
    private DBHelper helper;

    public EmpleadosDB(Context context) {
        helper = new DBHelper(context);
    }

    private void openReadableDB() {
        db = helper.getReadableDatabase();
    }

    private void openWriteableDB() {
        db = helper.getWritableDatabase();
    }

    private void closeDB() {
        if (db!=null){
            db.close();
        }
    }

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, ConstantesDB.DATABASE_NAME, null, ConstantesDB.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(ConstantesDB.TABLA_EMPLEADOS_SQL);
            db.execSQL(ConstantesDB.TABLA_HORAS_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    private ContentValues empleadoMapperContentValues(Empleado empleado) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantesDB.EMPLEADOS_NOMBRE, empleado.getNombre());
        return cv;
    }

    public long insertarEmpleado(Empleado empleado) {
        this.openWriteableDB();
        long rowID = db.insert(ConstantesDB.TABLA_EMPLEADOS, null, empleadoMapperContentValues(empleado));
        this.closeDB();

        return rowID;
    }

    public void actualizarEmpleado(Empleado empleado) {
        this.openWriteableDB();
        String where = ConstantesDB.EMPLEADOS_ID + "=?";
        db.update(ConstantesDB.TABLA_EMPLEADOS, empleadoMapperContentValues(empleado), where, new String[]{String.valueOf(empleado.getId())} );
        db.close();
    }

    public void eliminarEmpleado(int id) {
        this.openWriteableDB();
        String where = ConstantesDB.EMPLEADOS_ID + "=?";
        db.delete(ConstantesDB.TABLA_EMPLEADOS, where, new String[]{String.valueOf(id)});
    }

    public ArrayList cargarEmpleados() {
        ArrayList listaEmpleados = new ArrayList<>();

        this.openReadableDB();
        String[] campos = new String[] {ConstantesDB.EMPLEADOS_ID, ConstantesDB.EMPLEADOS_NOMBRE};
        Cursor cursor = db.query(ConstantesDB.TABLA_EMPLEADOS, campos, null, null, null, null, null);

        try {
            while (cursor.moveToNext()){
                Empleado empleado = new Empleado();
                empleado.setId(cursor.getInt(0));
                empleado.setNombre(cursor.getString(1));
                listaEmpleados.add(empleado);
            }
        } finally {
            cursor.close();
        }
        this.closeDB();

        return listaEmpleados;
    }

    public Empleado buscarEmpleado(String nombre) {
        Empleado empleado = new Empleado();
        this.openReadableDB();
        String where = ConstantesDB.EMPLEADOS_NOMBRE + "=?";
        String[] whereArgs = {nombre};
        Cursor cursor = db.query(ConstantesDB.TABLA_EMPLEADOS, null, where, whereArgs, null, null, null);

        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            empleado.setId(cursor.getInt(0));
            empleado.setNombre(cursor.getString(1));
            cursor.close();
        }

        this.closeDB();
        return empleado;

    }
}
