package com.pruebas.horasextra.model;

/**
 * Created by fhidalgo on 23/05/2016.
 */
public class Empleado {

    private int id;
    private String nombre;

    public Empleado() {}

    public Empleado(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String toString() {
        return "Empleado{id=" + id + ", nombre='" + nombre + "'}";

    }
}
