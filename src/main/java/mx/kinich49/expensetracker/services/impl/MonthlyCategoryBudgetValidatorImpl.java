package mx.kinich49.expensetracker.services.impl;

import mx.kinich49.expensetracker.models.database.MonthlyBudget;
import mx.kinich49.expensetracker.models.database.MonthlyBudgetCategory;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetCategoryRequest;
import mx.kinich49.expensetracker.services.MonthlyCategoryBudgetValidator;
import org.springframework.stereotype.Component;

@Component
public class MonthlyCategoryBudgetValidatorImpl implements MonthlyCategoryBudgetValidator {

    @Override
    public boolean isLimitValid(MonthlyBudgetCategoryRequest request, MonthlyBudget monthlyBudget) {
        if (monthlyBudget.getMonthlyBudgetCategories().isEmpty())
            return true;

        int monthlyLimit = monthlyBudget.getMonthlyLimit();
        long categoryId = request.getCategoryId();
        long monthlyCategoryLimit = request.getMonthlyLimit();

        int currentLimit = monthlyBudget.getMonthlyBudgetCategories().stream()
                .filter(monthlyBudgetCategory ->
                        monthlyBudgetCategory.getCategory().getId() != categoryId)
                .mapToInt(MonthlyBudgetCategory::getMonthlyLimit)
                .reduce(0, Integer::sum);

        return (monthlyCategoryLimit + currentLimit) <= monthlyLimit;
    }
}
