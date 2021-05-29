package mx.kinich49.expensetracker.validations.conditions.monthlybudgetservice;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Month;
import java.time.YearMonth;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BudgetRequestTest {

    @InjectMocks
    BudgetRequestCondition subject;

    @Test
    public void sanityTest() {
        assertNotNull(subject);
    }

    @Test
    @DisplayName("Should return empty when request is valid")
    public void shouldReturnEmpty_whenRequestIsValid() {
        //given
        MonthlyBudgetRequest request = new MonthlyBudgetRequest();
        request.setTitle("Test title");
        request.setBaseLimit(100000);
        request.setBeginDate(YearMonth.of(2021, Month.JANUARY));
        request.setEndDate(YearMonth.of(2021, Month.DECEMBER));

        //when
        Optional<String> result = assertDoesNotThrow(() ->
                subject.assertCondition(new BudgetRequestCondition.Parameter(request)));

        //then
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should throw exception when request is null")
    public void shouldThrowException_whenRequestIsNull() {
        //when
        ValidationFlowException exception = assertThrows(ValidationFlowException.class, () -> {
            subject.assertCondition(new BudgetRequestCondition.Parameter(null));
        });

        assertNotNull(exception);
    }


    @Test
    @DisplayName("Should return error when begin date is not set")
    public void shouldReturnError_whenBeginDateIsNotSet() {
        //given
        MonthlyBudgetRequest request = new MonthlyBudgetRequest();
        request.setTitle("Test title");
        request.setBaseLimit(100000);
        request.setEndDate(YearMonth.of(2021, Month.DECEMBER));

        //when
        Optional<String> result = assertDoesNotThrow(() ->
                subject.assertCondition(new BudgetRequestCondition.Parameter(request)));

        //then
        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Should return error when baseLimit is less than zero")
    public void shouldReturnError_whenBaseLimitIsLessThanZero() {
        //given
        MonthlyBudgetRequest request = new MonthlyBudgetRequest();
        request.setTitle("Test title");
        request.setBaseLimit(-1);
        request.setBeginDate(YearMonth.of(2021, Month.JANUARY));
        request.setEndDate(YearMonth.of(2021, Month.DECEMBER));

        //when
        Optional<String> result = assertDoesNotThrow(() ->
                subject.assertCondition(new BudgetRequestCondition.Parameter(request)));

        //then
        assertTrue(result.isPresent());
    }
}