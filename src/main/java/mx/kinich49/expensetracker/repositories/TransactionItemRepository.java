package mx.kinich49.expensetracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.kinich49.expensetracker.models.TransactionItem;

@Repository
public interface TransactionItemRepository extends JpaRepository<TransactionItem, Long>{

}