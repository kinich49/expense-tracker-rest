package mx.kinich49.expensetracker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.kinich49.expensetracker.models.TransactionItem;

@Repository
public interface TransactionItemRepository extends JpaRepository<TransactionItem, Long> {

        @Transactional
        @Modifying
        @Query("DELETE FROM Transaction_Items ti where ti.id = ?1")
        void deleteById(long id);

        @Query("SELECT ti from Transaction_Items ti where ti.category.id = ?1 AND MONTH(ti.dateCreated) = ?2 AND YEAR(ti.dateCreated) = ?3")
        List<TransactionItem> findMonthlyTransactions(long categoryId, int month, int year);

        @Query("SELECT ti from Transaction_Items ti where ti.category.id = ?1")
        List<TransactionItem> findByCategoryId(long categoryId);

        @Query("SELECT ti from Transaction_Items ti where month(ti.dateCreated) = ?1 AND year(ti.dateCreated) = ?2")
        List<TransactionItem> findByMonthAndYear(int month, int year);
}