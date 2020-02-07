package br.com.alura.ceep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import br.com.alura.ceep.R;
import br.com.alura.ceep.model.Nota;

import static br.com.alura.ceep.ui.activity.Constantes.CHAVE_NOTA;
import static br.com.alura.ceep.ui.activity.Constantes.CODIGO_NOTA_CRIADA;

public class FormularioNotaActivity extends AppCompatActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_formulario_nota);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.formulario_salva_nota, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		if(menuSalvaNotaIgualAo(item)){
			String titulo = atribuiTitulo();
			String descricao = atribuiDescricao();

			Nota nota = new Nota(titulo, descricao);
			if(nota.existe()){
				envia(nota);
				finish();
			}
		}
		return super.onOptionsItemSelected(item);
	}

	private void envia(Nota nota){
		Intent dadosNota = defineDados(nota);
		setResult(CODIGO_NOTA_CRIADA, dadosNota);
	}

	private Intent defineDados(Nota nota){
		Intent dadosNota = new Intent();
		dadosNota.putExtra(CHAVE_NOTA, nota);
		return dadosNota;
	}

	private String atribuiDescricao(){
		EditText campoDescricao = findViewById(R.id.formulario_nota_descricao);
		return campoDescricao.getText().toString();
	}

	private String atribuiTitulo(){
		EditText campoTitulo = findViewById(R.id.formulario_nota_titulo);
		return campoTitulo.getText().toString();
	}

	private boolean menuSalvaNotaIgualAo(MenuItem item){
		return item.getItemId() == R.id.formulario_menu_salva_nota;
	}
}
