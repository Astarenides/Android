package com.pruebas.activarbluetooth;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int CODIGO_SOLICITUD_PERMISO = 1;
    private static final int CODIGO_SOLICITUD_HABILITAR_BLUETOOTH = 0;
    private Context context;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        activity = this;
    }

    public void habilitarBluetooth (View v) {

        solicitarPermiso();

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null){
            Toast.makeText(MainActivity.this, "El dispositivo no tiene bluetooth", Toast.LENGTH_SHORT).show();
        }

        if (!bluetoothAdapter.isEnabled()){
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, CODIGO_SOLICITUD_HABILITAR_BLUETOOTH);
        }

    }

    public boolean comprobarEstadoPermiso () {
        int resultado = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH);
        return resultado == PackageManager.PERMISSION_GRANTED;
    }

    public void solicitarPermiso() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.BLUETOOTH)){
            Toast.makeText(MainActivity.this, "El permiso ya esta otorgado", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.BLUETOOTH}, CODIGO_SOLICITUD_PERMISO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CODIGO_SOLICITUD_PERMISO:
                if (comprobarEstadoPermiso()) {
                    Toast.makeText(MainActivity.this, "El permiso SI esta otorgado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "El permiso NO esta otorgado", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                Toast.makeText(MainActivity.this, "Ningún permiso otorgado", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
