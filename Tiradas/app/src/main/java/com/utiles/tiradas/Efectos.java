package com.utiles.tiradas;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Efectos extends Fragment {
	private Button btnAsignarStats;
	private Button btnCrearEfecto;
	private Button btnEditarEfecto;
	Intent intent;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
		View rootView = paramLayoutInflater.inflate(R.layout.efectos, paramViewGroup, false);
		
		btnCrearEfecto = (Button) rootView.findViewById(R.id.btnCrearEfecto);
		btnCrearEfecto.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				try {
					Class<?> clase = Class.forName("com.utiles.tiradas.CrearEfecto");
					intent = new Intent(getActivity(),clase);
					startActivity(intent);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		btnEditarEfecto = (Button) rootView.findViewById(R.id.btnEditarEfecto);
		btnEditarEfecto.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				try {
					Class<?> clase = Class.forName("com.utiles.tiradas.EditarEfecto");
					intent = new Intent(getActivity(),clase);
					startActivity(intent);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		btnAsignarStats = (Button) rootView.findViewById(R.id.btnAsignarStats);
		btnAsignarStats.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				try {
					Class<?> clase = Class.forName("com.utiles.tiradas.AsignarStats");
					intent = new Intent(getActivity(),clase);
					startActivity(intent);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		return rootView;
	}
}