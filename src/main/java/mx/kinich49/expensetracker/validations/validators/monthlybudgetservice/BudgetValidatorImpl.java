package mx.kinich49.expensetracker.validations.validators.monthlybudgetservice;

import lombok.Data;
import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetRequest;
import mx.kinich49.expensetracker.utils.StringUtils;
import mx.kinich49.expensetracker.validations.Validator;
import mx.kinich49.expensetracker.validations.ValidatorParameter;
import mx.kinich49.expensetracker.validations.conditions.monthlybudgetservice.BudgetLimitCondition;
import mx.kinich49.expensetracker.validations.conditions.monthlybudgetservice.BudgetRequestCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BudgetValidatorImpl implements Validator<BudgetValidatorImpl.Parameter> {

    private final BudgetLimitCondition budgetLimitCondition;
    private final BudgetRequestCondition budgetRequestCondition;

    @Autowired
    public BudgetValidatorImpl(BudgetLimitCondition budgetLimitCondition,
                               BudgetRequestCondition budgetRequestCondition) {
        this.budgetLimitCondition = budgetLimitCondition;
        this.budgetRequestCondition = budgetRequestCondition;
    }

    @Override
    public void validate(Parameter param) throws BusinessException {
        MonthlyBudgetRequest request = param.request;
        StringBuilder accumulator = new StringBuilder();

        budgetRequestCondition.assertCondition(new BudgetRequestCondition.Parameter(request))
                .ifPresent(accumulator::append);

        budgetLimitCondition.assertCondition(new BudgetLimitCondition.Parameter(request))
                .ifPresent(accumulator::append);

        String error = accumulator.toString().trim();

        if (StringUtils.isNeitherNullNorEmptyNorBlank(error)) {
            throw new BusinessException(error.trim());
        }
    }

    @Data
    public static class Parameter implements ValidatorParameter {
        private final MonthlyBudgetRequest request;
    }
}
