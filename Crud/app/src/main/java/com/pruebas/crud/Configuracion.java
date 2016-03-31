package com.pruebas.crud;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by fhidalgo on 30/03/2016.
 */
public class Configuracion extends PreferenceActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.configuracion);
    }
}