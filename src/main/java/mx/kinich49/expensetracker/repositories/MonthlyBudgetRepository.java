package mx.kinich49.expensetracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.kinich49.expensetracker.models.MonthlyBudget;

@Repository
public interface MonthlyBudgetRepository extends JpaRepository<MonthlyBudget, Long> {

    @Query("SELECT mb from Monthly_Budgets mb where month(mb.budgetDate) = ?1 AND year(mb.budgetDate) = ?2")
    MonthlyBudget findByMonthAndYear(int month, int year);
    
}