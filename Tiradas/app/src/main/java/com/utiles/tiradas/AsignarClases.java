package com.utiles.tiradas;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

public class AsignarClases extends Activity {
	private Button btnCrearCosa;
	private Button btnGuardar;
	private AdaptadorBD db;
	private Spinner desplegablePersonajes;
	private ViewGroup layout;
	private ScrollView scrollView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.asignarclases);
		
		desplegablePersonajes = (Spinner) findViewById(R.id.desplegablePersonajes);
		scrollView = (ScrollView) findViewById(R.id.scrollAsignarClases);
		layout = (ViewGroup) findViewById(R.id.layoutCosas);
		btnCrearCosa = (Button) findViewById(R.id.btnCrearCosa);
		btnGuardar = (Button) findViewById(R.id.btnGuardar);
		
		db = new AdaptadorBD(getBaseContext());
		
		ArrayList<String> listaPersonajes = new ArrayList<String>();
		listaPersonajes.add("Seleccionar");
		try {
			db.abrir();
			Cursor c = this.db.consulta("SELECT nombre FROM PERSONAJES ORDER BY nombre;", null);
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
		ArrayAdapter<String> localArrayAdapter = new ArrayAdapter<String>(
				getBaseContext(), android.R.layout.simple_spinner_item, listaPersonajes);
		localArrayAdapter.setDropDownViewResource(17367049);
		this.desplegablePersonajes.setAdapter(localArrayAdapter);
		this.desplegablePersonajes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> spinner, View v, int position, long id) {
				try {
					layout.removeAllViews();
					//Nombre personaje
					String nombrePersonaje = spinner.getSelectedItem().toString();
					db.abrir();
					int idPersonaje = 0;
					Cursor c1 = db.consulta("SELECT idPersonaje FROM PERSONAJES WHERE nombre = '" + nombrePersonaje + "';", null);
					if (c1.moveToFirst()){
						do {
							idPersonaje = c1.getInt(0);
						} while (c1.moveToNext());
					}
					
					Cursor c2 = db.consulta(
							"SELECT idClase, nivel FROM PERSONAJES_CLASES WHERE idPersonaje = " + idPersonaje + ";", null);
					if (c2.moveToFirst()) {
						do {
							int idClase = c2.getInt(0);
							int nivel = c2.getInt(1);
							CrearCosa();
							Spinner spinnerClase = (Spinner) layout.getChildAt(layout.getChildCount()-1).findViewById(R.id.desplegableClase);
							EditText txtNivel = (EditText) AsignarClases.this.layout.getChildAt(layout.getChildCount()-1).findViewById(R.id.txtNivel);
							Cursor c3 = db.consulta("SELECT nombre FROM clases WHERE idClase = " + idClase + ";", null);
							c3.moveToFirst();
							String clase = c3.getString(0);
							c3.close();
							spinnerClase.setSelection(AsignarClases.this.getIndexString(spinnerClase, clase));
							txtNivel.setText(Integer.toString(nivel));
						} while (c2.moveToNext());
					}
					c2.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					db.cerrar();
				}
			}

			public void onNothingSelected(AdapterView<?> paramAnonymousAdapterView) {
			}
		});
		this.btnCrearCosa.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				CrearCosa();
			}
		});
		this.btnGuardar.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View paramAnonymousView) {
				
				if (ClaseRepetida() == true){return;}
				if (NivelesOK()){
					db = new AdaptadorBD(getBaseContext());
					try {
						db.abrir();
						if (desplegablePersonajes.getSelectedItemId() == 0)return;
						String nombrePersonaje = desplegablePersonajes.getSelectedItem().toString();
						Cursor c1 = db.consulta("SELECT idPersonaje FROM PERSONAJES WHERE nombre = '" + nombrePersonaje + "'", null);
						int idPersonaje = 0;
						if (c1.moveToFirst()){
							do {
								idPersonaje = c1.getInt(0);
							} while (c1.moveToNext());
						}
						c1.close();
						if (idPersonaje == 0){
							Toast.makeText(getBaseContext(), "ID de personaje no encontrado", Toast.LENGTH_SHORT).show();
						}
						db.begin();
						db.insertar("DELETE FROM PERSONAJES_CLASES WHERE idPersonaje = " + idPersonaje + ";");
						for (int i=0; i < layout.getChildCount(); i++){
							RelativeLayout relativeLayout = (RelativeLayout) layout.getChildAt(i);
							Spinner spinnerClase = (Spinner) relativeLayout.findViewById(R.id.desplegableClase); 
							String clase = spinnerClase.getSelectedItem().toString();
							EditText txtNivel = (EditText) relativeLayout.findViewById(R.id.txtNivel);
							int nivel = Integer.parseInt(txtNivel.getText().toString());
							Cursor c = db.consulta("SELECT idClase FROM clases WHERE nombre = '" + clase + "';", null);
							int idClase = 0;
							if (c.moveToNext()){
								do {
									idClase = c.getInt(0);
								} while (c.moveToNext());
							}
							c.close();
							db.insertar(
									"INSERT INTO PERSONAJES_CLASES (idPersonaje, idClase, nivel) VALUES " + 
									"(" + idPersonaje + ", " + idClase + ", " + nivel + ");");
						}
						db.commit();
						Mensaje("Niveles añadidos con exito");
						desplegablePersonajes.setSelection(0);
					} catch (Exception e) {
						e.printStackTrace();
						db.rollback();
					} finally {
						db.rollback();
						db.cerrar();
					}
				}
			}
		});
	}
	

	private void ApagarTeclado() {
		getBaseContext();
		((InputMethodManager) getSystemService("input_method"))
				.hideSoftInputFromWindow(((EditText) this.layout
						.findViewById(R.id.txtNivel)).getWindowToken(), 0);
	}

	private boolean ClaseRepetida() {
		ArrayList<String> lista = new ArrayList<String>();
		for (int i=0; i < layout.getChildCount(); i++){
			RelativeLayout layoutCosas = (RelativeLayout) layout.getChildAt(i);
			Spinner spinnerClase = (Spinner) layoutCosas.findViewById(R.id.desplegableClase);
			lista.add(spinnerClase.getSelectedItem().toString());
		}
		for (int j=0; j < lista.size(); j++){
			String clase = lista.get(j);
			for (int k=j+1; k < lista.size(); k++){
				if (clase.equalsIgnoreCase(lista.get(k))){
					Mensaje("Clase duplicada: " + (String) lista.get(k));
					return true;
				}
			}
		}
		return false;
	}

	private void Mensaje(String paramString) {
		Toast.makeText(getBaseContext(), paramString, Toast.LENGTH_SHORT).show();
	}

	private boolean NivelesOK() {
		int niveles = 0;
		for (int i=0; i < layout.getChildCount(); i++){
			RelativeLayout relativeLayout = (RelativeLayout) layout.getChildAt(i);
			Spinner spinnerClase = (Spinner) relativeLayout.findViewById(R.id.desplegableClase); 
			String clase = spinnerClase.getSelectedItem().toString();
			EditText txtNivel = (EditText) relativeLayout.findViewById(R.id.txtNivel);
			int nivel = Integer.parseInt(txtNivel.getText().toString());
			Cursor cClase = db.consulta("SELECT idClase FROM CLASES WHERE nombre = '" + clase + "';", null);
			cClase.moveToFirst();
			int idClase = cClase.getInt(0);
			cClase.close();
			Cursor cNiveles = db.consulta("SELECT MIN(nivel), MAX(nivel) FROM CLASES_ATK_SAVES WHERE idClase = " + idClase + ";", null);
			int nivelMin = 0;
			int nivelMax = 0;
			if (cNiveles.moveToFirst()) {
				do {
					nivelMin = cNiveles.getInt(0);
					nivelMax = cNiveles.getInt(1);
				} while (cNiveles.moveToNext());
			}
			cNiveles.close();
			
			if (nivelMin > nivel || nivelMax < nivel){
				Mensaje("No puede seleccionar ese nivel de clase " + clase + 
						", \nEl nivel ha de ser entre " + nivelMin + " y " + nivelMax + ".");
				return false;
			} else {
				niveles += nivel;
			}
		}
		if (niveles > 20) {
			Mensaje("El numero total de niveles no puede exceder 20");
			return false;
		}
		return true;
	}

	private int getIndexString(Spinner spinner, String nombre) {
		int i = 0;
		for (int j = 0;; j++) {
			if (j >= spinner.getCount()) {
				return i;
			}
			if (spinner.getItemAtPosition(j).toString()
					.equals(nombre)) {
				i = j;
			}
		}
	}

	
	@SuppressLint("InflateParams")
	public void CrearCosa() {
		RelativeLayout layoutCosa = (RelativeLayout) LayoutInflater.from(getBaseContext()).inflate(R.layout.layout_cosas_personajes, null, false);
		layout.addView(layoutCosa);
		Spinner spinnerClase = (Spinner) layoutCosa.findViewById(R.id.desplegableClase);
		db = new AdaptadorBD(getBaseContext());
		ArrayList<String> lista = new ArrayList<String>();
		try {
			this.db.abrir();
			Cursor c = this.db.consulta("SELECT nombre FROM CLASES ORDER BY nombre;", null);
			if (c.moveToFirst()) {
				do {
					lista.add(c.getString(0));
				} while (c.moveToNext());
			}
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.cerrar();
		}
		ArrayAdapter<String> adapterClases = new ArrayAdapter<String> (getBaseContext(), android.R.layout.simple_spinner_item,
				lista);
		adapterClases.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerClase.setAdapter(adapterClases);
		ImageButton btnQuitarLinea = (ImageButton) layoutCosa.findViewById(R.id.btnQuitarLinea);
		btnQuitarLinea.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RelativeLayout layout = (RelativeLayout) v.getParent();
				((ViewGroup) ((ViewGroup) v.getParent()).getParent()).removeView(layout);
			}
		});
		scrollView.post(new Runnable() {
			public void run() {
				scrollView.fullScroll(ScrollView.FOCUS_DOWN);
			}
		});
		ApagarTeclado();
	}
}
