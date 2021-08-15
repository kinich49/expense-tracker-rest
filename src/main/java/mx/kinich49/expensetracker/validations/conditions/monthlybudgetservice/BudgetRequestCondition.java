package mx.kinich49.expensetracker.validations.conditions.monthlybudgetservice;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetRequest;
import mx.kinich49.expensetracker.utils.StringUtils;
import mx.kinich49.expensetracker.validations.Condition;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.util.Optional;

@Component
public class BudgetRequestCondition implements Condition<BudgetRequestConditionParameterImpl> {

    @Override
    public Optional<String> assertCondition(BudgetRequestConditionParameterImpl param) throws ValidationFlowException {
        MonthlyBudgetRequest request = param.getRequest();

        if (request == null)
            throw new ValidationFlowException("Request can't be null");

        StringBuilder accumulator = new StringBuilder();
        assertBudgetBeginDate(request, accumulator);
        assertBudgetEndDate(request, accumulator);
        assertBaseLimit(request, accumulator);

        String error = accumulator.toString().trim();

        if (StringUtils.isNeitherNullNorEmptyNorBlank(error)) {
            return Optional.of(error);
        }

        return Optional.empty();
    }

    private void assertBudgetBeginDate(MonthlyBudgetRequest request, StringBuilder accumulator) {
        if (request.getBeginDate() == null) {
            accumulator.append("Request must have a begin date. ");
        }
    }

    private void assertBudgetEndDate(MonthlyBudgetRequest request, StringBuilder accumulator) {
        YearMonth beginDate = request.getBeginDate();
        YearMonth endDate = request.getEndDate();

        if (endDate == null) {
            return;
        }

        if (beginDate == null) {
            return;
        }

        if (endDate.isBefore(beginDate)) {
            accumulator.append("End date can't be before begin date");
        }
    }

    private void assertBaseLimit(MonthlyBudgetRequest request, StringBuilder accumulator) {
        int baseLimit = request.getBaseLimit();

        if (baseLimit < 0) {
            accumulator.append("base limit can't be less than 0");
        }
    }


}
