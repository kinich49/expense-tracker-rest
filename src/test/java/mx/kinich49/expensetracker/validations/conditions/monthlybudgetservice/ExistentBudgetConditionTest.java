package mx.kinich49.expensetracker.validations.conditions.monthlybudgetservice;

import mx.kinich49.expensetracker.models.web.requests.CommercialEstablishmentRequest;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetRequest;
import mx.kinich49.expensetracker.repositories.MonthlyBudgetRepository;
import mx.kinich49.expensetracker.validations.conditions.commercialestablishmentservice.ConditionParameterImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Month;
import java.time.YearMonth;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExistentBudgetConditionTest {

    @InjectMocks
    ExistentBudgetCondition subject;

    @Mock
    MonthlyBudgetRepository repository;

    @Test
    void sanityTest() {
        assertNotNull(subject);
    }

    @Test
    @DisplayName("Should return empty when request is valid")
    void shouldReturnEmpty_whenRequestIsValid() {
        //given
        var request = new MonthlyBudgetRequest(1L, YearMonth.of(2021, Month.JANUARY),
                YearMonth.of(2021, Month.DECEMBER), "Test Title", 100000);
        var parameter = new BudgetRequestConditionParameterImpl(request);

        when(repository.existsById(eq(1L)))
                .thenReturn(true);
        //when
        var result = assertDoesNotThrow(() -> subject.assertCondition(parameter));

        //then
        assertFalse(result.isPresent());
    }

    @ParameterizedTest
    @MethodSource("notValidId")
    @DisplayName("Should return error when id is not valid")
    void shouldReturnError_whenIdIsNotValid(Long id) {
        //given
        //given
        var request = new MonthlyBudgetRequest(id, YearMonth.of(2021, Month.JANUARY),
                YearMonth.of(2021, Month.DECEMBER), "Test Title", 100000);
        var parameter = new BudgetRequestConditionParameterImpl(request);

        verify(repository, times(0)).existsById(eq(id));

        //when
        var result = assertDoesNotThrow(() -> subject.assertCondition(parameter));

        //then
        assertTrue(result.isPresent());
        var errorMessage = result.get();
        assertEquals("Invalid Id.", errorMessage.trim());
    }

    @ParameterizedTest
    @MethodSource("nonExistentID")
    @DisplayName("Should return error when id is not existent")
    void shouldReturnError_whenIdIsNotExistent(Long id) {
        //given
        //given
        var request = new MonthlyBudgetRequest(id, YearMonth.of(2021, Month.JANUARY),
                YearMonth.of(2021, Month.DECEMBER), "Test Title", 100000);
        var parameter = new BudgetRequestConditionParameterImpl(request);

        when(repository.existsById(eq(id)))
                .thenReturn(false);
        //when
        var result = assertDoesNotThrow(() -> subject.assertCondition(parameter));

        //then
        assertTrue(result.isPresent());
        var errorMessage = result.get();
        var expectedMessage = String.format("Monthly Budget with id %1$d does not exist", id);
        assertEquals(expectedMessage, errorMessage.trim());
    }

    static Stream<Arguments> notValidId() {
        return Stream.of(
                Arguments.of((Object) null),
                Arguments.of(0L),
                Arguments.of(-1L)
        );
    }

    static Stream<Arguments> nonExistentID() {
        return Stream.of(
                Arguments.of(Long.MAX_VALUE),
                Arguments.of(1L),
                Arguments.of(999_999L)
        );
    }
}
