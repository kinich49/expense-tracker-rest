package mx.kinich49.expensetracker.validations.monthlybudgetservice.validators;

import mx.kinich49.expensetracker.validations.AbstractValidator;
import mx.kinich49.expensetracker.validations.monthlybudgetservice.BudgetValidatorParameter;
import mx.kinich49.expensetracker.validations.monthlybudgetservice.providers.UpdateBudgetConditionProvider;
import org.springframework.stereotype.Component;

@Component
public class UpdateBudgetValidator extends AbstractValidator<BudgetValidatorParameter> {

    public UpdateBudgetValidator(UpdateBudgetConditionProvider conditionProvider) {
        super(conditionProvider);
    }
}
