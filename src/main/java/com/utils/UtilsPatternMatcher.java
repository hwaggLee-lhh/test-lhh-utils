package com.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式
 * @author huage
 *
 */
public class UtilsPatternMatcher {
	public static void main(String[] args) {
		_patternMatcher("中超新材821313(831313)831313", "([48]{1}3[0-9]{4})");
	}
	
	
	/**
	 * 正则表达式解析
	 * @param str:需要解析的字符串
	 * @param regex:正则表达式
	 */
	public static List<String> _patternMatcher(String str,String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		List<String> strs = new ArrayList<String>();
		while (m.find()) {
			strs.add(m.group(1));
		}
		for (String s : strs) {
			System.out.println(s);
		}
		return strs;

	}
}
