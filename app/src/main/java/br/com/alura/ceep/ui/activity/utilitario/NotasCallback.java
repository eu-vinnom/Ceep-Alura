package br.com.alura.ceep.ui.activity.utilitario;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import br.com.alura.ceep.ui.activity.adapter.ListaNotasAdapter;

public class NotasCallback extends ItemTouchHelper.Callback {

	private final ListaNotasAdapter adapter;

	public NotasCallback(ListaNotasAdapter adapter) {
		this.adapter = adapter;
	}

	@Override
	public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
		int sinalizacaoArraste = defineSinalizacaoArraste();
		int sinalizacaoDeslize = defineSinalizacaoDeslize();

		return makeMovementFlags(sinalizacaoArraste, sinalizacaoDeslize);
	}

	@Override
	public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder holderInicial,
												@NonNull RecyclerView.ViewHolder holderFinal) {
		int posicaoInicial = holderInicial.getAdapterPosition();
		int posicaoFinal = holderFinal.getAdapterPosition();

		adapter.troca(posicaoInicial, posicaoFinal);

		return true;
	}

	@Override
	public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
		int posicao = viewHolder.getAdapterPosition();
		adapter.remove(posicao);
	}

	private int defineSinalizacaoArraste() {
		return ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
	}

	private int defineSinalizacaoDeslize() {
		return ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
	}
}
