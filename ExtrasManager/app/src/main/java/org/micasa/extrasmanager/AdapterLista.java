package org.micasa.extrasmanager;

import android.app.Activity;
import android.database.Cursor;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Astarenides on 03/10/2015.
 */
public class AdapterLista extends BaseAdapter {

    private Activity activity;
    ArrayList<ObjetoLista> arrayitms;
    AdaptadorBD db;

    public AdapterLista(Activity activity, ArrayList<ObjetoLista> listarry) {
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
        TextView fecha;
        TextView descripcion;
        TextView notas;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Fila view;
        LayoutInflater inflator = activity.getLayoutInflater();
        final ObjetoLista itm = arrayitms.get(position);
        if (convertView == null) {
            view = new Fila();
            //Creo objeto item y lo obtengo del array

            convertView = inflator.inflate(R.layout.row2, null);
            //Fecha y seteo de fecha
            view.fecha = (TextView) convertView.findViewById(R.id.tituloFila);
            view.descripcion = (TextView) convertView.findViewById(R.id.descripcionFila);
            view.notas = (TextView) convertView.findViewById(R.id.txtNotas);
            convertView.setTag(view);

        } else {
            view = (Fila) convertView.getTag();
        }

        view.fecha.setText(itm.getFecha());
        view.descripcion.setText(itm.getDescripcion());
        view.notas.setText(itm.getNotas());

        return convertView;
    }
}
