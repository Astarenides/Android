package com.pruebas.botonatrasactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView txtNombre = (TextView) findViewById(R.id.label1);
        registerForContextMenu(txtNombre);
    }

    public void irSegundaActivity(View v) {
        Intent intent = new Intent(this, SegundoActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuopciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.mAbout:
                Intent intentAbout = new Intent(this, ActivityAbout.class);
                startActivity(intentAbout);
                break;
            case R.id.mSettings:
                Intent intentSettings = new Intent(this, ActivitySettings.class);
                startActivity(intentSettings);
                break;
            case R.id.mRefresh:
                Toast.makeText(getBaseContext(), getResources().getString(R.string.menu_refresh), Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menucontexto, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mDelete:
                Toast.makeText(getBaseContext(), getResources().getString(R.string.menu_context_delete), Toast.LENGTH_SHORT).show();
                break;
            case R.id.mEdit:
                Toast.makeText(getBaseContext(), getResources().getString(R.string.menu_context_edit), Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onContextItemSelected(item);
    }

    public void showMenuPopup(View v) {
        PopupMenu menuPopup = new PopupMenu(getBaseContext(), v);

        menuPopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mView:
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.menu_popup_verimagen), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.mDetail:
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.menu_popup_detalles), Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        menuPopup.getMenuInflater().inflate(R.menu.menupopup, menuPopup.getMenu());
        menuPopup.show();
    }
}
