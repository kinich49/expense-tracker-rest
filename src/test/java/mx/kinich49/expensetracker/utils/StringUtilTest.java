package mx.kinich49.expensetracker.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class StringUtilTest {

    @ParameterizedTest
    @DisplayName("Should print formatted amounts")
    @MethodSource("formattedAmountArguments")
    public void shouldPrint_formattedAmount(int amount, String currency, String formattedResult) {
        //when
        String result = StringUtils.formatCurrencyNumber(amount, currency);

        //then
        assertEquals(formattedResult, result);
    }

    @ParameterizedTest
    @DisplayName("Should validate null and empty string")
    @MethodSource("nullAndEmptyArguments")
    public void shouldValidate_nullAndEmptyString(String parameter){
        assertTrue(StringUtils.isNullOrEmpty(parameter));
        assertFalse(StringUtils.isNeitherNullNorEmpty(parameter));
    }

    @ParameterizedTest
    @MethodSource("nullAndEmptyAndBlankArguments")
    @DisplayName("Should validate null and empty and blank string")
    public void shouldValidate_nullAndEmptyAndBlank(String parameter){
        assertTrue(StringUtils.isNullOrEmptyOrBlank(parameter));
        assertFalse(StringUtils.isNeitherNullNorEmptyNorBlank(parameter));
    }

    @ParameterizedTest
    @MethodSource("neitherNullNorEmptyArguments")
    @DisplayName("Should validate is neither null nor empty")
    public void shouldValidate_isNeitherNullNorEmpty(String parameter) {
        assertTrue(StringUtils.isNeitherNullNorEmpty(parameter));
        assertFalse(StringUtils.isNullOrEmpty(parameter));
    }

    @ParameterizedTest
    @MethodSource("neitherNullNorEmptyNorBlankArguments")
    @DisplayName("Should validate is neither null nor empty nor blank")
    public void shouldValidate_isNeitherNullNorEmptyNorBlank(String parameter) {
        assertTrue(StringUtils.isNeitherNullNorEmptyNorBlank(parameter));
        assertFalse(StringUtils.isNullOrEmptyOrBlank(parameter));
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

    public static Collection<String> nullAndEmptyArguments() {
        List<String> arguments = new ArrayList<>();
        arguments.add("");
        arguments.add(null);

        return arguments;
    }

    public static Collection<String> nullAndEmptyAndBlankArguments() {
        List<String> arguments = new ArrayList<>();
        arguments.add("");
        arguments.add(" ");
        arguments.add(null);
        arguments.add("\u0020");

        return arguments;
    }

    public static Stream<Arguments> neitherNullNorEmptyArguments() {
        return Stream.of(
                Arguments.of("Test"),
                Arguments.of(" "),
                Arguments.of("\u2205")
        );
    }

    public static Stream<Arguments> neitherNullNorEmptyNorBlankArguments() {
        return Stream.of(
                Arguments.of("Test"),
                Arguments.of("\u2205"),
                Arguments.of(" This is a test ")
        );
    }
}
