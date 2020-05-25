package br.com.alura.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import br.com.alura.ceep.R;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.activity.adapter.PaletaCoresAdapter;

import static br.com.alura.ceep.ui.activity.Constantes.CHAVE_NOTA;

public class FormularioNotaActivity extends AppCompatActivity {

	private static final String APPBAR_EDITA = "Altera nota";
	private static final String APPBAR_INSERE = "Insere nota";
	private EditText campoTitulo;
	private EditText campoDescricao;
	private Intent dadosNota;
	private ConstraintLayout formularioTela;
	private Nota nota;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_formulario_nota);

		defineViewPeloId();
		criaNotaSeNula();
		dadosNota = getIntent();
		defineInsereOuEdita();
		defineBarraCores();
	}

	private void defineInsereOuEdita() {
		if(notaRecuperada()) {
			setTitle(APPBAR_EDITA);
			recuperaNota();
		} else {
			setTitle(APPBAR_INSERE);
		}
	}

	@Override
	protected void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable(CHAVE_NOTA, nota);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		nota = savedInstanceState.getParcelable(CHAVE_NOTA);
		if(nota != null) {
			formularioTela.setBackgroundColor(Color.parseColor(nota.getCor()));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.formulario_salva_nota, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem itemClicado) {
		if(menuSalvaNotaIgualAo(itemClicado)) {
			defineNota();
			finalizaFormulario(nota);
		}
		return super.onOptionsItemSelected(itemClicado);
	}

	private void defineNota() {
		nota.setTitulo(obtemTitulo());
		nota.setDescricao(obtemDescricao());
	}

	private void criaNotaSeNula() {
		if(nota == null) {
			nota = new Nota();
		}
	}

	private void defineBarraCores() {
		RecyclerView listaCores = findViewById(R.id.formulario_lista_cores);
		PaletaCoresAdapter adapter = new PaletaCoresAdapter(this);
		listaCores.setAdapter(adapter);
		adapter.setOnCorClickListener(this::alteraCorFundo);
	}

	private void alteraCorFundo(String cor) {
		formularioTela.setBackgroundColor(Color.parseColor(cor));
		nota.setCor(cor);
	}

	private boolean menuSalvaNotaIgualAo(MenuItem item) {
		return item.getItemId() == R.id.formulario_menu_salva_nota;
	}

	private String obtemTitulo() {
		return campoTitulo.getText().toString();
	}

	private String obtemDescricao() {
		return campoDescricao.getText().toString();
	}

	private void defineViewPeloId() {
		campoTitulo = findViewById(R.id.formulario_nota_titulo);
		campoDescricao = findViewById(R.id.formulario_nota_descricao);
		formularioTela = findViewById(R.id.formulario_tela);
	}

	private void recuperaNota() {
		nota = dadosNota.getParcelableExtra(CHAVE_NOTA);
		defineTitulo(nota.getTitulo());
		defineDescricao(nota.getDescricao());
		formularioTela.setBackgroundColor(Color.parseColor(nota.getCor()));
	}

	private void defineTitulo(String titulo) {
		campoTitulo.setText(titulo);
	}

	private void defineDescricao(String descricao) {
		campoDescricao.setText(descricao);
	}

	private void finalizaFormulario(Nota nota) {
		if(nota.valida()) {
			if(notaRecuperada()) {
				envia(nota);
			} else {
				dadosNota = new Intent();
				envia(nota);
			}
			finish();
		}
	}

	private boolean notaRecuperada() {
		return dadosNota.hasExtra(CHAVE_NOTA);
	}

	private void envia(Nota nota) {
		dadosNota.putExtra(CHAVE_NOTA, nota);
		setResult(Activity.RESULT_OK, dadosNota);
	}
}
