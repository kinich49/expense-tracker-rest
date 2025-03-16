package mx.kinich49.expensetracker.validations.commercialestablishmentservice.conditions;

import mx.kinich49.expensetracker.models.web.ErrorWrapper;
import mx.kinich49.expensetracker.models.web.requests.CommercialEstablishmentRequest;
import mx.kinich49.expensetracker.repositories.CommercialEstablishmentRepository;
import mx.kinich49.expensetracker.validations.commercialestablishmentservice.CommercialEstablishmentServiceErrorCodes;
import mx.kinich49.expensetracker.validations.commercialestablishmentservice.ConditionParameterImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommercialEstablishmentRequestNameConditionTest {

    @InjectMocks
    CommercialEstablishmentRequestNameCondition subject;

    @Mock
    CommercialEstablishmentRepository repository;

    @Test
    @DisplayName("Sanity test")
    void sanityTest() {
        assertNotNull(subject);
    }

    @ParameterizedTest
    @DisplayName("Should return error when request name is null, blank or empty")
    @MethodSource("nullBlankOrEmptyName")
    void shouldReturnError_whenRequestNameIsNotValid(CommercialEstablishmentRequest request) {
        //given
        ConditionParameterImpl conditionParameter = new ConditionParameterImpl(request);

        //when
        var optResult = subject.assertCondition(conditionParameter);

        //then
        assertTrue(optResult.isPresent());
        ErrorWrapper result = optResult.get();
        assertEquals(CommercialEstablishmentServiceErrorCodes.REQUEST_NAME_IS_NULL_OR_EMPTY, result.getErrorCode());
    }

    @ParameterizedTest
    @DisplayName("Should return error when name is not unique")
    @MethodSource("nonUniqueNameSourceMethod")
    void shouldReturnError_whenNameIsNotUnique(String name) {
        //given
        CommercialEstablishmentRequest request = new CommercialEstablishmentRequest(1L, name);
        ConditionParameterImpl conditionParameter = new ConditionParameterImpl(request);

        when(repository.exists(any()))
                .thenReturn(true);

        //when
        var optResult = subject.assertCondition(conditionParameter);

        //then
        assertTrue(optResult.isPresent());
        String expectedMessage = String.format("A Commercial Establishment entity already exists with the name %1$s. ",
                name);
        ErrorWrapper result = optResult.get();
        assertEquals(CommercialEstablishmentServiceErrorCodes.COMMERCIAL_ESTABLISHMENT_WITH_NAME_ALREADY_EXISTS, result.getErrorCode());
    }

    @Test
    @DisplayName("Should return empty when name is valid")
    void shouldReturnEmpty_whenNameIsValid() {
        //given
        CommercialEstablishmentRequest request = new CommercialEstablishmentRequest(1L, "Unique Name");
        ConditionParameterImpl conditionParameter = new ConditionParameterImpl(request);

        when(repository.exists(any()))
                .thenReturn(false);

        //when
        var optResult = subject.assertCondition(conditionParameter);

        //then
        assertFalse(optResult.isPresent());
    }

    @SuppressWarnings("unused")
    public static Stream<Arguments> nonUniqueNameSourceMethod() {
        return Stream.of(
                Arguments.of("Non unique name A"),
                Arguments.of("Non unique name B"),
                Arguments.of("Non unique name C")
        );
    }

    @SuppressWarnings("unused")
    public static Stream<Arguments> nullBlankOrEmptyName() {
        return Stream.of(
                Arguments.of(new CommercialEstablishmentRequest(1L, null)),
                Arguments.of(new CommercialEstablishmentRequest(1L, "")),
                Arguments.of(new CommercialEstablishmentRequest(1L, " "))
        );
    }
}
