package mx.kinich49.expensetracker.services;

import mx.kinich49.expensetracker.exceptions.InvalidMonthlyCategoryBudgetException;
import mx.kinich49.expensetracker.models.web.MonthlyBudgetCategoryWebModel;
import mx.kinich49.expensetracker.models.web.MonthlyBudgetWebModel;
import mx.kinich49.expensetracker.models.web.SimpleMonthlyBudgetWebModel;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetCategoryRequest;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetRequest;

import java.time.YearMonth;
import java.util.Optional;

public interface MonthlyBudgetService {

    SimpleMonthlyBudgetWebModel insertMonthlyBudget(MonthlyBudgetRequest request);

    Optional<MonthlyBudgetWebModel> findMonthlyBudgets(YearMonth date);

    MonthlyBudgetCategoryWebModel addMonthlyBudgetCategory(MonthlyBudgetCategoryRequest request)
            throws InvalidMonthlyCategoryBudgetException;

    void removeMonthlyBudgetCategory(long budgetId, long categoryId);

    void removeMonthlyBudget(long budgetId);
}
