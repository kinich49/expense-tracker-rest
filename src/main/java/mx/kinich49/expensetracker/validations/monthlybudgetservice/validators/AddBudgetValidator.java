package mx.kinich49.expensetracker.validations.monthlybudgetservice.validators;

import mx.kinich49.expensetracker.validations.AbstractValidator;
import mx.kinich49.expensetracker.validations.monthlybudgetservice.BudgetValidatorParameter;
import mx.kinich49.expensetracker.validations.monthlybudgetservice.providers.AddBudgetValidatorConditionProvider;
import org.springframework.stereotype.Component;

@Component
public class AddBudgetValidator
        extends AbstractValidator<BudgetValidatorParameter> {

    public AddBudgetValidator(AddBudgetValidatorConditionProvider conditionProvider) {
        super(conditionProvider);
    }

}
