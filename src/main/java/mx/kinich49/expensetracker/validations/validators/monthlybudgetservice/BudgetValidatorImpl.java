package mx.kinich49.expensetracker.validations.validators.monthlybudgetservice;

import lombok.Data;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetRequest;
import mx.kinich49.expensetracker.validations.AbstractValidator;
import mx.kinich49.expensetracker.validations.ValidatorParameter;
import org.springframework.stereotype.Component;

@Component
public class BudgetValidatorImpl
        extends AbstractValidator<BudgetValidatorImpl.Parameter> {

    public BudgetValidatorImpl(BudgetValidatorConditionProviderImpl conditionProvider) {
        super(conditionProvider);
    }

    @Data
    public static class Parameter implements ValidatorParameter {
        private final MonthlyBudgetRequest request;
    }
}
