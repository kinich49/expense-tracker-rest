package mx.kinich49.expensetracker.validations.conditions.paymentmethodservice;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.web.requests.PaymentMethodRequest;
import mx.kinich49.expensetracker.repositories.PaymentMethodRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExistentPaymentMethodConditionTest {

    @InjectMocks
    ExistentPaymentMethodCondition subject;

    @Mock
    PaymentMethodRepository repository;

    @Test
    @DisplayName("Sanity Test")
    void sanityTest() {
        assertNotNull(subject);
    }

    @ParameterizedTest
    @DisplayName("Should return error when request id is not valid")
    @MethodSource("invalidIds")
    void shouldReturnError_whenIdIsNotValid(Long id) {
        //given
        var request = new PaymentMethodRequest(id, "name");
        var conditionParameter = new PaymentMethodConditionParameter(request);


        //when
        Optional<String> result = assertDoesNotThrow(() -> subject.assertCondition(conditionParameter));

        //then
        assertTrue(result.isPresent());
    }

    @ParameterizedTest
    @DisplayName("Should return empty when request id is not valid")
    @MethodSource("validIds")
    void shouldReturnEmpty_whenIdIsValid(Long id) {
        //given
        var request = new PaymentMethodRequest(id, "name");
        var conditionParameter = new PaymentMethodConditionParameter(request);

        when(repository.existsById(eq(id)))
                .thenReturn(true);

        //when
        Optional<String> result = assertDoesNotThrow(() -> subject.assertCondition(conditionParameter));

        //then
        assertTrue(result.isEmpty());

        verify(repository, times(0)).deleteById(eq(id));
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
        verify(repository, times(0)).deleteById(anyLong());

    }

    static List<Long> invalidIds() {
        var arguments = new ArrayList<Long>();

        arguments.add(null);
        arguments.add(0L);
        arguments.add(Long.MIN_VALUE);
        arguments.add(-1L);

        return arguments;
    }

    static List<Long> validIds() {
        var arguments = new ArrayList<Long>();

        arguments.add(1L);
        arguments.add(Long.MAX_VALUE);
        arguments.add(999L);

        return arguments;
    }
}
