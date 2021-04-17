package mx.kinich49.expensetracker.repositories;

import mx.kinich49.expensetracker.models.database.MonthlyIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.YearMonth;
import java.util.Optional;

public interface MonthlyIncomeRepository extends JpaRepository<MonthlyIncome, Long> {

    @Query("SELECT mi FROM Monthly_Incomes mi " +
            "WHERE (?1 >= mi.beginDate AND mi.endDate IS NULL) OR " +
            "(?1 >= mi.beginDate AND ?1 <= mi.endDate)")
    Optional<MonthlyIncome> findByBeginDate(YearMonth beginDate);

    boolean existsByBeginDate(YearMonth beginDate);

}
