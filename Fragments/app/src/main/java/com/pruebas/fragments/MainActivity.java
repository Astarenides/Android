package com.pruebas.fragments;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.pruebas.fragments.adapter.PageAdapter;
import com.pruebas.fragments.fragment.PerfilFragment;
import com.pruebas.fragments.fragment.RecyclerViewFragment;
import com.pruebas.fragments.pojo.Contacto;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Contacto> contactos;
    private RecyclerView rvContactos;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        setUpViewPager();

        /*
        rvContactos = (RecyclerView) findViewById(R.id.rvContactos);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvContactos.setLayoutManager(llm);

        inicializarDatos();
        inicializarAdaptador();
        */

        if(toolbar != null){
            setSupportActionBar(toolbar);
        }

    }

    private ArrayList<Fragment> agregarFragments(){
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new RecyclerViewFragment());
        fragments.add(new PerfilFragment());
        return fragments;
    }

    private void setUpViewPager() {
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(), agregarFragments()));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void inicializarDatos() {
        contactos = new ArrayList<>();
        contactos.add(new Contacto("Fco. Javier Hidalgo", "987654321", "astarenides@hotmail.com"));
        contactos.add(new Contacto("Pedro Sanchez", "123456789", "pedrosanchez@hotmail.com"));
        contactos.add(new Contacto("Mireya Martinez", "123987456", "mireyamartinez@hotmail.com"));
        contactos.add(new Contacto("Juan Lopez", "444111111", "juanlopez@hotmail.com"));
    }

    public ContactoAdaptador adaptador;
    private void inicializarAdaptador() {
        adaptador = new ContactoAdaptador(contactos, this);
        rvContactos.setAdapter(adaptador);
    }
}
