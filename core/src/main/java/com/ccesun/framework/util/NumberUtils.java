package com.ccesun.framework.util;

public class NumberUtils extends org.apache.commons.lang.math.NumberUtils{

	public static int[] toIntArray(String[] source) {
		
		if (source == null) return new int[0];
		
		int[] result = new int[source.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = toInt(source[i]);
		}
		
		return result;
	}
	
	public static Integer[] toIntegerArray(String[] source) {
		
		if (source == null) return new Integer[0];
		
		Integer[] result = new Integer[source.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = toInt(source[i]);
		}
		
		return result;
	}
}
