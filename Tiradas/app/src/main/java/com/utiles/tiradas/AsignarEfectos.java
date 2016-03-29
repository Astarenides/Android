package com.utiles.tiradas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AsignarEfectos extends Activity {
	
	private Button btnGuardar;
	AdaptadorBD db;
	private Spinner desplegableEfectos;
	private Spinner desplegablePersonajes;
	private ViewGroup layout;
	private ScrollView scrollView;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.asignarefectos);
		
		desplegablePersonajes = (Spinner) findViewById(R.id.sipnnerNombrePersonajeAsignarEfectos);
		desplegableEfectos = (Spinner) findViewById(R.id.spinnerAsignarEfectosPersonaje);
		btnGuardar = (Button) findViewById(R.id.btnAceptarAsignarEfectosPersonaje);
		layout = (ViewGroup) findViewById(R.id.layoutCosasAsignarEfectosPersonaje);
		scrollView = (ScrollView) findViewById(R.id.scrollAsignarEfectosPersonaje);
		db = new AdaptadorBD(getBaseContext());
		
		ArrayList<String> listaPersonajes = new ArrayList<String>();
		listaPersonajes.add("Seleccionar");
		try {
			this.db.abrir();
			Cursor c = db.consulta(
					"SELECT nombre FROM PERSONAJES ORDER BY nombre;", null);
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
		this.desplegablePersonajes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> spinner, View view, int position, long id) {
				try {
					String nombrePersonaje = spinner.getSelectedItem().toString();
					layout.removeAllViews();
					db.abrir();
					int idPersonaje = 0;
					Cursor c1 = db.consulta("SELECT idPersonaje FROM PERSONAJES WHERE nombre = '"+ nombrePersonaje + "';", null);
					if (c1.moveToFirst()){
						do {
							idPersonaje = c1.getInt(0);
						} while (c1.moveToNext());
					}
					c1.close();
					Cursor c2 = db.consulta(
							"SELECT idEfecto FROM PERSONAJES_EFECTOS WHERE idPersonaje = " + idPersonaje + 
							" AND afectado = 1;", null);
					if (c2.moveToFirst()) {
						do {
							int idEfecto = c2.getInt(0);
							Cursor c3 = db.consulta("SELECT nombre FROM EFECTOS WHERE idEfecto = " + idEfecto + ";", null);
							c3.moveToFirst();
							String efecto = c3.getString(0);
							c3.close();
							CrearCosa(efecto);
						} while (c2.moveToNext());
					}
					c2.close();
					return;
				} catch (Exception e) {
					e.printStackTrace();
					return;
				} finally {
					db.cerrar();
				}
			}
	
			public void onNothingSelected(AdapterView<?> paramAnonymousAdapterView) {
			}
		});
		
		ArrayList<String> lista = new ArrayList<String>();
		ArrayList<String> listaFav = new ArrayList<String>();
		lista.add("-----------------");
		listaFav.add("-------FAV-------");
		try {
			db.abrir();
			Cursor c = db.consulta("SELECT nombre FROM FAV_EFECTOS ORDER BY NOMBRE;", null);
			if (c.moveToFirst()) {
				do {
					listaFav.add(c.getString(0));
				} while (c.moveToNext());
			}
			c.close();
			Cursor c1 = db.consulta("SELECT nombre FROM EFECTOS ORDER BY nombre;", null);
			if (c1.moveToFirst()) {
				do {
					lista.add(c1.getString(0));
				} while (c1.moveToNext());
			}
			c1.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.db.cerrar();
		}
		listaFav.addAll(lista);
		ArrayAdapter <String> adapterEfectos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaFav);
		adapterEfectos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.desplegableEfectos.setAdapter(adapterEfectos);
		this.desplegableEfectos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> spinner, View v, int position, long id) {
				String efecto = spinner.getSelectedItem().toString();
				if ((efecto.equalsIgnoreCase("-----------------")) 
						|| ((efecto.equalsIgnoreCase("-------FAV-------"))
						|| (spinner.getSelectedItemId() == 0)
						|| (AsignarEfectos.this.desplegablePersonajes.getSelectedItemId() == 0))) {
					return;
				}
				CrearCosa(efecto);
				CrearFavorito(efecto);
				ArrayList<String> lista = new ArrayList<String>();
				ArrayList<String> listaFav = new ArrayList<String>();
				lista.add("-----------------");
				listaFav.add("-------FAV-------");
				try {
					db.abrir();
					Cursor c = db.consulta("SELECT nombre FROM FAV_EFECTOS ORDER BY NOMBRE;",null);
					if (c.moveToFirst()) {
						do {
							listaFav.add(c.getString(0));
						} while (c.moveToNext());
					}
					c.close();
					Cursor c1 = db.consulta("SELECT nombre FROM EFECTOS ORDER BY nombre;",null);
					if (c1.moveToFirst()) {
						do {
							lista.add(c1.getString(0));
						} while (c1.moveToNext());
					}
					c1.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					db.cerrar();
				}
				listaFav.addAll(lista);
				ArrayAdapter<String> adapterEfectos = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item,
						listaFav);
				adapterEfectos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				AsignarEfectos.this.desplegableEfectos.setAdapter(adapterEfectos);
			}

			public void onNothingSelected(AdapterView<?> paramAnonymousAdapterView) {
				
			}
		});
		this.btnGuardar.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View paramAnonymousView) {
				if (EfectoRepetido()) return;
				if (desplegablePersonajes.getSelectedItemId() == 0){
					Toast.makeText(getBaseContext(), "Seleccione personaje", Toast.LENGTH_SHORT).show();
				}
				String personaje = desplegablePersonajes.getSelectedItem().toString();
				try {
					db.abrir();
					Cursor c = db.consulta("SELECT idPersonaje FROM PERSONAJES WHERE nombre = '" + personaje + "';",null);
					c.moveToFirst();
					int idPersonaje = c.getInt(0);
					c.close();
					db.begin(); 
					db.insertar("DELETE FROM PERSONAJES_EFECTOS WHERE idPersonaje = " + idPersonaje + ";");
					//Recorrer los childs del layout parent 
					int count = layout.getChildCount();
					for (int i=0; i < count; i++){
						//Recoger de cada child la clase y el nivel
						RelativeLayout cosas = (RelativeLayout) layout.getChildAt(i);
						TextView txtNombreEfecto = (TextView) cosas.findViewById(R.id.txtEfectoPersonajeTabla);
						String efecto = txtNombreEfecto.getText().toString();
						//Buscar id del efecto en la base de datos
						int idEfecto = 0;
						Cursor cc = db.consulta("SELECT idEfecto FROM EFECTOS WHERE nombre = '" + efecto + "';",null);
						cc.moveToFirst();
						idEfecto = cc.getInt(0);
						cc.close();
						//Insertar idEfecto, afectado en la base de datos
						db.insertar(
								"INSERT INTO PERSONAJES_EFECTOS (idPersonaje, idEfecto, afectado) VALUES " + 
								"(" + idPersonaje + ", " + idEfecto + ", 1);");
					}
					db.commit();
					Toast.makeText(getBaseContext(), "Efectos almacenados con exito", Toast.LENGTH_SHORT).show();
					desplegablePersonajes.setSelection(0);
				} catch (Exception e) {
					e.printStackTrace();
					db.rollback();
				} finally {
					db.rollback();
					db.cerrar();
				}
			}
		});
	}

	private boolean EfectoRepetido() {
		ArrayList<String> listaEfectos = new ArrayList<String>();
		for (int i=0; i < layout.getChildCount(); i++) {
			//Recoger de cada child la clase y el nivel
			RelativeLayout cosas = (RelativeLayout) layout.getChildAt(i);
			TextView txtNombreEfecto = (TextView) cosas.findViewById(R.id.txtEfectoPersonajeTabla);
			String efecto = txtNombreEfecto.getText().toString();
			listaEfectos.add(efecto);
		}
		for (int j=0; j < listaEfectos.size(); j++){
			String cad = listaEfectos.get(j);
			for (int k=j+1; k < listaEfectos.size(); k++){
				if (cad.equalsIgnoreCase(listaEfectos.get(k))){
					Toast.makeText(getBaseContext(),"Efecto duplicado: " + (String) listaEfectos.get(k),Toast.LENGTH_SHORT).show();
					return true;
				}
			}
		}
		return false;
	}

	@SuppressLint("InflateParams")
	public void CrearCosa(String efecto) {
		RelativeLayout layoutCosas = (RelativeLayout) LayoutInflater.from(getBaseContext()).inflate(R.layout.layout_cosas_tabla, null, false);
		layout.addView(layoutCosas);
		TextView txtEfecto = (TextView) layoutCosas.findViewById(R.id.txtEfectoPersonajeTabla);
		txtEfecto.setText(efecto);
		Button btnEliminarfilaEfectoPersonajeTabla = (Button) layoutCosas.findViewById(R.id.btnEliminarfilaEfectoPersonajeTabla);
		btnEliminarfilaEfectoPersonajeTabla.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RelativeLayout localRelativeLayout = (RelativeLayout) v.getParent();
				((ViewGroup) ((ViewGroup) v.getParent()).getParent()).removeView(localRelativeLayout);
			}
		});
		
		this.scrollView.post(new Runnable() {
			public void run() {
				scrollView.fullScroll(ScrollView.FOCUS_DOWN);
			}
		});
	}

	public void CrearFavorito(String efecto) {
		try {
			db.abrir();
			int idEfecto = 0;
			Cursor c = db.consulta("SELECT idEfecto FROM EFECTOS WHERE nombre = '" + efecto + "';", null);
			if (c.moveToFirst()){
				do {
					idEfecto = c.getInt(0);
				} while (c.moveToNext());
			}
			Cursor c1 = this.db.consulta("SELECT COUNT(idEfecto) FROM FAV_EFECTOS WHERE idEfecto = " + idEfecto + ";", null);
			c1.moveToFirst();
			int aux = c1.getInt(0);
			c1.close();
			db.begin();
			if (aux == 0) {
				this.db.insertar("INSERT INTO FAV_EFECTOS (idEfecto, nombre) VALUES (" + idEfecto + ", '" + efecto + "')");
			}
			db.commit();
		} catch (Exception e) {
			e.printStackTrace();
			this.db.rollback();
		} finally {
			this.db.rollback();
			this.db.cerrar();
		}
	}
}