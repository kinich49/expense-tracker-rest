package mx.kinich49.expensetracker.models.web;

import lombok.Data;
import mx.kinich49.expensetracker.models.database.MonthlyBudgetCategory;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MonthlyBudgetCategoryWebModel {

    private final long id;
    private final CategoryWebModel category;
    private final long budgetId;
    private final String monthlyLimit;

    public static MonthlyBudgetCategoryWebModel from(MonthlyBudgetCategory monthlyBudgetCategory) {
        if (monthlyBudgetCategory == null)
            return null;

        CategoryWebModel category = CategoryWebModel.from(monthlyBudgetCategory.getCategory());

        long budgetId = monthlyBudgetCategory.getMonthlyBudget().getId();
        int monthlyLimit = monthlyBudgetCategory.getMonthlyLimit();

        String monthlyLimitWithFormat = MonthlyBudgetWebModel.formatLimit(monthlyLimit, "MXN");

        return new MonthlyBudgetCategoryWebModel(
                monthlyBudgetCategory.getId(),
                category,
                budgetId,
                monthlyLimitWithFormat
        );
    }

    public static List<MonthlyBudgetCategoryWebModel> from(Collection<MonthlyBudgetCategory> monthlyBudgetCategories) {
        if (monthlyBudgetCategories == null || monthlyBudgetCategories.isEmpty())
            return null;

        return monthlyBudgetCategories.stream()
                .map(MonthlyBudgetCategoryWebModel::from)
                .collect(Collectors.toList());
    }
}
