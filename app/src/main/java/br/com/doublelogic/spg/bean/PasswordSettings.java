package br.com.doublelogic.spg.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.doublelogic.spg.common.RegExDefaults;

public class PasswordSettings implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String KEY = "passwordSettings";

	private long id;

	private String name;
	private String regEx;

	private int length;
	private int quantity;

	private final List<String> passwords;

	public PasswordSettings() {
		id = -1;
		name = "";
		regEx = String.valueOf(RegExDefaults.LETTERS_NUMBERS_REG_EX);
		length = 12;
		quantity = 5;

		passwords = new ArrayList<String>();
	}

	public String getRegEx() {
		return regEx;
	}

	public void setRegEx(String regEx) {
		this.regEx = regEx;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void addPassword(String p) {
		passwords.add(p);
	}

	public List<String> getPasswords() {
		return passwords;
	}

	public void clearPasswords() {
		passwords.clear();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}