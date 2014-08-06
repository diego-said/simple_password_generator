package br.com.doublelogic.spg.generate;

import br.com.doublelogic.spg.bean.PasswordSettings;

public interface PasswordGeneratorListener {

	void onComplete(PasswordSettings p);

}
