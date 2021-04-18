package mx.kinich49.expensetracker.validations.conditions.monthlyincome;

import lombok.RequiredArgsConstructor;
import mx.kinich49.expensetracker.models.web.requests.MonthlyIncomeRequest;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.ConditionParameter;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.util.Optional;

@Component("MonthlyIncome.RequestCondition")
public class RequestConditionImpl implements Condition<RequestConditionImpl.Parameter> {

    @Override
    public Optional<String> assertCondition(Parameter param) {
        MonthlyIncomeRequest request = param.request;

        if (request == null)
            return Optional.of("Request must not be null.");

        StringBuilder sb = new StringBuilder();
        assertBeginDate(request, sb);
        assertEndDate(request, sb);
        assertUpperIncomeLimit(request, sb);

        if (!sb.toString().trim().isEmpty()) {
            return Optional.of(sb.toString().trim());
        }

        return Optional.empty();
    }

    private void assertBeginDate(MonthlyIncomeRequest request, StringBuilder accum) {
        YearMonth beginDate = request.getBeginDate();

        if (beginDate == null) {
            accum.append("Begin Date must not be null. ");
        }
    }

    private void assertEndDate(MonthlyIncomeRequest request, StringBuilder accum) {
        YearMonth endDate = request.getEndDate();
        YearMonth beginDate = request.getBeginDate();

        if (endDate == null || beginDate == null) {
            return;
        }

        if (endDate.isBefore(beginDate)) {
            accum.append("End Date must be equal or after Begin Date. ");
        }
    }

    private void assertUpperIncomeLimit(MonthlyIncomeRequest request, StringBuilder accum) {
        int upperIncomeLimit = request.getUpperIncomeLimit();

        if (upperIncomeLimit == 0) {
            accum.append("Upper Income Limit must not be zero. ");
        }
    }

    @RequiredArgsConstructor
    public static final class Parameter implements ConditionParameter {
        private final MonthlyIncomeRequest request;
    }
}
