package br.com.doublelogic.spg.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.doublelogic.spg.R;
import br.com.doublelogic.spg.bean.PasswordSettings;

public class SavePasswordsAdapter extends BaseAdapter {

    private final Context context;

    private final List<PasswordSettings> savePasswords;
    private final SparseBooleanArray passwords;
    private final SparseArray<CheckBox> checkboxPasswords;

    public SavePasswordsAdapter(Context context) {
        this.context = context;

        savePasswords = new ArrayList<>();
        checkboxPasswords = new SparseArray<>();
        passwords = new SparseBooleanArray();
    }

    @Override
    public int getCount() {
        if(savePasswords != null) {
            return savePasswords.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return savePasswords.get(position);
    }

    @Override
    public long getItemId(int position) {
        PasswordSettings password = savePasswords.get(position);
        if(password != null) {
            return password.getId();
        }
        return 0;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        passwords.clear();
        checkboxPasswords.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_save_password, null);
        }
        final CheckBox checkboxPassword = (CheckBox) convertView.findViewById(R.id.checkBoxPassword);
        checkboxPassword.setTag(position);
        checkboxPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final Integer position = (Integer) buttonView.getTag();
                passwords.put(position, isChecked);
            }
        });
        checkboxPassword.setChecked(passwords.get(position));
        checkboxPasswords.put(position, checkboxPassword);

        PasswordSettings password = (PasswordSettings) getItem(position);

        final TextView passwordIdent = (TextView) convertView.findViewById(R.id.textViewPasswordIdent);
        passwordIdent.setText(password.getName());

        final TextView passwordDesc = (TextView) convertView.findViewById(R.id.textViewPasswordDesc);
        passwordDesc.setText(String.valueOf(password.getPasswords().size()));

        final TextView passwordRegex = (TextView) convertView.findViewById(R.id.textViewPasswordRegex);
        passwordRegex.setText(password.getRegEx());

        return convertView;
    }

    public List<PasswordSettings> getSelectedPasswords() {
        final List<PasswordSettings> selectedPasswords = new ArrayList<>(passwords.size());
        for (int i = 0; i < passwords.size(); i++) {
            final int pos = passwords.keyAt(i);
            if(passwords.get(pos)) {
                selectedPasswords.add(savePasswords.get(pos));
            }
        }
        return selectedPasswords;
    }

    public void checkedAll(boolean check) {
        for(int i = 0; i < savePasswords.size(); i++) {
            passwords.put(i, check);
            if(i < checkboxPasswords.size()) {
                final int pos = checkboxPasswords.keyAt(i);
                final CheckBox checkBox = checkboxPasswords.get(pos);
                checkBox.setChecked(check);
            }
        }
    }

    public void setSavePasswords(List<PasswordSettings> savePasswords) {
        this.savePasswords.clear();
        if(savePasswords != null)
            this.savePasswords.addAll(savePasswords);
        notifyDataSetChanged();
    }
}
