package mx.kinich49.expensetracker.validations.conditions.monthlycategorybudget;

import mx.kinich49.expensetracker.models.database.MonthlyBudget;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BudgetsConditionTest {

    @InjectMocks
    BudgetsConditionImpl subject;

    @Test
    void sanityTest() {
        assertNotNull(subject);
    }

    @Test
    @DisplayName("Should return empty when budget list is not empty")
    public void shouldReturn_empty_when_budgetIsNotEmpty() {
        //given
        List<MonthlyBudget> monthlyBudgets = new ArrayList<>();
        monthlyBudgets.add(new MonthlyBudget());

        BudgetsConditionImpl.Parameter parameter = new BudgetsConditionImpl.Parameter(monthlyBudgets);

        //when
        Optional<String> result = subject.assertCondition(parameter);

        //then
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should return error message when budget list is null")
    public void shouldReturn_errorMessage_when_budgetListIsNull() {
        //given
        BudgetsConditionImpl.Parameter parameter = new BudgetsConditionImpl.Parameter(null);

        //when
        Optional<String> result = subject.assertCondition(parameter);

        //then
        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Should return error message when budget list is empty")
    public void shouldReturn_errorMessage_when_budgetListIsEmpty() {
        //given
        BudgetsConditionImpl.Parameter parameter = new BudgetsConditionImpl.Parameter(Collections.emptyList());

        //when
        Optional<String> result = subject.assertCondition(parameter);

        //then
        assertTrue(result.isPresent());
    }
}
