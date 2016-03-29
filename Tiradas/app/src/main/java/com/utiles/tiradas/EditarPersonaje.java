package com.utiles.tiradas;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

public class EditarPersonaje extends Activity {
	private Button btnGuardarEditarPersonaje;
	AdaptadorBD db;
	private Spinner desplegableCar;
	private Spinner desplegableCon;
	private Spinner desplegableDes;
	private Spinner desplegableFue;
	private Spinner desplegableInt;
	private Spinner desplegablePersonajes;
	private Spinner desplegableSab;
	private Spinner desplegableTamanos;

	private void VaciarCampos() {
		desplegablePersonajes.setSelection(0);
		desplegableFue.setSelection(0);
		desplegableDes.setSelection(0);
		desplegableCon.setSelection(0);
		desplegableSab.setSelection(0);
		desplegableInt.setSelection(0);
		desplegableCar.setSelection(0);
	}

	private boolean comprobarDatos() {
		if ((desplegablePersonajes.getSelectedItem() == null)
				|| (desplegablePersonajes.getSelectedItemId() == 0)
				|| (desplegableDes.getSelectedItem() == null)
				|| (desplegableCon.getSelectedItem() == null)
				|| (desplegableSab.getSelectedItem() == null)
				|| (desplegableInt.getSelectedItem() == null)
				|| (desplegableCar.getSelectedItem() == null)) {
			return false;
		}
		return true;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editarpersonaje);
		
