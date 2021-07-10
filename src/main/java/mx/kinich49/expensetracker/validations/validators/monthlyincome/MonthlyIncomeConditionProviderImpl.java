package mx.kinich49.expensetracker.validations.validators.monthlyincome;

import mx.kinich49.expensetracker.validations.AbstractConditionProvider;
import mx.kinich49.expensetracker.validations.conditions.monthlyincome.CollisionConditionImpl;
import mx.kinich49.expensetracker.validations.conditions.monthlyincome.RequestConditionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MonthlyIncomeConditionProviderImpl extends AbstractConditionProvider<MonthlyIncomeValidatorImpl.Parameter> {

    @Qualifier("MonthlyIncome.RequestCondition")
    private final RequestConditionImpl requestCondition;
    private final CollisionConditionImpl collisionCondition;

    @Autowired
    public MonthlyIncomeConditionProviderImpl(RequestConditionImpl requestCondition,
                                              CollisionConditionImpl collisionCondition) {
        this.requestCondition = requestCondition;
        this.collisionCondition = collisionCondition;
    }

    @Override
    public void buildConditions(MonthlyIncomeValidatorImpl.Parameter parameter) {
        addCondition(requestCondition, new RequestConditionImpl.Parameter(parameter.getRequest()));
        addCondition(collisionCondition, new CollisionConditionImpl.Parameter(parameter.getRequest()));
    }
}
