package br.com.alura.ceep.ui.adapter.holder;

import android.view.View;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.alura.ceep.R;
import br.com.alura.ceep.model.Nota;

public class ListaNotasViewHolder extends RecyclerView.ViewHolder{

	private final List<Nota> lista;

	public ListaNotasViewHolder(@NonNull View itemView, List<Nota> lista){
		super(itemView);
		this.lista = lista;
	}

	public void vincula(View view, int posicao){
		defineTitulo(view, posicao);
		defineDescricao(view, posicao);
	}

	private void defineDescricao(View view, int posicao){
		final TextView campoDescricao = view.findViewById(R.id.item_notas_descricao);
		campoDescricao.setText(lista.get(posicao).getDescricao());
	}

	private void defineTitulo(View view, int posicao){
		final TextView campoTitulo = view.findViewById(R.id.item_notas_titulo);
		campoTitulo.setText(lista.get(posicao).getTitulo());
	}
}
