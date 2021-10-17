package mx.kinich49.expensetracker.validations.monthlycategorybudget.conditions;

import lombok.Data;
import mx.kinich49.expensetracker.models.internal.ErrorWrapper;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.ConditionParameter;
import mx.kinich49.expensetracker.validations.monthlycategorybudget.MonthlyCategoryBudgetErrorCodes;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LimitCondition implements Condition<LimitCondition.Parameter> {

    /**
     * Condition to pass: the sum of the new monthly category limit
     * plus all other limits should be less or equal than the expense limit
     *
     * @param param the instance to assert it meets all conditions
     * @return an Optional containing the error message if the condition is not met.
     * Empty Optional otherwise
     */
    @Override
    public Optional<ErrorWrapper> assertCondition(Parameter param) {
        boolean categoryLimitSupersMonthlyLimit =
                param.monthlyCategoryLimitAccumulated + param.newCategoryLimit > param.expenseLimit;

        if (categoryLimitSupersMonthlyLimit) {
            String error = String.format("The limit %1$d goes over the top of your monthly expense limit of %2$d",
                    param.newCategoryLimit, param.expenseLimit);
            return Optional.of(new ErrorWrapper(MonthlyCategoryBudgetErrorCodes.LIMIT_OVERCOMES_MONTHLY_EXPENSES, error));
        }

        return Optional.empty();
    }

    @Data
    public static final class Parameter implements ConditionParameter {
        final int monthlyCategoryLimitAccumulated;
        final int newCategoryLimit;
        final int expenseLimit;
    }
}
