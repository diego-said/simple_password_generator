package br.com.doublelogic.spg.adapter.loader;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import br.com.doublelogic.spg.bean.PasswordSettings;
import br.com.doublelogic.spg.db.PasswordsDbHelper;

public class SavePasswordsLoader extends AsyncTask<Void, Void, Boolean> {

    private final PasswordsDbHelper dbHelper;

    private final List<SavePasswordsListener> listeners;

    private List<PasswordSettings> passwordsList;

    public SavePasswordsLoader(Context context) {
        this.listeners = new ArrayList<>();
        dbHelper = new PasswordsDbHelper(context);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        dbHelper.getWritableDatabase();
        passwordsList = dbHelper.getListPasswordSettings();
        return passwordsList != null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        for(SavePasswordsListener listener : listeners) {
            listener.onPreExecute();
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        for(SavePasswordsListener listener : listeners) {
            listener.onPostExecute(passwordsList);
        }
    }

    public void addListener(SavePasswordsListener l) {
        listeners.add(l);
    }
}
