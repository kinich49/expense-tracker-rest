package mx.kinich49.expensetracker.services.validators;

import mx.kinich49.expensetracker.models.database.MonthlyBudget;
import mx.kinich49.expensetracker.models.database.MonthlyIncome;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetCategoryRequest;

import java.util.List;

public interface MonthlyCategoryBudgetValidator {

    boolean isLimitValid(MonthlyBudgetCategoryRequest request,
                         MonthlyIncome monthlyIncome,
                         List<MonthlyBudget> monthlyBudgets);
}
