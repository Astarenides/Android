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

public class AsignarStats extends Activity {
	
	private Button btnCrearCosa;
	private Button btnGuardar;
	private AdaptadorBD db;
	private Spinner desplegableEfectos;
	private ViewGroup layoutCosasEfectos;
	private ScrollView scrollView;
	
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.asignarstats);
		
		desplegableEfectos = (Spinner) findViewById(R.id.desplegableEfectos);
		scrollView = (ScrollView) findViewById(R.id.scrollAsignarStats);
		layoutCosasEfectos = (ViewGroup) findViewById(R.id.layoutCosasEfectos);
		btnCrearCosa = (Button) findViewById(R.id.btnCrearCosaEfectos);
		btnGuardar = (Button) findViewById(R.id.btnGuardar);
		
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
		
		ArrayAdapter<String> adapterEfectos = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item,
				listaEfectos);
		adapterEfectos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		desplegableEfectos.setAdapter(adapterEfectos);
		desplegableEfectos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@SuppressWarnings("unchecked")
			public void onItemSelected(AdapterView<?> adapter, View view, int position,long id) {
				try {
					layoutCosasEfectos.removeAllViews();
					String efecto = adapter.getSelectedItem().toString();
					db.abrir();
					int idEfecto = 0;
					Cursor c = db.consulta("SELECT idEfecto FROM EFECTOS WHERE nombre = '" + efecto + "';", null);
					if (c.moveToFirst()){
						do {
							idEfecto = c.getInt(0);
						} while (c.moveToNext());
					}
					c.close();
					Cursor c1 = db.consulta(
									"SELECT idStat, idTipo, valor FROM EFECTOS_STATS WHERE idEfecto = " + idEfecto + ";", null);
					if (c1.moveToFirst()) {
						do {
							int idStat = c1.getInt(0);
							int idTipo = c1.getInt(1);
							int valor = c1.getInt(2);
							CrearCosa();
							Spinner spinnerStat = (Spinner) layoutCosasEfectos.getChildAt(layoutCosasEfectos.getChildCount() -1).findViewById(R.id.desplegableStatsAsignarEfecto);
							Spinner spinnerTipo = (Spinner) layoutCosasEfectos.getChildAt(layoutCosasEfectos.getChildCount() -1).findViewById(R.id.desplegableTipoMod);
							EditText txtValor = (EditText) layoutCosasEfectos.getChildAt(layoutCosasEfectos.getChildCount() -1).findViewById(R.id.txtValor);
							
							Cursor c2 = db.consulta("SELECT nombreStat FROM STATS WHERE idStat = " + idStat + ";", null);
							c2.moveToFirst();
							String stat = c2.getString(0);
							c2.close();
							ArrayAdapter<String> myAdap = (ArrayAdapter<String>) spinnerStat.getAdapter(); //cast to an ArrayAdapter
							int spinnerPosition = myAdap.getPosition(stat);
							spinnerStat.setSelection(spinnerPosition);
							Cursor c3 = db.consulta("SELECT nombre FROM TIPOS WHERE idTipo = " + idTipo + ";", null);
							c3.moveToFirst();
							String tipo = c3.getString(0);
							c3.close();
							myAdap = (ArrayAdapter<String>) spinnerTipo.getAdapter();
							spinnerPosition = myAdap.getPosition(tipo);
							spinnerTipo.setSelection(spinnerPosition);
							txtValor.setText(Integer.toString(valor));
						} while (c1.moveToNext());
					}
					c1.close();
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
				if (StatRepetido())return;
				try {
					db = new AdaptadorBD(getBaseContext());
					db.abrir();
					if (desplegableEfectos.getSelectedItemId() == 0)return;
					String nombreEfecto = desplegableEfectos.getSelectedItem().toString();
					Cursor c1 = db.consulta("SELECT idEfecto FROM EFECTOS WHERE nombre = '" + nombreEfecto + "'", null);
					int idEfecto = 0;
					if (c1.moveToFirst()){
						do {
							idEfecto = c1.getInt(0);
						} while (c1.moveToNext());
					}
					c1.close();
					if (idEfecto == 0){
						Toast.makeText(getBaseContext(), "ID de efecto no encontrado", Toast.LENGTH_SHORT).show();
					}
					db.begin();
					db.insertar("DELETE FROM EFECTOS_STATS WHERE idEfecto = " + idEfecto + ";");
					for (int i=0; i < layoutCosasEfectos.getChildCount(); i++){
						RelativeLayout relativeLayout = (RelativeLayout) layoutCosasEfectos.getChildAt(i);
						Spinner spinnerStat = (Spinner) relativeLayout.findViewById(R.id.desplegableStatsAsignarEfecto); 
						String stat = spinnerStat.getSelectedItem().toString();
						Spinner spinnerTipo = (Spinner) relativeLayout.findViewById(R.id.desplegableTipoMod);
						String tipo = spinnerTipo.getSelectedItem().toString();
						EditText txtValor = (EditText) relativeLayout.findViewById(R.id.txtValor);
						int valor = Integer.parseInt(txtValor.getText().toString());
						Cursor c2 = db.consulta("SELECT idStat FROM STATS WHERE nombreStat = '" + stat + "';", null);
						int idStat = 0;
						if (c2.moveToNext()){
							do {
								idStat = c2.getInt(0);
							} while (c2.moveToNext());
						}
						c2.close();
						Cursor c3 = db.consulta("SELECT idTipo FROM TIPOS WHERE nombre = '" + tipo + "';", null);
						int idTipo = 0;
						if (c3.moveToFirst()){
							do {
								idTipo = c3.getInt(0);
							} while (c3.moveToNext());
						}
						db.insertar(
								"INSERT INTO EFECTOS_STATS (idEfecto, idStat, idTipo, valor) VALUES " + 
								"(" + idEfecto + ", " + idStat + ", " + idTipo + ", " + valor + ");");
					}
					db.commit();
					Mensaje("Caracteristicas añadidas con exito");
					desplegableEfectos.setSelection(0);
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

	private void ApagarTeclado() {
		getBaseContext();
		((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(((EditText) layoutCosasEfectos.findViewById(R.id.txtValor)).getWindowToken(), 0);
	}

	private void Mensaje(String mensaje) {
		Toast.makeText(getBaseContext(), mensaje, Toast.LENGTH_SHORT).show();
	}

	private boolean StatRepetido() {
		ArrayList<String> lista = new ArrayList<String>();
		int i = layoutCosasEfectos.getChildCount();
		int k;
		for (int j = 0;; j++) {
			if (j >= i) {
				k = 0;
				if (k < -1 + lista.size()) {
					break;
				}
				return false;
			}
			lista.add(((Spinner) ((RelativeLayout) this.layoutCosasEfectos.getChildAt(j)).findViewById(R.id.desplegableStatsAsignarEfecto)).getSelectedItem().toString());
		}
		for (int m = k + 1;; m++) {
			if (m >= lista.size()) {
				k++;
				break;
			}
			if (((String) lista.get(k)).equals(lista.get(m))) {
				Mensaje("Estadistica duplicada: " + (String) lista.get(k));
				return true;
			}
		}
		return false;
	}

	@SuppressLint("InflateParams")
	public void CrearCosa() {
		RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(getBaseContext()).inflate(R.layout.layout_cosas_efectos, null, false);
		layoutCosasEfectos.addView(relativeLayout);
		Spinner spinnerStats = (Spinner) relativeLayout.findViewById(R.id.desplegableStatsAsignarEfecto);
		Spinner spinnerTipo = (Spinner) relativeLayout.findViewById(R.id.desplegableTipoMod);
		db = new AdaptadorBD(getBaseContext());
		
		ArrayList<String> listaStats = new ArrayList<String>();
		try {
			db.abrir();
			Cursor c = db.consulta("SELECT nombreStat FROM STATS ORDER BY nombreStat;", null);
			if (c.moveToFirst()) {
				do {
					listaStats.add(c.getString(0));
				} while (c.moveToNext());
			}
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.cerrar();
		}
		ArrayAdapter<String> adapterListaStats = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item,
				listaStats);
		adapterListaStats.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerStats.setAdapter(adapterListaStats);
		
		ArrayList<String> listaTipos = new ArrayList<String>();
		try {
			db.abrir();
			Cursor c = db.consulta("SELECT nombre FROM TIPOS ORDER BY nombre;", null);
			if (c.moveToFirst()) {
				do {
					listaTipos.add(c.getString(0));
				} while (c.moveToNext());
			}
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.cerrar();
		}
		ArrayAdapter<String> adapterListaTipos = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item,
				listaTipos);
		adapterListaTipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerTipo.setAdapter(adapterListaTipos);
		ImageButton btnQuitarLinea = (ImageButton) relativeLayout.findViewById(R.id.btnQuitarLinea);
		btnQuitarLinea.setOnClickListener(new View.OnClickListener() {
			
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
		ApagarTeclado();
	}

	
}