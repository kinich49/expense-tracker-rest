package mx.kinich49.expensetracker.repositories;

import mx.kinich49.expensetracker.models.database.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transactions t " +
            "WHERE t.category.id = ?1 " +
            "AND MONTH(t.transactionDate) = ?2 " +
            "AND YEAR(t.transactionDate) = ?3")
    List<Transaction> findByCategoryIdAndYearAndMonth(long categoryId, int month, int year);

    @Query("SELECT t FROM Transactions t " +
            "WHERE month(t.transactionDate) = ?1 " +
            "AND year(t.transactionDate) = ?2")
    List<Transaction> findByMonthAndYear(int month, int year);
}