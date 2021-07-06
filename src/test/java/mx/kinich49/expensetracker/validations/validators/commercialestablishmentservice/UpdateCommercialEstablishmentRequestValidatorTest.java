package mx.kinich49.expensetracker.validations.validators.commercialestablishmentservice;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.web.requests.CommercialEstablishmentRequest;
import mx.kinich49.expensetracker.validations.conditions.commercialestablishmentservice.CommercialEstablishmentRequestNameConditionImpl;
import mx.kinich49.expensetracker.validations.conditions.commercialestablishmentservice.ConditionParameterImpl;
import mx.kinich49.expensetracker.validations.conditions.commercialestablishmentservice.NotNullCommercialEstablishmentRequestConditionImpl;
import mx.kinich49.expensetracker.validations.conditions.commercialestablishmentservice.UpdateCommercialEstablishmentConditionImpl;
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
public class UpdateCommercialEstablishmentRequestValidatorTest {

    @InjectMocks
    UpdateCommercialEstablishmentValidatorImpl subject;

    @Mock
    NotNullCommercialEstablishmentRequestConditionImpl notNullCondition;
    @Mock
    CommercialEstablishmentRequestNameConditionImpl nameCondition;
    @Mock
    UpdateCommercialEstablishmentConditionImpl updateCondition;

    @Test
    void sanityTest() {
        assertNotNull(subject);
    }
    
    @Test
    @DisplayName("Should throw exception when request is null")
    void shouldThrowException_whenRequestIsNull() throws ValidationFlowException {
        //given
        var parameter = new ValidatorParameterImpl(null);
        var conditionParameter = new ConditionParameterImpl(null);

        when(notNullCondition.assertCondition(eq(conditionParameter)))
                .thenThrow(ValidationFlowException.class);
        //when
        Exception result = assertThrows(BusinessException.class, () -> subject.validate(parameter));

        //then
        assertNotNull(result);
        verify(nameCondition, times(0))
                .assertCondition(eq(conditionParameter));
        verify(updateCondition, times(0))
                .assertCondition(eq(conditionParameter));
    }

    @ParameterizedTest
    @MethodSource("nameNotValid")
    @DisplayName("Should throw exception when name is not valid")
    void shouldThrowException_whenNameIsNotValid(String name) throws ValidationFlowException {
        //given
        var request = new CommercialEstablishmentRequest(1L, name);
        var parameter = new ValidatorParameterImpl(request);
        var conditionParameter = new ConditionParameterImpl(request);

        when(notNullCondition.assertCondition(eq(conditionParameter)))
                .thenReturn(Optional.empty());

        when(updateCondition.assertCondition(eq(conditionParameter)))
                .thenReturn(Optional.empty());

        String expectedMessage = "Name is null or empty";
        when(nameCondition.assertCondition(eq(conditionParameter)))
                .thenReturn(Optional.of(expectedMessage));

        //when
        Exception result = assertThrows(BusinessException.class, () -> subject.validate(parameter));

        //then
        assertNotNull(result);
        assertEquals(expectedMessage, result.getMessage().trim());
    }

    @Test
    @DisplayName("Should throw exception when id is not valid")
    void shouldThrowException_whenIdIsNotValid() throws Exception{
        //given
        var request = new CommercialEstablishmentRequest(null, "Test name");
        var parameter = new ValidatorParameterImpl(request);
        var conditionParameter = new ConditionParameterImpl(request);

        when(notNullCondition.assertCondition(eq(conditionParameter)))
                .thenReturn(Optional.empty());
        when(updateCondition.assertCondition(eq(conditionParameter)))
                .thenThrow(ValidationFlowException.class);
        when(nameCondition.assertCondition(eq(conditionParameter)))
                .thenReturn(Optional.empty());

        //when
        Exception result = assertThrows(BusinessException.class, () -> subject.validate(parameter));

        //then
        assertNotNull(result);
    }

    @Test
    @DisplayName("Should complete when conditions are met")
    void shouldComplete_whenConditionsAreMet() throws ValidationFlowException {
        //given
        var request = new CommercialEstablishmentRequest(1L, "Test name");
        var parameter = new ValidatorParameterImpl(request);
        var conditionParameter = new ConditionParameterImpl(request);

        when(updateCondition.assertCondition(eq(conditionParameter)))
                .thenReturn(Optional.empty());
        when(notNullCondition.assertCondition(eq(conditionParameter)))
                .thenReturn(Optional.empty());
        when(nameCondition.assertCondition(eq(conditionParameter)))
                .thenReturn(Optional.empty());

        //when and then
        assertDoesNotThrow(() -> subject.validate(parameter));
    }


    @SuppressWarnings("unused")
    static List<String> nameNotValid() {
        var notValidNames = new ArrayList<String>();
        notValidNames.add("");
        notValidNames.add(" ");
        notValidNames.add(null);

        return notValidNames;
    }
}
