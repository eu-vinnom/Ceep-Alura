package br.com.alura.ceep.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import br.com.alura.ceep.R;
import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;

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
		if(item.getItemId() == R.id.formulario_menu_salva_nota){
			EditText campoTitulo = findViewById(R.id.formulario_nota_titulo);
			String titulo = campoTitulo.getText().toString();

			EditText campoDescricao = findViewById(R.id.formulario_nota_descricao);
			String descricao = campoDescricao.getText().toString();

			Nota nota = new Nota(titulo, descricao);
			NotaDAO dao = new NotaDAO();
			dao.insere(nota);
			finish();
		}

		return super.onOptionsItemSelected(item);
	}
}
