package mx.kinich49.expensetracker.validations.validators.monthlyincome;

import lombok.Data;
import mx.kinich49.expensetracker.models.web.requests.MonthlyIncomeRequest;
import mx.kinich49.expensetracker.validations.AbstractValidator;
import mx.kinich49.expensetracker.validations.ValidatorParameter;
import org.springframework.stereotype.Component;

@Component
public class MonthlyIncomeValidatorImpl extends AbstractValidator<MonthlyIncomeValidatorImpl.Parameter> {

    public MonthlyIncomeValidatorImpl(MonthlyIncomeConditionProviderImpl conditionProvider) {
        super(conditionProvider);
    }

    @Data
    public static final class Parameter implements ValidatorParameter {
        private final MonthlyIncomeRequest request;
    }
}
