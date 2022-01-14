package mx.kinich49.expensetracker.utils;

public class NumberUtils {

    public static boolean isEitherNullZeroOrNegative(Long subject) {
        return subject == null ||
                subject <= 0;

    }

    public static boolean isNeitherNullZeroOrNegative(Long subject) {
        return !isEitherNullZeroOrNegative(subject);
    }
}
