package mx.kinich49.expensetracker.services;

import mx.kinich49.expensetracker.models.database.MonthlyBudget;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetCategoryRequest;

public interface MonthlyCategoryBudgetValidator {

    boolean isLimitValid(MonthlyBudgetCategoryRequest request, MonthlyBudget monthlyBudget);
}
