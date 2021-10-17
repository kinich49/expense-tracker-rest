package mx.kinich49.expensetracker.validations.monthlybudgetservice.conditions;

import lombok.Data;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetRequest;
import mx.kinich49.expensetracker.validations.ConditionParameter;

@Data
public class BudgetRequestConditionParameter implements ConditionParameter {

    private final MonthlyBudgetRequest request;
}