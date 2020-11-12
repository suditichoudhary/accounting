package com.nagarro.account.utils;

import java.util.regex.Pattern;

public class DateFormatUtil {
	private static Pattern DATE_ACCOUNT = Pattern.compile(
			"^\\d{2}.\\d{2}.\\d{4}$");

	public static boolean dateMatches(String date) {
		return DATE_ACCOUNT.matcher(date).matches();
	}
}
