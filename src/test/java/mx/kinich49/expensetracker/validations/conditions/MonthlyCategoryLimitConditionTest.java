package mx.kinich49.expensetracker.validations.conditions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MonthlyCategoryLimitConditionTest {

    @InjectMocks
    MonthlyCategoryLimitConditionImpl subject;

    @Test
    @DisplayName("Sanity test")
    public void sanityTest() {
        assertNotNull(subject);
    }

    @Test
    @DisplayName("Should return error message when accumulated limit is greater than expense limit")
    public void shouldReturnMessage_when_accumulatedLimitIsNotValid() {
        //given
        MonthlyCategoryLimitConditionImpl.Parameter parameter = new
                MonthlyCategoryLimitConditionImpl.Parameter(1000, 500, 1200);

        //when
        Optional<String> result = subject.assertCondition(parameter);

        //then
        assertTrue(result.isPresent());
        assertEquals("The limit 500 goes over the top of your monthly expense limit of 1200", result.get());

    }

    @Test
    @DisplayName("Should return empty when accumulated limit is less than expense limit ")
    public void shouldReturnEmpty_when_accumulatedIsLessThanExpenseLimit(){
        //given
        MonthlyCategoryLimitConditionImpl.Parameter parameter = new
                MonthlyCategoryLimitConditionImpl.Parameter(1000, 500, 2000);

        //when
        Optional<String> result = subject.assertCondition(parameter);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should return empty when accumulated limit is equals than expense limit ")
    public void shouldReturnEmpty_when_accumulatedIsEqualsThanExpenseLimit(){
        //given
        MonthlyCategoryLimitConditionImpl.Parameter parameter = new
                MonthlyCategoryLimitConditionImpl.Parameter(1000, 500, 1500);

        //when
        Optional<String> result = subject.assertCondition(parameter);

        assertFalse(result.isPresent());
    }
}
