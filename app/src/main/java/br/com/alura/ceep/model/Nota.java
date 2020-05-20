package br.com.alura.ceep.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Nota implements Parcelable {

	public static final Creator<Nota> CREATOR = new Creator<Nota>() {
		@Override
		public Nota createFromParcel(Parcel in) {
			return new Nota(in);
		}

		@Override
		public Nota[] newArray(int size) {
			return new Nota[size];
		}
	};

	private static final String BRANCO = "#FFFFFF";
	private String titulo;
	private String descricao;
	private String cor;

	private Nota(Parcel in) {
		titulo = in.readString();
		descricao = in.readString();
		cor = in.readString();
	}

	public Nota() {
		this.cor = BRANCO;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(titulo);
		parcel.writeString(descricao);
		parcel.writeString(cor);
	}

	public boolean valida() {
		return !preenchimentoInvalido();
	}

	private boolean preenchimentoInvalido() {
		return camposVazios() || camposComEspaco();
	}

	private boolean camposComEspaco() {
		return getTitulo().trim().isEmpty() && getDescricao().trim().isEmpty();
	}

	private boolean camposVazios() {
		return getTitulo().isEmpty() && getDescricao().isEmpty();
	}

}