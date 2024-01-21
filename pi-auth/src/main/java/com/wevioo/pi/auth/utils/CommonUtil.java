package com.wevioo.pi.auth.utils;

import java.util.regex.Pattern;

public abstract class CommonUtil {

	/**
	 * Email pattern
	 */
	public static final Pattern MAIL_PATTERN = Pattern
			.compile("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-+]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
	/**
	 * Phone pattern
	 */
	public static final Pattern TUNISIA_PHONE_NUMBER_PATTERN = Pattern.compile("^\\+216\\d{8}$");

	/**
	 * Check the email format
	 *
	 * @param emailAddress email
	 * @return checked or not
	 */
	public static boolean checkEmailAddress(String emailAddress) {
		return MAIL_PATTERN.matcher(emailAddress).matches();
	}

	/**
	 * Check the cellPhone format
	 *
	 * @param login cellPhone
	 * @return checked or not
	 */
	public static boolean checkCellPhone(String login) {
		return TUNISIA_PHONE_NUMBER_PATTERN.matcher(login).matches();
	}

	/**
	 * private Constructor
	 */
	private CommonUtil() {

	}
}
