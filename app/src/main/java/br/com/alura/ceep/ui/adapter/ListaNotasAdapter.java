package br.com.alura.ceep.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.alura.ceep.R;
import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;

public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.ListaNotasViewHolder>{

	private final List<Nota> notas;
	private final Context contexto;
	private final NotaDAO dao;

	public ListaNotasAdapter(Context contexto){
		this.dao = new NotaDAO();
		this.contexto = contexto;
		this.notas = dao.todos();
	}

	@NonNull
	@Override
	public ListaNotasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
		View item = LayoutInflater.from(contexto).inflate(R.layout.item_nota, parent, false);
		return new ListaNotasViewHolder(item);
	}

	@Override
	public void onBindViewHolder(@NonNull ListaNotasViewHolder holder, int posicao){
		holder.vincula(holder.itemView, posicao);
	}

	@Override
	public int getItemCount(){
		return notas.size();
	}

	public void adiciona(Nota nota){
		notas.add(nota);
		dao.insere(nota);
		notifyDataSetChanged();
	}

	class ListaNotasViewHolder extends RecyclerView.ViewHolder{

		ListaNotasViewHolder(@NonNull View itemView){
			super(itemView);
		}

		void vincula(View view, int posicao){
			defineTitulo(view, posicao);
			defineDescricao(view, posicao);
		}

		private void defineDescricao(View view, int posicao){
			final TextView campoDescricao = view.findViewById(R.id.item_notas_descricao);
			campoDescricao.setText(notas.get(posicao).getDescricao());
		}

		private void defineTitulo(View view, int posicao){
			final TextView campoTitulo = view.findViewById(R.id.item_notas_titulo);
			campoTitulo.setText(notas.get(posicao).getTitulo());
		}
	}
}
