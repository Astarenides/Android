package com.pruebas.horasextra.db;

/**
 * Created by fhidalgo on 23/05/2016.
 */
public class ConstantesDB {

    public static final String DATABASE_NAME = "horas_extras.db";
    public static final int DATABASE_VERSION = 1;

    //Tabla de empleados
    public static final String TABLA_EMPLEADOS = "empleados";

    public static final String EMPLEADOS_ID = "_id";
    public static final String EMPLEADOS_NOMBRE = "nombre";

    public static final String TABLA_EMPLEADOS_SQL =
            "CREATE TABLE " + TABLA_EMPLEADOS + "(" +
            EMPLEADOS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    EMPLEADOS_NOMBRE + " TEXT NOT NULL)";

    //Tabla de horas
    public static final String TABLA_HORAS = "horas";

    public static final String HORAS_ID = "_id";
    public static final String HORAS_ID_EMPLEADO = "id_empleado";
    public static final String HORAS_NUM_HORAS = "num_horas";
    public static final String HORAS_STATUS = "status";

    public static final String TABLA_HORAS_SQL =
            "CREATE TABLE " + TABLA_HORAS + "(" +
            HORAS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            HORAS_ID_EMPLEADO + " INTEGER NOT NULL, " +
            HORAS_NUM_HORAS + " INTEGER NOT NULL, " +
            HORAS_STATUS + " INTEGER NOT NULL)";

}
