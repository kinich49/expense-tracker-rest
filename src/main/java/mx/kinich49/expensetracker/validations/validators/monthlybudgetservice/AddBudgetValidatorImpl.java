package mx.kinich49.expensetracker.validations.validators.monthlybudgetservice;

import mx.kinich49.expensetracker.validations.AbstractValidator;
import org.springframework.stereotype.Component;

@Component
public class AddBudgetValidatorImpl
        extends AbstractValidator<BudgetValidatorParameterImpl> {

    public AddBudgetValidatorImpl(AddBudgetValidatorConditionProviderImpl conditionProvider) {
        super(conditionProvider);
    }


}
