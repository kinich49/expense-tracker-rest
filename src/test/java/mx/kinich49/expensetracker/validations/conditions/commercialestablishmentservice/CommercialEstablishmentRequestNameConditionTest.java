package mx.kinich49.expensetracker.validations.conditions.commercialestablishmentservice;

import mx.kinich49.expensetracker.models.web.requests.CommercialEstablishmentRequest;
import mx.kinich49.expensetracker.repositories.CommercialEstablishmentRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommercialEstablishmentRequestNameConditionTest {

    @InjectMocks
    CommercialEstablishmentRequestNameConditionImpl subject;

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
        Parameter parameter = new Parameter(request);

        //when
        Optional<String> result = subject.assertCondition(parameter);

        //then
        assertTrue(result.isPresent());
        assertEquals("Request name is null or empty. ", result.get());
    }

    @ParameterizedTest
    @DisplayName("Should return error when name is not unique")
    @MethodSource("nonUniqueNameSourceMethod")
    void shouldReturnError_whenNameIsNotUnique(String name) {
        //given
        CommercialEstablishmentRequest request = new CommercialEstablishmentRequest(1L, name);
        Parameter conditionParameter = new Parameter(request);

        when(repository.exists(any()))
                .thenReturn(true);

        //when
        Optional<String> result = subject.assertCondition(conditionParameter);

        //then
        assertTrue(result.isPresent());
        String expectedMessage = String.format("A Commercial Establishment entity already exists with the name %1$s. ",
                name);
        assertEquals(expectedMessage, result.get());
    }

    @Test
    @DisplayName("Should return empty when name is valid")
    void shouldReturnEmpty_whenNameIsValid() {
        //given
        CommercialEstablishmentRequest request = new CommercialEstablishmentRequest(1L, "Unique Name");
        Parameter conditionParameter = new Parameter(request);

        when(repository.exists(any()))
                .thenReturn(false);

        //when
        Optional<String> result = subject.assertCondition(conditionParameter);

        //then
        assertFalse(result.isPresent());
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
