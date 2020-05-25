package br.com.alura.ceep.asynctask;

import android.os.AsyncTask;

public class BaseAsyncTask<T> extends AsyncTask<Void, Void, T> {

	private final RealizaCallback<T> realizaCallback;
	private final PreparaCallback preparaCallback;
	private final FinalizaCallback<T> finalizaCallback;

	public BaseAsyncTask(PreparaCallback preparaCallback, RealizaCallback<T> realizaCallback, FinalizaCallback<T> finalizaCallback) {
		this.preparaCallback = preparaCallback;
		this.realizaCallback = realizaCallback;
		this.finalizaCallback = finalizaCallback;
	}

	@Override
	protected T doInBackground(Void... voids) {
		return realizaCallback.realiza();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		preparaCallback.prepara();
	}

	@Override
	protected void onPostExecute(T resultado) {
		super.onPostExecute(resultado);
		finalizaCallback.finaliza(resultado);
	}

	public interface PreparaCallback {
		void prepara();
	}

	public interface RealizaCallback<T> {
		T realiza();
	}

	public interface FinalizaCallback<T> {
		void finaliza(T resultado);
	}
}
