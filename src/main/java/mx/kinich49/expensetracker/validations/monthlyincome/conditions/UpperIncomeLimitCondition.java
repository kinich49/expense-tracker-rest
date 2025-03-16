package mx.kinich49.expensetracker.validations.monthlyincome.conditions;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.web.ErrorWrapper;
import mx.kinich49.expensetracker.models.web.requests.MonthlyIncomeRequest;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.monthlyincome.MonthlyIncomeErrorCodes;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UpperIncomeLimitCondition implements Condition<MonthlyIncomeRequestParameter> {

    /**
     * This condition validates {@link MonthlyIncomeRequest}
     * upperIncomeLimit is  positive non-zero
     *
     * @param param the instance to assert it meets all conditions
     * @return An optional containing an error message if the condition is not met.
     * Empty Otherwise
     * @throws ValidationFlowException if a 'gatekeeper validation' is not met.
     */
    @Override
    public Optional<ErrorWrapper> assertCondition(MonthlyIncomeRequestParameter param)
            throws ValidationFlowException {
        var request = param.getRequest();

        if (request == null) {
            var errorMessage = "Request is null or empty";
            return Optional.of(new ErrorWrapper(MonthlyIncomeErrorCodes.REQUEST_NULL_OR_EMPTY, errorMessage));
        }

        int upperIncomeLimit = request.getUpperIncomeLimit();

        if (upperIncomeLimit <= 0) {
            var errorMessage = "Upper Income Limit must be greater than 0";
            return Optional.of(new ErrorWrapper(MonthlyIncomeErrorCodes.UPPER_INCOME_LIMIT_IS_ZERO, errorMessage));
        }

        return Optional.empty();
    }
}
