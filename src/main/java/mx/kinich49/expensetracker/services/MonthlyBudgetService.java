package mx.kinich49.expensetracker.services;

import mx.kinich49.expensetracker.models.web.WMonthlyBudgetWebModel;
import mx.kinich49.expensetracker.exceptions.MonthlyBudgetNotFoundException;

public interface MonthlyBudgetService {

    WMonthlyBudgetWebModel findMonthlyBudgetBy(int month, int year) throws MonthlyBudgetNotFoundException;
    
}