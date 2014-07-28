package br.com.doublelogic.spg.ui;

import java.util.Random;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;
import br.com.doublelogic.spg.R;
import br.com.doublelogic.spg.bean.PasswordSettings;

public class ResultPasswordsActivity extends Activity {

	private PasswordSettings passSettings;

	private TreeMap<Long, CheckBox> checkBoxMap;

	private LinearLayout linearLayoutMain;

	private float scale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_passwords);

		loadUIReferences();

		checkBoxMap = new TreeMap<Long, CheckBox>();
		scale = getResources().getDisplayMetrics().density;

		if (getIntent().hasExtra(PasswordSettings.KEY)) {
			passSettings = (PasswordSettings) getIntent().getExtras().getSerializable(PasswordSettings.KEY);
		} else {
			finish();
		}

		generatePasswords();
	}

	private void loadUIReferences() {
		linearLayoutMain = (LinearLayout) findViewById(R.id.linearLayoutMain);
	}

	private void generatePasswords() {
		int layoutIndex = 0;
		for (int i = 0; i < passSettings.getQuantity(); i++) {
			String password = generatePassword(passSettings.getRegEx(), passSettings.getLength());

			LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			layout.setId(++layoutIndex);
			layout.setVisibility(LinearLayout.VISIBLE);

			addCheckBox(layout, password);

			linearLayoutMain.addView(layout, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		}
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

	private void addCheckBox(LinearLayout layout, String password) {
		CheckBox checkBox = new CheckBox(this);
		checkBox.setHeight((int) ((25 * scale) + 0.5f));
		checkBox.setText(password);
		layout.addView(checkBox);

		checkBoxMap.put(System.currentTimeMillis(), checkBox);
	}

	public void copyClickHandler(View view) {
		switch (view.getId()) {
		case R.id.buttonCopy:
			StringBuilder memory = new StringBuilder();

			for (CheckBox checkBox : checkBoxMap.values()) {
				if (checkBox.isChecked()) {
					if (memory.length() == 0) {
						memory.append(checkBox.getText());
					} else {
						memory.append("\n");
						memory.append(checkBox.getText());
					}
				}
			}

			ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
			clipboard.setText(memory.toString());

			Toast.makeText(this, getString(R.string.copy_message), Toast.LENGTH_LONG).show();
			finish();

			break;
		}
	}

	public void copyAllClickHandler(View view) {
		switch (view.getId()) {
		case R.id.buttonCopy:
			StringBuilder memory = new StringBuilder();

			for (CheckBox checkBox : checkBoxMap.values()) {
				if (memory.length() == 0) {
					memory.append(checkBox.getText());
				} else {
					memory.append("\n");
					memory.append(checkBox.getText());
				}
			}

			ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
			clipboard.setText(memory.toString());

			Toast.makeText(this, getString(R.string.copy_message), 999999999).show();
			finish();

			break;
		}
	}

}