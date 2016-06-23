package com.anncode.aplicacioncontactos.vista.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anncode.recyclerviewfragments.R;
import com.anncode.aplicacioncontactos.adapter.ContactoAdaptador;
import com.anncode.aplicacioncontactos.pojo.Contacto;
import com.anncode.aplicacioncontactos.presentador.IRecylerViewFragmentPresenter;
import com.anncode.aplicacioncontactos.presentador.RecyclerViewFragmentPresenter;

import java.util.ArrayList;

public class RecyclerViewFragment extends Fragment implements IRecyclerViewFragmentView {
    private ArrayList<Contacto> contactos;
    private RecyclerView rvContactos;
    private IRecylerViewFragmentPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recyclerview, container, false);

        rvContactos = (RecyclerView) v.findViewById(R.id.rvContactos);
        presenter = new RecyclerViewFragmentPresenter(this, getContext());
        return v;
    }

    @Override
    public void generarGridLayout() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rvContactos.setLayoutManager(gridLayoutManager);
    }

    @Override
    public ContactoAdaptador crearAdaptador(ArrayList<Contacto> contactos) {
        return new ContactoAdaptador(contactos, getActivity()  );
    }

    @Override
    public void inicializarAdaptadorRV(ContactoAdaptador adaptador) {
        rvContactos.setAdapter(adaptador);
    }
}
