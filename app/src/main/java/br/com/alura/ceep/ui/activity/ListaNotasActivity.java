package br.com.alura.ceep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import br.com.alura.ceep.R;
import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.adapter.ListaNotasAdapter;

public class ListaNotasActivity extends AppCompatActivity{

	private NotaDAO dao = new NotaDAO();

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_notas);

		final Intent daListaProFormulario = new Intent(this, FormularioNotaActivity.class);

		defineExemplos();

		TextView novaNota = findViewById(R.id.lista_notas_insere_nota);
		novaNota.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){
				startActivity(daListaProFormulario);
			}
		});

	}

	@Override
	protected void onResume(){
		super.onResume();

		defineLista();
	}

	private void defineLista(){
		RecyclerView listaNotas = findViewById(R.id.lista_notas_lista);
		ListaNotasAdapter adapter = new ListaNotasAdapter(this);
		listaNotas.setAdapter(adapter);

		adapter.getNotas().clear();
		adapter.getNotas().addAll(dao.todos());

		adapter.notifyDataSetChanged();
	}

	private void defineExemplos(){
		for(int i = 1; i < 3; i++){
			dao.insere(new Nota("teste" + i, "teste" + i));
		}
	}
}
