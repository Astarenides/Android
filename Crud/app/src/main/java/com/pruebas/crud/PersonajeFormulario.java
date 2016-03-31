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
import android.widget.Toast;

public class PersonajeFormulario extends Activity {

    private TiradasDBAdapter dbAdapter;
    private Cursor cursor;

    private int modo ;
    private long id ;

    private EditText nombre;
    private EditText raza;
    private EditText observaciones;
    private CheckBox dead ;
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

        boton_guardar = (Button) findViewById(R.id.boton_guardar);
        boton_cancelar = (Button) findViewById(R.id.boton_cancelar);

        // Creamos el adaptador
        dbAdapter = new TiradasDBAdapter(this);
        dbAdapter.abrir();

        // Obtenemos el identificador del registro si viene indicado
        if (extra.containsKey(TiradasDBAdapter.C_COLUMNA_ID)) {
            id = extra.getLong(TiradasDBAdapter.C_COLUMNA_ID);
            consultar(id);
        }

        // Establecemos el modo del formulario
        establecerModo(extra.getInt(MainActivity.C_MODO));

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

        if (modo == MainActivity.C_VISUALIZAR) {
            this.setTitle(nombre.getText().toString());
            this.setEdicion(false);
        } else if (modo == MainActivity.C_CREAR) {
            this.setTitle(R.string.personaje_crear_titulo);
            this.setEdicion(true);
        } else if (modo == MainActivity.C_EDITAR) {
            this.setTitle(R.string.personaje_editar_titulo);
            this.setEdicion(true);
        }
    }

    private void consultar(long id) {
        // Consultamos el centro por el identificador
        cursor = dbAdapter.getRegistro(id);

        nombre.setText(cursor.getString(cursor.getColumnIndex(TiradasDBAdapter.C_COLUMNA_NOMBRE)));
        raza.setText(cursor.getString(cursor.getColumnIndex(TiradasDBAdapter.C_COLUMNA_RAZA)));
        observaciones.setText(cursor.getString(cursor.getColumnIndex(TiradasDBAdapter.C_COLUMNA_OBSERVACIONES)));
        dead.setChecked(cursor.getString(cursor.getColumnIndex(TiradasDBAdapter.C_COLUMNA_DEAD)).equals("S"));
    }

    private void setEdicion(boolean opcion) {
        nombre.setEnabled(opcion);
        raza.setEnabled(opcion);
        observaciones.setEnabled(opcion);
        dead.setEnabled(opcion);
    }

    private void guardar() {
        // Obtenemos los datos del formulario
        ContentValues reg = new ContentValues();

        // Si estamos en modo edición añadimos el identificador del registro que se utilizará en el update
        if (modo == MainActivity.C_EDITAR) reg.put(TiradasDBAdapter.C_COLUMNA_ID, id);

        reg.put(TiradasDBAdapter.C_COLUMNA_NOMBRE, nombre.getText().toString());
        reg.put(TiradasDBAdapter.C_COLUMNA_RAZA, raza.getText().toString());
        reg.put(TiradasDBAdapter.C_COLUMNA_OBSERVACIONES, observaciones.getText().toString());
        reg.put(TiradasDBAdapter.C_COLUMNA_DEAD, (dead.isChecked())?"S":"N");

        if (modo == MainActivity.C_CREAR) {
            dbAdapter.insert(reg);
            Toast.makeText(PersonajeFormulario.this, R.string.personaje_crear_confirmacion, Toast.LENGTH_SHORT).show();
        } else if (modo == MainActivity.C_EDITAR) {
            dbAdapter.update(reg);
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

        if (modo == MainActivity.C_VISUALIZAR)
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
                establecerModo(MainActivity.C_EDITAR);
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }

}
