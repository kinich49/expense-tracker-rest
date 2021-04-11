package mx.kinich49.expensetracker.validations.validators;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.database.MonthlyBudget;
import mx.kinich49.expensetracker.models.database.MonthlyIncome;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetCategoryRequest;
import mx.kinich49.expensetracker.validations.conditions.monthlycategorybudget.BudgetsConditionImpl;
import mx.kinich49.expensetracker.validations.conditions.monthlycategorybudget.LimitConditionImpl;
import mx.kinich49.expensetracker.validations.conditions.monthlycategorybudget.RequestConditionImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class MonthlyCategoryBudgetValidatorTest {

    @InjectMocks
    MonthlyCategoryBudgetValidatorImpl subject;

    @Mock
    RequestConditionImpl monthlyBudgeCategoryRequestCondition;
    @Mock
    LimitConditionImpl monthlyCategoryLimitCondition;
    @Mock
    BudgetsConditionImpl monthlyCategoryBudgetBudgetsCondition;

    @Test
    @DisplayName("Sanity Test")
    public void sanityTest() {
        assertNotNull(subject);
    }

    @Test
    @DisplayName("Should finish when conditions are met")
    public void shouldFinish_when_conditionsAreMet() throws BusinessException {
        //given
        MonthlyBudgetCategoryRequest request = new MonthlyBudgetCategoryRequest();

        MonthlyIncome monthlyIncome = new MonthlyIncome();
        List<MonthlyBudget> monthlyBudgets = new ArrayList<>();

        MonthlyCategoryBudgetValidatorImpl.Parameter parameter =
                new MonthlyCategoryBudgetValidatorImpl.Parameter(request, monthlyIncome, monthlyBudgets);

        when(monthlyBudgeCategoryRequestCondition
                .assertCondition(any(RequestConditionImpl.Parameter.class)))
                .thenReturn(Optional.empty());

        when(monthlyCategoryLimitCondition
                .assertCondition(any(LimitConditionImpl.Parameter.class)))
                .thenReturn(Optional.empty());

        when(monthlyCategoryBudgetBudgetsCondition
                .assertCondition(any(BudgetsConditionImpl.Parameter.class)))
                .thenReturn(Optional.empty());
        //when
        subject.validate(parameter);
    }

    @Test
    @DisplayName("Should throw exception when request condition is not met")
    public void shouldThrowException_when_monthlyBudgetCategoryRequestCondition_isNotMet() {
        //given
        MonthlyBudgetCategoryRequest request = new MonthlyBudgetCategoryRequest();

        MonthlyIncome monthlyIncome = new MonthlyIncome();
        List<MonthlyBudget> monthlyBudgets = new ArrayList<>();

        MonthlyCategoryBudgetValidatorImpl.Parameter parameter =
                new MonthlyCategoryBudgetValidatorImpl.Parameter(request, monthlyIncome, monthlyBudgets);

        String errorMessage = "Something went wrong";

        lenient().when(monthlyBudgeCategoryRequestCondition
                .assertCondition(any(RequestConditionImpl.Parameter.class)))
                .thenReturn(Optional.of(errorMessage));

        lenient().when(monthlyCategoryLimitCondition
                .assertCondition(any(LimitConditionImpl.Parameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(monthlyCategoryBudgetBudgetsCondition
                .assertCondition(any(BudgetsConditionImpl.Parameter.class)))
                .thenReturn(Optional.empty());
        //when
        assertThrows(BusinessException.class, () -> subject.validate(parameter));
    }

    @Test
    @DisplayName("Should throw exception when limit condition condition is not met")
    public void shouldThrowException_when_monthlyCategoryLimitCondition_isNotMet() {
        //given
        MonthlyBudgetCategoryRequest request = new MonthlyBudgetCategoryRequest();

        MonthlyIncome monthlyIncome = new MonthlyIncome();
        List<MonthlyBudget> monthlyBudgets = new ArrayList<>();

        MonthlyCategoryBudgetValidatorImpl.Parameter parameter =
                new MonthlyCategoryBudgetValidatorImpl.Parameter(request, monthlyIncome, monthlyBudgets);

        String errorMessage = "Something went wrong";

        lenient().when(monthlyBudgeCategoryRequestCondition
                .assertCondition(any(RequestConditionImpl.Parameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(monthlyCategoryLimitCondition
                .assertCondition(any(LimitConditionImpl.Parameter.class)))
                .thenReturn(Optional.of(errorMessage));

        lenient().when(monthlyCategoryBudgetBudgetsCondition
                .assertCondition(any(BudgetsConditionImpl.Parameter.class)))
                .thenReturn(Optional.empty());
        //when
        Exception result = assertThrows(BusinessException.class,
                () -> subject.validate(parameter));

        //then
        assertEquals(errorMessage, result.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when budgets condition is not met")
    public void shouldThrowException_when_monthlyCategoryBudgetBudgetsCondition_isNotMet() {
        //given
        MonthlyBudgetCategoryRequest request = new MonthlyBudgetCategoryRequest();

        MonthlyIncome monthlyIncome = new MonthlyIncome();
        List<MonthlyBudget> monthlyBudgets = new ArrayList<>();

        MonthlyCategoryBudgetValidatorImpl.Parameter parameter =
                new MonthlyCategoryBudgetValidatorImpl.Parameter(request, monthlyIncome, monthlyBudgets);

        String errorMessage = "Something went wrong";

        lenient().when(monthlyBudgeCategoryRequestCondition
                .assertCondition(any(RequestConditionImpl.Parameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(monthlyCategoryLimitCondition
                .assertCondition(any(LimitConditionImpl.Parameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(monthlyCategoryBudgetBudgetsCondition
                .assertCondition(any(BudgetsConditionImpl.Parameter.class)))
                .thenReturn(Optional.of(errorMessage));
        //when
        Exception result = assertThrows(BusinessException.class, () -> subject.validate(parameter));

        //then
        assertEquals(errorMessage, result.getMessage());
    }

}
