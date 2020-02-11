package br.com.alura.ceep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import br.com.alura.ceep.R;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.adapter.ListaNotasAdapter;
import br.com.alura.ceep.ui.listener.NotasClickListener;

import static br.com.alura.ceep.ui.activity.Constantes.CHAVE_NOTA;
import static br.com.alura.ceep.ui.activity.Constantes.CODIGO_NOTA_CRIADA;
import static br.com.alura.ceep.ui.activity.Constantes.CODIGO_NOTA_NOVA;

public class ListaNotasActivity extends AppCompatActivity{

	private ListaNotasAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_notas);

		criaTela();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
		if(novaNotaExiste(requestCode, resultCode, data)){
			@SuppressWarnings("ConstantConditions") Nota nota = data.getParcelableExtra(CHAVE_NOTA);
			adapter.adiciona(nota);
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
		adapter.setOnItemClickListener(new NotasClickListener(){
			@Override
			public void onItemCLick(Nota nota, int posicao){
				Toast.makeText(ListaNotasActivity.this, nota.getTitulo(), Toast.LENGTH_SHORT).show();
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
				startActivityForResult(daListaProFormularioInsere, CODIGO_NOTA_NOVA);
			}
		});
	}

	private boolean novaNotaExiste(int requestCode, int resultCode, @Nullable Intent data){
		return codigoNotaNovaEh(requestCode) && codigoNotaCriadaEh(resultCode) && existe(data);
	}

	@SuppressWarnings("ConstantConditions")
	private boolean existe(@Nullable Intent data){
		return data.hasExtra(CHAVE_NOTA) && data != null;
	}

	private boolean codigoNotaCriadaEh(int resultCode){
		return resultCode == CODIGO_NOTA_CRIADA;
	}

	private boolean codigoNotaNovaEh(int requestCode){
		return requestCode == CODIGO_NOTA_NOVA;
	}
}
