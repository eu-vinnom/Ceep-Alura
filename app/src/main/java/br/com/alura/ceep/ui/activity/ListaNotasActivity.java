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
import br.com.alura.ceep.ui.activity.adapter.ListaNotasAdapter;
import br.com.alura.ceep.ui.activity.listener.NotasClickListener;
import br.com.alura.ceep.ui.activity.utilitario.NotasCallback;

import static br.com.alura.ceep.ui.activity.Constantes.CHAVE_NOTA;
import static br.com.alura.ceep.ui.activity.Constantes.CODIGO_NOTA_EDITADA;
import static br.com.alura.ceep.ui.activity.Constantes.CODIGO_NOTA_INSERIDA;

public class ListaNotasActivity extends AppCompatActivity{

	public static final String APPBAR = "Notas";
	public static final String POSICAO = "posicao";
	public static final int POSICAO_PADRAO = -1;
	private ListaNotasAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_notas);
		setTitle(APPBAR);

		criaTela();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
		if(existe(data)){
			if(notaInserida(requestCode, resultCode)){
				@SuppressWarnings("ConstantConditions") Nota nota = data.getParcelableExtra(CHAVE_NOTA);
				adapter.adiciona(nota);
			} else if(notaEditada(requestCode, resultCode)){
				@SuppressWarnings("ConstantConditions") Nota nota = data.getParcelableExtra(CHAVE_NOTA);
				int posicao = data.getIntExtra(POSICAO, POSICAO_PADRAO);
				adapter.edita(nota, posicao);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void criaTela(){
		defineLista();
		defineInsereNota();
	}

	private void defineLista(){
		RecyclerView listaNotas = findViewById(R.id.lista_notas_lista);
		adapter = new ListaNotasAdapter(this);

		listaNotas.setAdapter(adapter);

		configuraAnimacoes(listaNotas);

		defineEditaNota();
	}

	private void configuraAnimacoes(RecyclerView listaNotas){
		ItemTouchHelper touchHelper = new ItemTouchHelper(new NotasCallback(adapter));
		touchHelper.attachToRecyclerView(listaNotas);
	}

	private void defineInsereNota(){
		TextView novaNota = findViewById(R.id.lista_notas_insere_nota);
		novaNota.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){
				daListaProFormularioInsereNota();
			}
		});
	}

	private void daListaProFormularioInsereNota(){
		Intent formularioInsereNota = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
		startActivityForResult(formularioInsereNota, CODIGO_NOTA_INSERIDA);
	}

	private void defineEditaNota(){
		adapter.setOnItemClickListener(new NotasClickListener(){
			@Override
			public void onItemCLick(Nota nota, int posicao){
				daListaProFormularioEditaNota(nota, posicao);
			}
		});
	}

	private void daListaProFormularioEditaNota(Nota nota, int posicao){
		Intent formularioEditaNota = configuraIntentFormularioEditaNota(nota, posicao);
		startActivityForResult(formularioEditaNota, CODIGO_NOTA_EDITADA);
	}

	private Intent configuraIntentFormularioEditaNota(Nota nota, int posicao){
		Intent formularioEditaNota = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
		formularioEditaNota.putExtra(CHAVE_NOTA, nota);
		formularioEditaNota.putExtra(POSICAO, posicao);
		return formularioEditaNota;
	}

	private boolean notaInserida(int requestCode, int resultCode){
		return codigoNotaInseridaEh(requestCode) && codigoNotaSalvaEh(resultCode);
	}

	private boolean notaEditada(int requestCode, int resultCode){
		return codigoNotaEditadaEh(requestCode) && codigoNotaSalvaEh(resultCode);
	}

	private boolean codigoNotaInseridaEh(int requestCode){
		return requestCode == CODIGO_NOTA_INSERIDA;
	}

	private boolean codigoNotaEditadaEh(int requestCode){
		return requestCode == CODIGO_NOTA_EDITADA;
	}

	private boolean codigoNotaSalvaEh(int resultCode){
		return resultCode == Activity.RESULT_OK;
	}

	@SuppressWarnings("ConstantConditions")
	private boolean existe(@Nullable Intent data){
		return data.hasExtra(CHAVE_NOTA) && data != null;
	}
}
