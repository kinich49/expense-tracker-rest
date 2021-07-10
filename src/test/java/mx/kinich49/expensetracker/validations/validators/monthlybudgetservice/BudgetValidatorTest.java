package mx.kinich49.expensetracker.validations.validators.monthlybudgetservice;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetRequest;
import mx.kinich49.expensetracker.validations.conditions.monthlybudgetservice.BudgetLimitCondition;
import mx.kinich49.expensetracker.validations.conditions.monthlybudgetservice.BudgetRequestCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Month;
import java.time.YearMonth;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BudgetValidatorTest {
    
    BudgetValidatorImpl subject;
    @Mock
    BudgetRequestCondition budgetRequestCondition;
    @Mock
    BudgetLimitCondition budgetLimitCondition;

    BudgetValidatorConditionProviderImpl conditionProvider;

    @BeforeEach
    void setUp() {
        conditionProvider = Mockito
                .spy(new BudgetValidatorConditionProviderImpl(budgetLimitCondition, budgetRequestCondition));

        subject = new BudgetValidatorImpl(conditionProvider);
    }

    @Test
    @DisplayName("Sanity test")
    public void sanityTest() {
        assertNotNull(subject);
    }

    @Test
    @DisplayName("Should complete when request is valid")
    public void shouldComplete_whenRequestIsValid() throws ValidationFlowException {
        //given
        MonthlyBudgetRequest request = new MonthlyBudgetRequest();
        request.setTitle("Test title");
        request.setBaseLimit(100000);
        request.setBeginDate(YearMonth.of(2021, Month.JANUARY));
        request.setEndDate(YearMonth.of(2021, Month.DECEMBER));

        when(budgetRequestCondition.assertCondition(
                eq(new BudgetRequestCondition.Parameter(request))))
                .thenReturn(Optional.empty());
        when(budgetLimitCondition.assertCondition(
                eq(new BudgetLimitCondition.Parameter(request))))
                .thenReturn(Optional.empty());

        //when
        assertDoesNotThrow(() ->
                subject.validate(new BudgetValidatorImpl.Parameter(request)));
    }

    @Test
    @DisplayName("Should throw exception when request fails RequestCondition")
    public void shouldThrowException_whenRequestFailsRequestCondition() throws ValidationFlowException {
        //given
        MonthlyBudgetRequest request = new MonthlyBudgetRequest();
        request.setTitle("Test title");
        request.setBaseLimit(100000);
        request.setBeginDate(YearMonth.of(2021, Month.JANUARY));
        request.setEndDate(YearMonth.of(2021, Month.DECEMBER));

        String errorMessage = "Request Condition failed.";
        when(budgetRequestCondition.assertCondition(
                eq(new BudgetRequestCondition.Parameter(request))))
                .thenThrow(new ValidationFlowException(errorMessage));

        lenient().when(budgetLimitCondition.assertCondition(
                eq(new BudgetLimitCondition.Parameter(request))))
                .thenReturn(Optional.empty());

        //when
        BusinessException exception = assertThrows(BusinessException.class, () ->
                subject.validate(new BudgetValidatorImpl.Parameter(request)));

        //then
        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when request fails BudgetLimitCondition")
    public void shouldThrowException_whenRequestFailsBudgetLimitCondition() throws ValidationFlowException {
        //given
        MonthlyBudgetRequest request = new MonthlyBudgetRequest();
        request.setTitle("Test title");
        request.setBaseLimit(100000);
        request.setBeginDate(YearMonth.of(2021, Month.JANUARY));
        request.setEndDate(YearMonth.of(2021, Month.DECEMBER));

        String errorMessage = "Budget Condition failed.";
        lenient().when(budgetRequestCondition.assertCondition(
                eq(new BudgetRequestCondition.Parameter(request))))
                .thenReturn(Optional.empty());

        lenient().when(budgetLimitCondition.assertCondition(
                eq(new BudgetLimitCondition.Parameter(request))))
                .thenThrow(new ValidationFlowException(errorMessage));

        //when
        BusinessException exception = assertThrows(BusinessException.class, () ->
                subject.validate(new BudgetValidatorImpl.Parameter(request)));

        //then
        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
    }
}
