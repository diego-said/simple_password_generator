package br.com.doublelogic.spg.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import br.com.doublelogic.spg.R;
import br.com.doublelogic.spg.bean.PasswordSettings;
import br.com.doublelogic.spg.common.RegExDefaults;

public class SimplePasswordGeneratorActivity extends Activity {

	private static final int REQUEST_RESULT_PASSWORDS = 1;

	private PasswordSettings passSettings;

	private EditText editTextLength;
	private EditText editTextQuantity;
	private EditText editTextRegEx;

	private CheckBox checkBoxLetters;
	private CheckBox checkBoxNumbers;
	private CheckBox checkBoxSpecialChar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		loadUIReferences();

		passSettings = new PasswordSettings();

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	private void loadUIReferences() {
		editTextLength = (EditText) findViewById(R.id.editTextLength);
		editTextQuantity = (EditText) findViewById(R.id.editTextQuantity);
		editTextRegEx = (EditText) findViewById(R.id.editTextRegEx);

		checkBoxLetters = (CheckBox) findViewById(R.id.checkBoxLetters);
		checkBoxNumbers = (CheckBox) findViewById(R.id.checkBoxNumbers);
		checkBoxSpecialChar = (CheckBox) findViewById(R.id.checkBoxSpecialChar);
	}

	public void checkBoxClickHandler(View view) {
		if (checkBoxLetters.isChecked()) {
			if (checkBoxNumbers.isChecked() && checkBoxSpecialChar.isChecked()) {
				editTextRegEx.setText(String.valueOf(RegExDefaults.DEFAULT_REG_EX));
			} else if (checkBoxNumbers.isChecked()) {
				editTextRegEx.setText(String.valueOf(RegExDefaults.LETTERS_NUMBERS_REG_EX));
			} else if (checkBoxSpecialChar.isChecked()) {
				editTextRegEx.setText(String.valueOf(RegExDefaults.LETTERS_SPECIAL_CHAR_REG_EX));
			} else {
				editTextRegEx.setText(String.valueOf(RegExDefaults.LETTERS_REG_EX));
			}
		} else {
			if (checkBoxNumbers.isChecked() && checkBoxSpecialChar.isChecked()) {
				editTextRegEx.setText(String.valueOf(RegExDefaults.NUMBERS_SPECIAL_CHAR_REG_EX));
			} else if (checkBoxNumbers.isChecked()) {
				editTextRegEx.setText(String.valueOf(RegExDefaults.NUMBERS_REG_EX));
			} else if (checkBoxSpecialChar.isChecked()) {
				editTextRegEx.setText(String.valueOf(RegExDefaults.SPECIAL_CHAR_REG_EX));
			}
		}
	}

	public void generateClickHandler(View view) {
		switch (view.getId()) {
		case R.id.buttonGenerate:

			String length = editTextLength.getText().toString();
			if ((length != null) && (length.trim().length() > 0)) {
				passSettings.setLength(Integer.parseInt(length));
			}

			String quantity = editTextQuantity.getText().toString();
			if ((quantity != null) && (quantity.trim().length() > 0)) {
				passSettings.setQuantity(Integer.parseInt(quantity));
			}

			String regEx = editTextRegEx.getText().toString();
			if ((regEx != null) && (regEx.trim().length() > 0)) {
				passSettings.setRegEx(regEx);
			}

			Intent requestResult = new Intent(this, ResultPasswordsActivity.class);
			requestResult.putExtra(PasswordSettings.KEY, passSettings);
			startActivityForResult(requestResult, REQUEST_RESULT_PASSWORDS);

			break;
		}
	}

}