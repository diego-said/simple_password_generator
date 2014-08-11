package br.com.doublelogic.spg.generate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.AsyncTask;
import br.com.doublelogic.spg.bean.PasswordSettings;

public class PasswordGenerator extends AsyncTask<PasswordSettings, Integer, Boolean> {

	private final List<PasswordGeneratorListener> listeners;
	private PasswordSettings passwordSettings;

	public PasswordGenerator() {
		listeners = new ArrayList<PasswordGeneratorListener>();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		for(PasswordGeneratorListener listener : listeners) {
			listener.onPreExecute();
		}
	}

	@Override
	protected Boolean doInBackground(PasswordSettings... params) {
		if ((params == null) || (params.length <= 0)) {
			return false;
		}

		passwordSettings = params[0];
		passwordSettings.clearPasswords();

		for (int i = 0; i < passwordSettings.getQuantity(); i++) {
			String password = generatePassword(passwordSettings.getRegEx(), passwordSettings.getLength());
			passwordSettings.addPassword(password);
		}
		return true;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		for(PasswordGeneratorListener listener : listeners) {
			listener.onPostExecute(passwordSettings);
		}
	}

	public void addListener(PasswordGeneratorListener l) {
		listeners.add(l);
	}

	private String generatePassword(String regex, int length) {
		StringBuilder password = new StringBuilder();
		Random rand = new Random(System.currentTimeMillis());

		Pattern er = Pattern.compile(regex);
		Matcher result = null;

		while (password.length() != length) {
			char[] c = { (char) (32 + rand.nextInt(255)) };
			result = er.matcher(new String(c));
			if (result.matches()) {
				password.append(c);
			}
		}

		return password.toString();
	}

}