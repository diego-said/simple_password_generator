package br.com.doublelogic.spg.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseArray;
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
	private final SparseArray<CheckBox> checkboxPasswords;

	public ResultPasswordsAdapter(Context context) {
		this.context = context;

		passwords = new SparseBooleanArray();
		checkboxPasswords = new SparseArray<CheckBox>();
	}

	public int getCount() {
		if(passwordSettings != null) {
			return passwordSettings.getPasswords().size();
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

	@SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item_password, null);

			final CheckBox checkboxPassword = (CheckBox) convertView.findViewById(R.id.checkBoxPassword);
			checkboxPassword.setText(passwordSettings.getPasswords().get(position));
			checkboxPassword.setTag(position);
			checkboxPassword.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					final Integer position = (Integer) buttonView.getTag();
					passwords.put(position, isChecked);
				}
			});
			checkboxPasswords.put(position, checkboxPassword);
		}

		return convertView;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
		passwords.clear();
		checkboxPasswords.clear();
	}

	public PasswordSettings getPasswordSettings() {
		return passwordSettings;
	}

	public void setPasswordSettings(PasswordSettings passwordSettings) {
		this.passwordSettings = passwordSettings;
	}

	public List<String> getSelectedPasswords() {
		final List<String> selectedPasswords = new ArrayList<String>(passwords.size());
		for (int i = 0; i < passwords.size(); i++) {
			final int pos = passwords.keyAt(i);
			if(passwords.get(pos)) {
				selectedPasswords.add(passwordSettings.getPasswords().get(pos));
			}
		}
		return selectedPasswords;
	}

	public void checkedAll(boolean check) {
		for (int i = 0; i < checkboxPasswords.size(); i++) {
			final int pos = checkboxPasswords.keyAt(i);
			final CheckBox checkBox = checkboxPasswords.get(pos);
			checkBox.setChecked(check);
		}
	}

}
