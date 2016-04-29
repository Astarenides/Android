package com.pruebas.contactos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.pruebas.contactos.model.Contacto;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Contacto> contactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactos = new ArrayList<>();
        contactos.add(new Contacto("Fco. Javier Hidalgo", "987654321", "astarenides@hotmail.com"));
        contactos.add(new Contacto("Pedro Sanchez", "123456789", "pedrosanchez@hotmail.com"));
        contactos.add(new Contacto("Mireya Martinez", "123987456", "mireyamartinez@hotmail.com"));
        contactos.add(new Contacto("Juan Lopez", "444111111", "juanlopez@hotmail.com"));

        ArrayList<String> nombres = new ArrayList<>();
        for (Contacto c : contactos ){
            nombres.add(c.getNombre());
        }
        ListView listaContactos = (ListView) findViewById(R.id.listaContactos);
        listaContactos.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nombres));

        listaContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetalleContacto.class);
                intent.putExtra(getResources().getString(R.string.pnombre), contactos.get(position).getNombre());
                intent.putExtra(getResources().getString(R.string.ptelefono), contactos.get(position).getTelefono());
                intent.putExtra(getResources().getString(R.string.pemail), contactos.get(position).getEmail());
                startActivity(intent);
            }
        });
    }
}
