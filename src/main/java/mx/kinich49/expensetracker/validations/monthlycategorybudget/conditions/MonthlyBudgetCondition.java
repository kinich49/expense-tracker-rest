package mx.kinich49.expensetracker.validations.monthlycategorybudget.conditions;

import lombok.Data;
import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.database.MonthlyBudget;
import mx.kinich49.expensetracker.models.internal.ErrorWrapper;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetCategoryRequest;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.ConditionParameter;
import mx.kinich49.expensetracker.validations.monthlycategorybudget.MonthlyCategoryBudgetErrorCodes;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MonthlyBudgetCondition implements Condition<MonthlyBudgetCondition.Parameter> {

    /**
     * This condition validates the parameter has at least one {@link MonthlyBudget}
     * @param param the instance to assert it meets all conditions
     * @return An optional containing an error message if the condition is not met.
     * Empty Otherwise
     */
    @Override
    public Optional<ErrorWrapper> assertCondition(Parameter param) {
        List<MonthlyBudget> monthlyBudgets = param.monthlyBudgets;

        if (monthlyBudgets == null || monthlyBudgets.isEmpty()) {
            var errorMessage = "No monthly budgets. ";
            return Optional.of(new ErrorWrapper(MonthlyCategoryBudgetErrorCodes.BUDGET_NOT_FOUND, errorMessage));
        }

        return Optional.empty();
    }

    @Data
    public static class Parameter implements ConditionParameter {
        private final List<MonthlyBudget> monthlyBudgets;
    }
}
