package mx.kinich49.expensetracker.repositories;

import mx.kinich49.expensetracker.models.database.MonthlyIncome;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MonthlyIncomeRepository extends JpaRepository<MonthlyIncome, Long> {

    Optional<MonthlyIncome> findByActive(boolean active);
}
