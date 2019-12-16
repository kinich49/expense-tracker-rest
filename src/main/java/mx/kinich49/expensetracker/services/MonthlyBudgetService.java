package mx.kinich49.expensetracker.services;

import java.util.List;

import mx.kinich49.expensetracker.dtos.MonthlyBudgetDto;

public interface MonthlyBudgetService {

    List<MonthlyBudgetDto> findMonthlyBudgetBy(int monthOfYear, int year);
    
}