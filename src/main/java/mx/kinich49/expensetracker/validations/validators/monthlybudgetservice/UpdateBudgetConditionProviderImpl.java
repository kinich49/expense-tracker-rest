package mx.kinich49.expensetracker.validations.validators.monthlybudgetservice;

import mx.kinich49.expensetracker.validations.AbstractConditionProvider;
import mx.kinich49.expensetracker.validations.conditions.monthlybudgetservice.BudgetRequestCondition;
import mx.kinich49.expensetracker.validations.conditions.monthlybudgetservice.BudgetRequestConditionParameterImpl;
import mx.kinich49.expensetracker.validations.conditions.monthlybudgetservice.UpdateBudgetLimitCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateBudgetConditionProviderImpl extends AbstractConditionProvider<BudgetValidatorParameterImpl> {

    private final BudgetRequestCondition budgetRequestCondition;
    private final UpdateBudgetLimitCondition updateBudgetLimitCondition;

    @Autowired
    public UpdateBudgetConditionProviderImpl(BudgetRequestCondition budgetRequestCondition,
                                             UpdateBudgetLimitCondition updateBudgetLimitCondition) {
        this.budgetRequestCondition = budgetRequestCondition;
        this.updateBudgetLimitCondition = updateBudgetLimitCondition;
    }

    @Override
    public void buildConditions(BudgetValidatorParameterImpl parameter) {
        var conditionParameter = new BudgetRequestConditionParameterImpl(parameter.getRequest());

        addCondition(budgetRequestCondition, conditionParameter);
        addCondition(updateBudgetLimitCondition, conditionParameter);

    }
}
