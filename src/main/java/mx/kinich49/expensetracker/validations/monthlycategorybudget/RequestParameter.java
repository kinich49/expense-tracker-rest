package mx.kinich49.expensetracker.validations.monthlycategorybudget;

import lombok.Data;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetCategoryRequest;
import mx.kinich49.expensetracker.validations.ConditionParameter;

@Data
public class RequestParameter implements ConditionParameter {
    private final MonthlyBudgetCategoryRequest request;
}
