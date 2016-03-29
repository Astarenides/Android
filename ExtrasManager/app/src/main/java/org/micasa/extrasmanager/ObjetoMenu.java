package org.micasa.extrasmanager;

public class ObjetoMenu {
	protected int icono;
	protected String texto;

	public ObjetoMenu(String title, int icon) {
		this.texto = title;
		this.icono = icon;
	}

	public int getIcono() {
		return this.icono;
	}

	public String getTexto() {
		return this.texto;
	}

	public void setIcono(int icono) {
		this.icono = icono;
	}

	public void setTexto(String titulo) {
		this.texto = titulo;
	}
}