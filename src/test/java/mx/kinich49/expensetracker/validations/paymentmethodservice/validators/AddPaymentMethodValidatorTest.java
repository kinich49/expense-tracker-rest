package mx.kinich49.expensetracker.validations.paymentmethodservice.validators;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.web.ErrorWrapper;
import mx.kinich49.expensetracker.models.web.requests.PaymentMethodRequest;
import mx.kinich49.expensetracker.validations.paymentmethodservice.PaymentMethodServiceErrorCodes;
import mx.kinich49.expensetracker.validations.paymentmethodservice.conditions.AddingPaymentMethodRequiredDataCondition;
import mx.kinich49.expensetracker.validations.paymentmethodservice.conditions.NotExistentPaymentMethodNameCondition;
import mx.kinich49.expensetracker.validations.paymentmethodservice.conditions.PaymentMethodConditionParameter;
import mx.kinich49.expensetracker.validations.paymentmethodservice.providers.AddPaymentValidatorConditionProvider;
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

    AddPaymentMethodValidator subject;
    @Mock
    AddingPaymentMethodRequiredDataCondition requiredDataCondition;
    @Mock
    NotExistentPaymentMethodNameCondition notExistentNameCondition;

    AddPaymentValidatorConditionProvider conditionProvider;

    @BeforeEach
    void setUp() {
        conditionProvider = Mockito.spy(new AddPaymentValidatorConditionProvider(
                requiredDataCondition, notExistentNameCondition));

        subject = new AddPaymentMethodValidator(conditionProvider);
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

        var errorWrapper = new ErrorWrapper(PaymentMethodServiceErrorCodes.ID_IS_NULL_OR_ZERO,
                "Something went wrong. ");

        when(requiredDataCondition.assertCondition(eq(conditionParameter)))
                .thenReturn(Optional.of(errorWrapper));

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

        var errorWrapper = new ErrorWrapper(PaymentMethodServiceErrorCodes.ID_IS_NULL_OR_ZERO,
                "Something went wrong. ");

        when(requiredDataCondition.assertCondition(eq(conditionParameter)))
                .thenReturn(Optional.empty());

        when(notExistentNameCondition.assertCondition(eq(conditionParameter)))
                .thenReturn(Optional.of(errorWrapper));

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

        var errorWrapper = new ErrorWrapper(PaymentMethodServiceErrorCodes.ID_IS_NULL_OR_ZERO,
                "Something went wrong. ");
        when(requiredDataCondition.assertCondition(eq(conditionParameter)))
                .thenReturn(Optional.of(errorWrapper));

        when(notExistentNameCondition.assertCondition(eq(conditionParameter)))
                .thenReturn(Optional.of(errorWrapper));

        var validatorParameter = new PaymentMethodValidatorParameter(request);

        //when
        Exception result = assertThrows(BusinessException.class, () -> subject.validate(validatorParameter));

        //then
        assertNotNull(result);
    }
}
