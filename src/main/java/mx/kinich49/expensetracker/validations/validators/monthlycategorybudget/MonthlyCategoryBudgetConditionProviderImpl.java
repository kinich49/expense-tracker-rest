package mx.kinich49.expensetracker.validations.validators.monthlycategorybudget;

import mx.kinich49.expensetracker.validations.AbstractConditionProvider;
import mx.kinich49.expensetracker.validations.conditions.monthlycategorybudget.BudgetsConditionImpl;
import mx.kinich49.expensetracker.validations.conditions.monthlycategorybudget.LimitConditionImpl;
import mx.kinich49.expensetracker.validations.conditions.monthlycategorybudget.RequestConditionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MonthlyCategoryBudgetConditionProviderImpl
        extends AbstractConditionProvider<MonthlyCategoryBudgetValidatorImpl.Parameter> {

    private final RequestConditionImpl monthlyCategoryBudgetCondition;
    private final LimitConditionImpl monthlyCategoryLimitCondition;
    private final BudgetsConditionImpl monthlyCategoryBudgetBudgetsCondition;

    @Autowired
    public MonthlyCategoryBudgetConditionProviderImpl(RequestConditionImpl monthlyCategoryBudgetCondition,
                                                      LimitConditionImpl monthlyCategoryLimitCondition,
                                                      BudgetsConditionImpl monthlyCategoryBudgetBudgetsCondition) {
        this.monthlyCategoryBudgetCondition = monthlyCategoryBudgetCondition;
        this.monthlyCategoryLimitCondition = monthlyCategoryLimitCondition;
        this.monthlyCategoryBudgetBudgetsCondition = monthlyCategoryBudgetBudgetsCondition;
    }


    @Override
    public void buildConditions(MonthlyCategoryBudgetValidatorImpl.Parameter parameter) {
        addCondition(monthlyCategoryBudgetCondition,
                new RequestConditionImpl.Parameter(parameter.getRequest()));

        addCondition(monthlyCategoryBudgetBudgetsCondition,
                new BudgetsConditionImpl.Parameter(parameter.getMonthlyBudgets()));

        addCondition(monthlyCategoryLimitCondition,
                new LimitConditionImpl.Parameter(parameter.getCurrentLimit(), parameter.getRequest().getMonthlyLimit(),
                        parameter.getMonthlyIncome().getUpperIncomeLimit()));
    }
}
