package com.pruebas.tutorialdagger.module;

import com.pruebas.tutorialdagger.model.Motor;
import com.pruebas.tutorialdagger.model.Vehicle;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fhidalgo on 17/05/2016.
 */

@Module
public class VehicleModule {

    @Provides @Singleton
    Motor provideMotor() {
        return new Motor();
    }

    @Provides @Singleton
    Vehicle provideVehicle() {
        return new Vehicle(new Motor());
    }
}
