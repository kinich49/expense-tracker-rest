package mx.kinich49.expensetracker.utils;

import com.sun.org.apache.xpath.internal.Arg;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilTest {

    @ParameterizedTest
    @DisplayName("Should print formatted amounts")
    @MethodSource("formattedAmountArguments")
    public void should_printFormattedAmount(int amount, String currency, String formattedResult) {
        //when
        String result = StringUtils.formatCurrencyNumber(amount, currency);

        //then
        assertEquals(formattedResult, result);
    }

    public static Stream<Arguments> formattedAmountArguments() {
        return Stream.of(
                Arguments.of("1", "MXN", "$0.01 MXN"),
                Arguments.of(50, "MXN", "$0.50 MXN"),
                Arguments.of(100, "MXN", "$1.00 MXN"),
                Arguments.of(100000, "MXN", "$1,000.00 MXN"),
                Arguments.of(100000000, "MXN", "$1,000,000.00 MXN"),
                Arguments.of(123456789, "MXN","$1,234,567.89 MXN" )
        );
    }
}
