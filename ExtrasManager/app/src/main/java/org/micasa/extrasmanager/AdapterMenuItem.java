package org.micasa.extrasmanager;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class AdapterMenuItem extends BaseAdapter {
	private Activity activity;
	ArrayList<ObjetoMenu> arrayitms;

	public AdapterMenuItem(Activity activity, ArrayList<ObjetoMenu> listarry) {
		super();
		this.activity = activity;
		this.arrayitms = listarry;
	}

	public int getCount() {
		return arrayitms.size();
	}
	//Retorna objeto ObjetoMenu del array list
	public Object getItem(int position) {
		return arrayitms.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	//Declaramos clase estatica la cual representa a la fila
	public static class Fila {
		TextView titulo_itm;
		ImageView icono;
	}

	public View getView(int position, View convertView, ViewGroup parent){
		Fila view;
		LayoutInflater inflator = activity.getLayoutInflater();
		if (convertView == null) {
			view = new Fila();
			//Creo objeto item y lo obtengo del array
			ObjetoMenu itm = arrayitms.get(position);
			convertView = inflator.inflate(R.layout.item, null);
			//Titulo
			view.titulo_itm = (TextView) convertView.findViewById(R.id.title_item);
			//Seteo en el campo titulo el nombre correspondiente obtenido del objeto
			view.titulo_itm.setText(itm.getTexto());
			//Icono
			view.icono = (ImageView) convertView.findViewById(R.id.icon);
			//Seteo el icono
			view.icono.setImageResource(itm.getIcono());
			convertView.setTag(view);
		} else {
			view = (Fila) convertView.getTag();
		}
		
		return convertView;
  	}


}