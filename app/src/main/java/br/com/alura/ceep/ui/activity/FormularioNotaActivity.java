package br.com.alura.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import br.com.alura.ceep.R;
import br.com.alura.ceep.model.Nota;

import static br.com.alura.ceep.ui.activity.Constantes.CHAVE_NOTA;

public class FormularioNotaActivity extends AppCompatActivity{

	private static final String APPBAR_EDITA = "Altera nota";
	private static final String APPBAR_INSERE = "Insere nota";
	private EditText campoTitulo;
	private EditText campoDescricao;
	private Intent dadosNota;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_formulario_nota);

		defineCampos();

		dadosNota = getIntent();
		if(notaRecuperada()){
			setTitle(APPBAR_EDITA);
			recuperaNota();
		} else{
			setTitle(APPBAR_INSERE);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.formulario_salva_nota, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem itemClicado){
		if(menuSalvaNotaIgualAo(itemClicado)){
			Nota nota = new Nota(obtemTitulo(), obtemDescricao());
			finalizaFormulario(nota);
		}
		return super.onOptionsItemSelected(itemClicado);
	}

	private boolean menuSalvaNotaIgualAo(MenuItem item){
		return item.getItemId() == R.id.formulario_menu_salva_nota;
	}

	private String obtemTitulo(){
		return campoTitulo.getText().toString();
	}

	private String obtemDescricao(){
		return campoDescricao.getText().toString();
	}

	private void defineCampos(){
		campoTitulo = findViewById(R.id.formulario_nota_titulo);
		campoDescricao = findViewById(R.id.formulario_nota_descricao);
	}

	private void recuperaNota(){
		Nota nota = dadosNota.getParcelableExtra(CHAVE_NOTA);
		defineTitulo(nota.getTitulo());
		defineDescricao(nota.getDescricao());
	}

	private void defineTitulo(String titulo){
		campoTitulo.setText(titulo);
	}

	private void defineDescricao(String descricao){
		campoDescricao.setText(descricao);
	}

	private void finalizaFormulario(Nota nota){
		if(nota.valida()){
			if(notaRecuperada()){
				envia(nota);
				finish();
			} else{
				dadosNota = new Intent();
				envia(nota);
				finish();
			}
		}
	}

	private boolean notaRecuperada(){
		return dadosNota.hasExtra(CHAVE_NOTA);
	}

	private void envia(Nota nota){
		dadosNota.putExtra(CHAVE_NOTA, nota);
		setResult(Activity.RESULT_OK, dadosNota);
	}
}
