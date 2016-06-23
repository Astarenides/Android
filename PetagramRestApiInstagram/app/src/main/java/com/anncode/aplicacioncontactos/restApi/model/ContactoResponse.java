package com.anncode.aplicacioncontactos.restApi.model;

import com.anncode.aplicacioncontactos.pojo.Contacto;

import java.util.ArrayList;

public class ContactoResponse {

    ArrayList<Contacto> contactos;

    public ArrayList<Contacto> getContactos() {
        return contactos;
    }

    public void setContactos(ArrayList<Contacto> contactos) {
        this.contactos = contactos;
    }
}
