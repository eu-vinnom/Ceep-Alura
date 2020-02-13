package br.com.alura.ceep.ui.utilitario;

import android.util.Log;

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

		int sinalizacaoDeslize = ItemTouchHelper. RIGHT | ItemTouchHelper.LEFT;

		return makeMovementFlags(0, sinalizacaoDeslize);
	}

	@Override
	public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
												@NonNull RecyclerView.ViewHolder target){
		return false;
	}

	@Override
	public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction){
		int posicao = viewHolder.getAdapterPosition();
		adapter.remove(posicao);
	}
}
