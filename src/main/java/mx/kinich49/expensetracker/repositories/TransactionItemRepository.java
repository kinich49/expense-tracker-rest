package mx.kinich49.expensetracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.kinich49.expensetracker.models.TransactionItem;
import mx.kinich49.expensetracker.repositories.custom.CustomTransactionItemRepository;

@Repository
public interface TransactionItemRepository
        extends JpaRepository<TransactionItem, Long>, CustomTransactionItemRepository {

        @Transactional
        @Modifying
        @Query("DELETE FROM Transaction_Items ti where ti.id = ?1")
        void deleteById(long id);
}