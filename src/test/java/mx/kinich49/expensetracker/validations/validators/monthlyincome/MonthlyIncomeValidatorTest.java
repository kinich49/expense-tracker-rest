package mx.kinich49.expensetracker.validations.validators.monthlyincome;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.web.requests.MonthlyIncomeRequest;
import mx.kinich49.expensetracker.validations.conditions.monthlyincome.CollisionConditionImpl;
import mx.kinich49.expensetracker.validations.conditions.monthlyincome.RequestConditionImpl;
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

    MonthlyIncomeValidatorImpl subject;
    @Mock
    RequestConditionImpl requestCondition;
    @Mock
    CollisionConditionImpl collisionCondition;

    MonthlyIncomeConditionProviderImpl conditionProvider;

    @BeforeEach
    void setup() {
        conditionProvider = Mockito.spy(new MonthlyIncomeConditionProviderImpl(requestCondition, collisionCondition));
        subject = new MonthlyIncomeValidatorImpl(conditionProvider);
    }

    @Test
    @DisplayName("Sanity test")
    public void sanityTest() {
        assertNotNull(subject);
    }

    @Test
    @DisplayName("Should complete without BusinessException")
    public void shouldComplete_withoutException() {
        //given
        MonthlyIncomeRequest request = new MonthlyIncomeRequest();
        request.setBeginDate(YearMonth.of(2020, 1));
        request.setUpperIncomeLimit(100000);
        MonthlyIncomeValidatorImpl.Parameter parameter = new MonthlyIncomeValidatorImpl.Parameter(request);

        when(requestCondition.assertCondition(any(RequestConditionImpl.Parameter.class)))
                .thenReturn(Optional.empty());

        when(collisionCondition.assertCondition(any(CollisionConditionImpl.Parameter.class)))
                .thenReturn(Optional.empty());

        //when and then
        assertDoesNotThrow(() -> subject.validate(parameter));
    }

    @Test
    @DisplayName("Should complete without BusinessException")
    public void shouldFail_when_requestIsInvalid() {
        //given
        MonthlyIncomeRequest request = new MonthlyIncomeRequest();
        request.setBeginDate(YearMonth.of(2020, 1));
        request.setUpperIncomeLimit(100000);
        MonthlyIncomeValidatorImpl.Parameter parameter = new MonthlyIncomeValidatorImpl.Parameter(request);

        String errorMessage = "Error with request";
        when(requestCondition.assertCondition(any(RequestConditionImpl.Parameter.class)))
                .thenReturn(Optional.of(errorMessage));

        lenient().when(collisionCondition.assertCondition(any(CollisionConditionImpl.Parameter.class)))
                .thenReturn(Optional.empty());

        //when
        Exception exception = assertThrows(BusinessException.class, () ->
                subject.validate(parameter));

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Should complete without BusinessException")
    public void shouldFail_when_collisionWithIncome() {
        //given
        MonthlyIncomeRequest request = new MonthlyIncomeRequest();
        request.setBeginDate(YearMonth.of(2020, 1));
        request.setUpperIncomeLimit(100000);
        MonthlyIncomeValidatorImpl.Parameter parameter = new MonthlyIncomeValidatorImpl.Parameter(request);

        String errorMessage = "Error with collision";
        lenient().when(requestCondition.assertCondition(any(RequestConditionImpl.Parameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(collisionCondition.assertCondition(any(CollisionConditionImpl.Parameter.class)))
                .thenReturn(Optional.of(errorMessage));

        //when
        Exception exception = assertThrows(BusinessException.class, () ->
                subject.validate(parameter));

        assertEquals(errorMessage, exception.getMessage());
    }
}

