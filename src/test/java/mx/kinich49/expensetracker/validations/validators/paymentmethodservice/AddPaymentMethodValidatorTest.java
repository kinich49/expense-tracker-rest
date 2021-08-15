package mx.kinich49.expensetracker.validations.validators.paymentmethodservice;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.web.requests.PaymentMethodRequest;
import mx.kinich49.expensetracker.validations.conditions.paymentmethodservice.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddPaymentMethodValidatorTest {

    AddPaymentMethodValidatorImpl subject;
    @Mock
    AddingPaymentMethodRequiredDataCondition requiredDataCondition;
    @Mock
    NotExistentPaymentMethodNameCondition notExistentNameCondition;

    AddPaymentValidatorConditionProvider conditionProvider;

    @BeforeEach
    void setUp() {
        conditionProvider = Mockito.spy(new AddPaymentValidatorConditionProvider(
                requiredDataCondition, notExistentNameCondition));

        subject = new AddPaymentMethodValidatorImpl(conditionProvider);
    }

    @Test
    @DisplayName("Should complete when conditions are met")
    void shouldComplete_whenConditionsAreMet() throws BusinessException {
        //given
        var request = new PaymentMethodRequest(1L, "Test name");
        var conditionParameter = new PaymentMethodConditionParameter(request);

        when(requiredDataCondition.assertCondition(eq(conditionParameter)))
                .thenReturn(Optional.empty());

        when(notExistentNameCondition.assertCondition(eq(conditionParameter)))
                .thenReturn(Optional.empty());

        var validatorParameter = new PaymentMethodValidatorParameter(request);

        //when and then
        assertDoesNotThrow(() -> subject.validate(validatorParameter));

    }

    @Test
    @DisplayName("Should throw exception when required data condition is not met")
    void shouldThrowError_whenRequiredDataConditionIsNotMet() throws BusinessException{
        //given
        var request = new PaymentMethodRequest(1L, "Test name");
        var conditionParameter = new PaymentMethodConditionParameter(request);

        when(requiredDataCondition.assertCondition(eq(conditionParameter)))
                .thenReturn(Optional.of("An error occurred"));

        when(notExistentNameCondition.assertCondition(eq(conditionParameter)))
                .thenReturn(Optional.empty());

        var validatorParameter = new PaymentMethodValidatorParameter(request);

        //when
        Exception result = assertThrows(BusinessException.class, () -> subject.validate(validatorParameter));

        //then
        assertNotNull(result);
    }

    @Test
    @DisplayName("Should throw exception when existent condition is not met")
    void shouldThrowError_whenRequiredExistentConditionIsNotMet() throws BusinessException{
        //given
        var request = new PaymentMethodRequest(1L, "Test name");
        var conditionParameter = new PaymentMethodConditionParameter(request);

        when(requiredDataCondition.assertCondition(eq(conditionParameter)))
                .thenReturn(Optional.empty());

        when(notExistentNameCondition.assertCondition(eq(conditionParameter)))
                .thenReturn(Optional.of("An error occurred."));

        var validatorParameter = new PaymentMethodValidatorParameter(request);

        //when
        Exception result = assertThrows(BusinessException.class, () -> subject.validate(validatorParameter));

        //then
        assertNotNull(result);
    }

    @Test
    @DisplayName("Should throw exception when conditions are not met")
    void shouldThrowError_whenConditionsAreNotMet() throws BusinessException{
        //given
        var request = new PaymentMethodRequest(1L, "Test name");
        var conditionParameter = new PaymentMethodConditionParameter(request);

        when(requiredDataCondition.assertCondition(eq(conditionParameter)))
                .thenReturn(Optional.of("An error occurred."));

        when(notExistentNameCondition.assertCondition(eq(conditionParameter)))
                .thenReturn(Optional.of("An error occurred."));

        var validatorParameter = new PaymentMethodValidatorParameter(request);

        //when
        Exception result = assertThrows(BusinessException.class, () -> subject.validate(validatorParameter));

        //then
        assertNotNull(result);
    }
}
