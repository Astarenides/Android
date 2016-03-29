package com.utiles.tiradas;

public class EfectosPersonaje {
	protected int idStat;
	protected int idTipo;
	protected int valor;

	public EfectosPersonaje(int idStat, int valor) {
		this.idStat = idStat;
		this.idTipo = 1;
		this.valor = valor;
	}

	public EfectosPersonaje(int idStat, int idTipo, int valor) {
		this.idStat = idStat;
		this.idTipo = idTipo;
		this.valor = valor;
	}

	public int getStat() {
		return this.idStat;
	}

	public int getTipo() {
		return this.idTipo;
	}

	public int getValor() {
		return this.valor;
	}
}