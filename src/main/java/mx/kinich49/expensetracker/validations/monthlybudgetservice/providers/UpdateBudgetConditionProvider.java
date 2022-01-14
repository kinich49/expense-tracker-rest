package mx.kinich49.expensetracker.validations.monthlybudgetservice.providers;

import mx.kinich49.expensetracker.validations.AbstractConditionProvider;
import mx.kinich49.expensetracker.validations.monthlybudgetservice.conditions.*;
import mx.kinich49.expensetracker.validations.monthlybudgetservice.BudgetValidatorParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateBudgetConditionProvider
        extends AbstractConditionProvider<BudgetValidatorParameter> {

    private final UpdateBudgetLimitCondition updateBudgetLimitCondition;
    private final BudgetRequestBeginDateCondition beginDateCondition;
    private final BudgetRequestBaseLimitCondition baseLimitCondition;
    private final BudgetRequestEndDateCondition endDateCondition;

    @Autowired
    public UpdateBudgetConditionProvider(UpdateBudgetLimitCondition updateBudgetLimitCondition,
                                         BudgetRequestBeginDateCondition beginDateCondition,
                                         BudgetRequestBaseLimitCondition baseLimitCondition,
                                         BudgetRequestEndDateCondition endDateCondition) {
        this.updateBudgetLimitCondition = updateBudgetLimitCondition;
        this.beginDateCondition = beginDateCondition;
        this.baseLimitCondition = baseLimitCondition;
        this.endDateCondition = endDateCondition;
    }

    @Override
    public void buildConditions(BudgetValidatorParameter parameter) {
        var conditionParameter = new BudgetRequestConditionParameter(parameter.getRequest());

        addCondition(beginDateCondition, conditionParameter);
        addCondition(baseLimitCondition, conditionParameter);
        addCondition(endDateCondition, conditionParameter);
        addCondition(updateBudgetLimitCondition, conditionParameter);
    }
}
