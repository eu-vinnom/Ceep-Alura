package br.com.alura.ceep.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
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
	@PrimaryKey(autoGenerate = true)
	private long id = 0;
	private String titulo;
	private String descricao;
	private String cor;
	private int posicao;

	private Nota(Parcel in) {
		titulo = in.readString();
		descricao = in.readString();
		cor = in.readString();
		id = in.readLong();
		posicao = in.readInt();
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getPosicao() {
		return posicao;
	}

	public void setPosicao(int posicao) {
		this.posicao = posicao;
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
		parcel.writeLong(id);
		parcel.writeInt(posicao);
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