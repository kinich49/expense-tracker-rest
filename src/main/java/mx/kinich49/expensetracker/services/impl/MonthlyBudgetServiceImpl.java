package mx.kinich49.expensetracker.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.kinich49.expensetracker.models.web.WMonthlyBudgetWebModel;
import mx.kinich49.expensetracker.exceptions.MonthlyBudgetNotFoundException;
import mx.kinich49.expensetracker.models.database.MonthlyBudget;
import mx.kinich49.expensetracker.repositories.MonthlyBudgetRepository;
import mx.kinich49.expensetracker.repositories.TransactionItemRepository;
import mx.kinich49.expensetracker.services.MonthlyBudgetService;

@Service
public class MonthlyBudgetServiceImpl implements MonthlyBudgetService {

    private MonthlyBudgetRepository monthlyBudgetRepository;
    private TransactionItemRepository transactionRepository;

    @Autowired
    public MonthlyBudgetServiceImpl(MonthlyBudgetRepository monthlyBudgetRepository,
            TransactionItemRepository transactionRepository) {
        this.monthlyBudgetRepository = monthlyBudgetRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public WMonthlyBudgetWebModel findMonthlyBudgetBy(int month, int year) throws MonthlyBudgetNotFoundException {
        MonthlyBudget monthlyBudget = Optional.ofNullable(monthlyBudgetRepository.findByMonthAndYear(month, year))
                .orElseThrow(() -> new MonthlyBudgetNotFoundException(month, year));


        return WMonthlyBudgetWebModel.from(monthlyBudget, null);
    }
}