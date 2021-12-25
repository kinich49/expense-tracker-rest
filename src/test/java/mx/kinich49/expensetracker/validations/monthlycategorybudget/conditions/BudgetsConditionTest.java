package mx.kinich49.expensetracker.validations.monthlycategorybudget.conditions;

import mx.kinich49.expensetracker.models.database.MonthlyBudget;
import mx.kinich49.expensetracker.validations.monthlycategorybudget.MonthlyCategoryBudgetErrorCodes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BudgetsConditionTest {

    @InjectMocks
    MonthlyBudgetCondition subject;

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

        MonthlyBudgetCondition.Parameter parameter = new MonthlyBudgetCondition.Parameter(monthlyBudgets);

        //when
        var optResult = subject.assertCondition(parameter);

        //then
        assertFalse(optResult.isPresent());
    }

    @Test
    @DisplayName("Should return error message when budget list is null")
    public void shouldReturn_errorMessage_when_budgetListIsNull() {
        //given
        MonthlyBudgetCondition.Parameter parameter = new MonthlyBudgetCondition.Parameter(null);

        //when
        var optResult = subject.assertCondition(parameter);

        //then
        assertTrue(optResult.isPresent());
        var result = optResult.get();
        assertEquals(MonthlyCategoryBudgetErrorCodes.BUDGET_NOT_FOUND, result.getErrorCode());
    }

    @Test
    @DisplayName("Should return error message when budget list is empty")
    public void shouldReturn_errorMessage_when_budgetListIsEmpty() {
        //given
        MonthlyBudgetCondition.Parameter parameter = new MonthlyBudgetCondition.Parameter(Collections.emptyList());

        //when
        var optResult = subject.assertCondition(parameter);

        //then
        assertTrue(optResult.isPresent());
        var result = optResult.get();
        assertEquals(MonthlyCategoryBudgetErrorCodes.BUDGET_NOT_FOUND, result.getErrorCode());
    }
}
