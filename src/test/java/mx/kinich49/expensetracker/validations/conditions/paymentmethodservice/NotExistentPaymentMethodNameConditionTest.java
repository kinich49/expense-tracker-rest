package mx.kinich49.expensetracker.validations.conditions.paymentmethodservice;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.web.requests.PaymentMethodRequest;
import mx.kinich49.expensetracker.repositories.PaymentMethodRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotExistentPaymentMethodNameConditionTest {

    @InjectMocks
    NotExistentPaymentMethodNameCondition subject;

    @Mock
    PaymentMethodRepository repository;

    @Test
    @DisplayName("Sanity Test")
    void sanityTest() {
        assertNotNull(subject);
    }

    @Test
    @DisplayName("Should return error when name already exists")
    void shouldReturnError_whenNameAlreadyExists() {
        //given
        var name = "Test Name";
        var parameter = new PaymentMethodConditionParameter(new PaymentMethodRequest(1L, name));
        when(repository.existsByNameIgnoreCase(eq(name)))
                .thenReturn(true);

        //when
        var result = assertDoesNotThrow(() -> subject.assertCondition(parameter));

        //then
        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Should return empty when name does not exist")
    void shouldReturnEmpty_whenNameDoesNotExists() {
        //given
        var name = "Test Name";
        var parameter = new PaymentMethodConditionParameter(new PaymentMethodRequest(1L, name));
        when(repository.existsByNameIgnoreCase(eq(name)))
                .thenReturn(false);

        //when
        var result = assertDoesNotThrow(() -> subject.assertCondition(parameter));

        //then
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should throw Exception when request is null")
    void shouldThrowException_whenRequestIsNull()  {
        //given
        var parameter = new PaymentMethodConditionParameter(null);

        //when
        Exception exception = assertThrows(ValidationFlowException.class,() -> subject.assertCondition(parameter));

        //then
        assertNotNull(exception);
    }
}
