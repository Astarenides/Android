package com.pruebas.tutorialdagger.component;

import com.pruebas.tutorialdagger.model.Vehicle;
import com.pruebas.tutorialdagger.module.VehicleModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by fhidalgo on 17/05/2016.
 */

@Singleton
@Component(modules = {VehicleModule.class})
public interface VehicleComponent {

    Vehicle provideVehicle();

}
