package com.pruebas.tutorialdagger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pruebas.tutorialdagger.component.VehicleComponent;
import com.pruebas.tutorialdagger.model.Vehicle;
import com.pruebas.tutorialdagger.module.VehicleModule;

public class MainActivity extends AppCompatActivity {

    Vehicle vehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
