package mx.kinich49.expensetracker.validations.monthlycategorybudget.providers;

import mx.kinich49.expensetracker.validations.AbstractConditionProvider;
import mx.kinich49.expensetracker.validations.monthlycategorybudget.RequestParameter;
import mx.kinich49.expensetracker.validations.monthlycategorybudget.conditions.*;
import mx.kinich49.expensetracker.validations.monthlycategorybudget.validators.MonthlyCategoryBudgetValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MonthlyCategoryBudgetConditionProvider
        extends AbstractConditionProvider<MonthlyCategoryBudgetValidator.Parameter> {

    private final LimitCondition monthlyCategoryLimitCondition;
    private final BudgetCondition budgetCondition;
    private final CategoryCondition categoryCondition;
    private final MonthlyLimitCondition monthlyLimitCondition;
    private final MonthlyBudgetCondition monthlyCategoryBudgetBudgetsCondition;

    @Autowired
    public MonthlyCategoryBudgetConditionProvider(LimitCondition monthlyCategoryLimitCondition,
                                                  BudgetCondition budgetCondition,
                                                  CategoryCondition categoryCondition,
                                                  MonthlyLimitCondition monthlyLimitCondition,
                                                  MonthlyBudgetCondition monthlyCategoryBudgetBudgetsCondition) {
        this.monthlyCategoryLimitCondition = monthlyCategoryLimitCondition;
        this.budgetCondition = budgetCondition;
        this.categoryCondition = categoryCondition;
        this.monthlyLimitCondition = monthlyLimitCondition;
        this.monthlyCategoryBudgetBudgetsCondition = monthlyCategoryBudgetBudgetsCondition;
    }

    @Override
    public void buildConditions(MonthlyCategoryBudgetValidator.Parameter parameter) {
        var conditionParameter = new RequestParameter(parameter.getRequest());
        addCondition(budgetCondition, conditionParameter);
        addCondition(categoryCondition, conditionParameter);
        addCondition(monthlyLimitCondition, conditionParameter);

        addCondition(monthlyCategoryBudgetBudgetsCondition,
                new MonthlyBudgetCondition.Parameter(parameter.getMonthlyBudgets()));
        addCondition(monthlyCategoryLimitCondition,
                new LimitCondition.Parameter(parameter.getCurrentLimit(), parameter.getRequest().getMonthlyLimit(),
                        parameter.getMonthlyIncome().getUpperIncomeLimit()));
    }
}
