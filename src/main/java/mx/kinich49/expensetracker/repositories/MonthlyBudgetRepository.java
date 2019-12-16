package mx.kinich49.expensetracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.kinich49.expensetracker.models.MonthlyBudget;

@Repository
public interface MonthlyBudgetRepository extends JpaRepository<MonthlyBudget, Long> {

    MonthlyBudget findByMonthAndYear(int month, int year);
}