package br.com.doublelogic.spg.bean;

import java.io.Serializable;

import br.com.doublelogic.spg.common.RegExDefaults;

public class PasswordSettings implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String KEY = "passwordSettings";
	
	private String regEx;
	
	private int length;
	private int quantity;
	
	public PasswordSettings() {
		regEx = String.valueOf(RegExDefaults.LETTERS_NUMBERS_REG_EX);
		
		length = 12;
		quantity = 5;
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
		
}