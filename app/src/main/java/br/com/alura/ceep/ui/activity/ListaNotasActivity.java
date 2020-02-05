package br.com.alura.ceep.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import br.com.alura.ceep.R;
import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.adapter.ListaNotasAdapter;

import android.os.Bundle;

public class ListaNotasActivity extends AppCompatActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_notas);

		NotaDAO dao = new NotaDAO();

		defineLista(dao);

	}

	private void defineLista(NotaDAO dao){
		defineExemplos(dao);

		RecyclerView listaNotas = findViewById(R.id.lista_notas_lista);
		listaNotas.setAdapter(new ListaNotasAdapter(this));
	}

	private void defineExemplos(NotaDAO dao){
		for(int i = 0; i < 10000; i++){
			dao.insere(new Nota("titulo: " + i, "descrição: " + i));
		}
	}
}
