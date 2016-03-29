package com.utiles.tiradas;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Inicio extends Fragment {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	public View onCreateView(LayoutInflater inflator,ViewGroup parent, Bundle savedInstanceState) {
		View rootView = inflator.inflate(R.layout.inicio, parent, false);
		return rootView;
	}
}