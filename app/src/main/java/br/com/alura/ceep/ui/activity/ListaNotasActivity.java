package br.com.alura.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.Objects;

import br.com.alura.ceep.R;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.activity.adapter.ListaNotasAdapter;
import br.com.alura.ceep.ui.activity.utilitario.NotasCallback;

import static br.com.alura.ceep.R.id.home_ajuda_feedback;
import static br.com.alura.ceep.R.id.home_layout_grade;
import static br.com.alura.ceep.R.id.home_layout_lista;
import static br.com.alura.ceep.ui.activity.Constantes.CHAVE_NOTA;
import static br.com.alura.ceep.ui.activity.Constantes.CODIGO_NOTA_EDITADA;
import static br.com.alura.ceep.ui.activity.Constantes.CODIGO_NOTA_INSERIDA;
import static br.com.alura.ceep.ui.activity.TipoLayout.GRID;
import static br.com.alura.ceep.ui.activity.TipoLayout.LISTA;

public class ListaNotasActivity extends AppCompatActivity {

	private static final String APPBAR = "Notas";
	private static final String LAYOUT = "Layout";
	private static final String ERRO_ALTERACAO_LAYOUT = "Não foi possível alterar o layout";
	private ListaNotasAdapter adapter;
	private RecyclerView listaNotas;
	private StaggeredGridLayoutManager gradeLayout;
	private LinearLayoutManager listaLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_notas);
		setTitle(APPBAR);

		defineLayoutPadrao();

		criaTela();
		adapter.recuperaNotas();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(existe(data)) {
			if(notaInserida(requestCode, resultCode)) {
				@SuppressWarnings("ConstantConditions") Nota nota = data.getParcelableExtra(CHAVE_NOTA);
				adapter.adiciona(nota);
			} else if(notaEditada(requestCode, resultCode)) {
				@SuppressWarnings("ConstantConditions") Nota nota = data.getParcelableExtra(CHAVE_NOTA);
				adapter.edita(nota);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.lista_opcao_layout, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		defineItemVisivelNoMenu(menu);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		alteraLayout(item);
		return super.onOptionsItemSelected(item);
	}

	private void defineLayoutPadrao() {
		if(!getPreferences(MODE_PRIVATE).contains(LAYOUT)) {
			SharedPreferences.Editor editorLayout = getPreferences(MODE_PRIVATE).edit();
			editorLayout.putString(LAYOUT, LISTA.name()).apply();
		}
	}

	private void criaTela() {
		defineLista();
		defineInsereNota();
	}

	private void defineItemVisivelNoMenu(Menu menu) {
		if(ehLayoutLista()) {
			menu.findItem(home_layout_lista).setVisible(!ehLayoutLista());
		} else {
			menu.findItem(home_layout_grade).setVisible(ehLayoutLista());
		}
		menu.findItem(home_ajuda_feedback).setVisible(true);
	}

	private void alteraLayout(MenuItem item) {
		SharedPreferences.Editor editorLayout = getPreferences(MODE_PRIVATE).edit();
		switch(item.getItemId()) {
			case home_layout_grade:
				escolheLayout(editorLayout, home_layout_grade);
				break;
			case home_layout_lista:
				escolheLayout(editorLayout, home_layout_lista);
				break;
			case home_ajuda_feedback:
				startActivity(new Intent(ListaNotasActivity.this, FeedbackActivity.class));
				break;
			default:
				Toast.makeText(this, ERRO_ALTERACAO_LAYOUT, Toast.LENGTH_SHORT).show();
				break;
		}
	}

	private void defineLista() {
		listaNotas = findViewById(R.id.lista_notas_lista);
		adapter = new ListaNotasAdapter(this);
		gradeLayout = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
		listaLayout = new LinearLayoutManager(this);

		listaNotas.setAdapter(adapter);
		defineLayoutInicial();
		configuraAnimacoes(listaNotas);
		defineEditaNota();
	}

	private void escolheLayout(SharedPreferences.Editor editor, int layoutId) {
		if(layoutId == home_layout_grade) {
			editor.putString(LAYOUT, GRID.name()).apply();
			listaNotas.setLayoutManager(gradeLayout);
			invalidateOptionsMenu();
		} else {
			editor.putString(LAYOUT, LISTA.name()).apply();
			listaNotas.setLayoutManager(listaLayout);
			invalidateOptionsMenu();
		}
	}


	private void defineLayoutInicial() {
		if(ehLayoutLista()) {
			listaNotas.setLayoutManager(listaLayout);
		} else {
			listaNotas.setLayoutManager(gradeLayout);
		}
	}

	private boolean ehLayoutLista() {
		return Objects.requireNonNull(getPreferences(MODE_PRIVATE).getString(LAYOUT, LISTA.name())).equals(LISTA.name());
	}

	private void configuraAnimacoes(RecyclerView listaNotas) {
		ItemTouchHelper touchHelper = new ItemTouchHelper(new NotasCallback(adapter));
		touchHelper.attachToRecyclerView(listaNotas);
	}

	private void defineInsereNota() {
		TextView novaNota = findViewById(R.id.lista_notas_insere_nota);
		novaNota.setOnClickListener(view -> daListaProFormularioInsereNota());
	}

	private void daListaProFormularioInsereNota() {
		Intent formularioInsereNota = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
		startActivityForResult(formularioInsereNota, CODIGO_NOTA_INSERIDA);
	}

	private void defineEditaNota() {
		adapter.setOnItemClickListener(this::daListaProFormularioEditaNota);
	}

	private void daListaProFormularioEditaNota(Nota nota, int posicao) {
		Intent formularioEditaNota = configuraIntentFormularioEditaNota(nota, posicao);
		startActivityForResult(formularioEditaNota, CODIGO_NOTA_EDITADA);
	}

	private Intent configuraIntentFormularioEditaNota(Nota nota, int posicao) {
		Intent formularioEditaNota = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
		formularioEditaNota.putExtra(CHAVE_NOTA, nota);
		return formularioEditaNota;
	}

	private boolean notaInserida(int requestCode, int resultCode) {
		return codigoNotaInseridaEh(requestCode) && codigoNotaSalvaEh(resultCode);
	}

	private boolean notaEditada(int requestCode, int resultCode) {
		return codigoNotaEditadaEh(requestCode) && codigoNotaSalvaEh(resultCode);
	}

	private boolean codigoNotaInseridaEh(int requestCode) {
		return requestCode == CODIGO_NOTA_INSERIDA;
	}

	private boolean codigoNotaEditadaEh(int requestCode) {
		return requestCode == CODIGO_NOTA_EDITADA;
	}

	private boolean codigoNotaSalvaEh(int resultCode) {
		return resultCode == Activity.RESULT_OK;
	}

	@SuppressWarnings("ConstantConditions")
	private boolean existe(@Nullable Intent data) {
		return data != null && data.hasExtra(CHAVE_NOTA);
	}
}
