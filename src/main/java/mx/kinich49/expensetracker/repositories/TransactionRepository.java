package mx.kinich49.expensetracker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.kinich49.expensetracker.models.database.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

        @Query("SELECT ti from Transactions ti where ti.category.id = ?1 AND MONTH(ti.dateCreated) = ?2 AND YEAR(ti.dateCreated) = ?3")
        List<Transaction> findMonthlyTransactions(long categoryId, int month, int year);

        @Query("SELECT ti from Transactions ti where ti.category.id = ?1")
        List<Transaction> findByCategoryId(long categoryId);

        @Query("SELECT ti from Transactions ti where month(ti.dateCreated) = ?1 AND year(ti.dateCreated) = ?2")
        List<Transaction> findByMonthAndYear(int month, int year);
}