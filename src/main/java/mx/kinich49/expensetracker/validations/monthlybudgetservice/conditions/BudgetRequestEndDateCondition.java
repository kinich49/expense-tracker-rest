package mx.kinich49.expensetracker.validations.monthlybudgetservice.conditions;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.internal.ErrorWrapper;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.monthlybudgetservice.MonthlyBudgetServiceErrorCodes;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BudgetRequestEndDateCondition implements Condition<BudgetRequestConditionParameter> {

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
