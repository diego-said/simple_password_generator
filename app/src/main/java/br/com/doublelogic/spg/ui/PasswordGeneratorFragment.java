package br.com.doublelogic.spg.ui;

import android.app.Fragment;
import android.content.Intent;
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
import br.com.doublelogic.spg.common.RegExDefaults;

public class PasswordGeneratorFragment extends Fragment {

	private static final int REQUEST_RESULT_PASSWORDS = 1;

	private PasswordSettings passSettings;

	private EditText editTextLength;
	private EditText editTextQuantity;
	private EditText editTextRegEx;

	private CheckBox checkBoxLetters;
	private CheckBox checkBoxNumbers;
	private CheckBox checkBoxSpecialChar;
	
	private Button buttonGenerate;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.generate_passwords, container, false);

		loadUIReferences(view);

		passSettings = new PasswordSettings();

		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		return view;
	}

	private void loadUIReferences(View view) {
		editTextLength = (EditText) view.findViewById(R.id.editTextLength);
		editTextQuantity = (EditText) view.findViewById(R.id.editTextQuantity);
		editTextRegEx = (EditText) view.findViewById(R.id.editTextRegEx);

		checkBoxLetters = (CheckBox) view.findViewById(R.id.checkBoxLetters);
		checkBoxLetters.setOnClickListener(new OnClickListener() {
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

			Intent requestResult = new Intent(getActivity(), ResultPasswordsActivity.class);
			requestResult.putExtra(PasswordSettings.KEY, passSettings);
			startActivityForResult(requestResult, REQUEST_RESULT_PASSWORDS);

			break;
		}
	}
}