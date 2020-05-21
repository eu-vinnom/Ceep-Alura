package br.com.alura.ceep.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import br.com.alura.ceep.R;

public class SplashScreenActivity extends AppCompatActivity {

	private static final String PRIMEIRO_ACESSO = "Primeiro acesso";
	private static final String FEITO = "feito";
	private static final int ATRASO_PRIMEIRO_ACESSO = 2000;
	private static final int ATRASO_ACESSO = 500;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);

		exibeApresentacao();

	}

	private void exibeApresentacao() {
		if(!getPreferences(MODE_PRIVATE).contains(PRIMEIRO_ACESSO)) {
			controlaDuracaoApresentacao(ATRASO_PRIMEIRO_ACESSO);
			definePreferences();
		} else {
			controlaDuracaoApresentacao(ATRASO_ACESSO);
		}
	}

	private void controlaDuracaoApresentacao(int atrasoPrimeiroAcesso) {
		new Handler().postDelayed(this::daApresentacaoParaListaNotas, atrasoPrimeiroAcesso);
	}

	private void definePreferences() {
		SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
		editor.putString(PRIMEIRO_ACESSO, FEITO).apply();
	}

	private void daApresentacaoParaListaNotas() {
		startActivity(new Intent(SplashScreenActivity.this, ListaNotasActivity.class));
		finish();
	}
}
