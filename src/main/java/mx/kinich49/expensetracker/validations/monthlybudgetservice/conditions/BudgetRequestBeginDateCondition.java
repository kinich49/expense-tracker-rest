package mx.kinich49.expensetracker.validations.monthlybudgetservice.conditions;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.web.ErrorWrapper;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetRequest;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.monthlybudgetservice.MonthlyBudgetServiceErrorCodes;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BudgetRequestBeginDateCondition implements Condition<BudgetRequestConditionParameter> {

    /**
     * This condition validates the {@link MonthlyBudgetRequest}
     * has a non-null begin date
     *
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

        if (request.getBeginDate() == null) {
            var errorMessage = "Request must have a begin date. ";
            return Optional.of(new ErrorWrapper(MonthlyBudgetServiceErrorCodes.REQUEST_MUST_HAVE_BEGIN_DATE, errorMessage));
        }

        return Optional.empty();
    }
}
