package com.pruebas.tutorialdagger.model;

/**
 * Created by fhidalgo on 17/05/2016.
 */
public class Motor {

    private int rpm;

    public Motor(){
        this.rpm = 0;
    }

    public int getRpm(){
        return rpm;
    }

    public void accelerate(int value){
        rpm = rpm + value;
    }

    public void brake(){
        rpm = 0;
    }

}
