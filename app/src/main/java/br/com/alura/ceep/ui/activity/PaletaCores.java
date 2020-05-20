package br.com.alura.ceep.ui.activity;

public enum PaletaCores {
	AZUL("#408EC9"),
	BRANCO("#FFFFFF"),
	VERMELHO("#EC2F4B"),
	VERDE("#9ACD32"),
	AMARELO("#F9F256"),
	LILAS("#F1CBFF"),
	CINZA("#D2D4DC"),
	MARROM("#A47C48"),
	ROXO("#BE29EC");

	public String hex;

	PaletaCores(String valor) {
		this.hex = valor;
	}
}
