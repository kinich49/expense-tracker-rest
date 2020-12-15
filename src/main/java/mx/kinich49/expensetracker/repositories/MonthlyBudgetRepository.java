package mx.kinich49.expensetracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.kinich49.expensetracker.models.database.MonthlyBudget;

import java.util.List;
import java.util.Optional;

@Repository
public interface MonthlyBudgetRepository extends JpaRepository<MonthlyBudget, Long> {

    @Query("SELECT mb FROM Monthly_Budgets mb " +
            "WHERE month(mb.budgetDate) = ?1 " +
            "AND year(mb.budgetDate) = ?2")
    List<MonthlyBudget> findByMonthAndYear(int month, int year);
    
}