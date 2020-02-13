package br.com.alura.ceep.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Nota implements Parcelable{

	public static final Creator<Nota> CREATOR = new Creator<Nota>(){
		@Override
		public Nota createFromParcel(Parcel in){
			return new Nota(in);
		}

		@Override
		public Nota[] newArray(int size){
			return new Nota[size];
		}
	};
	private final String titulo;
	private final String descricao;

	public Nota(String titulo, String descricao){
		this.titulo = titulo;
		this.descricao = descricao;
	}


	private Nota(Parcel in){
		titulo = in.readString();
		descricao = in.readString();
	}

	public String getTitulo(){
		return titulo;
	}

	public String getDescricao(){
		return descricao;
	}

	@Override
	public int describeContents(){
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i){
		parcel.writeString(titulo);
		parcel.writeString(descricao);
	}

	public boolean valida(){
		return !preenchimentoInvalido();
	}

	private boolean preenchimentoInvalido(){
		return camposVazios() || camposComEspaco();
	}

	private boolean camposComEspaco(){
		return getTitulo().trim().isEmpty() && getDescricao().trim().isEmpty();
	}

	private boolean camposVazios(){
		return getTitulo().isEmpty() && getDescricao().isEmpty();
	}


}