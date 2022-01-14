package mx.kinich49.expensetracker.validations.monthlyincome.validators;

import lombok.Data;
import mx.kinich49.expensetracker.models.web.requests.MonthlyIncomeRequest;
import mx.kinich49.expensetracker.validations.AbstractValidator;
import mx.kinich49.expensetracker.validations.ValidatorParameter;
import mx.kinich49.expensetracker.validations.monthlyincome.providers.MonthlyIncomeConditionProvider;
import org.springframework.stereotype.Component;

@Component
public class MonthlyIncomeValidator extends AbstractValidator<MonthlyIncomeValidator.Parameter> {

    public MonthlyIncomeValidator(MonthlyIncomeConditionProvider conditionProvider) {
        super(conditionProvider);
    }

    @Data
    public static final class Parameter implements ValidatorParameter {
        private final MonthlyIncomeRequest request;
    }
}
