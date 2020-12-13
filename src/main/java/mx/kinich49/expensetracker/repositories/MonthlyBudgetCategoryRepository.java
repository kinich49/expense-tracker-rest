package mx.kinich49.expensetracker.repositories;

import mx.kinich49.expensetracker.models.database.MonthlyBudgetCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface MonthlyBudgetCategoryRepository extends JpaRepository<MonthlyBudgetCategory, Long> {

    Optional<MonthlyBudgetCategory> findByCategoryIdAndMonthlyBudgetId(long categoryId, long monthlyBudgetId);
}
