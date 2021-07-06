package mx.kinich49.expensetracker.validations.conditions.commercialestablishmentservice;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.web.requests.CommercialEstablishmentRequest;
import mx.kinich49.expensetracker.validations.validators.commercialestablishmentservice.AddCommercialEstablishmentValidatorImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommercialEstablishmentRequestValidatorTest {

    @InjectMocks
    AddCommercialEstablishmentValidatorImpl subject;

    @Mock
    CommercialEstablishmentRequestNameConditionImpl nameCondition;

    @Mock
    NotNullCommercialEstablishmentRequestConditionImpl notNullCondition;

    @Test
    void sanityTest() {
        assertNotNull(subject);
    }

    @Test
    @DisplayName("Should not throw exception when request is valid")
    void shouldNotThrowException_whenRequestIsValid() throws ValidationFlowException {
        //given
        var request = new CommercialEstablishmentRequest(1L, "Test name");
        var parameter = new mx.kinich49.expensetracker.validations.validators.commercialestablishmentservice.Parameter(request);
        var conditionParameter = new Parameter(request);

        when(notNullCondition.assertCondition(eq(conditionParameter)))
                .thenReturn(Optional.empty());

        when(nameCondition.assertCondition(eq(conditionParameter)))
                .thenReturn(Optional.empty());

        //when and then
        assertDoesNotThrow(() -> subject.validate(parameter));
    }

    @Test
    @DisplayName("Should throw exception when request is null")
    void shouldThrowException_whenRequestIsNull() throws ValidationFlowException {
        //given
        var parameter = new mx.kinich49.expensetracker.validations.validators.commercialestablishmentservice.Parameter(null);
        var conditionParameter = new Parameter(null);
        ValidationFlowException exception = new ValidationFlowException("Request was null");

        when(notNullCondition.assertCondition(eq(conditionParameter)))
                .thenThrow(exception);

        //when
        Exception result = assertThrows(BusinessException.class, () -> subject.validate(parameter));

        assertNotNull(result);
        assertEquals(exception.getMessage(), result.getMessage());
    }

    @ParameterizedTest
    @DisplayName("Should throw exception when request name is not valid")
    @MethodSource("requestNameNotValid")
    void shouldThrowException_whenRequestNameIsNotValid(String commercialEstablishmentName) throws BusinessException {
        //given
        var request = new CommercialEstablishmentRequest(1L, "Test name");
        var parameter = new mx.kinich49.expensetracker.validations.validators.commercialestablishmentservice.Parameter(request);
        var conditionParameter = new Parameter(request);

        when(notNullCondition.assertCondition(eq(conditionParameter)))
                .thenReturn(Optional.empty());

        String errorMessage = String.format("%1$s is already registered", commercialEstablishmentName);
        when(nameCondition.assertCondition(eq(conditionParameter)))
                .thenReturn(Optional.of(errorMessage));

        //when
        Exception result = assertThrows(BusinessException.class, () -> subject.validate(parameter));

        assertNotNull(result);
        assertEquals(errorMessage, result.getMessage());
    }

    @SuppressWarnings("unused")
    public static Stream<Arguments> requestNameNotValid() {
        return Stream.of(
                Arguments.of("Test name"),
                Arguments.of("Invalid name"),
                Arguments.of("Non unique name")
        );
    }
}
