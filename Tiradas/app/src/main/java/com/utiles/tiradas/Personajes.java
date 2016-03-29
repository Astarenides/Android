package com.utiles.tiradas;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Personajes extends Fragment {
	private Button btnAsignarClases;
	private Button btnAsignarEfectosPersonajes;
	private Button btnCrearPersonaje;
	private Button btnEditarPersonaje;
	private Button btnEquiparPersonaje;
	Intent intent;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setRetainInstance(true);
	}

	public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(R.layout.personajes, paramViewGroup, false);
		btnCrearPersonaje = (Button) localView.findViewById(R.id.btnCrearPersonaje);
		btnCrearPersonaje.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					Class<?> clase = Class.forName("com.utiles.tiradas.CrearPersonaje");
					intent = new Intent(getActivity(), clase);
					startActivity(intent);
					return;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

		this.btnEditarPersonaje = (Button) localView.findViewById(R.id.btnEditarPersonaje);
		this.btnEditarPersonaje.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				try {
					Class<?> clase = Class.forName("com.utiles.tiradas.EditarPersonaje");
					intent = new Intent(getActivity().getBaseContext(), clase);
					startActivity(intent);
					return;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		this.btnAsignarClases = (Button) localView.findViewById(R.id.btnClases);
		this.btnAsignarClases.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				try {
					Class<?> clase = Class.forName("com.utiles.tiradas.AsignarClases");
					intent = new Intent(getActivity().getBaseContext(), clase);
					startActivity(intent);
					return;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		this.btnEquiparPersonaje = ((Button) localView.findViewById(R.id.btnEquiparPersonaje));
		this.btnEquiparPersonaje.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				try {
					Class<?> clase = Class.forName("com.utiles.tiradas.EquiparPersonaje");
					intent = new Intent(getActivity().getBaseContext(), clase);
					startActivity(intent);
					return;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		this.btnAsignarEfectosPersonajes = (Button) localView.findViewById(R.id.btnAsignarEfectosPersonajes);
		this.btnAsignarEfectosPersonajes.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				try {
					Class<?> clase = Class.forName("com.utiles.tiradas.AsignarEfectos");
					intent = new Intent(getActivity().getBaseContext(), clase);
					startActivity(intent);
					return;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		return localView;
	}
}