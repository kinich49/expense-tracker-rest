package mx.kinich49.expensetracker.validations.validators.monthlybudgetservice;

import mx.kinich49.expensetracker.validations.AbstractConditionProvider;
import mx.kinich49.expensetracker.validations.conditions.monthlybudgetservice.BudgetLimitCondition;
import mx.kinich49.expensetracker.validations.conditions.monthlybudgetservice.BudgetRequestCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BudgetValidatorConditionProviderImpl extends AbstractConditionProvider<BudgetValidatorImpl.Parameter> {

    private final BudgetLimitCondition budgetLimitCondition;
    private final BudgetRequestCondition budgetRequestCondition;

    @Autowired
    public BudgetValidatorConditionProviderImpl(BudgetLimitCondition budgetLimitCondition,
                                                BudgetRequestCondition budgetRequestCondition) {
        this.budgetLimitCondition = budgetLimitCondition;
        this.budgetRequestCondition = budgetRequestCondition;
    }

    @Override
    public void buildConditions(BudgetValidatorImpl.Parameter parameter) {
        addCondition(budgetRequestCondition, new BudgetRequestCondition.Parameter(parameter.getRequest()));
        addCondition(budgetLimitCondition, new BudgetLimitCondition.Parameter(parameter.getRequest()));
    }
}
