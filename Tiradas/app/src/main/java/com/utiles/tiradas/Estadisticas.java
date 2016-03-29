package com.utiles.tiradas;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Estadisticas extends Fragment {
	
	Button btnEstadisticasPersonaje;
	Button btnTablaGeneral;
	Intent intent;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.estadisticas, parent, false);
		
		btnEstadisticasPersonaje = (Button) rootView.findViewById(R.id.btnEstadisticasPersonaje);
		btnEstadisticasPersonaje.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					Class<?> clase = Class.forName("com.utiles.tiradas.EstadisticasPersonaje");
					intent = new Intent(getActivity(), clase);
					startActivity(intent);
					return;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		
		btnTablaGeneral = (Button) rootView.findViewById(R.id.btnTablaGeneral);
		btnTablaGeneral.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					Class<?> clase = Class.forName("com.utiles.tiradas.TablaGeneral");
					intent = new Intent(getActivity(), clase);
					startActivity(intent);
					return;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		
		return rootView;
	}
}