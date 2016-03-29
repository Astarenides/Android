package com.utiles.tiradas;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CrearEfecto extends Activity {
	private Button btnGuardar;
	AdaptadorBD db;
	private EditText txtDescripcion;
	private EditText txtNombre;

	public void VaciarCampos() {
		this.txtNombre.setText("");
		this.txtDescripcion.setText("");
	}

	public boolean errors() {
		if (this.txtNombre.getText().toString() == null) {
			return true;
		} else if (this.txtNombre.getText().toString().equals("")) {
			return true;
		} else {
			return false;			
		}
	}

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.crearefectos);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		txtNombre = ((EditText) findViewById(R.id.txtNombreEfecto));
		txtDescripcion = ((EditText) findViewById(R.id.txtDescipcionEfecto));
		btnGuardar = ((Button) findViewById(R.id.btnGuardarEfecto));
		btnGuardar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				if (errors()) {
					Toast.makeText(CrearEfecto.this.getBaseContext(),"Debe rellenar el nombre correctamente", Toast.LENGTH_SHORT).show();
					return;
				}
				String nombre = txtNombre.getText().toString();
				String especial = txtDescripcion.getText().toString();
				if ((especial != null) && (!txtDescripcion.getText() .toString().equals(""))) {
				}
				
				try {
					db = new AdaptadorBD(getBaseContext());
					db.abrir();
					db.insertar("INSERT INTO EFECTOS (nombre, especial) VALUES ('" + nombre + "', '" + especial + "');");
					Toast.makeText(CrearEfecto.this.getBaseContext(),"Efecto añadido con exito", Toast.LENGTH_SHORT).show();
					VaciarCampos();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					db.cerrar();
				}
				
			}
		});
	}
}