package mx.kinich49.expensetracker.validations.monthlyincome.conditions;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.internal.ErrorWrapper;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.monthlyincome.MonthlyIncomeErrorCodes;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BeginDateCondition implements Condition<MonthlyIncomeRequestParameter> {

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
