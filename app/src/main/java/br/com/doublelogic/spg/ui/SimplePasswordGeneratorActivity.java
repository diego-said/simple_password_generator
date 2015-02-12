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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		passwordGeneratorFragment = new PasswordGeneratorFragment();
		
		buttonPasswords = (ImageButton) findViewById(R.id.buttonPassword);
		buttonPasswords.setSelected(true);
		buttonPasswords.setOnClickListener(listener);

		buttonSaves = (ImageButton) findViewById(R.id.buttonSaves);
		buttonSaves.setSelected(false);
		buttonSaves.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_find_holo_dark_dark));
		buttonSaves.setOnClickListener(listener);
		
		buttonSettings = (ImageButton) findViewById(R.id.buttonSettings);
		buttonSettings.setSelected(false);
		buttonSettings.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_settings_dark));
		buttonSettings.setOnClickListener(listener);

		showFragment(passwordGeneratorFragment);
	}

	private final OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.buttonPassword:
				showFragment(passwordGeneratorFragment);
				buttonPasswords.setSelected(true);
				buttonPasswords.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_login));
				buttonSaves.setSelected(false);
				buttonSaves.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_find_holo_dark_dark));
				buttonSettings.setSelected(false);
				buttonSettings.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_settings_dark));
				break;

			case R.id.buttonSaves:
				showFragment(passwordGeneratorFragment);
				buttonPasswords.setSelected(false);
				buttonPasswords.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_login_dark));
				buttonSaves.setSelected(true);
				buttonSaves.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_find_holo_dark));
				buttonSettings.setSelected(false);
				buttonSettings.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_settings_dark));
				break;
			case R.id.buttonSettings:
				showFragment(passwordGeneratorFragment);
				buttonPasswords.setSelected(false);
				buttonPasswords.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_login_dark));
				buttonSaves.setSelected(false);
				buttonSaves.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_find_holo_dark_dark));
				buttonSettings.setSelected(true);
				buttonSettings.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_settings));
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

}