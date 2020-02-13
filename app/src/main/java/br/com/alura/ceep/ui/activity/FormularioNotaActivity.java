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

	private EditText campoTitulo;
	private EditText campoDescricao;
	private Intent dadosNota;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_formulario_nota);

		campoTitulo = findViewById(R.id.formulario_nota_titulo);
		campoDescricao = findViewById(R.id.formulario_nota_descricao);

		dadosNota = getIntent();
		if(dadosNota.hasExtra(CHAVE_NOTA)){
			setTitle("Altera nota");
			Nota nota = dadosNota.getParcelableExtra(CHAVE_NOTA);
			campoTitulo.setText(nota.getTitulo());
			campoDescricao.setText(nota.getDescricao());
		} else{
			setTitle("Insere nota");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.formulario_salva_nota, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		if(menuSalvaNotaIgualAo(item)){
			Nota nota = new Nota(atribuiTitulo(), atribuiDescricao());
			if(nota.valida()){
				if(dadosNota.hasExtra(CHAVE_NOTA)){
					dadosNota.putExtra(CHAVE_NOTA, nota);
					setResult(Activity.RESULT_OK, dadosNota);
					finish();
				} else{
					dadosNota = new Intent();
					dadosNota.putExtra(CHAVE_NOTA, nota);
					setResult(Activity.RESULT_OK, dadosNota);
					finish();
				}
			}

		}
		return super.onOptionsItemSelected(item);
	}

	private String atribuiTitulo(){
		return campoTitulo.getText().toString();
	}

	private String atribuiDescricao(){
		return campoDescricao.getText().toString();
	}

	//
	private boolean menuSalvaNotaIgualAo(MenuItem item){
		return item.getItemId() == R.id.formulario_menu_salva_nota;
	}
}
