package br.com.doublelogic.spg.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import br.com.doublelogic.spg.R;

public class SettingsFragment extends Fragment {

    private CheckBox checkBoxKeepSettings;
    private CheckBox checkBoxNoRepeat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings, container, false);

        loadUIReferences(view);

        return view;
    }

    private void loadUIReferences(View view) {
        checkBoxKeepSettings = (CheckBox) view.findViewById(R.id.checkBoxKeepSettings);
        checkBoxKeepSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkBoxClickHandler(v);
            }
        });

        checkBoxNoRepeat = (CheckBox) view.findViewById(R.id.checkBoxNoRepeat);
        checkBoxNoRepeat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkBoxClickHandler(v);
            }
        });
    }

    public void checkBoxClickHandler(View view) {

    }

}