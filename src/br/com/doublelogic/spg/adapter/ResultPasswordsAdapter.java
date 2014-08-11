package br.com.doublelogic.spg.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import br.com.doublelogic.spg.R;
import br.com.doublelogic.spg.bean.PasswordSettings;

public class ResultPasswordsAdapter extends BaseAdapter {

	private final Context context;

	private PasswordSettings passwordSettings;

	private final SparseBooleanArray passwords;

	private ResultPasswordsAdapter(Context context) {
		this.context = context;

		passwords = new SparseBooleanArray();
	}

	public int getCount() {
		if(passwordSettings != null) {
			return passwordSettings.getLength();
		}
		return 0;
	}

	public Object getItem(int position) {
		if(passwordSettings != null) {
			return passwordSettings.getPasswords().get(position);
		}
		return "";
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item_password, parent);

			CheckBox checkboxPassword = (CheckBox) convertView.findViewById(R.id.checkBoxPassword);
			checkboxPassword.setTag(position);
			checkboxPassword.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					Integer position = (Integer) buttonView.getTag();
					passwords.put(position, isChecked);
				}
			});
		}

		return convertView;
	}

	public PasswordSettings getPasswordSettings() {
		return passwordSettings;
	}

	public void setPasswordSettings(PasswordSettings passwordSettings) {
		this.passwordSettings = passwordSettings;
	}

	public List<String> getSelectedPasswords() {
		List<String> selectedPasswords = new ArrayList<String>(passwords.size());
		for (int i = 0; i < passwords.size(); i++) {
			int pos = passwords.keyAt(i);
			if(passwords.get(pos)) {
				selectedPasswords.add(passwordSettings.getPasswords().get(pos));
			}
	    }
		return selectedPasswords;
	}

}
