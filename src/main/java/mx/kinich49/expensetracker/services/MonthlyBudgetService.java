package mx.kinich49.expensetracker.services;

import java.util.List;

import mx.kinich49.expensetracker.dtos.MonthlyBudgetDto;
import mx.kinich49.expensetracker.exceptions.MonthlyBudgetNotFoundException;

public interface MonthlyBudgetService {

    MonthlyBudgetDto findMonthlyBudgetBy(int month, int year) throws MonthlyBudgetNotFoundException;
    
}