		desplegablePersonajes = (Spinner) findViewById(R.id.sipnnerNombrePersonaje);
		desplegableTamanos = (Spinner) findViewById(R.id.desplegableTamanoPersonaje);
		desplegableFue = (Spinner) findViewById(R.id.desplegableFue);
		desplegableDes = (Spinner) findViewById(R.id.desplegableDes);
		desplegableCon = (Spinner) findViewById(R.id.desplegableCon);
		desplegableSab = (Spinner) findViewById(R.id.desplegableSab);
		desplegableInt = (Spinner) findViewById(R.id.desplegableInt);
		desplegableCar = (Spinner) findViewById(R.id.desplegableCar);
		btnGuardarEditarPersonaje = (Button) findViewById(R.id.btnGuardarEditarPersonaje);
		btnGuardarEditarPersonaje.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (comprobarDatos()) {
					String nombrePersonaje = desplegablePersonajes.getSelectedItem().toString();
					int idPersonaje = -1;
					try {
						db = new AdaptadorBD(getBaseContext());
						db.abrir();
						Cursor c = db.consulta("SELECT idPersonaje FROM personajes WHERE nombre = '" + nombrePersonaje + "';", null);
						if (c.moveToFirst()) {
							do {
								idPersonaje = c.getInt(0);
							} while (c.moveToNext());
						}
						c.close();
						if (idPersonaje == -1) {
							Toast.makeText(getBaseContext(),"Id de personaje no encontrado",Toast.LENGTH_SHORT).show();
							return;
						}
						db.begin();
						db.insertar(
								"UPDATE PERSONAJES_STATS SET valor = " + 
								Integer.parseInt(desplegableFue.getSelectedItem().toString()) + " " + 
								"WHERE idPersonaje = " + idPersonaje + " AND idStat = 1;");
						db.insertar(
								"UPDATE PERSONAJES_STATS SET valor = " + 
								Integer.parseInt(desplegableDes.getSelectedItem().toString()) + " " + 
								"WHERE idPersonaje = " + idPersonaje + " AND idStat = 2;");
						db.insertar(
								"UPDATE PERSONAJES_STATS SET valor = " + 
								Integer.parseInt(desplegableCon.getSelectedItem().toString()) + " " + 
								"WHERE idPersonaje = " + idPersonaje + " AND idStat = 3;");
						db.insertar(
								"UPDATE PERSONAJES_STATS SET valor = " + 
								Integer.parseInt(desplegableSab.getSelectedItem().toString()) + " " + 
								"WHERE idPersonaje = " + idPersonaje + " AND idStat = 4;");
						db.insertar(
								"UPDATE PERSONAJES_STATS SET valor = " + 
								Integer.parseInt(desplegableInt.getSelectedItem().toString()) + " " + 
								"WHERE idPersonaje = " + idPersonaje + " AND idStat = 5;");
						db.insertar(
								"UPDATE PERSONAJES_STATS SET valor = " + 
								Integer.parseInt(desplegableCar.getSelectedItem().toString()) + " " + 
								"WHERE idPersonaje = " + idPersonaje + " AND idStat = 6;");
						db.commit();
						Toast.makeText(getBaseContext(),"Caracteristicas modificadas con exito",Toast.LENGTH_SHORT).show();
						VaciarCampos();
					} catch (Exception e) {
						e.printStackTrace();
						db.rollback();
					} finally {
						db.rollback();
						db.cerrar();
					}
				} else {
					Toast.makeText(v.getContext(),"Por favor, rellene todos los datos", Toast.LENGTH_SHORT).show();					
				}
			}
		});
		db = new AdaptadorBD(getBaseContext());
		ArrayList<String> listaPersonajes = new ArrayList<String>();
		listaPersonajes.add("Seleccionar");
		try {
			db.abrir();
			Cursor c = db.consulta("SELECT nombre FROM PERSONAJES ORDER BY nombre;", null);
			if (c.moveToFirst()) {
				do {
					listaPersonajes.add(c.getString(0));
				} while (c.moveToNext());
			}
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.cerrar();
		}
		ArrayAdapter<String> adapterPersonajes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaPersonajes);
		adapterPersonajes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.desplegablePersonajes.setAdapter(adapterPersonajes);
		
		ArrayList<String> listaTamanos = new ArrayList<String>();
		listaTamanos.add("Seleccionar");
		try {
			this.db.abrir();
			Cursor c1 = db.consulta("SELECT nombre FROM TAMANOS;", null);
			if (c1.moveToFirst()) {
				do {
					listaTamanos.add(c1.getString(0));
				} while (c1.moveToNext());
			}
			c1.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.cerrar();
		}
		ArrayAdapter <String> adapterTamanos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaTamanos);
		adapterTamanos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.desplegableTamanos.setAdapter(adapterTamanos);
		
		ArrayList<String> listaStats = new ArrayList<String>();
		for (int i = 1; i <= 45 ; i++) {
			listaStats.add(Integer.toString(i));
		}
		ArrayAdapter<String> adapterStats = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listaStats);
		adapterStats.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		desplegableFue.setAdapter(adapterStats);
		desplegableDes.setAdapter(adapterStats);
		desplegableCon.setAdapter(adapterStats);
		desplegableSab.setAdapter(adapterStats);
		desplegableInt.setAdapter(adapterStats);
		desplegableCar.setAdapter(adapterStats);
		
		desplegablePersonajes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@SuppressWarnings("unchecked")
			public void onItemSelected(AdapterView<?> spinner,View v,int position,long id) {
				if (desplegablePersonajes.getSelectedItemPosition() == 0)return;
				try {
					String nombrePersonaje = desplegablePersonajes.getSelectedItem().toString();
					db = new AdaptadorBD(getBaseContext());
					db.abrir();
					Cursor c = db.consulta("SELECT idPersonaje, idTamano FROM PERSONAJES WHERE nombre = '"+ nombrePersonaje + "';",null);
					c.moveToFirst();
					int idPersonaje = c.getInt(0);
					int idTamano = c.getInt(1);
					c.close();
					Cursor c1 = db.consulta("SELECT nombre FROM tamanos WHERE idTamano = " + idTamano + ";", null);
					c1.moveToFirst();
					String tamano = c1.getString(0);
					c1.close();
					ArrayAdapter<String> adapterTamanos = (ArrayAdapter<String>)desplegableTamanos.getAdapter();
					int posTamano = adapterTamanos.getPosition(tamano);
					desplegableTamanos.setSelection(posTamano);
					
					Cursor c2 = db.consulta("SELECT idStat, valor FROM PERSONAJES_STATS WHERE idPersonaje = " + idPersonaje + ";", null);
					if (c2.moveToFirst()) {
						do {
							switch (c2.getInt(0)){
							case 1:
								ArrayAdapter<String> adapterFue = (ArrayAdapter<String>)desplegableFue.getAdapter();
								desplegableFue.setSelection(adapterFue.getPosition(Integer.toString(c2.getInt(1))));
								break;
							case 2:
								ArrayAdapter<String> adapterDes = (ArrayAdapter<String>)desplegableDes.getAdapter();
								desplegableDes.setSelection(adapterDes.getPosition(Integer.toString(c2.getInt(1))));
								break;
							case 3:
								ArrayAdapter<String> adapterCon = (ArrayAdapter<String>)desplegableCon.getAdapter();
								desplegableCon.setSelection(adapterCon.getPosition(Integer.toString(c2.getInt(1))));
								break;
							case 4:
								ArrayAdapter<String> adapterSab = (ArrayAdapter<String>)desplegableSab.getAdapter();
								desplegableSab.setSelection(adapterSab.getPosition(Integer.toString(c2.getInt(1))));
								break;
							case 5:
								ArrayAdapter<String> adapterInt = (ArrayAdapter<String>)desplegableInt.getAdapter();
								desplegableInt.setSelection(adapterInt.getPosition(Integer.toString(c2.getInt(1))));
								break;
							case 6:
								ArrayAdapter<String> adapterCar = (ArrayAdapter<String>)desplegableCar.getAdapter();
								desplegableCar.setSelection(adapterCar.getPosition(Integer.toString(c2.getInt(1))));
								break;
							}
						} while (c2.moveToNext());
					}
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
	}
}