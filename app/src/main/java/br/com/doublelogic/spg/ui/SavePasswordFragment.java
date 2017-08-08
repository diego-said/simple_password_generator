package br.com.doublelogic.spg.ui;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.doublelogic.spg.R;
import br.com.doublelogic.spg.adapter.SavePasswordsAdapter;
import br.com.doublelogic.spg.adapter.loader.SavePasswordsListener;
import br.com.doublelogic.spg.adapter.loader.SavePasswordsLoader;
import br.com.doublelogic.spg.bean.PasswordSettings;
import br.com.doublelogic.spg.db.PasswordsDbHelper;

public class SavePasswordFragment extends Fragment implements SavePasswordsListener {

    private static final int EDIT_PASSWORDS = 2;

    private ListView listViewPasswords;

    private ImageView buttonRefresh;
    private ImageView buttonSelectAll;
    private ImageView buttonEdit;
    private ImageView buttonDelete;

    private View loadingBar;
    private View loadingText;

    private View textViewNoPasswords;

    private SavePasswordsAdapter adapter;
    private SavePasswordsLoader loader;

    private boolean checkAll = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.save_passwords, container, false);

        adapter = new SavePasswordsAdapter(getActivity());

		loadUIReferences(view);

        loadPasswords();

		return view;
	}

	private void loadUIReferences(View view) {
        listViewPasswords = (ListView) view.findViewById(R.id.listViewPasswords);
        listViewPasswords.setAdapter(adapter);

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

        textViewNoPasswords = view.findViewById(R.id.textViewNoPasswords);
        textViewNoPasswords.setVisibility(View.GONE);
	}

    private void refreshClickHandler(View v) {
        loadPasswords();
    }

    private void selectAllClickHandler(View v) {
        adapter.checkedAll(checkAll = !checkAll);
    }

    private void editClickHandler(View v) {
        final List<PasswordSettings> passwords = adapter.getSelectedPasswords();
        if(passwords != null && passwords.size() > 0) {
            PasswordSettings passwordSettings = passwords.get(0);

            Intent requestResult = new Intent(getActivity(), ResultPasswordsActivity.class);
            requestResult.putExtra(PasswordSettings.KEY, passwordSettings);
            startActivityForResult(requestResult, EDIT_PASSWORDS);
        } else {
            Toast.makeText(getActivity(), getString(R.string.no_password), Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteClickHandler(View v) {
        final List<PasswordSettings> passwords = adapter.getSelectedPasswords();
        if(passwords != null && passwords.size() > 0) {
            AlertDialog deleteDialogBox =new AlertDialog.Builder(getActivity())
            .setMessage(R.string.confirm_remove_passwords)
            .setPositiveButton(R.string.button_yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String[] ids = new String[passwords.size()];
                    for(int i=0; i<passwords.size(); i++) {
                        ids[i] = String.valueOf(passwords.get(i).getId());
                    }
                    PasswordsDbHelper dbHelper = new PasswordsDbHelper(getActivity());
                    dbHelper.removePasswords(ids);
                    dialog.dismiss();
                    loadPasswords();
                }
            })
            .setNegativeButton(R.string.button_no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create();
            deleteDialogBox.show();
        } else {
            Toast.makeText(getActivity(), getString(R.string.no_password), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPreExecute() {
        loadingBar.setVisibility(View.VISIBLE);
        loadingText.setVisibility(View.VISIBLE);
        textViewNoPasswords.setVisibility(View.GONE);

        if(adapter != null) {
            adapter.setSavePasswords(null);
        }
    }

    @Override
    public void onPostExecute(List<PasswordSettings> passwordsList) {
        loadingBar.setVisibility(View.GONE);
        loadingText.setVisibility(View.GONE);

        if(adapter != null) {
            adapter.setSavePasswords(passwordsList);
        }

        if(passwordsList.size() == 0) {
            textViewNoPasswords.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_PASSWORDS) {
            loadPasswords();
        }
    }

    private void loadPasswords() {
        loader = new SavePasswordsLoader(getActivity());
        loader.addListener(this);
        loader.execute();
    }
}
