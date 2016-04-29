package com.pruebas.contactos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetalleContacto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_contacto);

        Bundle parametros = getIntent().getExtras();

        String nombre       = parametros.getString(getResources().getString(R.string.pnombre));       // Nombre
        String telefono     = parametros.getString(getResources().getString(R.string.ptelefono));   // Telefono
        String email        = parametros.getString(getResources().getString(R.string.pemail));         // Email

        TextView txtNombre      = (TextView) findViewById(R.id.txtNombre);
        TextView txtTelefono    = (TextView) findViewById(R.id.txtTelefono);
        TextView txtEmail       = (TextView) findViewById(R.id.txtEmail);

        txtNombre.setText(nombre);
        txtTelefono.setText(telefono);
        txtEmail.setText(email);

    }
}
