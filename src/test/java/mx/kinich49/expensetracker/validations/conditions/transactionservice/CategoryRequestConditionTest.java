package mx.kinich49.expensetracker.validations.conditions.transactionservice;

import mx.kinich49.expensetracker.helpers.MockHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CategoryRequestConditionTest {

    @InjectMocks
    CategoryRequestCondition subject;

    @Test
    void sanityTest() {
        assertNotNull(subject);
    }

    @Test
    @DisplayName("Should return empty when request has no ID")
    public void shouldReturn_empty_whenRequestIsNew() {
        //given
        MockHelper mockHelper = MockHelper.init()
                .with(MockHelper.addMock()
                        .withNewCategory());

        CategoryRequestCondition.Parameter parameter = new CategoryRequestCondition
                .Parameter(mockHelper.get().getCategoryRequest());

        //when
        Optional<String> result = subject.assertCondition(parameter);

        //then
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should return empty when request has ID and name")
    public void shouldReturn_empty_whenRequestIsNotNew() {
        //given
        MockHelper mockHelper = MockHelper.init()
                .with(MockHelper.addMock()
                        .withPersistedCategory());

        CategoryRequestCondition.Parameter parameter = new CategoryRequestCondition
                .Parameter(mockHelper.get().getCategoryRequest());

        //when
        Optional<String> result = subject.assertCondition(parameter);

        //then
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should return empty when request is null")
    public void shouldReturn_error_whenRequestIsNull() {
        CategoryRequestCondition.Parameter parameter = new CategoryRequestCondition
                .Parameter(null);
        //when
        Optional<String> result = subject.assertCondition(parameter);

        //then
        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Should return error when request is empty")
    public void shouldReturn_error_whenRequestIsEmpty() {
        //given
        MockHelper mockHelper = MockHelper.init()
                .with(MockHelper.addMock()
                        .withEmptyCategory());

        CategoryRequestCondition.Parameter parameter = new CategoryRequestCondition
                .Parameter(mockHelper.get().getCategoryRequest());

        //when
        Optional<String> result = subject.assertCondition(parameter);

        //then
        assertTrue(result.isPresent());
    }
}
