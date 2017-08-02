package br.com.doublelogic.spg.adapter.loader;

import java.util.List;

import br.com.doublelogic.spg.bean.PasswordSettings;

public interface SavePasswordsListener {

    void onPreExecute();
    void onPostExecute(List<PasswordSettings> passwordsList);

}