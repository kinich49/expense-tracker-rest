package mx.kinich49.expensetracker.repositories;

import mx.kinich49.expensetracker.models.database.MonthlyIncome;

import java.time.YearMonth;
import java.util.Optional;

public interface MonthlyIncomeRepositoryCustom {

    Optional<MonthlyIncome> findCurrentIncome();

    Optional<MonthlyIncome> collides(YearMonth beginDate, YearMonth endDate);
}
