package com.utiles.tiradas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

@SuppressLint("InflateParams")
public class AdapterMenuItem extends BaseAdapter {
	private Activity activity;
	ArrayList<ObjetoMenu> items;

	public AdapterMenuItem(Activity activity, ArrayList<ObjetoMenu> listarry) {
		this.activity = activity;
		this.items = listarry;
	}

	public int getCount() {
		return items.size();
	}

	public Object getItem(int position) {
		return items.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent){
	    Fila view;
		LayoutInflater inflator = activity.getLayoutInflater();
		if (convertView == null) {
			view = new Fila();
			//Creo objeto item y lo obtengo del array
			ObjetoMenu localObjetoMenu = (ObjetoMenu)this.items.get(position);
			convertView = inflator.inflate(R.layout.menusdesplegable, null);
			//Titulo
			view.textoMenu = (TextView) convertView.findViewById(R.id.textoMenu);
			view.textoMenu.setText(localObjetoMenu.getTexto());
			view.iconoMenu = (ImageView) convertView.findViewById(R.id.iconoMenu);
			view.iconoMenu.setImageResource(localObjetoMenu.getIcono());
			convertView.setTag(view);
		} else {
			view = (Fila) convertView.getTag();
		}
		
		return convertView;
  }

	public static class Fila {
		ImageView iconoMenu;
		TextView textoMenu;
	}
}