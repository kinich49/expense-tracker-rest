package mx.kinich49.expensetracker.validations.commercialestablishmentservice.validators;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.web.ErrorWrapper;
import mx.kinich49.expensetracker.models.web.requests.CommercialEstablishmentRequest;
import mx.kinich49.expensetracker.validations.commercialestablishmentservice.CommercialEstablishmentServiceErrorCodes;
import mx.kinich49.expensetracker.validations.commercialestablishmentservice.conditions.CommercialEstablishmentRequestNameCondition;
import mx.kinich49.expensetracker.validations.commercialestablishmentservice.ConditionParameterImpl;
import mx.kinich49.expensetracker.validations.commercialestablishmentservice.conditions.NotNullCommercialEstablishmentRequestCondition;
import mx.kinich49.expensetracker.validations.commercialestablishmentservice.providers.AddCommercialEstablishmentConditionProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddCommercialEstablishmentRequestValidatorTest {

    AddCommercialEstablishmentValidator subject;
    @Mock
    NotNullCommercialEstablishmentRequestCondition notNullCondition;
    @Mock
    CommercialEstablishmentRequestNameCondition nameCondition;

    AddCommercialEstablishmentConditionProvider conditionProvider;

    @BeforeEach
    void setup() {
        conditionProvider = Mockito
                .spy(new AddCommercialEstablishmentConditionProvider(notNullCondition, nameCondition));

        subject = new AddCommercialEstablishmentValidator(conditionProvider);
    }

    @Test
    void sanityTest() {
        assertNotNull(subject);
    }

    @Test
    @DisplayName("Should complete when conditions are met")
    void shouldComplete_whenConditionsAreMet() throws ValidationFlowException {
        //given
        var request = new CommercialEstablishmentRequest(1L, "Test name");
        var parameter = new CommercialEstablishmentValidatorParameter(request);
        var conditionParameter = new ConditionParameterImpl(request);

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
        var parameter = new CommercialEstablishmentValidatorParameter(null);
        var conditionParameter = new ConditionParameterImpl(null);

        when(notNullCondition.assertCondition(eq(conditionParameter)))
                .thenThrow(ValidationFlowException.class);
        //when
        Exception result = assertThrows(BusinessException.class, () -> subject.validate(parameter));

        //then
        assertNotNull(result);
        verify(nameCondition, times(0))
                .assertCondition(any(ConditionParameterImpl.class));
    }

    @ParameterizedTest
    @MethodSource("nameNotValid")
    @DisplayName("Should throw exception when name is not valid")
    void shouldThrowException_whenNameIsNotValid(String name) {
        //given
        var request = new CommercialEstablishmentRequest(1L, name);
        var parameter = new CommercialEstablishmentValidatorParameter(request);
        var conditionParameter = new ConditionParameterImpl(request);

        var errorWrapper = new ErrorWrapper(CommercialEstablishmentServiceErrorCodes.REQUEST_NAME_IS_NULL_OR_EMPTY,
                "Something went wrong.");
        when(nameCondition.assertCondition(eq(conditionParameter)))
                .thenReturn(Optional.of(errorWrapper));
        //when
        Exception result = assertThrows(BusinessException.class, () -> subject.validate(parameter));

        //then
        assertNotNull(result);
        assertEquals(errorWrapper.getErrorMessage(), result.getMessage().trim());
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
