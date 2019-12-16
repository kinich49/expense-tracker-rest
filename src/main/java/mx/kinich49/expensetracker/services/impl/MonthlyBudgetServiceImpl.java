package mx.kinich49.expensetracker.services.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.kinich49.expensetracker.dtos.MonthlyBudgetDto;
import mx.kinich49.expensetracker.models.TransactionItem;
import mx.kinich49.expensetracker.repositories.MonthlyBudgetRepository;
import mx.kinich49.expensetracker.repositories.TransactionItemRepository;
import mx.kinich49.expensetracker.services.MonthlyBudgetService;

@Service
public class MonthlyBudgetServiceImpl implements MonthlyBudgetService {

    private MonthlyBudgetRepository repository;
    private TransactionItemRepository tranactionRepository;

    @Autowired
    public MonthlyBudgetServiceImpl(MonthlyBudgetRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<MonthlyBudgetDto> findMonthlyBudgetBy(int monthOfYear, int year) {
        return null;
    }
}