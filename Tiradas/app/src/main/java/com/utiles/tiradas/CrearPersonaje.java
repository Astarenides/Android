package com.utiles.tiradas;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

public class CrearPersonaje extends Activity {
	
	private Button btnGuardarPersonaje;
	private AdaptadorBD db;
	private Spinner desplegableCar;
	private Spinner desplegableCon;
	private Spinner desplegableDes;
	private Spinner desplegableFue;
	private Spinner desplegableInt;
	private Spinner desplegableRaza;
	private Spinner desplegableSab;
	private EditText txtNombre;

	private void VaciarCampos() {
		txtNombre.setText("");
		desplegableRaza.setSelection(0);
		desplegableFue.setSelection(0);
		desplegableDes.setSelection(0);
		desplegableCon.setSelection(0);
		desplegableSab.setSelection(0);
		desplegableInt.setSelection(0);
		desplegableCar.setSelection(0);
	}

	private boolean comprobarDatos() {
		if ((txtNombre.getText().toString().matches(""))
				|| (desplegableRaza.getSelectedItem() == null)
				|| (desplegableRaza.getSelectedItemId() == 0L)
				|| (desplegableFue.getSelectedItem() == null)
				|| (desplegableDes.getSelectedItem() == null)
				|| (desplegableCon.getSelectedItem() == null)
				|| (desplegableSab.getSelectedItem() == null)
				|| (desplegableInt.getSelectedItem() == null)
				|| (desplegableCar.getSelectedItem() == null)) {
			return false;
		}
		return true;
	}

	public void onCreate(Bundle paramBundle) {
		
		super.onCreate(paramBundle);
		setContentView(R.layout.crearpersonajes);
		
		txtNombre = (EditText) findViewById(R.id.txtNombrePersonaje);
		desplegableRaza = (Spinner) findViewById(R.id.desplegableRazaPersonaje);
		desplegableFue = (Spinner) findViewById(R.id.desplegableFue);
		desplegableDes = (Spinner) findViewById(R.id.desplegableDes);
		desplegableCon = (Spinner) findViewById(R.id.desplegableCon);
		desplegableSab = (Spinner) findViewById(R.id.desplegableSab);
		desplegableInt = (Spinner) findViewById(R.id.desplegableInt);
		desplegableCar = (Spinner) findViewById(R.id.desplegableCar);
		btnGuardarPersonaje = ((Button) findViewById(R.id.btnGuardarPersonaje));
		btnGuardarPersonaje.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (comprobarDatos()) {
					String nombrePersonaje = txtNombre.getText().toString();
					int idRaza = -1;
					String raza = desplegableRaza.getSelectedItem().toString();
					try {
						db = new AdaptadorBD(getBaseContext());
						db.abrir();
						Cursor c = db.consulta("SELECT idRaza FROM RAZAS WHERE nombre = '" + raza + "';", null);
						if (c.moveToFirst()) {
							do {
								idRaza = c.getInt(0);
							} while (c.moveToNext());
						}
						c.close();
						if (idRaza == -1) {
							Toast.makeText(getBaseContext(), "Id de raza no encontrada", Toast.LENGTH_SHORT).show();
							return;
						} else {
							Cursor c1 = db.consulta(
									"SELECT COUNT(idPersonaje) FROM PERSONAJES WHERE nombre = '" + nombrePersonaje + "';", null);
							c1.moveToFirst();
							if (c1.getInt(0) > 0) {
								Toast.makeText(getBaseContext(),"Ya existe un personaje llamado " + nombrePersonaje, Toast.LENGTH_SHORT).show();
								return;
							} else {
								Cursor c2 = db.consulta("SELECT tamano FROM RAZAS WHERE idRaza = " + idRaza + ";", null);
								c2.moveToFirst();
								int idTamano = c2.getInt(0);
								c2.close();
								db.insertar("INSERT INTO PERSONAJES (nombre, idRaza, idTamano) VALUES ('"
												+ nombrePersonaje + "', " + idRaza + ", " + idTamano + ");");
								Cursor c3 = db.consulta(
												"SELECT idPersonaje FROM personajes WHERE rowid = last_insert_rowid();",
												null);
								c3.moveToFirst();
								int idPersonaje = c3.getInt(0);
								c3.close();
								db.insertar("INSERT INTO PERSONAJES_STATS (idPersonaje, idStat, valor) VALUES ("
										+ idPersonaje + ", 1, " + Integer.parseInt(desplegableFue.getSelectedItem().toString())+ ");");
								db.insertar("INSERT INTO PERSONAJES_STATS (idPersonaje, idStat, valor) VALUES ("
										+ idPersonaje + ", 2, " + Integer.parseInt(desplegableDes.getSelectedItem().toString())+ ");");
								db.insertar("INSERT INTO PERSONAJES_STATS (idPersonaje, idStat, valor) VALUES ("
										+ idPersonaje + ", 3, " + Integer.parseInt(desplegableCon.getSelectedItem().toString())+ ");");
								db.insertar("INSERT INTO PERSONAJES_STATS (idPersonaje, idStat, valor) VALUES ("
										+ idPersonaje + ", 4, " + Integer.parseInt(desplegableSab.getSelectedItem().toString())+ ");");
								db.insertar("INSERT INTO PERSONAJES_STATS (idPersonaje, idStat, valor) VALUES ("
										+ idPersonaje + ", 5, " + Integer.parseInt(desplegableInt.getSelectedItem().toString())+ ");");
								db.insertar("INSERT INTO PERSONAJES_STATS (idPersonaje, idStat, valor) VALUES ("
										+ idPersonaje + ", 6, " + Integer.parseInt(desplegableCar.getSelectedItem().toString())+ ");");
								
								Toast.makeText(getBaseContext(),"Personaje y caracteristicas añadidos con exito",Toast.LENGTH_SHORT).show();
								VaciarCampos();
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						db.cerrar();
					}
				} else {
					Toast.makeText(v.getContext(),"Por favor, rellene \n todos los datos", Toast.LENGTH_SHORT).show();
					return;
				}
			}
		});
		db = new AdaptadorBD(getBaseContext());
		ArrayList<String> listaRazas = new ArrayList<String>();
		listaRazas.add("Seleccionar");
		try {
			db.abrir();
			Cursor c = db.consulta("SELECT nombre FROM RAZAS ORDER BY nombre;", null);
			if (c.moveToFirst()) {
				do {
					listaRazas.add(c.getString(0));
				} while (c.moveToNext());
			}
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.cerrar();
		}
		ArrayAdapter<String> adapterRazas = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaRazas);
		adapterRazas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.desplegableRaza.setAdapter(adapterRazas);
		
		ArrayList<String> listaStats = new ArrayList<String>();
		for (int i = 1; i <= 45; i++) {listaStats.add(Integer.toString(i));}
		ArrayAdapter<String> adapterStats = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listaStats);
		adapterStats.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		desplegableFue.setAdapter(adapterStats);
		desplegableDes.setAdapter(adapterStats);
		desplegableCon.setAdapter(adapterStats);
		desplegableSab.setAdapter(adapterStats);
		desplegableInt.setAdapter(adapterStats);
		desplegableCar.setAdapter(adapterStats);
	}
}

/*
 * Location: C:\Users\Astarenides\Desktop\Nueva
 * carpeta\com.utiles.tiradas-2\classes_dex2jar.jar
 * 
 * Qualified Name: com.utiles.tiradas.CrearPersonaje
 * 
 * JD-Core Version: 0.7.0.1
 */