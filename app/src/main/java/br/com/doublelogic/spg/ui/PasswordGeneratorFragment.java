package br.com.doublelogic.spg.ui;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import br.com.doublelogic.spg.R;
import br.com.doublelogic.spg.bean.PasswordSettings;
import br.com.doublelogic.spg.common.PasswordPreferences;
import br.com.doublelogic.spg.common.Preferences;
import br.com.doublelogic.spg.common.RegExDefaults;

public class PasswordGeneratorFragment extends Fragment {

	private static final int REQUEST_RESULT_PASSWORDS = 1;

	private PasswordSettings passSettings;

	private EditText editTextLength;
	private EditText editTextQuantity;
	private EditText editTextRegEx;

	private CheckBox checkBoxLetters;
	private CheckBox checkBoxSmallLetters;
	private CheckBox checkBoxNumbers;
	private CheckBox checkBoxSpecialChar;
	
	private Button buttonGenerate;

	private SharedPreferences preferences;
	private SharedPreferences passwordPreferences;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.generate_passwords, container, false);

		preferences = getActivity().getSharedPreferences(Preferences.SETTINGS.name(), Context.MODE_PRIVATE);
		passwordPreferences = getActivity().getSharedPreferences(PasswordPreferences.LAST_CONFIG.name(), Context.MODE_PRIVATE);

		loadUIReferences(view);

		passSettings = new PasswordSettings();

		if(preferences.getBoolean(Preferences.KEEP_LAST_CONFIG.name(), false)) {
			loadLastConfig();
		}

		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		return view;
	}

	private void loadUIReferences(View view) {
		editTextLength = (EditText) view.findViewById(R.id.editTextLength);
		editTextQuantity = (EditText) view.findViewById(R.id.editTextQuantity);
		editTextRegEx = (EditText) view.findViewById(R.id.editTextRegEx);
		if(preferences.getBoolean(Preferences.SHOW_REG_EX.name(), false)) {
			editTextRegEx.setVisibility(View.VISIBLE);
		} else {
			editTextRegEx.setVisibility(View.GONE);
		}

		checkBoxLetters = (CheckBox) view.findViewById(R.id.checkBoxLetters);
		checkBoxLetters.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				checkBoxClickHandler(v);
			}
		});

		checkBoxSmallLetters = (CheckBox) view.findViewById(R.id.checkBoxSmallLetters);
		checkBoxSmallLetters.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				checkBoxClickHandler(v);
			}
		});

		checkBoxNumbers = (CheckBox) view.findViewById(R.id.checkBoxNumbers);
		checkBoxNumbers.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				checkBoxClickHandler(v);
			}
		});

		checkBoxSpecialChar = (CheckBox) view.findViewById(R.id.checkBoxSpecialChar);
		checkBoxSpecialChar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				checkBoxClickHandler(v);
			}
		});

		buttonGenerate = (Button) view.findViewById(R.id.buttonGenerate);
		buttonGenerate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				generateClickHandler(v);
			}
		});
	}

	public void checkBoxClickHandler(View view) {
		if(checkBoxLetters.isChecked() && checkBoxSmallLetters.isChecked()) {
			if (checkBoxNumbers.isChecked() && checkBoxSpecialChar.isChecked()) {
				editTextRegEx.setText(String.valueOf(RegExDefaults.DEFAULT_REG_EX));
			} else if (checkBoxNumbers.isChecked()) {
				editTextRegEx.setText(String.valueOf(RegExDefaults.LETTERS_SMALL_LETTERS_NUMBERS_REG_EX));
			} else if (checkBoxSpecialChar.isChecked()) {
				editTextRegEx.setText(String.valueOf(RegExDefaults.LETTERS_SMALL_LETTERS_SPECIAL_CHAR_REG_EX));
			} else {
				editTextRegEx.setText(String.valueOf(RegExDefaults.LETTERS_SMALL_LETTERS_REG_EX));
			}
		} else if(checkBoxLetters.isChecked()) {
			if (checkBoxNumbers.isChecked() && checkBoxSpecialChar.isChecked()) {
				editTextRegEx.setText(String.valueOf(RegExDefaults.LETTERS_NUMBERS_SPECIAL_CHAR_REG_EX));
			} else if (checkBoxNumbers.isChecked()) {
				editTextRegEx.setText(String.valueOf(RegExDefaults.LETTERS_NUMBERS_REG_EX));
			} else if (checkBoxSpecialChar.isChecked()) {
				editTextRegEx.setText(String.valueOf(RegExDefaults.LETTERS_SPECIAL_CHAR_REG_EX));
			} else {
				editTextRegEx.setText(String.valueOf(RegExDefaults.LETTERS_REG_EX));
			}
		} else if(checkBoxSmallLetters.isChecked()) {
			if (checkBoxNumbers.isChecked() && checkBoxSpecialChar.isChecked()) {
				editTextRegEx.setText(String.valueOf(RegExDefaults.SMALL_LETTERS_NUMBERS_SPECIAL_CHAR_REG_EX));
			} else if (checkBoxNumbers.isChecked()) {
				editTextRegEx.setText(String.valueOf(RegExDefaults.SMALL_LETTERS_NUMBERS_REG_EX));
			} else if (checkBoxSpecialChar.isChecked()) {
				editTextRegEx.setText(String.valueOf(RegExDefaults.SMALL_LETTERS_SPECIAL_CHAR_REG_EX));
			} else {
				editTextRegEx.setText(String.valueOf(RegExDefaults.SMALL_LETTERS_REG_EX));
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

			if(preferences.getBoolean(Preferences.KEEP_LAST_CONFIG.name(), false)) {
				saveConfig();
			}

			Intent requestResult = new Intent(getActivity(), ResultPasswordsActivity.class);
			requestResult.putExtra(PasswordSettings.KEY, passSettings);
			startActivityForResult(requestResult, REQUEST_RESULT_PASSWORDS);

			break;
		}
	}

	private void loadLastConfig() {
		int length = passwordPreferences.getInt(PasswordPreferences.LENGTH.name(), passSettings.getLength());
		int quantity = passwordPreferences.getInt(PasswordPreferences.QUANTITY.name(), passSettings.getQuantity());
		boolean letters = passwordPreferences.getBoolean(PasswordPreferences.LETTERS.name(), true);
		boolean smallLetters = passwordPreferences.getBoolean(PasswordPreferences.SMALL_LETTERS.name(), true);
		boolean numbers = passwordPreferences.getBoolean(PasswordPreferences.NUMBERS.name(), true);
		boolean specialChar = passwordPreferences.getBoolean(PasswordPreferences.SPECIAL_CHAR.name(), false);
		String regEx = passwordPreferences.getString(PasswordPreferences.REGEX.name(), passSettings.getRegEx());

		passSettings.setLength(length);
		passSettings.setQuantity(quantity);
		passSettings.setRegEx(regEx);

		editTextLength.setText(String.valueOf(length));
		editTextQuantity.setText(String.valueOf(quantity));
		editTextRegEx.setText(String.valueOf(regEx));

		checkBoxLetters.setChecked(letters);
		checkBoxSmallLetters.setChecked(smallLetters);
		checkBoxNumbers.setChecked(numbers);
		checkBoxSpecialChar.setChecked(specialChar);
	}

	private void saveConfig() {
		int length = Integer.parseInt(editTextLength.getText().toString());
		int quantity = Integer.parseInt(editTextQuantity.getText().toString());
		boolean letters = checkBoxLetters.isChecked();
		boolean smallLetters = checkBoxSmallLetters.isChecked();
		boolean numbers = checkBoxNumbers.isChecked();
		boolean specialChar = checkBoxSpecialChar.isChecked();
		String regEx = editTextRegEx.getText().toString().trim();

		SharedPreferences.Editor editor = passwordPreferences.edit();
		editor.putInt(PasswordPreferences.LENGTH.name(), length);
		editor.putInt(PasswordPreferences.QUANTITY.name(), quantity);
		editor.putBoolean(PasswordPreferences.LETTERS.name(), letters);
		editor.putBoolean(PasswordPreferences.SMALL_LETTERS.name(), smallLetters);
		editor.putBoolean(PasswordPreferences.NUMBERS.name(), numbers);
		editor.putBoolean(PasswordPreferences.SPECIAL_CHAR.name(), specialChar);
		editor.putString(PasswordPreferences.REGEX.name(), regEx);
		editor.commit();
	}

}