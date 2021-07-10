package mx.kinich49.expensetracker.validations.validators.monthlybudgetservice;

import mx.kinich49.expensetracker.validations.AbstractConditionProvider;
import mx.kinich49.expensetracker.validations.conditions.monthlybudgetservice.BudgetLimitCondition;
import mx.kinich49.expensetracker.validations.conditions.monthlybudgetservice.BudgetRequestCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateBudgetConditionProviderImpl extends AbstractConditionProvider<BudgetValidatorParameterImpl> {

    private final BudgetRequestCondition budgetRequestCondition;
    private final BudgetLimitCondition budgetLimitCondition;

    @Autowired
    public UpdateBudgetConditionProviderImpl(BudgetRequestCondition budgetRequestCondition,
                                             BudgetLimitCondition budgetLimitCondition) {
        this.budgetRequestCondition = budgetRequestCondition;
        this.budgetLimitCondition = budgetLimitCondition;
    }

    @Override
    public void buildConditions(BudgetValidatorParameterImpl parameter) {

    }
}
