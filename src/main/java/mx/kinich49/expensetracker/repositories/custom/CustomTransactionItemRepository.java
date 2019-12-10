package mx.kinich49.expensetracker.repositories.custom;

import org.springframework.data.repository.Repository;

import mx.kinich49.expensetracker.models.TransactionItem;

public interface CustomTransactionItemRepository extends Repository<TransactionItem, Long> {

}