package mx.kinich49.expensetracker.validations.commercialestablishmentservice.conditions;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.web.requests.CommercialEstablishmentRequest;
import mx.kinich49.expensetracker.validations.commercialestablishmentservice.ConditionParameterImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class NotNullCommercialEstablishmentRequestConditionTest {

    @InjectMocks
    NotNullCommercialEstablishmentRequestCondition subject;

    @Test
    @DisplayName("Sanity Test")
    void sanityTest() {
        assertNotNull(subject);
    }

    @Test
    @DisplayName("Should throw exception when request is null")
    void shouldThrowException_whenRequestIsNull() {
        //given
        var conditionParameter = new ConditionParameterImpl(null);

        //when
        var exception = assertThrows(ValidationFlowException.class, () ->
                subject.assertCondition(conditionParameter));

        //then
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Should return empty when request is not null")
    void shouldReturnEmpty_whenRequestIsNotNull() {
        //given
        var parameter = new ConditionParameterImpl(new CommercialEstablishmentRequest(1L, "Test"));

        //when
        var optResult = assertDoesNotThrow(() -> subject.assertCondition(parameter));

        //then
        assertFalse(optResult.isPresent());
    }
}
