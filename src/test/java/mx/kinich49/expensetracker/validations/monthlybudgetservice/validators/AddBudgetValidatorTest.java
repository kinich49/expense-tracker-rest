package mx.kinich49.expensetracker.validations.monthlybudgetservice.validators;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.internal.ErrorWrapper;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetRequest;
import mx.kinich49.expensetracker.validations.monthlybudgetservice.BudgetValidatorParameter;
import mx.kinich49.expensetracker.validations.monthlybudgetservice.MonthlyBudgetServiceErrorCodes;
import mx.kinich49.expensetracker.validations.monthlybudgetservice.conditions.*;
import mx.kinich49.expensetracker.validations.monthlybudgetservice.providers.AddBudgetValidatorConditionProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Month;
import java.time.YearMonth;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddBudgetValidatorTest {

    AddBudgetValidator subject;
    AddBudgetValidatorConditionProvider conditionProvider;

    @Mock
    BudgetLimitCondition budgetLimitCondition;
    @Mock
    BudgetRequestBaseLimitCondition baseLimitCondition;
    @Mock
    BudgetRequestBeginDateCondition beginDateCondition;
    @Mock
    BudgetRequestEndDateCondition endDateCondition;

    @BeforeEach
    void setUp() {
        conditionProvider = Mockito
                .spy(new AddBudgetValidatorConditionProvider(budgetLimitCondition, baseLimitCondition,
                        beginDateCondition, endDateCondition));

        subject = new AddBudgetValidator(conditionProvider);
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
        MonthlyBudgetRequest request = new MonthlyBudgetRequest(null,
                YearMonth.of(2021, Month.JANUARY), YearMonth.of(2021, Month.DECEMBER),
                "Test title", 100000);

        var parameter = new BudgetRequestConditionParameter(request);

        when(baseLimitCondition.assertCondition(
                eq(parameter)))
                .thenReturn(Optional.empty());

        when(budgetLimitCondition.assertCondition(
                eq(parameter)))
                .thenReturn(Optional.empty());

        when(beginDateCondition.assertCondition(
                eq(parameter)))
                .thenReturn(Optional.empty());

        when(endDateCondition.assertCondition(
                eq(parameter)))
                .thenReturn(Optional.empty());
        //when
        assertDoesNotThrow(() ->
                subject.validate(new BudgetValidatorParameter(request)));
    }

    @Test
    @DisplayName("Should throw exception when request fails BudgetLimitCondition")
    public void shouldThrowException_whenRequestFailsBudgetLimitCondition() throws ValidationFlowException {
        //given
        MonthlyBudgetRequest request = new MonthlyBudgetRequest(null,
                YearMonth.of(2021, Month.JANUARY), YearMonth.of(2021, Month.DECEMBER),
                "Test title", 100000);


        var errorWrapper = new ErrorWrapper(MonthlyBudgetServiceErrorCodes.BASE_LIMIT_LESS_THAN_ZERO,
                "Something went wrong");
        var parameter = new BudgetRequestConditionParameter(request);

        lenient().when(budgetLimitCondition.assertCondition(
                        eq(new BudgetRequestConditionParameter(request))))
                .thenReturn(Optional.of(errorWrapper));

        lenient().when(baseLimitCondition.assertCondition(
                        eq(parameter)))
                .thenReturn(Optional.empty());

        lenient().when(beginDateCondition.assertCondition(
                        eq(parameter)))
                .thenReturn(Optional.empty());

        lenient().when(endDateCondition.assertCondition(
                        eq(parameter)))
                .thenReturn(Optional.empty());

        //when
        BusinessException exception = assertThrows(BusinessException.class, () ->
                subject.validate(new BudgetValidatorParameter(request)));

        //then
        assertNotNull(exception);
        assertEquals(errorWrapper.getErrorMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when request fails BaseLimitCondition")
    public void shouldThrowException_whenRequestFailsBaseLimitCondition() throws ValidationFlowException {
        //given
        MonthlyBudgetRequest request = new MonthlyBudgetRequest(null,
                YearMonth.of(2021, Month.JANUARY), YearMonth.of(2021, Month.DECEMBER),
                "Test title", 100000);


        var errorWrapper = new ErrorWrapper(MonthlyBudgetServiceErrorCodes.BASE_LIMIT_LESS_THAN_ZERO,
                "Something went wrong");
        var parameter = new BudgetRequestConditionParameter(request);

        lenient().when(budgetLimitCondition.assertCondition(
                        eq(new BudgetRequestConditionParameter(request))))
                .thenReturn(Optional.empty());

        lenient().when(baseLimitCondition.assertCondition(
                        eq(parameter)))
                .thenReturn(Optional.of(errorWrapper));

        lenient().when(beginDateCondition.assertCondition(
                        eq(parameter)))
                .thenReturn(Optional.empty());

        lenient().when(endDateCondition.assertCondition(
                        eq(parameter)))
                .thenReturn(Optional.empty());

        //when
        BusinessException exception = assertThrows(BusinessException.class, () ->
                subject.validate(new BudgetValidatorParameter(request)));

        //then
        assertNotNull(exception);
        assertEquals(errorWrapper.getErrorMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when request fails BeginDateCondition")
    public void shouldThrowException_whenRequestFailsBeginDateCondition() throws ValidationFlowException {
        //given
        MonthlyBudgetRequest request = new MonthlyBudgetRequest(null,
                YearMonth.of(2021, Month.JANUARY), YearMonth.of(2021, Month.DECEMBER),
                "Test title", 100000);


        var errorWrapper = new ErrorWrapper(MonthlyBudgetServiceErrorCodes.BASE_LIMIT_LESS_THAN_ZERO,
                "Something went wrong");
        var parameter = new BudgetRequestConditionParameter(request);

        lenient().when(budgetLimitCondition.assertCondition(
                        eq(parameter)))
                .thenReturn(Optional.empty());

        lenient().when(baseLimitCondition.assertCondition(
                        eq(parameter)))
                .thenReturn(Optional.empty());

        lenient().when(beginDateCondition.assertCondition(
                        eq(parameter)))
                .thenReturn(Optional.of(errorWrapper));

        lenient().when(endDateCondition.assertCondition(
                        eq(parameter)))
                .thenReturn(Optional.empty());

        //when
        BusinessException exception = assertThrows(BusinessException.class, () ->
                subject.validate(new BudgetValidatorParameter(request)));

        //then
        assertNotNull(exception);
        assertEquals(errorWrapper.getErrorMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when request fails EndDateCondition")
    public void shouldThrowException_whenRequestFailsEndDateCondition() throws ValidationFlowException {
        //given
        MonthlyBudgetRequest request = new MonthlyBudgetRequest(null,
                YearMonth.of(2021, Month.JANUARY), YearMonth.of(2021, Month.DECEMBER),
                "Test title", 100000);


        var errorWrapper = new ErrorWrapper(MonthlyBudgetServiceErrorCodes.BASE_LIMIT_LESS_THAN_ZERO,
                "Something went wrong");
        var parameter = new BudgetRequestConditionParameter(request);

        lenient().when(budgetLimitCondition.assertCondition(
                        eq(parameter)))
                .thenReturn(Optional.empty());

        lenient().when(baseLimitCondition.assertCondition(
                        eq(parameter)))
                .thenReturn(Optional.empty());

        lenient().when(beginDateCondition.assertCondition(
                        eq(parameter)))
                .thenReturn(Optional.empty());

        lenient().when(endDateCondition.assertCondition(
                        eq(parameter)))
                .thenReturn(Optional.of(errorWrapper));

        //when
        BusinessException exception = assertThrows(BusinessException.class, () ->
                subject.validate(new BudgetValidatorParameter(request)));

        //then
        assertNotNull(exception);
        assertEquals(errorWrapper.getErrorMessage(), exception.getMessage());
    }
}
