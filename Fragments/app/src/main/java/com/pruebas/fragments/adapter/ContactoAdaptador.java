package com.pruebas.fragments.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pruebas.fragments.DetalleContacto;
import com.pruebas.fragments.R;
import com.pruebas.fragments.db.ConstructorContactos;
import com.pruebas.fragments.pojo.Contacto;

import java.util.ArrayList;

/**
 * Created by fhidalgo on 09/05/2016.
 */
public class ContactoAdaptador extends RecyclerView.Adapter<ContactoAdaptador.ContactoViewHolder>{

    private ArrayList<Contacto> contactos;
    private Activity activity;

    public ContactoAdaptador(ArrayList<Contacto> contactos, Activity activity){
        ConstructorContactos constructor = new ConstructorContactos(activity.getBaseContext());
        this.contactos = constructor.obtenerDatos();
        this.activity = activity;
    }

    @Override
    public ContactoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_contacto, parent, false);
        return new ContactoViewHolder(v);
    }

    @Override
<<<<<<< HEAD
    public void onBindViewHolder(ContactoViewHolder holder, final int position) {
=======
    public void onBindViewHolder(final ContactoViewHolder holder, int position) {
>>>>>>> 19b9e7965d1ee3d521f397f293262ab7e7a6649a
        final Contacto contacto = contactos.get(position);
        holder.txtNombre.setText(contacto.getNombre());
        holder.txtTelefono.setText(contacto.getTelefono());
        holder.txtLikes.setText(String.valueOf(contacto.getLikes()) + " Likes");

        holder.txtNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (activity, DetalleContacto.class);
                intent.putExtra("nombre", contacto.getNombre());
                intent.putExtra("telefono", contacto.getTelefono());
                intent.putExtra("email", contacto.getEmail());
                activity.startActivity(intent);
            }
        });

        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Diste like a " + contacto.getNombre(), Toast.LENGTH_SHORT).show();
<<<<<<< HEAD
                contacto.setLikes(contacto.getLikes()+1);
                notifyItemChanged(position);
=======
                ConstructorContactos constructorContactos = new ConstructorContactos(activity);
                constructorContactos.darLikeContacto(contacto);
                holder.txtLikes.setText(constructorContactos.obtenerLikesContacto(contacto));
>>>>>>> 19b9e7965d1ee3d521f397f293262ab7e7a6649a
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
