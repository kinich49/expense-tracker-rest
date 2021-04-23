package mx.kinich49.expensetracker.validations.conditions.monthlycategorybudget;

import lombok.Data;
import mx.kinich49.expensetracker.models.database.MonthlyBudget;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.ConditionParameter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BudgetsConditionImpl implements Condition<BudgetsConditionImpl.Parameter> {

    @Override
    public Optional<String> assertCondition(Parameter param) {
        List<MonthlyBudget> monthlyBudgets = param.monthlyBudgets;

        if (monthlyBudgets == null || monthlyBudgets.isEmpty())
            return Optional.of("No monthly budgets.");

        return Optional.empty();
    }

    @Data
    public static class Parameter implements ConditionParameter {
        private final List<MonthlyBudget> monthlyBudgets;
    }
}
