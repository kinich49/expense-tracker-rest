package mx.kinich49.expensetracker.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringUtils {

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static boolean isNullOrEmptyOrBlank(String s) {
        return s == null || s.isEmpty() || s.equals(" ");
    }

    public static boolean isNeitherNullNorEmpty(String s) {
        return s != null && s.length() > 0;
    }

    public static boolean isNeitherNullNorEmptyNorBlank(String s) {
        return s != null && s.length() > 0 && !s.equals(" ");
    }

    public static String formatCurrencyNumber(int number, String currency) {
        double scaledLimit = transformLimit(number);

        return String.format("%1$s%2$s %3$s",
                "$", Constants.LIMIT_FORMAT.format(scaledLimit), currency);
    }

    private static double transformLimit(int number) {
        return number / Constants.PRICE_SCALE;
    }
}
