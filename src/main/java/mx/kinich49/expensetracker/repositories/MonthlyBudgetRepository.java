package mx.kinich49.expensetracker.repositories;

import mx.kinich49.expensetracker.models.database.MonthlyBudget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface MonthlyBudgetRepository extends JpaRepository<MonthlyBudget, Long> {

    @Query("SELECT mb FROM Monthly_Budgets mb " +
            "WHERE (?1 >= mb.beginDate AND mb.endDate IS NULL) OR " +
            "(?1 >= mb.beginDate AND ?1 <= mb.endDate)")
    List<MonthlyBudget> findByDate(YearMonth date);

}