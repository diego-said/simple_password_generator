package br.com.doublelogic.spg.generate;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import br.com.doublelogic.spg.bean.PasswordSettings;

public class PasswordGenerator extends AsyncTask<PasswordSettings, Integer, Boolean> {

	private final List<PasswordGeneratorListener> listeners;

	public PasswordGenerator() {
		listeners = new ArrayList<PasswordGeneratorListener>();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected Boolean doInBackground(PasswordSettings... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

	public void addListener(PasswordGeneratorListener l) {
		listeners.add(l);
	}

}
