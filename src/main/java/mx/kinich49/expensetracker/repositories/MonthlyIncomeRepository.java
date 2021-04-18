package mx.kinich49.expensetracker.repositories;

import mx.kinich49.expensetracker.models.database.MonthlyIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.YearMonth;
import java.util.Optional;

public interface MonthlyIncomeRepository extends JpaRepository<MonthlyIncome, Long>, MonthlyIncomeRepositoryCustom {

    @Query("SELECT mi FROM Monthly_Incomes mi " +
            "WHERE (?1 >= mi.beginDate AND mi.endDate IS NULL) OR " +
            "(?1 >= mi.beginDate AND ?1 < mi.endDate)")
    Optional<MonthlyIncome> findByBeginDate(YearMonth beginDate);

    @Query("SELECT count(mi) > 0 FROM Monthly_Incomes mi " +
            "WHERE (?1 >= mi.beginDate AND mi.endDate IS NULL) OR " +
            "(?1 >= mi.beginDate AND ?1 < mi.endDate)")
    boolean existsByBeginDate(YearMonth beginDate);

    @Modifying
    @Query("DELETE FROM Monthly_Incomes WHERE id=?1")
    void deleteById(long id);
}
