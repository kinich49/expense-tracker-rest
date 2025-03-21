package mx.kinich49.expensetracker.validations.monthlycategorybudget.conditions;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.web.ErrorWrapper;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetCategoryRequest;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.monthlycategorybudget.MonthlyCategoryBudgetErrorCodes;
import mx.kinich49.expensetracker.validations.monthlycategorybudget.RequestParameter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MonthlyLimitCondition implements Condition<RequestParameter> {

    /**
     * This condition validates the {@link MonthlyBudgetCategoryRequest#getMonthlyLimit()}
     * is non-zero
     *
     * @param param the instance to assert it meets all conditions
     * @return An optional containing an error message if the condition is not met.
     * Empty Otherwise
     * @throws ValidationFlowException if a 'gatekeeper validation' is not met.
     */
    @Override
    public Optional<ErrorWrapper> assertCondition(RequestParameter param) throws ValidationFlowException {
        MonthlyBudgetCategoryRequest request = param.getRequest();

        if (request == null)
            throw new ValidationFlowException("Request must not be null. ");

        boolean monthlyLimitIsPresent = request.getMonthlyLimit() > 0;

        if (!monthlyLimitIsPresent) {
            var errorMessage = "Monthly limit is not set. ";
            return Optional.of(new ErrorWrapper(MonthlyCategoryBudgetErrorCodes.MONTHLY_LIMIT_NOT_SET, errorMessage));
        }

        return Optional.empty();
    }
}
