package com.utiles.tiradas;

public class ObjetoMenu {
	protected int icono;
	protected String texto;

	public ObjetoMenu(String paramString, int paramInt) {
		this.texto = paramString;
		this.icono = paramInt;
	}

	public int getIcono() {
		return this.icono;
	}

	public String getTexto() {
		return this.texto;
	}

	public void setIcono(int paramInt) {
		this.icono = paramInt;
	}

	public void setTexto(String paramString) {
		this.texto = paramString;
	}
}