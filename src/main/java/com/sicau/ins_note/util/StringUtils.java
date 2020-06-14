package com.sicau.ins_note.util;

public class StringUtils {

	/**
	 * 判空操作
	 */
	public static boolean isBlank(String value) {
		return value == null || "".equals(value) || "null".equals(value) || "undefined".equals(value);
	}

}
