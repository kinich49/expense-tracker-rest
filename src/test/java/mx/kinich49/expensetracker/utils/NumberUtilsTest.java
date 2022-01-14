package mx.kinich49.expensetracker.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("ALL")
public class NumberUtilsTest {

    @ParameterizedTest
    @MethodSource("nullZeroOrNegativeLongs")
    @DisplayName("Should return true when Long is null or zero or negative")
    public void shouldReturnTrue_whenValueIsNullOrZeroOrNegative(Long value) {
        //when
        boolean firstResult = NumberUtils.isEitherNullZeroOrNegative(value);
        assertTrue(firstResult);
    }

    @ParameterizedTest
    @MethodSource("nullZeroOrNegativeLongs")
    @DisplayName("Should return false when Long is null or zero or negative")
    public void shouldReturnFalse_whenValueIsNullOrZeroOrNegative(Long value) {
        //when
        boolean result = NumberUtils.isNeitherNullZeroOrNegative(value);
        assertFalse(result);
    }

    @ParameterizedTest
    @MethodSource("onlyPositiveNonNullLongs")
    @DisplayName("Should return true when Long is neither null nor zero nor negative")
    public void shouldReturnTrue_whenValueIsNeitherlOrZeroOrNegative(Long value) {
        //when
        boolean firstResult = NumberUtils.isNeitherNullZeroOrNegative(value);
        assertTrue(firstResult);
    }

    @ParameterizedTest
    @MethodSource("onlyPositiveNonNullLongs")
    @DisplayName("Should return false when Long is neither null nor zero nor negative")
    public void shouldReturnFalse_whenValueIsNeitherNullOrZeroOrNegative(Long value) {
        //when
        boolean result = NumberUtils.isEitherNullZeroOrNegative(value);
        assertFalse(result);
    }

    public static List<Long> nullZeroOrNegativeLongs(){
        var list = new ArrayList<Long>();

        list.add(null);
        list.add(0L);
        list.add(-1L);
        list.add(Long.valueOf(-1L));
        list.add(Long.MIN_VALUE);

        return list;
    }

    public static List<Long> onlyPositiveNonNullLongs() {
        var list = new ArrayList<Long>();

        list.add(1L);
        list.add(Long.valueOf(1L));
        list.add(Long.MAX_VALUE);

        return list;
    }
}
