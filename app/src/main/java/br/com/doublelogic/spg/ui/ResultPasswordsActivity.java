package br.com.doublelogic.spg.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.doublelogic.spg.R;
import br.com.doublelogic.spg.adapter.ResultPasswordsAdapter;
import br.com.doublelogic.spg.bean.PasswordSettings;
import br.com.doublelogic.spg.db.PasswordsDbHelper;
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
    private PasswordsDbHelper dbHelper;

	private boolean checkAll = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_passwords);

		adapter = new ResultPasswordsAdapter(this);
        dbHelper = new PasswordsDbHelper(this);
		dbHelper.getWritableDatabase();

		loadUIReferences();

		if (getIntent().hasExtra(PasswordSettings.KEY)) {
			passSettings = (PasswordSettings) getIntent().getExtras().getSerializable(PasswordSettings.KEY);
		} else {
			finish();
		}

		if(passSettings.getPasswords().size() > 0) {
			onPostExecute(passSettings);
		} else {
			generatePasswords();
		}
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
		buttonSave.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
                saveClickHandler(v);
			}
		});

		loadingBar = findViewById(R.id.loadingBar);
		loadingText = findViewById(R.id.loadingText);
	}

	private void generatePasswords() {
		passwordGenerator = new PasswordGenerator(this);
		passwordGenerator.addListener(this);
		passwordGenerator.execute(passSettings);
	}

	private void refreshClickHandler(View v) {
		generatePasswords();
	}

	private void selectAllClickHandler(View v) {
		adapter.checkedAll(checkAll = !checkAll);
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

	private void saveClickHandler(View v) {
		final List<String> passwords = getPasswords();
        if(passwords != null && passwords.size() > 0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.save_passwords);

			final EditText input = new EditText(this);
			input.setInputType(InputType.TYPE_CLASS_TEXT);
			input.setText(passSettings.getName());
			builder.setView(input);

			builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					passSettings.setName(input.getText().toString());
					if(passSettings.getId() > 0) {
						dbHelper.removePasswords(String.valueOf(passSettings.getId()));
					}
					dbHelper.savePasswords(passSettings, passwords);
					dialog.dismiss();
					Toast.makeText(getApplicationContext(), getString(R.string.save_message), Toast.LENGTH_SHORT).show();
				}
			});
			builder.setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});

			builder.show();
        }  else {
            Toast.makeText(this, getString(R.string.no_password), Toast.LENGTH_SHORT).show();
        }
	}

    private List<String> getPasswords() {
        return adapter.getSelectedPasswords();
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

		buttonRefresh.setEnabled(false);
		buttonSelectAll.setEnabled(false);
		buttonCopy.setEnabled(false);
		buttonMail.setEnabled(false);
		buttonSave.setEnabled(false);

		if(adapter != null) {
			adapter.setPasswordSettings(null);
			adapter.notifyDataSetChanged();
		}
	}

	public void onPostExecute(PasswordSettings p) {
		loadingBar.setVisibility(View.GONE);
		loadingText.setVisibility(View.GONE);

		buttonRefresh.setEnabled(true);
		buttonSelectAll.setEnabled(true);
		buttonCopy.setEnabled(true);
		buttonMail.setEnabled(true);
		buttonSave.setEnabled(true);

		if(adapter != null) {
			adapter.setPasswordSettings(p);
			adapter.notifyDataSetChanged();
		}
	}

}