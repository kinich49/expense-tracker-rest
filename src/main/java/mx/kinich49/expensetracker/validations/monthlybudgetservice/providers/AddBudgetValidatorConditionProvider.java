package mx.kinich49.expensetracker.validations.monthlybudgetservice.providers;

import mx.kinich49.expensetracker.validations.AbstractConditionProvider;
import mx.kinich49.expensetracker.validations.monthlybudgetservice.conditions.*;
import mx.kinich49.expensetracker.validations.monthlybudgetservice.BudgetValidatorParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddBudgetValidatorConditionProvider
        extends AbstractConditionProvider<BudgetValidatorParameter> {

    private final BudgetLimitCondition budgetLimitCondition;
    private final BudgetRequestBaseLimitCondition baseLimitCondition;
    private final BudgetRequestBeginDateCondition beginDateCondition;
    private final BudgetRequestEndDateCondition endDateCondition;

    @Autowired
    public AddBudgetValidatorConditionProvider(BudgetLimitCondition budgetLimitCondition,
                                               BudgetRequestBaseLimitCondition baseLimitCondition,
                                               BudgetRequestBeginDateCondition beginDateCondition,
                                               BudgetRequestEndDateCondition endDateCondition) {
        this.budgetLimitCondition = budgetLimitCondition;
        this.baseLimitCondition = baseLimitCondition;
        this.beginDateCondition = beginDateCondition;
        this.endDateCondition = endDateCondition;
    }

    @Override
    public void buildConditions(BudgetValidatorParameter parameter) {
        var conditionParameter = new BudgetRequestConditionParameter(parameter.getRequest());
        addCondition(baseLimitCondition, conditionParameter);
        addCondition(beginDateCondition, conditionParameter);
        addCondition(endDateCondition, conditionParameter);
        addCondition(budgetLimitCondition, conditionParameter);
    }
}
