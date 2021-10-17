package mx.kinich49.expensetracker.validations.monthlybudgetservice;

import lombok.Data;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetRequest;
import mx.kinich49.expensetracker.validations.ValidatorParameter;

@Data
public class BudgetValidatorParameter implements ValidatorParameter {

    private final MonthlyBudgetRequest request;
}