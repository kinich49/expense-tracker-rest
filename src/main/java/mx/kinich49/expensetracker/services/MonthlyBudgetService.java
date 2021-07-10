package mx.kinich49.expensetracker.services;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.web.MonthlyBudgetCategoryWebModel;
import mx.kinich49.expensetracker.models.web.MonthlyBudgetWebModel;
import mx.kinich49.expensetracker.models.web.SimpleMonthlyBudgetWebModel;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetCategoryRequest;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetRequest;

import java.time.YearMonth;
import java.util.Optional;

public interface MonthlyBudgetService {

    SimpleMonthlyBudgetWebModel insertMonthlyBudget(MonthlyBudgetRequest request) throws BusinessException;

    Optional<MonthlyBudgetWebModel> findMonthlyBudgets(YearMonth date) throws BusinessException;

    MonthlyBudgetCategoryWebModel addMonthlyBudgetCategory(MonthlyBudgetCategoryRequest request)
            throws BusinessException;

    SimpleMonthlyBudgetWebModel update(MonthlyBudgetRequest request) throws BusinessException;

    void removeMonthlyBudgetCategory(long budgetId, long categoryId);

    void removeMonthlyBudget(long budgetId);
}
