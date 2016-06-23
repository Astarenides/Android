package com.anncode.aplicacioncontactos.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anncode.aplicacioncontactos.pojo.Contacto;
import com.anncode.aplicacioncontactos.DetalleContacto;
import com.anncode.recyclerviewfragments.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class ContactoAdaptador extends RecyclerView.Adapter<ContactoAdaptador.ContactoViewHolder>{

    ArrayList<Contacto> contactos;
    Activity activity;

    public ContactoAdaptador(ArrayList<Contacto> contactos, Activity activity) {
        this.contactos = contactos;
        this.activity = activity;
    }

    @Override
    public ContactoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_grid_contacto, parent, false);
        return new ContactoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ContactoViewHolder contactoViewHolder, int position) {
        final Contacto contacto = contactos.get(position);
        Picasso.with(activity)
                .load(contacto.getUrlFoto())
                .placeholder(R.drawable.shock_rave_bonus_icon)
                .into(contactoViewHolder.imgFoto);
        contactoViewHolder.tvLikes.setText(String.valueOf(contacto.getLikes()));
        
        contactoViewHolder.imgFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, DetalleContacto.class);
                intent.putExtra("url", contacto.getUrlFoto());
                intent.putExtra("like", contacto.getLikes());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactos.size();
    }

    public static class ContactoViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgFoto;
        private TextView tvLikes;

        public ContactoViewHolder(View itemView) {
            super(itemView);

            imgFoto     = (ImageView) itemView.findViewById(R.id.imgFoto);
            tvLikes     = (TextView) itemView.findViewById(R.id.tvLikes);

        }
    }
}
