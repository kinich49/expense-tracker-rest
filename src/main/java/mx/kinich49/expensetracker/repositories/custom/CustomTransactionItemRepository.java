package mx.kinich49.expensetracker.repositories.custom;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import mx.kinich49.expensetracker.models.TransactionItem;

public interface CustomTransactionItemRepository extends Repository<TransactionItem, Long> {

    @Query("SELECT ti from Transaction_Items ti where ti.category.id = ?1")
    List<TransactionItem> findByCategoryId(long categoryId);

    @Query("SELECT ti from Transaction_Items ti where month(ti.dateCreated) = ?1 AND year(ti.dateCreated) = ?2")
    List<TransactionItem> findByMonthAndYear(int month, int year);
}