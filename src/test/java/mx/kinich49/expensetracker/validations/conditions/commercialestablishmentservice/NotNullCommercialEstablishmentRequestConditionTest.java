package mx.kinich49.expensetracker.validations.conditions.commercialestablishmentservice;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.web.requests.CommercialEstablishmentRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class NotNullCommercialEstablishmentRequestConditionTest {

    @InjectMocks
    NotNullCommercialEstablishmentRequestConditionImpl subject;

    @Test
    @DisplayName("Sanity Test")
    void sanityTest() {
        assertNotNull(subject);
    }

    @Test
    @DisplayName("Should throw exception when request is null")
    void shouldThrowException_whenRequestIsNull() {
        //given
        Parameter parameter = new Parameter(null);

        //when
        Exception exception = assertThrows(ValidationFlowException.class, () ->
                subject.assertCondition(parameter));

        //then
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Should return empty when request is not null")
    void shouldReturnEmpty_whenRequestIsNotNull() {
        //given
        var parameter = new Parameter(new CommercialEstablishmentRequest(1L, "Test"));

        //when
        Optional<String> result = assertDoesNotThrow(() -> subject.assertCondition(parameter));

        //then
        assertFalse(result.isPresent());
    }
}
