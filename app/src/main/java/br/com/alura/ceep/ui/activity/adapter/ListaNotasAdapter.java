package br.com.alura.ceep.ui.activity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.asynctask.BaseAsyncTask;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.room.CeepBD;
import br.com.alura.ceep.room.dao.NotaDAO;

public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.NotasViewHolder> {

	private final List<Nota> notas;
	private final Context contexto;
	private final NotaDAO dao;
	private NotasClickListener onItemClickListener;

	public ListaNotasAdapter(Context contexto) {
		this.dao = CeepBD.getInstance(contexto).getNotaDao();
		this.contexto = contexto;
		this.notas = new ArrayList<>();
	}

	@NonNull
	@Override
	public NotasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View item = LayoutInflater.from(contexto).inflate(R.layout.item_nota, parent, false);
		return new NotasViewHolder(item);
	}

	@Override
	public void onBindViewHolder(@NonNull NotasViewHolder holder, int posicao) {
		holder.vincula(notas.get(posicao), holder.itemView);
	}

	@Override
	public int getItemCount() {
		return notas.size();
	}

	public void adiciona(Nota nota) {
		new BaseAsyncTask<>(
			() -> insereNotaNaLista(nota),
			this::insereNotaNoDB,
			idNotas -> defineId(idNotas[0])).execute();
	}

	public void edita(Nota nota) {
		new BaseAsyncTask<>(
			getVoidPreparaCallback(),
			() -> alteraNotaNoDB(nota),
			resultado -> alteraNotaNaLista(nota)).execute();
	}

	public void remove(int posicao) {
		Nota notaRemovida = notas.get(posicao);
		new BaseAsyncTask<>(
			() -> atualizaPosicaoNotasDepoisDaRemovida(posicao),
			() -> removeNotaNoDB(notaRemovida),
			resultado -> removeNotaNaLista(posicao, notaRemovida)).execute();

	}

	public void troca(int posicaoInicial, int posicaoFinal) {
		new BaseAsyncTask<>(
			() -> atualizaPosicaoNotasNaLista(posicaoInicial, posicaoFinal),
			this::atualizaPosicaoNotasNoDB,
			resultado -> notifyItemMoved(posicaoInicial, posicaoFinal)).execute();
	}

	public void recuperaNotas() {
		new BaseAsyncTask<>(
			getVoidPreparaCallback(),
			dao::todas,
			this::regeneraLista).execute();
	}

	private BaseAsyncTask.PreparaCallback getVoidPreparaCallback() {
		return () -> {};
	}

	private void insereNotaNaLista(Nota nota) {
		notas.add(0, nota);

		for(Nota existente : notas) {
			existente.setPosicao(notas.indexOf(existente));
		}
	}

	private long[] insereNotaNoDB() {
		return dao.insere(notas.toArray(new Nota[0]));
	}

	private void defineId(long idNota) {
		notas.get(0).setId(idNota);
		notifyDataSetChanged();
	}

	private Void alteraNotaNoDB(Nota nota) {
		return dao.altera(nota);
	}

	private void alteraNotaNaLista(Nota nota) {
		notas.set(nota.getPosicao(), nota);
		notifyItemChanged(nota.getPosicao());
	}

	private void atualizaPosicaoNotasDepoisDaRemovida(int posicao) {
		if(posicao < notas.size() - 1) {
			for(int i = posicao + 1; i < notas.size(); i++) {
				notas.get(i).setPosicao(i - 1);
			}
		}
	}

	private Void removeNotaNoDB(Nota notaRemovida) {
		return dao.remove(notaRemovida);
	}

	private void removeNotaNaLista(int posicao, Nota notaRemovida) {
		notas.remove(notaRemovida);
		notifyItemRemoved(posicao);
		new BaseAsyncTask<>(
			getVoidPreparaCallback(),
			this::atualizaPosicaoNotasNoDB,
			resultado -> {}).execute();
	}

	private void atualizaPosicaoNotasNaLista(int posicaoInicial, int posicaoFinal) {
		Collections.swap(notas, posicaoInicial, posicaoFinal);
		redefinePosicaoNotas(posicaoInicial, posicaoFinal);
	}

	private Void atualizaPosicaoNotasNoDB() {
		return dao.atualiza(notas.toArray(new Nota[0]));
	}

	private void redefinePosicaoNotas(int posicaoInicial, int posicaoFinal) {
		if(posicaoInicial < posicaoFinal) {
			for(int posicao = posicaoInicial; posicao <= posicaoFinal; posicao++) {
				atualizaPosicaoNota(posicao);
			}
		} else if(posicaoInicial > posicaoFinal) {
			for(int posicao = posicaoFinal; posicao <= posicaoInicial; posicao++) {
				atualizaPosicaoNota(posicao);
			}
		}
	}

	private void atualizaPosicaoNota(int i) {
		Nota nota = notas.get(i);
		nota.setPosicao(notas.indexOf(nota));
	}

	private void regeneraLista(List<Nota> notasDoDB) {
		notas.clear();
		notas.addAll(notasDoDB);
		notifyDataSetChanged();
	}

	public void setOnItemClickListener(NotasClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public interface NotasClickListener {
		void onItemCLick(Nota nota, int posicao);
	}

	class NotasViewHolder extends RecyclerView.ViewHolder {

		private final TextView campoDescricao;
		private final TextView campoTitulo;
		private Nota nota;

		NotasViewHolder(@NonNull final View itemView) {
			super(itemView);
			campoDescricao = itemView.findViewById(R.id.item_notas_descricao);
			campoTitulo = itemView.findViewById(R.id.item_notas_titulo);
			configuraListenerLista(itemView);
		}

		private void configuraListenerLista(@NonNull View itemView) {
			itemView.setOnClickListener(view -> onItemClickListener.onItemCLick(nota, getAdapterPosition()));
		}

		void vincula(Nota nota, View itemView) {
			this.nota = nota;
			itemView.setBackgroundColor(Color.parseColor(nota.getCor()));
			defineCampos(nota);
		}

		private void defineCampos(Nota nota) {
			defineTitulo(nota);
			defineDescricao(nota);
		}

		private void defineDescricao(Nota nota) {
			campoDescricao.setText(nota.getDescricao());
		}

		private void defineTitulo(Nota nota) {
			campoTitulo.setText(nota.getTitulo());
		}
	}
}
