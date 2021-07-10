package mx.kinich49.expensetracker.validations.validators.monthlybudgetservice;

import mx.kinich49.expensetracker.validations.AbstractConditionProvider;
import mx.kinich49.expensetracker.validations.conditions.monthlybudgetservice.BudgetLimitCondition;
import mx.kinich49.expensetracker.validations.conditions.monthlybudgetservice.BudgetRequestCondition;
import mx.kinich49.expensetracker.validations.conditions.monthlybudgetservice.BudgetRequestConditionParameterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddBudgetValidatorConditionProviderImpl extends AbstractConditionProvider<BudgetValidatorParameterImpl> {

    private final BudgetLimitCondition budgetLimitCondition;
    private final BudgetRequestCondition budgetRequestCondition;

    @Autowired
    public AddBudgetValidatorConditionProviderImpl(BudgetLimitCondition budgetLimitCondition,
                                                   BudgetRequestCondition budgetRequestCondition) {
        this.budgetLimitCondition = budgetLimitCondition;
        this.budgetRequestCondition = budgetRequestCondition;
    }

    @Override
    public void buildConditions(BudgetValidatorParameterImpl parameter) {
        var conditionParameter = new BudgetRequestConditionParameterImpl(parameter.getRequest());
        addCondition(budgetRequestCondition, conditionParameter);
        addCondition(budgetLimitCondition, conditionParameter);
    }
}
