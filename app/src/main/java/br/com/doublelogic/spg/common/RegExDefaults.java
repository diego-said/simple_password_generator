package br.com.doublelogic.spg.common;

public enum RegExDefaults {

	DEFAULT_REG_EX("[a-zA-Z0-9!@#$%&]"),
	
	LETTERS_REG_EX("[A-Z]"),
	SMALL_LETTERS_REG_EX("[a-z]"),
	NUMBERS_REG_EX("[0-9]"),
	SPECIAL_CHAR_REG_EX("[!@#$%&]"),

	LETTERS_NUMBERS_REG_EX("[A-Z0-9]"),
	LETTERS_SPECIAL_CHAR_REG_EX("[A-Z!@#$%&]"),
	LETTERS_NUMBERS_SPECIAL_CHAR_REG_EX("[A-Z0-9!@#$%&]"),

	SMALL_LETTERS_NUMBERS_REG_EX("[a-z0-9]"),
	SMALL_LETTERS_SPECIAL_CHAR_REG_EX("[a-z!@#$%&]"),
	SMALL_LETTERS_NUMBERS_SPECIAL_CHAR_REG_EX("[a-z0-9!@#$%&]"),

	LETTERS_SMALL_LETTERS_NUMBERS_REG_EX("[a-zA-Z0-9]"),
	LETTERS_SMALL_LETTERS_SPECIAL_CHAR_REG_EX("[a-zA-Z!@#$%&]"),

	LETTERS_SMALL_LETTERS_REG_EX("[a-zA-Z]"),
	
	NUMBERS_SPECIAL_CHAR_REG_EX("[0-9!@#$%&]");
	
	private final String regEx;
	
	RegExDefaults(String regEx) {
		this.regEx = regEx;
	}
	
	@Override
	public String toString() {
		return regEx;
	}
	
}
