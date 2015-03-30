package br.com.doublelogic.spg.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import br.com.doublelogic.spg.R;

public class SavePasswordFragment extends Fragment {

    private ListView listViewPasswords;

    private ImageView buttonRefresh;
    private ImageView buttonSelectAll;
    private ImageView buttonEdit;
    private ImageView buttonDelete;

    private View loadingBar;
    private View loadingText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.save_passwords, container, false);

		loadUIReferences(view);

		return view;
	}

	private void loadUIReferences(View view) {
        {
            listViewPasswords = (ListView) view.findViewById(R.id.listViewPasswords);
            //listViewPasswords.setAdapter(adapter);

            buttonRefresh = (ImageView) view.findViewById(R.id.buttonRefresh);
            buttonRefresh.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    refreshClickHandler(v);
                }
            });

            buttonSelectAll = (ImageView) view.findViewById(R.id.buttonSelectAll);
            buttonSelectAll.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    selectAllClickHandler(v);
                }
            });

            buttonEdit = (ImageView) view.findViewById(R.id.buttonEdit);
            buttonEdit.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    editClickHandler(v);
                }
            });

            buttonDelete = (ImageView) view.findViewById(R.id.buttonDelete);
            buttonDelete.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    deleteClickHandler(v);
                }
            });

            loadingBar = view.findViewById(R.id.loadingBar);
            loadingText = view.findViewById(R.id.loadingText);
        }
	}

    private void refreshClickHandler(View v) {
    }

    private void selectAllClickHandler(View v) {
    }

    private void editClickHandler(View v) {
    }

    private void deleteClickHandler(View v) {
    }
}
