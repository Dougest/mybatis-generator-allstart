package org.mybatis.generator.base.core;

public class StringUtils {
	public static boolean isBlank(String str) {
		return str == null ? true : "".equals(str);
	}
}
