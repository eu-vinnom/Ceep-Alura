package br.com.alura.ceep.ui.activity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.ui.activity.PaletaCores;

import static br.com.alura.ceep.R.id.botao_cor;

public class PaletaCoresAdapter extends RecyclerView.Adapter<PaletaCoresAdapter.CoresViewHolder> {

	private final Context contexto;
	private List<String> paleta;
	private AlteraCorClickListener onCorClickListener;

	public PaletaCoresAdapter(Context contexto) {
		this.contexto = contexto;
		this.paleta = new ArrayList<>();
		adicionaCoresNaPaleta();
	}

	@NonNull
	@Override
	public CoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View item = LayoutInflater.from(contexto).inflate(R.layout.circulo_cor, parent, false);
		return new CoresViewHolder(item);
	}

	@Override
	public void onBindViewHolder(@NonNull CoresViewHolder holder, int posicao) {
		holder.pinta(paleta.get(posicao));
	}

	@Override
	public int getItemCount() {
		return paleta.size();
	}

	public void setOnCorClickListener(AlteraCorClickListener onCorClickListener) {
		this.onCorClickListener = onCorClickListener;
	}

	private void adicionaCoresNaPaleta() {
		for(PaletaCores cor : PaletaCores.values()) {
			paleta.add(cor.hex);
		}
	}

	public interface AlteraCorClickListener {
		void onClickListener(String cor);
	}

	class CoresViewHolder extends RecyclerView.ViewHolder {

		private final ImageView botaoCor;
		private String cor;

		CoresViewHolder(@NonNull View itemView) {
			super(itemView);
			botaoCor = itemView.findViewById(botao_cor);
			configuraListenerCores(itemView);
		}

		private void configuraListenerCores(@NonNull View itemView) {
			itemView.setOnClickListener(view -> onCorClickListener.onClickListener(cor));
		}

		void pinta(String cor) {
			this.cor = cor;
			botaoCor.setColorFilter(Color.parseColor(cor));
		}
	}
}
