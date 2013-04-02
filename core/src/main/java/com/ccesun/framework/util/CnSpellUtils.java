package com.ccesun.framework.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class CnSpellUtils {

	private static final HanyuPinyinOutputFormat hanyuPinyinOutputFormat;

	static {
		hanyuPinyinOutputFormat = new HanyuPinyinOutputFormat();
		hanyuPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		hanyuPinyinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		hanyuPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	}

	public static String getFullCnSpell(String source) {
		StringBuilder result = new StringBuilder();
		char[] sourceCharArray = source.toCharArray();

		for (char c : sourceCharArray) {
			try {
				String[] tmpArray = PinyinHelper.toHanyuPinyinStringArray(c,
						hanyuPinyinOutputFormat);
				if (tmpArray != null && tmpArray.length > 0)
					result.append(tmpArray[0]);
			} catch (BadHanyuPinyinOutputFormatCombination e) {
			}
		}

		return result.toString();
	}

	public static String getFirstCnSpell(String source) {
		StringBuilder result = new StringBuilder();
		char[] sourceCharArray = source.toCharArray();

		for (char c : sourceCharArray) {
			try {
				if (c > 128) {
					String[] tmpArray = PinyinHelper.toHanyuPinyinStringArray(
							c, hanyuPinyinOutputFormat);
					if (tmpArray != null && tmpArray.length > 0) {
						String v = tmpArray[0];
						if (v.length() > 0)
							result.append(v.charAt(0));

					}
				} else {
					result.append(c);
				}
			} catch (BadHanyuPinyinOutputFormatCombination e) {
			}
		}

		return result.toString();
	}

}
