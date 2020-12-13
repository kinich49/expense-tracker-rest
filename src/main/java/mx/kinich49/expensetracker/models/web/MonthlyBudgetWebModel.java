package mx.kinich49.expensetracker.models.web;

import lombok.Data;
import mx.kinich49.expensetracker.models.database.MonthlyBudget;

import java.time.LocalDate;
import java.util.List;

@Data
public class MonthlyBudgetWebModel {

    private final long id;
    private final LocalDate budgetDate;
    private final List<MonthlyBudgetCategoryWebModel> monthlyBudgetCategories;

    public static MonthlyBudgetWebModel from(MonthlyBudget monthlyBudget) {
        if (monthlyBudget == null)
            return null;

        List<MonthlyBudgetCategoryWebModel> monthlyBudgetCategories = MonthlyBudgetCategoryWebModel
                .from(monthlyBudget.getMonthlyBudgetCategories());

        return new MonthlyBudgetWebModel(monthlyBudget.getId(),
                monthlyBudget.getBudgetDate(),
                monthlyBudgetCategories);
    }

}
