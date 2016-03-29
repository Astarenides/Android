package com.utiles.tiradas;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

public class EditarEfecto extends Activity {
	
	private Button btnGuardarEditarEfecto;
	AdaptadorBD db;
	private Spinner desplegableEfectos;
	private EditText txtDescripcion;

	private void VaciarCampos() {
		this.desplegableEfectos.setSelection(0);
		this.txtDescripcion.setText("");
	}

	private boolean comprobarDatos() {
		if ((this.desplegableEfectos.getSelectedItem() != null) && (this.desplegableEfectos.getSelectedItemId() != 0)){
			return true;
		} else {
			return false;
		}
	}

	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editarefecto);
		
		desplegableEfectos = (Spinner) findViewById(R.id.spinnerNombreEfecto);
		txtDescripcion = (EditText) findViewById(R.id.txtDescipcionEfecto);
		btnGuardarEditarEfecto = (Button) findViewById(R.id.btnGuardarEditarEfecto);
		btnGuardarEditarEfecto.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (comprobarDatos()) {
					String efecto = desplegableEfectos.getSelectedItem().toString();
					int idEfecto = -1;
					try {
						db = new AdaptadorBD(getBaseContext());
						db.abrir();
						Cursor c = db.consulta("SELECT idEfecto FROM EFECTOS WHERE nombre = '" + efecto + "';", null);
						if (c.moveToFirst()) {
							do {
								idEfecto = c.getInt(0);
							} while (c.moveToNext());
						}
						c.close();
						if (idEfecto == -1) {
							Toast.makeText(getBaseContext(),"Id de efecto no encontrado", Toast.LENGTH_SHORT).show();
							return;
						}
						db.begin();
						db.insertar(
								"UPDATE EFECTOS  SET especial = '" + txtDescripcion.getText().toString() + "' " + 
								"WHERE idEfecto = " + idEfecto + ";");
						db.commit();
						Toast.makeText(getBaseContext(),"Cambios guardados", Toast.LENGTH_SHORT).show();
						VaciarCampos();
					} catch (Exception e) {
						e.printStackTrace();
						db.rollback();
					} finally {
						db.rollback();
						db.cerrar();
					}
				} else {
					Toast.makeText(v.getContext(),"Por favor, rellene \n todos los datos", Toast.LENGTH_SHORT).show();					
				}
				
			}
		});
		db = new AdaptadorBD(getBaseContext());
		ArrayList<String> listaEfectos = new ArrayList<String>();
		listaEfectos.add("Seleccionar");
		try {
			db.abrir();
			Cursor c = db.consulta("SELECT nombre FROM EFECTOS ORDER BY nombre;", null);
			if (c.moveToFirst()) {
				do {
					listaEfectos.add(c.getString(0));
				} while (c.moveToNext());
			}
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.cerrar();
		}
		ArrayAdapter <String> adapterEfectos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaEfectos);
		adapterEfectos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.desplegableEfectos.setAdapter(adapterEfectos);
		this.desplegableEfectos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> spinner,View view, int position,long id) {
				try {
					String efecto = spinner.getSelectedItem().toString();
					db.abrir();
					Cursor c = db.consulta("SELECT idEfecto FROM EFECTOS WHERE nombre = '" + efecto + "';", null);
					int idEfecto = 0;
					if (c.moveToFirst()) {
						do {
							idEfecto = c.getInt(0);
						} while (c.moveToNext());
					}
					c.close();
					Cursor c1 = db.consulta("SELECT especial FROM EFECTOS WHERE idEfecto = " + idEfecto + ";", null);
					if (c1.moveToFirst()) {
						do {
							String especial = c1.getString(0);
							txtDescripcion.setText(especial);
						} while (c1.moveToNext());
					}
					c1.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					db.cerrar();
				}
			}

			public void onNothingSelected(
					AdapterView<?> paramAnonymousAdapterView) {
			}
		});
		getWindow().setSoftInputMode(3);
	}
}