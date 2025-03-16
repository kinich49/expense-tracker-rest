package mx.kinich49.expensetracker.validations.monthlybudgetservice.validators;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.web.ErrorWrapper;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetRequest;
import mx.kinich49.expensetracker.validations.monthlybudgetservice.BudgetValidatorParameter;
import mx.kinich49.expensetracker.validations.monthlybudgetservice.MonthlyBudgetServiceErrorCodes;
import mx.kinich49.expensetracker.validations.monthlybudgetservice.conditions.*;
import mx.kinich49.expensetracker.validations.monthlybudgetservice.providers.UpdateBudgetConditionProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Month;
import java.time.YearMonth;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class UpdateBudgetValidatorTest {

    UpdateBudgetValidator subject;
    UpdateBudgetConditionProvider conditionProvider;

    @Mock
    UpdateBudgetLimitCondition updateBudgetLimitCondition;
    @Mock
    BudgetRequestBeginDateCondition beginDateCondition;
    @Mock
    BudgetRequestBaseLimitCondition baseLimitCondition;
    @Mock
    BudgetRequestEndDateCondition endDateCondition;

    @BeforeEach
    public void setUp() {
        conditionProvider = new UpdateBudgetConditionProvider(updateBudgetLimitCondition,
                beginDateCondition, baseLimitCondition, endDateCondition);

        subject = new UpdateBudgetValidator(conditionProvider);
    }

    @DisplayName("Sanity test")
    @Test
    public void sanityTest() {
        assertNotNull(subject);
    }

    @DisplayName("Should complete when request is valid")
    @Test
    public void shouldComplete_whenRequestIsValid() throws ValidationFlowException {
        //given
        var request = new MonthlyBudgetRequest(1L, YearMonth.of(2021, Month.JANUARY),
                YearMonth.of(2021, Month.DECEMBER), "Test request", 1000);
        var condition = new BudgetRequestConditionParameter(request);

        lenient().when(updateBudgetLimitCondition.assertCondition(
                        eq(condition)))
                .thenReturn(Optional.empty());

        lenient().when(beginDateCondition.assertCondition(
                        eq(condition)))
                .thenReturn(Optional.empty());

        lenient().when(baseLimitCondition.assertCondition(
                        eq(condition)))
                .thenReturn(Optional.empty());

        lenient().when(endDateCondition.assertCondition(
                        eq(condition)))
                .thenReturn(Optional.empty());

        //when
        var validationParameter = new BudgetValidatorParameter(request);

        //then
        assertDoesNotThrow(() -> subject.validate(validationParameter));
    }

    @DisplayName("Should throw exception when UpdateBudgetLimitCondition fails")
    @Test
    public void shouldThrowException_whenUpdateBudgetLimitConditionFails()
            throws ValidationFlowException {
        //given
        var request = new MonthlyBudgetRequest(1L, YearMonth.of(2021, Month.JANUARY),
                YearMonth.of(2021, Month.DECEMBER), "Test request", 1000);
        var condition = new BudgetRequestConditionParameter(request);

        var errorWrapper = new ErrorWrapper(MonthlyBudgetServiceErrorCodes.BASE_LIMIT_LESS_THAN_ZERO,
                "Something went wrong");

        lenient().when(updateBudgetLimitCondition.assertCondition(
                        eq(condition)))
                .thenReturn(Optional.of(errorWrapper));

        lenient().when(beginDateCondition.assertCondition(
                        eq(condition)))
                .thenReturn(Optional.empty());

        lenient().when(baseLimitCondition.assertCondition(
                        eq(condition)))
                .thenReturn(Optional.empty());

        lenient().when(endDateCondition.assertCondition(
                        eq(condition)))
                .thenReturn(Optional.empty());

        var validatorParameter = new BudgetValidatorParameter(request);

        //when
        var result = assertThrows(BusinessException.class,
                () -> subject.validate(validatorParameter));

        //then
        assertEquals(errorWrapper.getErrorMessage(), result.getMessage());
    }

    @DisplayName("Should throw exception when BeginDateCondition fails")
    @Test
    public void shouldThrowException_whenBeginDateConditionFails()
            throws ValidationFlowException {
        //given
        var request = new MonthlyBudgetRequest(1L, YearMonth.of(2021, Month.JANUARY),
                YearMonth.of(2021, Month.DECEMBER), "Test request", 1000);
        var condition = new BudgetRequestConditionParameter(request);

        var errorWrapper = new ErrorWrapper(MonthlyBudgetServiceErrorCodes.BASE_LIMIT_LESS_THAN_ZERO,
                "Something went wrong");

        lenient().when(updateBudgetLimitCondition.assertCondition(
                        eq(condition)))
                .thenReturn(Optional.empty());

        lenient().when(beginDateCondition.assertCondition(
                        eq(condition)))
                .thenReturn(Optional.of(errorWrapper));

        lenient().when(baseLimitCondition.assertCondition(
                        eq(condition)))
                .thenReturn(Optional.empty());

        lenient().when(endDateCondition.assertCondition(
                        eq(condition)))
                .thenReturn(Optional.empty());

        var validatorParameter = new BudgetValidatorParameter(request);

        //when
        var result = assertThrows(BusinessException.class,
                () -> subject.validate(validatorParameter));

        //then
        assertEquals(errorWrapper.getErrorMessage(), result.getMessage());
    }

    @DisplayName("Should throw exception when BaseLimitCondition fails")
    @Test
    public void shouldThrowException_whenBaseLimitConditionFails()
            throws ValidationFlowException {
        //given
        var request = new MonthlyBudgetRequest(1L, YearMonth.of(2021, Month.JANUARY),
                YearMonth.of(2021, Month.DECEMBER), "Test request", 1000);
        var condition = new BudgetRequestConditionParameter(request);

        var errorWrapper = new ErrorWrapper(MonthlyBudgetServiceErrorCodes.BASE_LIMIT_LESS_THAN_ZERO,
                "Something went wrong");

        lenient().when(updateBudgetLimitCondition.assertCondition(
                        eq(condition)))
                .thenReturn(Optional.empty());

        lenient().when(beginDateCondition.assertCondition(
                        eq(condition)))
                .thenReturn(Optional.empty());

        lenient().when(baseLimitCondition.assertCondition(
                        eq(condition)))
                .thenReturn(Optional.of(errorWrapper));

        lenient().when(endDateCondition.assertCondition(
                        eq(condition)))
                .thenReturn(Optional.empty());

        var validatorParameter = new BudgetValidatorParameter(request);

        //when
        var result = assertThrows(BusinessException.class,
                () -> subject.validate(validatorParameter));

        //then
        assertEquals(errorWrapper.getErrorMessage(), result.getMessage());
    }

    @DisplayName("Should throw exception when EndDateCondition fails")
    @Test
    public void shouldThrowException_whenEndDateConditionFails()
            throws ValidationFlowException {
        //given
        var request = new MonthlyBudgetRequest(1L, YearMonth.of(2021, Month.JANUARY),
                YearMonth.of(2021, Month.DECEMBER), "Test request", 1000);
        var condition = new BudgetRequestConditionParameter(request);

        var errorWrapper = new ErrorWrapper(MonthlyBudgetServiceErrorCodes.BASE_LIMIT_LESS_THAN_ZERO,
                "Something went wrong");

        lenient().when(updateBudgetLimitCondition.assertCondition(
                        eq(condition)))
                .thenReturn(Optional.empty());

        lenient().when(beginDateCondition.assertCondition(
                        eq(condition)))
                .thenReturn(Optional.empty());

        lenient().when(baseLimitCondition.assertCondition(
                        eq(condition)))
                .thenReturn(Optional.empty());

        lenient().when(endDateCondition.assertCondition(
                        eq(condition)))
                .thenReturn(Optional.of(errorWrapper));

        var validatorParameter = new BudgetValidatorParameter(request);

        //when
        var result = assertThrows(BusinessException.class,
                () -> subject.validate(validatorParameter));

        //then
        assertEquals(errorWrapper.getErrorMessage(), result.getMessage());
    }
}
