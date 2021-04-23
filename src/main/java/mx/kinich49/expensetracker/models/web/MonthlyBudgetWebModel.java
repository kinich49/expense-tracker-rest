package mx.kinich49.expensetracker.models.web;

import lombok.Data;
import mx.kinich49.expensetracker.models.database.MonthlyBudget;
import mx.kinich49.expensetracker.models.database.MonthlyIncome;

import java.util.List;

@Data
public class MonthlyBudgetWebModel {

    private final String monthlyIncome;
    private final List<SimpleMonthlyBudgetWebModel> budgets;

    public static MonthlyBudgetWebModel from(MonthlyIncome monthlyIncome,
                                             List<MonthlyBudget> monthlyBudgets) {
        if (monthlyIncome == null)
            return null;

        if (monthlyBudgets == null || monthlyBudgets.isEmpty())
            return null;

        List<SimpleMonthlyBudgetWebModel> budgets = SimpleMonthlyBudgetWebModel.from(monthlyBudgets);
        String upperIncomeLimitWithFormat = SimpleMonthlyBudgetWebModel.formatLimit(monthlyIncome.getUpperIncomeLimit(),
                "MXN");

        return new MonthlyBudgetWebModel(upperIncomeLimitWithFormat,
                budgets);
    }
}
