package mx.kinich49.expensetracker.validations.monthlyincome.providers;

import mx.kinich49.expensetracker.validations.AbstractConditionProvider;
import mx.kinich49.expensetracker.validations.monthlyincome.conditions.*;
import mx.kinich49.expensetracker.validations.monthlyincome.validators.MonthlyIncomeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MonthlyIncomeConditionProvider extends AbstractConditionProvider<MonthlyIncomeValidator.Parameter> {

    private final BeginDateCondition beginDateCondition;
    private final EndDateCondition endDateCondition;
    private final UpperIncomeLimitCondition upperIncomeLimitCondition;
    private final CollisionCondition collisionCondition;

    @Autowired
    public MonthlyIncomeConditionProvider(BeginDateCondition beginDateCondition,
                                          EndDateCondition endDateCondition,
                                          UpperIncomeLimitCondition upperIncomeLimitCondition,
                                          CollisionCondition collisionCondition) {
        this.beginDateCondition = beginDateCondition;
        this.endDateCondition = endDateCondition;
        this.upperIncomeLimitCondition = upperIncomeLimitCondition;
        this.collisionCondition = collisionCondition;
    }

    @Override
    public void buildConditions(MonthlyIncomeValidator.Parameter parameter) {
        var conditionParameter = new MonthlyIncomeRequestParameter(parameter.getRequest());
        addCondition(beginDateCondition, conditionParameter);
        addCondition(endDateCondition, conditionParameter);
        addCondition(upperIncomeLimitCondition, conditionParameter);
        addCondition(collisionCondition, new CollisionCondition.Parameter(parameter.getRequest()));
    }
}
