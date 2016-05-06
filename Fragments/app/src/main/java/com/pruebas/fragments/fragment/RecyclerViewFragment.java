package com.pruebas.fragments.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pruebas.fragments.R;
import com.pruebas.fragments.pojo.Contacto;

import java.util.ArrayList;

/**
 * Created by fhidalgo on 05/05/2016.
 */
public class RecyclerViewFragment extends Fragment {

    private ArrayList<Contacto> contactos;
    private RecyclerView rvContactos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_recyclerview, container, false);

        rvContactos = (RecyclerView) v.findViewById(R.id.rvContactos);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvContactos.setLayoutManager(llm);

        inicializarDatos();
//        inicializarAdaptador();

        return v;
    }

    private void inicializarDatos() {
        contactos = new ArrayList<>();
        contactos.add(new Contacto("Fco. Javier Hidalgo", "987654321", "astarenides@hotmail.com"));
        contactos.add(new Contacto("Pedro Sanchez", "123456789", "pedrosanchez@hotmail.com"));
        contactos.add(new Contacto("Mireya Martinez", "123987456", "mireyamartinez@hotmail.com"));
        contactos.add(new Contacto("Juan Lopez", "444111111", "juanlopez@hotmail.com"));
    }

    /*public ContactoAdaptador adaptador;
    private void inicializarAdaptador() {
        adaptador = new ContactoAdaptador(contactos, getActivity());
        rvContactos.setAdapter(adaptador);
    }*/
}
