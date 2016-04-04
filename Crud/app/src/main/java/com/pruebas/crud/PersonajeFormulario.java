package com.pruebas.crud;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class PersonajeFormulario extends Activity {

    private PersonajesDBAdapter dbAdapter;
    private Cursor cursor;
    private ClaseDBAdapter dbAdapterClase;
    private Cursor cursorListaClase;

    private int modo ;
    private long id ;

    // Elementos de la vista
    private EditText nombre;
    private EditText raza;
    private EditText observaciones;
    private CheckBox dead ;
    private Spinner clase;

    private Button boton_guardar;
    private Button boton_cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personaje_formulario);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        if (extra == null) return;

        // Obtenemos los elementos de la vista
        nombre = (EditText) findViewById(R.id.nombre);
        raza = (EditText) findViewById(R.id.raza);
        observaciones = (EditText) findViewById(R.id.observaciones);
        dead = (CheckBox) findViewById(R.id.dead);
        clase = (Spinner) findViewById(R.id.clase);

        boton_guardar = (Button) findViewById(R.id.boton_guardar);
        boton_cancelar = (Button) findViewById(R.id.boton_cancelar);

        // Creamos el adaptador de clase
        dbAdapterClase = new ClaseDBAdapter(this);
        dbAdapterClase.abrir();

        cursorListaClase = dbAdapterClase.getLista();

        SimpleCursorAdapter adapterClase = new SimpleCursorAdapter(this,android.R.layout.simple_spinner_item,cursorListaClase,new String[] {ClaseDBAdapter.C_COLUMNA_NOMBRE}, new int[] {android.R.id.text1});
        adapterClase.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clase.setAdapter(adapterClase);

        // Creamos el adaptador
        dbAdapter = new PersonajesDBAdapter(this);
        dbAdapter.abrir();

        // Obtenemos el identificador del registro si viene indicado
        if (extra.containsKey(PersonajesDBAdapter.C_COLUMNA_ID)) {
            id = extra.getLong(PersonajesDBAdapter.C_COLUMNA_ID);
            consultar(id);
        }

        // Establecemos el modo del formulario
        establecerModo(extra.getInt(PersonajesActivity.C_MODO));

        // Definimos las acciones para los dos botones
        boton_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });

        boton_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });
    }

    private void establecerModo(int m) {
        this.modo = m ;

        if (modo == PersonajesActivity.C_VISUALIZAR) {
            this.setTitle(nombre.getText().toString());
            this.setEdicion(false);
        } else if (modo == PersonajesActivity.C_CREAR) {
            this.setTitle(R.string.personaje_crear_titulo);
            this.setEdicion(true);
        } else if (modo == PersonajesActivity.C_EDITAR) {
            this.setTitle(R.string.personaje_editar_titulo);
            this.setEdicion(true);
        }
    }

    private void consultar(long id) {
        // Consultamos el personaje por el identificador
        Personaje personaje = Personaje.find(this, id);

        nombre.setText(personaje.getNombre());
        raza.setText(personaje.getRaza());
        observaciones.setText(personaje.getObservaciones());
        dead.setChecked(personaje.isDead());
        clase.setSelection(getItemPositionById(cursorListaClase, personaje.getClase_id()));
    }

    private void setEdicion(boolean opcion) {
        nombre.setEnabled(opcion);
        raza.setEnabled(opcion);
        observaciones.setEnabled(opcion);
        dead.setEnabled(opcion);
        clase.setEnabled(opcion);

        // Controlamos visibilidad de botonera
        LinearLayout v = (LinearLayout) findViewById(R.id.botonera);

        if (opcion) v.setVisibility(View.VISIBLE);
        else v.setVisibility(View.GONE);
    }

    private void guardar() {
        Personaje personaje;

        if (this.modo == PersonajesActivity.C_EDITAR){
            personaje = Personaje.find(this, this.id);
        } else {
            personaje = new Personaje (this);
        }

        personaje.setNombre(nombre.getText().toString());
        personaje.setRaza(raza.getText().toString());
        personaje.setObservaciones(observaciones.getText().toString());
        personaje.setDead(dead.isChecked());
        personaje.setClase_id(clase.getSelectedItemId());

        personaje.save();

        if (modo == PersonajesActivity.C_CREAR){
            Toast.makeText(PersonajeFormulario.this, R.string.personaje_crear_confirmacion, Toast.LENGTH_SHORT).show();
        } else if (modo == PersonajesActivity.C_EDITAR) {
            Toast.makeText(PersonajeFormulario.this, R.string.personaje_editar_confirmacion, Toast.LENGTH_SHORT).show();
        }

        // Devolvemos el control
        setResult(RESULT_OK);
        finish();
    }

    private void cancelar() {
        setResult(RESULT_CANCELED, null);
        finish();
    }

    private void borrar(final long id) {
        /**
         * Borramos el registro con confirmación
         */
        AlertDialog.Builder dialogEliminar = new AlertDialog.Builder(this);

        dialogEliminar.setIcon(android.R.drawable.ic_dialog_alert);
        dialogEliminar.setTitle(getResources().getString(R.string.personaje_eliminar_titulo));
        dialogEliminar.setMessage(getResources().getString(R.string.personaje_eliminar_mensaje));
        dialogEliminar.setCancelable(false);

        dialogEliminar.setPositiveButton(getResources().getString(android.R.string.ok), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int boton) {
                dbAdapter.delete(id);
                Toast.makeText(PersonajeFormulario.this, R.string.personaje_eliminar_confirmacion, Toast.LENGTH_SHORT).show();
                /**
                 * Devolvemos el control
                 */
                setResult(RESULT_OK);
                finish();
            }
        });

        dialogEliminar.setNegativeButton(android.R.string.no, null);

        dialogEliminar.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.clear();

        if (modo == PersonajesActivity.C_VISUALIZAR)
            getMenuInflater().inflate(R.menu.personaje_formulario_ver, menu);
        else
            getMenuInflater().inflate(R.menu.personaje_formulario_editar, menu);

        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_eliminar:
                borrar(id);
                return true;
            case R.id.menu_cancelar:
                cancelar();
                return true;
            case R.id.menu_guardar:
                guardar();
                return true;
            case R.id.menu_editar:
                establecerModo(PersonajesActivity.C_EDITAR);
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }

    private int getItemPositionById(Cursor c, long id) {
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            if (c.getLong(c.getColumnIndex(ClaseDBAdapter.C_COLUMNA_ID)) == id) {
                return c.getPosition() ;
            }
        }

        return 0 ;
    }
}
