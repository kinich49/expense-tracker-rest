package mx.kinich49.expensetracker.repositories;

import mx.kinich49.expensetracker.models.database.MonthlyIncome;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthlyIncomeRepository extends JpaRepository<MonthlyIncome, Long>, MonthlyIncomeRepositoryCustom {

}
