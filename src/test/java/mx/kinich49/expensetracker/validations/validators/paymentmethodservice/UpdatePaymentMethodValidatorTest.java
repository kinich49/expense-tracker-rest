package mx.kinich49.expensetracker.validations.validators.paymentmethodservice;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.web.requests.PaymentMethodRequest;
import mx.kinich49.expensetracker.repositories.PaymentMethodRepository;
import mx.kinich49.expensetracker.validations.ValidatorParameter;
import mx.kinich49.expensetracker.validations.conditions.paymentmethodservice.ExistentPaymentMethodCondition;
import mx.kinich49.expensetracker.validations.conditions.paymentmethodservice.PaymentMethodConditionParameter;
import mx.kinich49.expensetracker.validations.conditions.paymentmethodservice.UpdatingPaymentMethodRequiredDataCondition;
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
public class UpdatePaymentMethodValidatorTest {

    UpdatePaymentMethodValidatorImpl subject;
    @Mock
    UpdatingPaymentMethodRequiredDataCondition requiredDataCondition;
    @Mock
    ExistentPaymentMethodCondition existentCondition;

    UpdatePaymentMethodConditionProvider conditionProvider;

    @BeforeEach
    void setup() {
        conditionProvider = Mockito.spy(new UpdatePaymentMethodConditionProvider(requiredDataCondition, existentCondition));
        subject = new UpdatePaymentMethodValidatorImpl(conditionProvider);
    }

    @Test
    @DisplayName("Should complete when conditions are met")
    void shouldComplete_whenConditionsAreMet() throws BusinessException {
        //given
        var request = new PaymentMethodRequest(1L, "Test name");
        var conditionParameter = new PaymentMethodConditionParameter(request);

        when(requiredDataCondition.assertCondition(eq(conditionParameter)))
                .thenReturn(Optional.empty());

        when(existentCondition.assertCondition(eq(conditionParameter)))
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

        when(existentCondition.assertCondition(eq(conditionParameter)))
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

        when(existentCondition.assertCondition(eq(conditionParameter)))
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

        when(existentCondition.assertCondition(eq(conditionParameter)))
                .thenReturn(Optional.of("An error occurred."));

        var validatorParameter = new PaymentMethodValidatorParameter(request);

        //when
        Exception result = assertThrows(BusinessException.class, () -> subject.validate(validatorParameter));

        //then
        assertNotNull(result);
    }

}
