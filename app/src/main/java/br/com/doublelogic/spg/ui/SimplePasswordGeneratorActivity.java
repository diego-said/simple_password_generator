package br.com.doublelogic.spg.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import br.com.doublelogic.spg.R;

public class SimplePasswordGeneratorActivity extends Activity {

	private ImageButton buttonPasswords;
	private ImageButton buttonSaves;
	private ImageButton buttonSettings;

	private Fragment passwordGeneratorFragment;
	private Fragment savePasswordFragment;
	private Fragment settingsFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		passwordGeneratorFragment = new PasswordGeneratorFragment();
		savePasswordFragment = new SavePasswordFragment();
		settingsFragment = new SettingsFragment();
		
		buttonPasswords = (ImageButton) findViewById(R.id.buttonPassword);
		buttonPasswords.setSelected(true);
		buttonPasswords.setOnClickListener(listener);

		buttonSaves = (ImageButton) findViewById(R.id.buttonSaves);
		buttonSaves.setSelected(false);
		buttonSaves.setImageDrawable(getResources().getDrawable(R.drawable.ic_find_grey));
		buttonSaves.setOnClickListener(listener);
		
		buttonSettings = (ImageButton) findViewById(R.id.buttonSettings);
		buttonSettings.setSelected(false);
		buttonSettings.setImageDrawable(getResources().getDrawable(R.drawable.ic_settings_grey));
		buttonSettings.setOnClickListener(listener);

		showFragment(passwordGeneratorFragment);
	}

	private final OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.buttonPassword:
				showFragment(passwordGeneratorFragment);
				buttonPasswords.setSelected(true);
				buttonPasswords.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_white));
				buttonSaves.setSelected(false);
				buttonSaves.setImageDrawable(getResources().getDrawable(R.drawable.ic_find_grey));
				buttonSettings.setSelected(false);
				buttonSettings.setImageDrawable(getResources().getDrawable(R.drawable.ic_settings_grey));
				break;

			case R.id.buttonSaves:
				showFragment(savePasswordFragment);
				buttonPasswords.setSelected(false);
				buttonPasswords.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_grey));
				buttonSaves.setSelected(true);
				buttonSaves.setImageDrawable(getResources().getDrawable(R.drawable.ic_find_white));
				buttonSettings.setSelected(false);
				buttonSettings.setImageDrawable(getResources().getDrawable(R.drawable.ic_settings_grey));
				break;
			case R.id.buttonSettings:
				showFragment(settingsFragment);
				buttonPasswords.setSelected(false);
				buttonPasswords.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_grey));
				buttonSaves.setSelected(false);
				buttonSaves.setImageDrawable(getResources().getDrawable(R.drawable.ic_find_grey));
				buttonSettings.setSelected(true);
				buttonSettings.setImageDrawable(getResources().getDrawable(R.drawable.ic_settings_white));
				break;	
				
			}
		}
	};

	private void showFragment(Fragment f) {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction fragmentTransaction = fm.beginTransaction();
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.replace(R.id.fragmentContent, f);
		fragmentTransaction.commit();
	}

	@Override
	public void onBackPressed() {
		finish();
	}
}