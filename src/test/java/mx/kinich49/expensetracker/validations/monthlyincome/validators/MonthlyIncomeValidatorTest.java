package mx.kinich49.expensetracker.validations.monthlyincome.validators;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.internal.ErrorWrapper;
import mx.kinich49.expensetracker.models.web.requests.MonthlyIncomeRequest;
import mx.kinich49.expensetracker.validations.monthlyincome.MonthlyIncomeErrorCodes;
import mx.kinich49.expensetracker.validations.monthlyincome.conditions.*;
import mx.kinich49.expensetracker.validations.monthlyincome.providers.MonthlyIncomeConditionProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.YearMonth;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MonthlyIncomeValidatorTest {

    MonthlyIncomeValidator subject;
    MonthlyIncomeConditionProvider conditionProvider;

    @Mock
    BeginDateCondition beginDateCondition;
    @Mock
    EndDateCondition endDateCondition;
    @Mock
    UpperIncomeLimitCondition upperIncomeLimitCondition;
    @Mock
    CollisionCondition collisionCondition;

    @BeforeEach
    void setup() {
        conditionProvider = Mockito.spy(new MonthlyIncomeConditionProvider(beginDateCondition,
                endDateCondition, upperIncomeLimitCondition, collisionCondition));
        subject = new MonthlyIncomeValidator(conditionProvider);
    }

    @Test
    @DisplayName("Sanity test")
    public void sanityTest() {
        assertNotNull(subject);
    }

    @Test
    @DisplayName("Should complete when conditions are met")
    public void shouldComplete_whenConditionsAreMet() throws ValidationFlowException {
        //given
        MonthlyIncomeRequest request = new MonthlyIncomeRequest();
        request.setBeginDate(YearMonth.of(2020, 1));
        request.setUpperIncomeLimit(100000);
        MonthlyIncomeValidator.Parameter parameter = new MonthlyIncomeValidator.Parameter(request);

        when(beginDateCondition
                .assertCondition(any(MonthlyIncomeRequestParameter.class)))
                .thenReturn(Optional.empty());

        when(endDateCondition
                .assertCondition(any(MonthlyIncomeRequestParameter.class)))
                .thenReturn(Optional.empty());

        when(upperIncomeLimitCondition
                .assertCondition(any(MonthlyIncomeRequestParameter.class)))
                .thenReturn(Optional.empty());

        when(collisionCondition
                .assertCondition(any(CollisionCondition.Parameter.class)))
                .thenReturn(Optional.empty());

        //when and then
        assertDoesNotThrow(() -> subject.validate(parameter));
    }

    @Test
    @DisplayName("Should throw exception when BeginDateCondition fails")
    public void shouldThrowException_whenBeginDateConditionFails() throws ValidationFlowException {
        //given
        MonthlyIncomeRequest request = new MonthlyIncomeRequest();
        request.setBeginDate(YearMonth.of(2020, 1));
        request.setUpperIncomeLimit(100000);
        MonthlyIncomeValidator.Parameter parameter = new MonthlyIncomeValidator.Parameter(request);

        var errorWrapper = new ErrorWrapper(MonthlyIncomeErrorCodes.MONTHLY_INCOME_COLLIDES_WITH_REQUEST,
                "Something went wrong.");

        lenient().when(beginDateCondition
                        .assertCondition(any(MonthlyIncomeRequestParameter.class)))
                .thenReturn(Optional.of(errorWrapper));

        lenient().when(endDateCondition
                        .assertCondition(any(MonthlyIncomeRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(upperIncomeLimitCondition
                        .assertCondition(any(MonthlyIncomeRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(collisionCondition
                        .assertCondition(any(CollisionCondition.Parameter.class)))
                .thenReturn(Optional.empty());
        //when
        Exception exception = assertThrows(BusinessException.class, () ->
                subject.validate(parameter));

        assertEquals(errorWrapper.getErrorMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when EndDateCondition fails")
    public void shouldThrowException_whenEndDateConditionFails() throws ValidationFlowException {
        //given
        MonthlyIncomeRequest request = new MonthlyIncomeRequest();
        request.setBeginDate(YearMonth.of(2020, 1));
        request.setUpperIncomeLimit(100000);
        MonthlyIncomeValidator.Parameter parameter = new MonthlyIncomeValidator.Parameter(request);

        var errorWrapper = new ErrorWrapper(MonthlyIncomeErrorCodes.MONTHLY_INCOME_COLLIDES_WITH_REQUEST,
                "Something went wrong.");

        lenient().when(beginDateCondition
                        .assertCondition(any(MonthlyIncomeRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(endDateCondition
                        .assertCondition(any(MonthlyIncomeRequestParameter.class)))
                .thenReturn(Optional.of(errorWrapper));

        lenient().when(upperIncomeLimitCondition
                        .assertCondition(any(MonthlyIncomeRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(collisionCondition
                        .assertCondition(any(CollisionCondition.Parameter.class)))
                .thenReturn(Optional.empty());
        //when
        Exception exception = assertThrows(BusinessException.class, () ->
                subject.validate(parameter));

        assertEquals(errorWrapper.getErrorMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when UpperIncomeLimitCondition fails")
    public void shouldThrowException_whenUpperIncomeLimitConditionFails() throws ValidationFlowException {
        //given
        MonthlyIncomeRequest request = new MonthlyIncomeRequest();
        request.setBeginDate(YearMonth.of(2020, 1));
        request.setUpperIncomeLimit(100000);
        MonthlyIncomeValidator.Parameter parameter = new MonthlyIncomeValidator.Parameter(request);

        var errorWrapper = new ErrorWrapper(MonthlyIncomeErrorCodes.MONTHLY_INCOME_COLLIDES_WITH_REQUEST,
                "Something went wrong.");

        lenient().when(beginDateCondition
                        .assertCondition(any(MonthlyIncomeRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(endDateCondition
                        .assertCondition(any(MonthlyIncomeRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(upperIncomeLimitCondition
                        .assertCondition(any(MonthlyIncomeRequestParameter.class)))
                .thenReturn(Optional.of(errorWrapper));

        lenient().when(collisionCondition
                        .assertCondition(any(CollisionCondition.Parameter.class)))
                .thenReturn(Optional.empty());
        //when
        Exception exception = assertThrows(BusinessException.class, () ->
                subject.validate(parameter));

        assertEquals(errorWrapper.getErrorMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when CollisionCondition fails")
    public void shouldThrowException_whenCollisionConditionFails() throws ValidationFlowException {
        //given
        MonthlyIncomeRequest request = new MonthlyIncomeRequest();
        request.setBeginDate(YearMonth.of(2020, 1));
        request.setUpperIncomeLimit(100000);
        MonthlyIncomeValidator.Parameter parameter = new MonthlyIncomeValidator.Parameter(request);

        var errorWrapper = new ErrorWrapper(MonthlyIncomeErrorCodes.MONTHLY_INCOME_COLLIDES_WITH_REQUEST,
                "Something went wrong.");

        lenient().when(beginDateCondition
                        .assertCondition(any(MonthlyIncomeRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(endDateCondition
                        .assertCondition(any(MonthlyIncomeRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(upperIncomeLimitCondition
                        .assertCondition(any(MonthlyIncomeRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(collisionCondition
                        .assertCondition(any(CollisionCondition.Parameter.class)))
                .thenReturn(Optional.of(errorWrapper));
        //when
        Exception exception = assertThrows(BusinessException.class, () ->
                subject.validate(parameter));

        assertEquals(errorWrapper.getErrorMessage(), exception.getMessage());
    }
}

