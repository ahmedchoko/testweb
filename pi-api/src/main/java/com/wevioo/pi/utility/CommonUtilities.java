package com.wevioo.pi.utility;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

import com.wevioo.pi.domain.entity.referential.Bank;
import com.wevioo.pi.domain.entity.request.referential.SpecificReferential;
import com.wevioo.pi.domain.enumeration.Language;
import com.wevioo.pi.domain.enumeration.OperationType;
import com.wevioo.pi.domain.enumeration.PersonType;
import com.wevioo.pi.domain.enumeration.PersonTypeEnum;
import com.wevioo.pi.domain.enumeration.TypeAdministrator;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import com.wevioo.pi.rest.dto.BankDto;

public final class CommonUtilities {

    private CommonUtilities() {
        super();
    }

    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    public static final String PHONE_NUMBER_PATTERN = "^\\+\\d{10,15}$";
    public static final String TUNISIA_PHONE_NUMBER_PATTERN = "^\\+216\\d{8}$";
    public static final String FRANCE_PHONE_NUMBER_PATTERN = "^\\+33\\d{9}$";

    private static final String ALPHA_NUMERIC_PATTERN = "^[a-zA-Z0-9@#$%^&+=]+$";
    private static final String SOCIAL_REASON_PATTERN = "^[^€]*$";
    private static final String UPPERCASE_ALPHANUMERIC_PATTERN = "^[A-Z0-9]*$";

    public static final String IPADDRESS_RANGE_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\/" + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    public static final String LONG_NUMBER_PATTERN = "^\\d*$";
    public static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+-=])[A-Za-z\\d!@#$%^&*()_+-=]{8,}$";
    public static final String NUMERO_DOMICILIATION_PATTERN = "^[A-Za-z0-9]{6}$";

    /**
     * Validate email
     */
    public static boolean isValidEmail(String email) {
        return isValidFormat(email, EMAIL_PATTERN);
    }

    /**
     * Validate AlphaNumeriqueValue
     */
    public static boolean isValidAlphaNumeriqueValue(String str) {
        return isValidFormat(str, ALPHA_NUMERIC_PATTERN);
    }

    /**
     * Validate decimal Number
     */
    public static boolean isValidDecimalNumber(float input, int maxIntegerDigits, int maxFractionDigits) {
        String inputString = String.valueOf(input);
        String regex = "^\\d{1," + maxIntegerDigits + "}(\\.\\d{1," + maxFractionDigits + "})?$";
        return inputString.matches(regex);
    }

    /**
     * Validate Big decimal Number
     */
    public static boolean isValidBigDecimalNumber(BigDecimal input, int maxIntegerDigits, int maxFractionDigits) {
        String inputString = input.toPlainString();
        String regex = "^\\d{1," + maxIntegerDigits + "}(\\.\\d{1," + maxFractionDigits + "})?$";
        return inputString.matches(regex);
    }

    /**
     * Control the Validation On phone format
     *
     * @param phone
     * @return true if the format of the input match the pattern. Otherwise, false.
     */
    public static boolean isValidPhoneNumber(final String phone) {
        return isValidFormat(phone, PHONE_NUMBER_PATTERN);
    }

    public static boolean isValidTunisianPhoneNumber(final String phone) {
        return isValidFormat(phone, TUNISIA_PHONE_NUMBER_PATTERN);
    }

    public static boolean isValidFrancePhoneNumber(String phone) {
        return isValidFormat(phone, FRANCE_PHONE_NUMBER_PATTERN);
    }

    /**
     * Control the length on Fields
     *
     * @param str
     * @param maxLength
     * @return true if the input length is equal to param length
     */
    public static boolean isValidLength(final String str, final int maxLength) {
        if (isNullOrEmpty(str)) {
            return false;
        }
        return (str.length() <= maxLength);
    }

    public static boolean isNull(final Long input) {
        return (input == null);
    }

    public static boolean isNull(final Locale input) {
        return (input == null);
    }

    public static boolean isValidLocale(final Locale input) {
        return (input  instanceof  Locale);
    }

    public static boolean isValidSocialReason(final String str) {
        return isValidFormat(str, SOCIAL_REASON_PATTERN);
    }

    public static boolean isValidFormat(final String str, final String strPattern) {
        Pattern pattern = Pattern.compile(strPattern);
        if (str == null) {
            return false;
        }
        return pattern.matcher(str).matches();
    }

    /**
     *
     * @param str
     * @return true if str is null or str.length() = 0, otherwise false.
     */
    public static boolean isNullOrEmpty(final String str) {
        return (str == null || str.isEmpty());
    }
    public  static  boolean isNull(final  Integer number){
        return (number == null || number ==0);
    }

    public static boolean areNullOrEmpty(final String... strs) {
        for (String str : strs) {
            if (!isNullOrEmpty(str)) {
                return false;
            }
        }
        return true;

    }

    /**
     *
     * @param number
     * @return true if input is null otherwise false.
     */
    public static boolean isNull(final Float number) {
        return (number == null);
    }

    /**
     *
     * @param number
     * @return true if input is null otherwise false.
     */
    public static boolean isNull(final BigDecimal number) {
        return (number == null);
    }

    /**
     *
     * @param input
     * @return true if input is null otherwise false.
     */
    public static boolean isNull(final Boolean input) {
        return (input == null);
    }

    /**
     *
     * @param str1
     * @param str2
     * @return return true if the string str1 equals str2, otherwise false
     */
    public static boolean compareTo(String str1, String str2) {
        return str1.equalsIgnoreCase(str2);
    }
    public static boolean compareToActualDate(Date date) {return date.after(new Date());}


    public static boolean areNullDate(final Date... dates) {
        for (Date d : dates) {
            if (d == null || d.getTime() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     */
    public static boolean isValidPassword(String pwd) {
        return isValidFormat(pwd, PASSWORD_PATTERN);
    }
    public static boolean isValidUppercasephanumeric(String socialReason){return isValidFormat(socialReason,UPPERCASE_ALPHANUMERIC_PATTERN);}
    public static boolean isNullOrEmptyDate(Date date) {
        return (date == null || date.getTime() == 0);
    }

    public static String dateToString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        return formatter.format(date);
    }


    public  static boolean isNull(UserTypeEnum userTypeEnum) {return  userTypeEnum == null; }
    public  static boolean isNull(PersonType personTypeEnum) {return  personTypeEnum == null; }

    public static boolean isNull(PersonTypeEnum personTypeEnum) {return personTypeEnum == null; }

    public static boolean isNull(OperationType operationType) {return operationType == null; }


    public  static boolean isNull( TypeAdministrator  typeAdministrator) {return  typeAdministrator == null; }
    public  static  boolean isNull (Bank bank) {
        return bank == null || bank.getId() == null;
    }

    public static boolean existIdInSpecificReferential(List<SpecificReferential> specificReferentials, Long id) {
        return specificReferentials.stream().anyMatch(c -> Objects.equals(c.getId(), id));
    }



    public  static  boolean isNull (BankDto bank) {
        return bank == null || bank.getId() == null;
    }
    public  static  boolean isNull (Language language) {
        return language == null;
    }

    public static boolean isInvestorForeign(PersonTypeEnum investorType) {
        return (investorType == PersonTypeEnum.PP_NON_RESIDENT_FOREIGN || investorType == PersonTypeEnum.PM_NON_RESIDENT_FOREIGN);
    }

}
