package mx.kinich49.expensetracker.validations.conditions.monthlybudgetservice;

import lombok.Data;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetRequest;
import mx.kinich49.expensetracker.validations.ConditionParameter;

@Data
public class BudgetRequestConditionParameterImpl implements ConditionParameter {

    private final MonthlyBudgetRequest request;
}