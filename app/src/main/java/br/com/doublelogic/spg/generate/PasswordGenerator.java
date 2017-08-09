package br.com.doublelogic.spg.generate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.SystemClock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.doublelogic.spg.bean.PasswordSettings;
import br.com.doublelogic.spg.common.Preferences;

public class PasswordGenerator extends AsyncTask<PasswordSettings, Integer, Boolean> {

	private final Context context;

	private final SharedPreferences preferences;

	private final List<PasswordGeneratorListener> listeners;
	private PasswordSettings passwordSettings;

	public PasswordGenerator(Context context) {
		this.context = context;
		preferences = context.getSharedPreferences(Preferences.SETTINGS.name(), Context.MODE_PRIVATE);
		listeners = new ArrayList<>();
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

		Set<Character> chars = new HashSet<>();

		boolean noRepeat = preferences.getBoolean(Preferences.NO_REPEAT.name(), false);

		long startTime = SystemClock.uptimeMillis();

		while (!abortExecution(startTime) && password.length() != length) {
			char[] c = { (char) (32 + rand.nextInt(255)) };
			result = er.matcher(new String(c));
			if (result.matches()) {
				if(noRepeat) {
					chars.add(c[0]);
					if(chars.size() > password.length()) {
						password.append(c);
					}
				} else {
					password.append(c);

				}
			}
		}
		return password.toString();
	}

	private boolean abortExecution(long startTime) {
		long now = SystemClock.uptimeMillis();
		return (now - startTime) / 1000 > 1;
	}

}