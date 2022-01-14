package mx.kinich49.expensetracker.validations.monthlyincome.conditions;

import lombok.Data;
import mx.kinich49.expensetracker.models.web.requests.MonthlyIncomeRequest;
import mx.kinich49.expensetracker.validations.ConditionParameter;

@Data
public class MonthlyIncomeRequestParameter implements ConditionParameter {
    private final MonthlyIncomeRequest request;
}
