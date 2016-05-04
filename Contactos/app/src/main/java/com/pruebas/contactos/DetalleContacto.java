package com.pruebas.contactos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DetalleContacto extends AppCompatActivity {

    TextView txtNombre;
    TextView txtTelefono;
    TextView txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_contacto);

        Toolbar miActionBar = (Toolbar) findViewById(R.id.miActionBar);
        setSupportActionBar(miActionBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle parametros = getIntent().getExtras();

        String nombre = parametros.getString(getResources().getString(R.string.pnombre));       // Nombre
        String telefono = parametros.getString(getResources().getString(R.string.ptelefono));   // Telefono
        String email = parametros.getString(getResources().getString(R.string.pemail));         // Email

        txtNombre = (TextView) findViewById(R.id.txtNombre);
        txtTelefono = (TextView) findViewById(R.id.txtTelefono);
        txtEmail = (TextView) findViewById(R.id.txtEmail);

        txtNombre.setText(nombre);
        txtTelefono.setText(telefono);
        txtEmail.setText(email);

    }

    public void llamar(View v) {
        String telefono = txtTelefono.getText().toString();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telefono)));
    }

    public void enviarMail (View v) {
        String correo = txtEmail.getText().toString();
        Log.i("email", correo);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{correo});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Asuntillo");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Texto del mensaje");
        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent, "Email "));
    }
}
