package mx.kinich49.expensetracker.validations.paymentmethodservice.conditions;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.web.requests.PaymentMethodRequest;
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
//TODO break in smaller test classes
public class UpdatingPaymentMethodRequiredDataConditionTest {
//
//    @InjectMocks
//    UpdatingPaymentMethodRequiredDataCondition subject;
//
//    @Test
//    @DisplayName("Sanity Test")
//    void sanityTest() {
//        assertNotNull(subject);
//    }
//
//    @ParameterizedTest
//    @DisplayName("Should return error when request has missing data")
//    @MethodSource("missingDataSource")
//    void shouldReturnError_whenRequestHasMissingData(PaymentMethodRequest request) {
//        //given
//        var parameter = new PaymentMethodConditionParameter(request);
//
//        //when
//        var result = assertDoesNotThrow(() -> subject.assertCondition(parameter));
//
//        //then
//        assertTrue(result.isPresent());
//
//    }
//
//    @ParameterizedTest
//    @DisplayName("Should return empty when request is valid")
//    @MethodSource("validDataSource")
//    void shouldReturnEmpty_whenRequestIsValid(PaymentMethodRequest request) {
//        //given
//        var parameter = new PaymentMethodConditionParameter(request);
//
//        //when
//        var result = assertDoesNotThrow(() -> subject.assertCondition(parameter));
//
//        //then
//        assertTrue(result.isEmpty());
//
//    }
//
//    @Test
//    @DisplayName("Should throw Exception when request is null")
//    void shouldThrowException_whenRequestIsNull()  {
//        //given
//        var parameter = new PaymentMethodConditionParameter(null);
//
//        //when
//        Exception exception = assertThrows(ValidationFlowException.class,() -> subject.assertCondition(parameter));
//
//        //then
//        assertNotNull(exception);
//    }
//
//    static List<PaymentMethodRequest> missingDataSource() {
//        var arguments = new ArrayList<PaymentMethodRequest>();
//
//        arguments.add(new PaymentMethodRequest(null, null));
//        arguments.add(new PaymentMethodRequest(null, "Test name"));
//        arguments.add(new PaymentMethodRequest(1L, null));
//
//        return arguments;
//    }
//
//    static List<PaymentMethodRequest> validDataSource() {
//        var arguments = new ArrayList<PaymentMethodRequest>();
//
//        arguments.add(new PaymentMethodRequest(1L, "Test Name"));
//
//        return arguments;
//    }
}
