package mx.kinich49.expensetracker.validations.validators.monthlybudgetservice;

import lombok.Data;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetRequest;
import mx.kinich49.expensetracker.validations.ValidatorParameter;

@Data
public class BudgetValidatorParameterImpl implements ValidatorParameter {

    private final MonthlyBudgetRequest request;
}