package br.com.doublelogic.spg.ui;

import java.util.TreeMap;

import android.app.Activity;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import br.com.doublelogic.spg.R;
import br.com.doublelogic.spg.bean.PasswordSettings;

public class ResultPasswordsActivity extends Activity {

	private PasswordSettings passSettings;

	private ListView listViewPasswords;

	private ImageView buttonRefresh;
	private ImageView buttonSelectAll;
	private ImageView buttonCopy;
	private ImageView buttonMail;
	private ImageView buttonSave;

	private View loadingBar;
	private View loadingText;


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

		listViewPasswords = (ListView) findViewById(R.id.listViewPasswords);

		buttonRefresh = (ImageView) findViewById(R.id.buttonRefresh);
		buttonSelectAll = (ImageView) findViewById(R.id.buttonSelectAll);
		buttonCopy = (ImageView) findViewById(R.id.buttonCopy);
		buttonMail = (ImageView) findViewById(R.id.buttonMail);
		buttonSave = (ImageView) findViewById(R.id.buttonSave);

		loadingBar;
		loadingText;
		linearLayoutMain = (LinearLayout) findViewById(R.id.linearLayoutMain);
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