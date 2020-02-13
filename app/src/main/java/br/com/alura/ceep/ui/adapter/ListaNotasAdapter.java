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
import br.com.alura.ceep.ui.listener.NotasClickListener;

public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.NotasViewHolder>{

	private final List<Nota> notas;
	private final Context contexto;
	private final NotaDAO dao;
	private NotasClickListener onItemClickListener;

	public ListaNotasAdapter(Context contexto){
		this.dao = new NotaDAO();
		this.contexto = contexto;
		this.notas = dao.todos();
	}

	@NonNull
	@Override
	public NotasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
		View item = LayoutInflater.from(contexto).inflate(R.layout.item_nota, parent, false);
		return new NotasViewHolder(item);
	}

	@Override
	public void onBindViewHolder(@NonNull NotasViewHolder holder, int posicao){
		holder.vincula(notas.get(posicao));
	}

	@Override
	public int getItemCount(){
		return notas.size();
	}

	public void adiciona(Nota nota){
		dao.insere(nota);
		notas.add(nota);
		notifyDataSetChanged();
	}

	public void edita(Nota nota, int posicao){
		dao.altera(posicao, nota);
		notas.set(posicao, nota);
		notifyItemChanged(posicao);
	}

	public void setOnItemClickListener(NotasClickListener onItemClickListener){
		this.onItemClickListener = onItemClickListener;
	}

	class NotasViewHolder extends RecyclerView.ViewHolder{

		private final TextView campoDescricao;
		private final TextView campoTitulo;
		private Nota nota;

		NotasViewHolder(@NonNull final View itemView){
			super(itemView);

			campoDescricao = itemView.findViewById(R.id.item_notas_descricao);
			campoTitulo = itemView.findViewById(R.id.item_notas_titulo);

			itemView.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View view){
					onItemClickListener.onItemCLick(nota, getAdapterPosition());
				}
			});
		}

		void vincula(Nota nota){
			this.nota = nota;
			defineCampos(nota);
		}

		private void defineCampos(Nota nota){
			defineTitulo(nota);
			defineDescricao(nota);
		}

		private void defineDescricao(Nota nota){
			campoDescricao.setText(nota.getDescricao());
		}

		private void defineTitulo(Nota nota){
			campoTitulo.setText(nota.getTitulo());
		}
	}
}
