package com.pruebas.tutorialdagger.model;

import javax.inject.Inject;

/**
 * Created by fhidalgo on 17/05/2016.
 */
public class Vehicle {

    private Motor motor;

    @Inject
    public Vehicle(Motor motor){
        this.motor = motor;
    }

    public void increaseSpeed(int value){
        motor.accelerate(value);
    }

    public void stop(){
        motor.brake();
    }

    public int getSpeed(){
        return motor.getRpm();
    }

}
