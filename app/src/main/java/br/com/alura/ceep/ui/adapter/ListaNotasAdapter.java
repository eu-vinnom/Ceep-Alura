package br.com.alura.ceep.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.alura.ceep.R;
import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.adapter.holder.ListaNotasViewHolder;

public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasViewHolder>{

	private final List<Nota> notas;
	private final Context contexto;

	public ListaNotasAdapter(Context contexto){
		NotaDAO dao = new NotaDAO();
		this.contexto = contexto;
		this.notas = dao.todos();
	}

	@NonNull
	@Override
	public ListaNotasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
		View item = LayoutInflater.from(contexto).inflate(R.layout.item_nota, parent, false);

		return new ListaNotasViewHolder(item, notas);
	}

	@Override
	public void onBindViewHolder(@NonNull ListaNotasViewHolder holder, int posicao){
		holder.vincula(holder.itemView, posicao);
	}

	@Override
	public int getItemCount(){
		return notas.size();
	}
}
