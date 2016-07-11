package com.pruebas.fragments.pojo;

/**
 * Created by fhidalgo on 05/05/2016.
 */
public class Contacto {

    private int id;
    private String nombre;
    private String telefono;
    private String email;
    private int likes;

    public Contacto(String nombre, String telefono, String email, int likes) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.likes = likes;
    }

    public Contacto() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLikes() { return likes; }

    public void setLikes(int likes) { this.likes = likes; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }
}
