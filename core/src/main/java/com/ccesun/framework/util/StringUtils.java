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
	/**
	 * 提取html中的文本信息
	 * 
	 * @param html
	 *            html信息
	 * @param length
	 *            截取的长度
	 * @return 返回截取后的字符串
	 */
	public static String getHtmlText(String html) {
		if (html == null)
			return null;
		String txtcontent = html.replaceAll("</?[^>]+>", ""); // 剔出<html>的标签
		String text = txtcontent.replaceAll("<a>\\s*|\t|\r|\n</a>", "");// 去除字符串中的空格,回车,换行符,制表符

		// Pattern pattern = Pattern.compile("\\<.[^\\<]*\\>");
		// Matcher matcher = pattern.matcher(html);
		// String text = matcher.replaceAll("");
		if (text != null) {
			text = text.replace("&nbsp;", "");
		}
		// if (length < text.length()) {
		// text = text.substring(0, length) + "...";
		// }
		return txtcontent;
	}
}
