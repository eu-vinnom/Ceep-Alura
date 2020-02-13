package br.com.alura.ceep.ui.utilitario;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import br.com.alura.ceep.ui.adapter.ListaNotasAdapter;

public class NotasCallback extends ItemTouchHelper.Callback{

	private ListaNotasAdapter adapter;

	public NotasCallback(ListaNotasAdapter adapter){
		this.adapter = adapter;
	}

	@Override
	public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder){
		int sinalizacaoArraste = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
		int sinalizacaoDeslize = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;

		return makeMovementFlags(sinalizacaoArraste, sinalizacaoDeslize);
	}

	@Override
	public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder holderInicial,
												@NonNull RecyclerView.ViewHolder holderFinal){
		int posicaoInicial = holderInicial.getAdapterPosition();
		int posicaoFinal = holderFinal.getAdapterPosition();

		adapter.troca(posicaoInicial, posicaoFinal);

		return true;
	}

	@Override
	public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction){
		int posicao = viewHolder.getAdapterPosition();
		adapter.remove(posicao);
	}
}
