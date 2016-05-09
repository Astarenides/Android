package com.pruebas.manejomemoria;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void guardarPreferencia (View v){
        SharedPreferences preferencias = getSharedPreferences("MisDatosPersonales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        EditText txtNombre = (EditText) findViewById(R.id.txtNombre);
        EditText txtCorreo = (EditText) findViewById(R.id.txtCorreo);

        String nombre = txtNombre.getText().toString();
        String correo = txtCorreo.getText().toString();

        editor.putString(getString(R.string.clave_nombre), nombre);
        editor.putString(getString(R.string.clave_correo), correo);
        editor.commit();

        Toast.makeText(MainActivity.this, "SharedPreference almacenada", Toast.LENGTH_SHORT).show();

        txtNombre.setText("");
        txtCorreo.setText("");
    }

    public void mostrarPreferencia (View v) {

        SharedPreferences preferencias = getSharedPreferences("MisDatosPersonales", Context.MODE_PRIVATE);

        String nombre = preferencias.getString(getString(R.string.clave_nombre), "No existe esa variable");
        String correo = preferencias.getString(getString(R.string.clave_correo), "No existe esa variable");

        TextView txtPreferencia = (TextView) findViewById(R.id.txtPreferencias);
        txtPreferencia.setText("\nNombre: " + nombre + "\nCorreo: " + correo);

    }

    public void generarArchivo(View v){
        try {

            EditText nombre = (EditText) findViewById(R.id.txtNombre);
            String name = nombre.getText().toString();

            FileOutputStream outputStream = null;
            outputStream = openFileOutput("MiArchivo.txt", Context.MODE_PRIVATE);
            outputStream.write(name.getBytes());
            outputStream.close();

            Toast.makeText(MainActivity.this, "El archivo se ha creado", Toast.LENGTH_SHORT).show();
            nombre.setText("");

        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Hubo un error en la escritura del archivo", Toast.LENGTH_SHORT).show();
        }
    }
}
