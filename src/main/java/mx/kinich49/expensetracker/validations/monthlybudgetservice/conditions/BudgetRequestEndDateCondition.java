package mx.kinich49.expensetracker.validations.monthlybudgetservice.conditions;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.internal.ErrorWrapper;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetRequest;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.monthlybudgetservice.MonthlyBudgetServiceErrorCodes;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BudgetRequestEndDateCondition implements Condition<BudgetRequestConditionParameter> {

    /**
     * This condition validates the {@link MonthlyBudgetRequest}
     * end date is non-null and is equal or after its beginDate
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

        var beginDate = request.getBeginDate();
        var endDate = request.getEndDate();

        if (endDate == null) {
            return Optional.empty();
        }

        if (beginDate == null) {
            return Optional.empty();
        }

        if (endDate.isBefore(beginDate)) {
            var errorMessage = "End date can't be before begin date";
            return Optional.of(new ErrorWrapper(MonthlyBudgetServiceErrorCodes.END_DATE_BEFORE_BEGIN_DATE, errorMessage));
        }

        return Optional.empty();
    }
}
