package mx.kinich49.expensetracker.validations.validators.monthlybudgetservice;

import mx.kinich49.expensetracker.validations.AbstractValidator;
import org.springframework.stereotype.Component;

@Component
public class UpdateBudgetValidatorImpl extends AbstractValidator<BudgetValidatorParameterImpl> {

    public UpdateBudgetValidatorImpl(UpdateBudgetConditionProviderImpl conditionProvider) {
        super(conditionProvider);
    }
}
