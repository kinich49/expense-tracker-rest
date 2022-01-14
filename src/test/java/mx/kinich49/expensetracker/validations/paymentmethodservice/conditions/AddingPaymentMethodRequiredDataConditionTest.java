package mx.kinich49.expensetracker.validations.paymentmethodservice.conditions;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.web.requests.PaymentMethodRequest;
import mx.kinich49.expensetracker.validations.paymentmethodservice.PaymentMethodServiceErrorCodes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AddingPaymentMethodRequiredDataConditionTest {

    @InjectMocks
    AddingPaymentMethodRequiredDataCondition subject;

    @Test
    @DisplayName("Sanity Test")
    void sanityTest() {
        assertNotNull(subject);
    }

    @ParameterizedTest
    @DisplayName("Should return error when request name is null")
    @MethodSource("invalidRequestNames")
    void shouldReturnError_whenRequestNameIsInvalid(String name){
        //given
        var request = new PaymentMethodRequest(1L, name);
        var parameter = new PaymentMethodConditionParameter(request);

        //when
        var optResult = assertDoesNotThrow(() -> subject.assertCondition(parameter));

        //then
        assertTrue(optResult.isPresent());
        var result = optResult.get();
        assertEquals(PaymentMethodServiceErrorCodes.NAME_IS_NULL_EMPTY_OR_BLANK, result.getErrorCode());
    }

    @ParameterizedTest
    @DisplayName("Should return empty when request name is valid")
    @MethodSource("validRequestNames")
    void shouldReturnEmpty_whenRequestNameIsValid(String name){
        //given
        var request = new PaymentMethodRequest(1L, name);
        var parameter = new PaymentMethodConditionParameter(request);

        //when
        var optResult = assertDoesNotThrow(() -> subject.assertCondition(parameter));

        //then
        assertTrue(optResult.isEmpty());
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

    static List<String> invalidRequestNames() {
        var arguments = new ArrayList<String>();

        arguments.add("");
        arguments.add(null);
        arguments.add(" ");

        return arguments;
    }

    static List<String> validRequestNames() {
        var arguments = new ArrayList<String>();

        arguments.add("Test");
        arguments.add("This is a test");

        return arguments;
    }
}
