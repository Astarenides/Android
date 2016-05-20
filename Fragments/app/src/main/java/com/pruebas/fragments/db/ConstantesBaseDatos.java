package com.pruebas.fragments.db;

/**
 * Created by fhidalgo on 20/05/2016.
 */
public final class ConstantesBaseDatos {

    public static final String DATA_BASE_NAME = "contactos";
    public static final int DATA_BASE_VERSION = 1;

    public static final String TABLE_CONTACTS           = "contactos";
    public static final String TABLE_CONTACTS_ID        = "id";
    public static final String TABLE_CONTACTS_NOMBRE    = "nombre";
    public static final String TABLE_CONTACTS_TELEFONO  = "telefono";
    public static final String TABLE_CONTACTS_EMAIL     = "email";

    public static final String TABLE_LIKES_CONTACTS                 = "contacto_likes";
    public static final String TABLE_LIKES_CONTACTS_ID              = "id";
    public static final String TABLE_LIKES_CONTACTS_ID_CONTACTO     = "id_contacto";
    public static final String TABLE_LIKES_CONTACTS_LIKES           = "numero_likes";
}
