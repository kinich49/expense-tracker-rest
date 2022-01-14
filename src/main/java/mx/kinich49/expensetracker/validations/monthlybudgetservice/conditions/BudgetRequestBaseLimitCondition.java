package mx.kinich49.expensetracker.validations.monthlybudgetservice.conditions;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.internal.ErrorWrapper;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetRequest;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.monthlybudgetservice.MonthlyBudgetServiceErrorCodes;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BudgetRequestBaseLimitCondition implements Condition<BudgetRequestConditionParameter> {

    /**
     * This condition validates the {@link MonthlyBudgetRequest}
     * @param param the instance to assert it meets all conditions
     * @return An optional containing an error message if the condition is not met.
     * Empty Otherwise
     * @throws ValidationFlowException if a 'gatekeeper validation' is not met.
     */
    @Override
    public Optional<ErrorWrapper> assertCondition(BudgetRequestConditionParameter param) throws ValidationFlowException {
        var request = param.getRequest();

        if (request == null)
            throw new ValidationFlowException("Request can't be null");

        int baseLimit = request.getBaseLimit();

        if (baseLimit < 0) {
           var errorMessage = "base limit can't be less than 0";
           return Optional.of(new ErrorWrapper(MonthlyBudgetServiceErrorCodes.BASE_LIMIT_LESS_THAN_ZERO, errorMessage));
        }

        return Optional.empty();
    }
}
