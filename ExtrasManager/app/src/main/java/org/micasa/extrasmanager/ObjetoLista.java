package org.micasa.extrasmanager;

import android.widget.ImageButton;

public class ObjetoLista {
	protected String fecha;
	protected String descripcion;
	protected String notas;

	public ObjetoLista(String fecha, String info, String notas) {
		this.fecha = fecha;
		this.descripcion = info;
		this.notas = notas;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public String getFecha() {
		return this.fecha;
	}

	public String getNotas() {
		return this.notas;
	}

	public void setDescripcion(String info) {
		this.descripcion = info;
	}

	public void setFecha(String fecha) {
		this.fecha= fecha;
	}

	public void setNotas (String notas) {
		this.notas = notas;
	}
}