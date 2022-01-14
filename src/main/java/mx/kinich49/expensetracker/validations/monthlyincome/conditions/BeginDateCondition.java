package mx.kinich49.expensetracker.validations.monthlyincome.conditions;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.internal.ErrorWrapper;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetCategoryRequest;
import mx.kinich49.expensetracker.models.web.requests.MonthlyIncomeRequest;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.monthlyincome.MonthlyIncomeErrorCodes;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BeginDateCondition implements Condition<MonthlyIncomeRequestParameter> {

    /**
     * This condition validates {@link MonthlyIncomeRequest}
     * has a non-null begin date
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

        if (request == null){
            var errorMessage = "Request is null or empty";
            return Optional.of(new ErrorWrapper(MonthlyIncomeErrorCodes.REQUEST_NULL_OR_EMPTY, errorMessage));
        }

        if (request.getBeginDate() == null) {
            var errorMessage = "Begin Date must not be null. ";
            return Optional.of(new ErrorWrapper(MonthlyIncomeErrorCodes.BEGIN_DATE_NULL, errorMessage));
        }

        return Optional.empty();
    }
}
