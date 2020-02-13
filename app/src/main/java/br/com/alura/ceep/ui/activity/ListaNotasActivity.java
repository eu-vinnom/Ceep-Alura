package br.com.alura.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import br.com.alura.ceep.R;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.adapter.ListaNotasAdapter;
import br.com.alura.ceep.ui.listener.NotasClickListener;
import br.com.alura.ceep.ui.utilitario.NotasCallback;

import static br.com.alura.ceep.ui.activity.Constantes.CHAVE_NOTA;
import static br.com.alura.ceep.ui.activity.Constantes.CODIGO_NOTA_EDITADA;
import static br.com.alura.ceep.ui.activity.Constantes.CODIGO_NOTA_INSERIDA;

public class ListaNotasActivity extends AppCompatActivity{

	private ListaNotasAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_notas);
		setTitle("Notas");

		criaTela();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
		if(notaInserida(requestCode, resultCode, data)){
			@SuppressWarnings("ConstantConditions") Nota nota = data.getParcelableExtra(CHAVE_NOTA);
			adapter.adiciona(nota);
		} else if(notaEditada(requestCode, resultCode, data)){
			@SuppressWarnings("ConstantConditions") Nota nota = data.getParcelableExtra(CHAVE_NOTA);
			int posicao = data.getIntExtra("posicao", -1);
			adapter.edita(nota, posicao);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void criaTela(){
		defineLista();
		defineInsereNovaNota();
	}

	private void defineLista(){
		RecyclerView listaNotas = findViewById(R.id.lista_notas_lista);
		adapter = new ListaNotasAdapter(this);
		listaNotas.setAdapter(adapter);

		ItemTouchHelper touchHelper = new ItemTouchHelper(new NotasCallback(adapter));
		touchHelper.attachToRecyclerView(listaNotas);

		adapter.setOnItemClickListener(new NotasClickListener(){
			@Override
			public void onItemCLick(Nota nota, int posicao){
				Intent daListaProFormularioEdita =
					new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
				daListaProFormularioEdita.putExtra(CHAVE_NOTA, nota);
				daListaProFormularioEdita.putExtra("posicao", posicao);
				startActivityForResult(daListaProFormularioEdita, 2);
			}
		});
	}

	private void defineInsereNovaNota(){
		TextView novaNota = findViewById(R.id.lista_notas_insere_nota);
		novaNota.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){
				Intent daListaProFormularioInsere =
					new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
				startActivityForResult(daListaProFormularioInsere, CODIGO_NOTA_INSERIDA);
			}
		});
	}

	private boolean notaInserida(int requestCode, int resultCode, @Nullable Intent data){
		return codigoNotaNovaEh(requestCode) && codigoNotaInseridaEh(resultCode) && existe(data);
	}

	private boolean notaEditada(int requestCode, int resultCode, @Nullable Intent data){
		return codigoNotaEditadaEh(requestCode) && codigoNotaInseridaEh(resultCode) && existe(data);
	}

	private boolean codigoNotaNovaEh(int requestCode){
		return requestCode == CODIGO_NOTA_INSERIDA;
	}

	private boolean codigoNotaEditadaEh(int requestCode){
		return requestCode == CODIGO_NOTA_EDITADA;
	}

	private boolean codigoNotaInseridaEh(int resultCode){
		return resultCode == Activity.RESULT_OK;
	}

	@SuppressWarnings("ConstantConditions")
	private boolean existe(@Nullable Intent data){
		return data.hasExtra(CHAVE_NOTA) && data != null;
	}
}
