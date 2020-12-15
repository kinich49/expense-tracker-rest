package mx.kinich49.expensetracker.repositories;

import mx.kinich49.expensetracker.models.database.MonthlyIncome;

import java.util.Optional;

public interface MonthlyIncomeRepositoryCustom {

    Optional<MonthlyIncome> customFind(int month, int year);
}
