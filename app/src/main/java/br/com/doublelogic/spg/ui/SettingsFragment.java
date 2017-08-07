package br.com.doublelogic.spg.ui;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import br.com.doublelogic.spg.R;
import br.com.doublelogic.spg.common.Preferences;

public class SettingsFragment extends Fragment {

    private CheckBox checkBoxKeepSettings;
    private CheckBox checkBoxNoRepeat;

    private SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings, container, false);

        preferences = getActivity().getSharedPreferences(Preferences.SETTINGS.name(), Context.MODE_PRIVATE);

        loadUIReferences(view);

        return view;
    }

    private void loadUIReferences(View view) {
        checkBoxKeepSettings = (CheckBox) view.findViewById(R.id.checkBoxKeepSettings);
        checkBoxKeepSettings.setChecked(preferences.getBoolean(Preferences.KEEP_LAST_CONFIG.name(), false));
        checkBoxKeepSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences();
            }
        });

        checkBoxNoRepeat = (CheckBox) view.findViewById(R.id.checkBoxNoRepeat);
        checkBoxNoRepeat.setChecked(preferences.getBoolean(Preferences.NO_REPEAT.name(), false));
        checkBoxNoRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences();
            }
        });
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Preferences.KEEP_LAST_CONFIG.name(), checkBoxKeepSettings.isChecked());
        editor.putBoolean(Preferences.NO_REPEAT.name(), checkBoxNoRepeat.isChecked());
        editor.commit();
    }

}