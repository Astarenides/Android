package org.micasa.extrasmanager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class MainActivity extends FragmentActivity {

    private String[] titulos;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout navDrawerLayout;
    private RelativeLayout navContent;
    private ListView navList;
    private ArrayList<ObjetoMenu> navItems;
    private TypedArray navIcons;
    private AdapterMenuItem navAdapter;
    private boolean doubleBackToExitPressedOnce = false;
    int currentAPI = Build.VERSION.SDK_INT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Drawer Layout
        navDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //Contenedor de lista y footer
        navContent = (RelativeLayout) findViewById(R.id.content);
        //Lista
        navList = (ListView) findViewById(R.id.lista);
        //Declaramos el header del cual sera el layout de header.xml
        View header = getLayoutInflater().inflate(R.layout.header, null, false);
        //Establecemos header
        navList.addHeaderView(header);
        //Tomamos listado de imgs desde drawable
        navIcons = getResources().obtainTypedArray(R.array.navigation_iconos);
        //Tomamos listado de titulos desde el string-array de los recursos @string/nav_options
        titulos = getResources().getStringArray(R.array.nav_options);
        //Listado de titulos de barra de navegacion
        navItems = new ArrayList<ObjetoMenu>();
        //Agregamos obtetos ObjetoMenu al array
        //Horas extra
        navItems.add(new ObjetoMenu(titulos[0], navIcons.getResourceId(0, -1)));
        //Botes
        navItems.add(new ObjetoMenu(titulos[1], navIcons.getResourceId(1, -1)));
        //Declaramos y seteamos nuestro adaptadoral cual le pasamos el array con los titulos
        navAdapter = new AdapterMenuItem(this,navItems);
        navList.setAdapter(navAdapter);

        //Establecemos accion al clickear sobre cualquier item del menu de la misma forma
        //que hariamos en una app comun con un listview
        navList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id){
                MostrarFragment(position);
            }
        });

        ((RelativeLayout) findViewById(R.id.footer)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostrarFragment(3);
            }
        });

        try {
            File localFile1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/data/org.micasa.extrasmanager/databases");
            localFile1.mkdirs();
            File localFile2 = new File(localFile1, "extras");
            if (!localFile2.exists()) {
                CopyDB(getBaseContext().getAssets().open("extras"), new FileOutputStream(localFile2));
            }
        } catch (FileNotFoundException localFileNotFoundException) {
            localFileNotFoundException.printStackTrace();
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
        }
        if (null== savedInstanceState) MostrarFragment(1);

        Fragment f = getFragmentManager().findFragmentByTag("HORAS");
        if (f != null && f.isVisible()) Toast.makeText(getBaseContext(), "kuku", Toast.LENGTH_SHORT).show();

    }

    protected void onResume() {
        super.onResume();
        this.doubleBackToExitPressedOnce = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void openDrawer(){
        if(navDrawerLayout.isDrawerOpen(navContent)) {
            navDrawerLayout.closeDrawer(navContent);
        } else {
            navDrawerLayout.openDrawer(navContent);
        }
    }

    private void MostrarFragment(int position) {
        //update the main content by replacing fragments
        Fragment fragment = null;
        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        String TAG = "";
        switch(position){
            case 0:
                break;
            case 1:
                fragment = new HorasExtra();
                TAG = "HORAS";
                if (currentAPI >= Build.VERSION_CODES.LOLLIPOP) getWindow().setStatusBarColor(0xFF1E88E5);
                break;
            case 2:
                fragment = new Botes();
                TAG = "BOTES";
                if (currentAPI >= Build.VERSION_CODES.LOLLIPOP) window.setStatusBarColor(0xFFE53935);
                break;
            case 3:
                fragment = new Opciones();
                TAG = "OPCIONES";
                if (currentAPI >= Build.VERSION_CODES.LOLLIPOP) window.setStatusBarColor(0xFF43A047);
                break;
            default:
                //si no esta la opcion mostrara un toast y nos mandara a Horas Extra
                Toast.makeText(getApplicationContext(), "Opcion " + titulos[position-1] + " no disponible", Toast.LENGTH_SHORT).show();
                fragment = new HorasExtra();
                position=1;
                break;
        }
        //validamos si el fragment no es nulo
        if (fragment != null){
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment,TAG).commit();

            //Actualizamos el contenido segun la opcion elegida
            navList.setItemChecked(position, true);
            navList.setSelection(position);
            //Cerramos el menu deslizable
            navDrawerLayout.closeDrawer(navContent);
        } else {
            //si el fragment es nulo mostramos un mensaje de error
            Log.e("Error ", "MostrarFragment" + position);
        }
    }

    public void onBackPressed() {
        if (this.doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Pulsa atras de nuevo para salir", Toast.LENGTH_SHORT).show();
    }

    public void CopyDB(InputStream paramInputStream, OutputStream paramOutputStream) throws IOException {
        // ---copy 1K bytes at a time---
        byte[] buffer = new byte[1024];
        int length;
        while ((length = paramInputStream.read(buffer)) > 0) {
            paramOutputStream.write(buffer, 0, length);
        }
        paramInputStream.close();
        paramOutputStream.close();
    }
}
