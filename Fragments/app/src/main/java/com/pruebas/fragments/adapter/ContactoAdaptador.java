package com.pruebas.fragments.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pruebas.fragments.R;
import com.pruebas.fragments.pojo.Contacto;

import java.util.ArrayList;

/**
 * Created by fhidalgo on 09/05/2016.
 */
public class ContactoAdaptador extends RecyclerView.Adapter{

    ContactoViewHolder contactoViewHolder;
    ArrayList<Contacto> contactos;
    Activity activity;

    public ContactoAdaptador(ArrayList<Contacto> contactos, Activity activity){
        this.contactos = contactos;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_contacto, parent);
        return new ContactoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Contacto contacto = contactos.get(position);
        contactoViewHolder.txtNombre.setText(contacto.getNombre());
        contactoViewHolder.txtTelefono.setText(contacto.getTelefono());
        contactoViewHolder.txtLikes.setText(String.valueOf(contacto.getLikes()) + " Likes");
        contactoViewHolder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Diste like a " + contacto.getNombre(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() { return contactos.size(); }

    public static class ContactoViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNombre;
        private TextView txtTelefono;
        private ImageButton btnLike;
        private TextView txtLikes;

        public ContactoViewHolder(View itemView) {
            super(itemView);

            txtNombre   = (TextView) itemView.findViewById(R.id.tvNombreCV);
            txtTelefono = (TextView) itemView.findViewById(R.id.tvTelefonoCV);
            btnLike     = (ImageButton) itemView.findViewById(R.id.btnLike);
            txtLikes    = (TextView) itemView.findViewById(R.id.tvLikes);
        }
    }
}
