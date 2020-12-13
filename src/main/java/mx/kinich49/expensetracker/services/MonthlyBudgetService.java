package mx.kinich49.expensetracker.services;

import mx.kinich49.expensetracker.exceptions.CategoryNotFoundException;
import mx.kinich49.expensetracker.exceptions.MonthlyBudgetNotFoundException;
import mx.kinich49.expensetracker.models.web.MonthlyBudgetCategoryWebModel;
import mx.kinich49.expensetracker.models.web.MonthlyBudgetWebModel;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetCategoryRequest;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetRequest;

import java.util.Optional;

public interface MonthlyBudgetService {

    MonthlyBudgetWebModel insertMonthlyBudget(MonthlyBudgetRequest request);

    Optional<MonthlyBudgetWebModel> findMonthlyBudget(int month, int year);

    MonthlyBudgetCategoryWebModel addMonthlyBudgetCategory(MonthlyBudgetCategoryRequest request)
            throws MonthlyBudgetNotFoundException, CategoryNotFoundException;
}
