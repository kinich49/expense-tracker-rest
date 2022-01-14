package mx.kinich49.expensetracker.validations.monthlycategorybudget.conditions;

import mx.kinich49.expensetracker.validations.monthlycategorybudget.MonthlyCategoryBudgetErrorCodes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LimitConditionTest {

    @InjectMocks
    LimitCondition subject;

    @Test
    @DisplayName("Sanity test")
    public void sanityTest() {
        assertNotNull(subject);
    }

    @Test
    @DisplayName("Should return error message when accumulated limit is greater than expense limit")
    public void shouldReturnMessage_when_accumulatedLimitIsNotValid() {
        //given
        LimitCondition.Parameter parameter = new
                LimitCondition.Parameter(1000, 500, 1200);

        //when
        var optResult = subject.assertCondition(parameter);

        //then
        assertTrue(optResult.isPresent());
        var result = optResult.get();
        assertEquals(MonthlyCategoryBudgetErrorCodes.LIMIT_OVERCOMES_MONTHLY_EXPENSES, result.getErrorCode());
    }

    @Test
    @DisplayName("Should return empty when accumulated limit is less than expense limit ")
    public void shouldReturnEmpty_when_accumulatedIsLessThanExpenseLimit(){
        //given
        LimitCondition.Parameter parameter = new
                LimitCondition.Parameter(1000, 500, 2000);

        //when
        var optResult = subject.assertCondition(parameter);

        assertFalse(optResult.isPresent());
    }

    @Test
    @DisplayName("Should return empty when accumulated limit is equals than expense limit ")
    public void shouldReturnEmpty_when_accumulatedIsEqualsThanExpenseLimit(){
        //given
        LimitCondition.Parameter parameter = new
                LimitCondition.Parameter(1000, 500, 1500);

        //when
        var optResult = subject.assertCondition(parameter);

        assertFalse(optResult.isPresent());
    }
}
