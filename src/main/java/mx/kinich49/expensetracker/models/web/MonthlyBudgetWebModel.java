package mx.kinich49.expensetracker.models.web;

import lombok.Data;
import mx.kinich49.expensetracker.models.database.MonthlyIncome;

import java.util.List;

@Data
public class MonthlyBudgetWebModel {

    private final String monthlyIncome;
    private final List<SimpleMonthlyBudgetWebModel> budgets;

    public static MonthlyBudgetWebModel from(MonthlyIncome monthlyIncome) {
        if (monthlyIncome == null)
            return null;

        List<SimpleMonthlyBudgetWebModel> budgets = SimpleMonthlyBudgetWebModel.from(monthlyIncome.getMonthlyBudgets());
        String upperIncomeLimitWithFormat = SimpleMonthlyBudgetWebModel.formatLimit(monthlyIncome.getUpperIncomeLimit(),
                "MXN");

        return new MonthlyBudgetWebModel(upperIncomeLimitWithFormat,
                budgets);
    }
}
