package br.com.doublelogic.spg.common;

public enum RegExDefaults {

	DEFAULT_REG_EX("[a-zA-Z0-9!@#$%&]"),
	
	LETTERS_REG_EX("[a-zA-Z]"),
	NUMBERS_REG_EX("[0-9]"),
	SPECIAL_CHAR_REG_EX("[!@#$%&]"),
	
	LETTERS_NUMBERS_REG_EX("[a-zA-Z0-9]"),
	LETTERS_SPECIAL_CHAR_REG_EX("[a-zA-Z!@#$%&]"),
	
	NUMBERS_SPECIAL_CHAR_REG_EX("[0-9!@#$%&]");
	
	private final String regEx;
	
	private RegExDefaults(String regEx) {
		this.regEx = regEx;
	}
	
	@Override
	public String toString() {
		return regEx;
	}
	
}
