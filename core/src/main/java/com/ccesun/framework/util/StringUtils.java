package com.ccesun.framework.util;

import java.util.Random;

public class StringUtils extends org.apache.commons.lang.StringUtils {

	private static final String RANDOM_STRING_SEED = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final int RANDOM_DEFAULT_LENGTH = 16;

	public static String generateString(String characters, int length) {
		Random rng = new Random();
		char[] text = new char[length];
		for (int i = 0; i < length; i++) {
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		}
		return new String(text);
	}
	
	public static String generateString(int length) {
		return generateString(RANDOM_STRING_SEED, length);
	}
	
	public static String generateString() {
		return generateString(RANDOM_STRING_SEED, RANDOM_DEFAULT_LENGTH);
	}
	
}
