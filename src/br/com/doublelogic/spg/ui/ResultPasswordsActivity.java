package br.com.doublelogic.spg.ui;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import br.com.doublelogic.spg.R;
import br.com.doublelogic.spg.adapter.ResultPasswordsAdapter;
import br.com.doublelogic.spg.bean.PasswordSettings;
import br.com.doublelogic.spg.generate.PasswordGenerator;
import br.com.doublelogic.spg.generate.PasswordGeneratorListener;

public class ResultPasswordsActivity extends Activity implements PasswordGeneratorListener {

	private PasswordSettings passSettings;

	private ListView listViewPasswords;

	private ImageView buttonRefresh;
	private ImageView buttonSelectAll;
	private ImageView buttonCopy;
	private ImageView buttonMail;
	private ImageView buttonSave;

	private View loadingBar;
	private View loadingText;

	private ResultPasswordsAdapter adapter;
	private PasswordGenerator passwordGenerator;

	private boolean checkAll = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_passwords);

		adapter = new ResultPasswordsAdapter(this);

		loadUIReferences();

		if (getIntent().hasExtra(PasswordSettings.KEY)) {
			passSettings = (PasswordSettings) getIntent().getExtras().getSerializable(PasswordSettings.KEY);
		} else {
			finish();
		}

		generatePasswords();
	}

	private void loadUIReferences() {
		listViewPasswords = (ListView) findViewById(R.id.listViewPasswords);
		listViewPasswords.setAdapter(adapter);

		buttonRefresh = (ImageView) findViewById(R.id.buttonRefresh);
		buttonRefresh.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				refreshClickHandler(v);
			}
		});

		buttonSelectAll = (ImageView) findViewById(R.id.buttonSelectAll);
		buttonSelectAll.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				selectAllClickHandler(v);
			}
		});

		buttonCopy = (ImageView) findViewById(R.id.buttonCopy);
		buttonCopy.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				copyClickHandler(v);
			}
		});

		buttonMail = (ImageView) findViewById(R.id.buttonMail);
		buttonMail.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mailClickHandler(v);
			}
		});

		buttonSave = (ImageView) findViewById(R.id.buttonSave);

		loadingBar = findViewById(R.id.loadingBar);
		loadingText = findViewById(R.id.loadingText);
	}

	private void generatePasswords() {
		passwordGenerator = new PasswordGenerator();
		passwordGenerator.addListener(this);
		passwordGenerator.execute(passSettings);
	}

	private void refreshClickHandler(View v) {
		generatePasswords();
	}

	private void selectAllClickHandler(View v) {
		adapter.checkedAll(checkAll= !checkAll);
	}

	private void copyClickHandler(View view) {
		final StringBuilder memory = new StringBuilder();

		getPasswords(memory);

		if(memory.length() > 0) {
			final ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
			clipboard.setText(memory.toString());

			Toast.makeText(this, getString(R.string.copy_message), Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, getString(R.string.no_password), Toast.LENGTH_SHORT).show();
		}
	}

	private void mailClickHandler(View v) {
		final StringBuilder mailBody = new StringBuilder();

		getPasswords(mailBody);

		if(mailBody.length() > 0) {
			final Intent i = new Intent(Intent.ACTION_SEND);
			i.setType("message/rfc822");
			i.putExtra(Intent.EXTRA_SUBJECT, "Passwords");
			i.putExtra(Intent.EXTRA_TEXT, mailBody.toString());
			try {
				startActivity(Intent.createChooser(i, "Send mail..."));
			} catch (final android.content.ActivityNotFoundException ex) {
				Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(this, getString(R.string.no_password), Toast.LENGTH_SHORT).show();
		}
	}

	private void getPasswords(final StringBuilder memory) {
		for (final String password : adapter.getSelectedPasswords()) {
			if (memory.length() == 0) {
				memory.append(password);
			} else {
				memory.append("\n");
				memory.append(password);
			}
		}
	}

	public void onPreExecute() {
		loadingBar.setVisibility(View.VISIBLE);
		loadingText.setVisibility(View.VISIBLE);

		if(adapter != null) {
			adapter.setPasswordSettings(null);
			adapter.notifyDataSetChanged();
		}
	}

	public void onPostExecute(PasswordSettings p) {
		loadingBar.setVisibility(View.GONE);
		loadingText.setVisibility(View.GONE);

		if(adapter != null) {
			adapter.setPasswordSettings(p);
			adapter.notifyDataSetChanged();
		}
	}

}