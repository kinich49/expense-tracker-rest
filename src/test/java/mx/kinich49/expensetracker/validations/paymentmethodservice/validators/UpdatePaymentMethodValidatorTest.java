package mx.kinich49.expensetracker.validations.paymentmethodservice.validators;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.internal.ErrorWrapper;
import mx.kinich49.expensetracker.models.web.requests.PaymentMethodRequest;
import mx.kinich49.expensetracker.validations.paymentmethodservice.PaymentMethodServiceErrorCodes;
import mx.kinich49.expensetracker.validations.paymentmethodservice.conditions.ExistentPaymentMethodCondition;
import mx.kinich49.expensetracker.validations.paymentmethodservice.conditions.PaymentMethodConditionParameter;
import mx.kinich49.expensetracker.validations.paymentmethodservice.conditions.PaymentMethodIdCondition;
import mx.kinich49.expensetracker.validations.paymentmethodservice.conditions.PaymentMethodNameCondition;
import mx.kinich49.expensetracker.validations.paymentmethodservice.providers.UpdatePaymentMethodConditionProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdatePaymentMethodValidatorTest {

    UpdatePaymentMethodValidator subject;
    UpdatePaymentMethodConditionProvider conditionProvider;

    @Mock
    ExistentPaymentMethodCondition existentCondition;
    @Mock
    PaymentMethodIdCondition idCondition;
    @Mock
    PaymentMethodNameCondition nameCondition;

    @BeforeEach
    void setup() {
        conditionProvider = Mockito.spy(new UpdatePaymentMethodConditionProvider(
                existentCondition, idCondition, nameCondition));
        subject = new UpdatePaymentMethodValidator(conditionProvider);
    }

    @Test
    @DisplayName("Should complete when conditions are met")
    void shouldComplete_whenConditionsAreMet() throws BusinessException {
        //given
        var request = new PaymentMethodRequest(1L, "Test name");
        var validatorParameter = new PaymentMethodValidatorParameter(request);

        when(existentCondition
                .assertCondition(any(PaymentMethodConditionParameter.class)))
                .thenReturn(Optional.empty());

        when(idCondition
                .assertCondition(any(PaymentMethodConditionParameter.class)))
                .thenReturn(Optional.empty());

        when(nameCondition
                .assertCondition(any(PaymentMethodConditionParameter.class)))
                .thenReturn(Optional.empty());

        //when and then
        assertDoesNotThrow(() -> subject.validate(validatorParameter));
    }

    @Test
    @DisplayName("Should throw exception when ExistentCondition fails")
    void shouldThrowError_whenRequiredExistentConditionFails() throws BusinessException {
        //given
        var request = new PaymentMethodRequest(1L, "Test name");
        var validatorParameter = new PaymentMethodValidatorParameter(request);

        var errorWrapper = new ErrorWrapper(PaymentMethodServiceErrorCodes.ID_IS_NULL_OR_ZERO,
                "Something went wrong. ");

        when(existentCondition
                .assertCondition(any(PaymentMethodConditionParameter.class)))
                .thenReturn(Optional.of(errorWrapper));

        when(idCondition
                .assertCondition(any(PaymentMethodConditionParameter.class)))
                .thenReturn(Optional.empty());

        when(nameCondition
                .assertCondition(any(PaymentMethodConditionParameter.class)))
                .thenReturn(Optional.empty());

        //when
        Exception result = assertThrows(BusinessException.class, () -> subject.validate(validatorParameter));

        //then
        assertNotNull(result);
        assertEquals(errorWrapper.getErrorMessage(), result.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when IdCondition fails")
    void shouldThrowError_whenIdConditionFails() throws BusinessException {
        //given
        var request = new PaymentMethodRequest(1L, "Test name");
        var validatorParameter = new PaymentMethodValidatorParameter(request);

        var errorWrapper = new ErrorWrapper(PaymentMethodServiceErrorCodes.ID_IS_NULL_OR_ZERO,
                "Something went wrong. ");

        when(existentCondition
                .assertCondition(any(PaymentMethodConditionParameter.class)))
                .thenReturn(Optional.empty());

        when(idCondition
                .assertCondition(any(PaymentMethodConditionParameter.class)))
                .thenReturn(Optional.of(errorWrapper));

        when(nameCondition
                .assertCondition(any(PaymentMethodConditionParameter.class)))
                .thenReturn(Optional.empty());

        //when
        Exception result = assertThrows(BusinessException.class, () -> subject.validate(validatorParameter));

        //then
        assertNotNull(result);
        assertEquals(errorWrapper.getErrorMessage(), result.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when NameCondition fails")
    void shouldThrowError_whenNameConditionFails() throws BusinessException {
        //given
        var request = new PaymentMethodRequest(1L, "Test name");
        var validatorParameter = new PaymentMethodValidatorParameter(request);

        var errorWrapper = new ErrorWrapper(PaymentMethodServiceErrorCodes.ID_IS_NULL_OR_ZERO,
                "Something went wrong. ");

        when(existentCondition
                .assertCondition(any(PaymentMethodConditionParameter.class)))
                .thenReturn(Optional.empty());

        when(idCondition
                .assertCondition(any(PaymentMethodConditionParameter.class)))
                .thenReturn(Optional.empty());

        when(nameCondition
                .assertCondition(any(PaymentMethodConditionParameter.class)))
                .thenReturn(Optional.of(errorWrapper));

        //when
        Exception result = assertThrows(BusinessException.class, () -> subject.validate(validatorParameter));

        //then
        assertNotNull(result);
        assertEquals(errorWrapper.getErrorMessage(), result.getMessage());
    }

}
