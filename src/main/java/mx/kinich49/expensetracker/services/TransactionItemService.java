package mx.kinich49.expensetracker.services;

import javax.validation.constraints.NotNull;

import mx.kinich49.expensetracker.exceptions.CategoryNotFoundException;
import mx.kinich49.expensetracker.models.TransactionItem;
import mx.kinich49.expensetracker.requests.TransactionItemRequest;

public interface TransactionItemService {

    TransactionItem addTransactionItem(@NotNull TransactionItemRequest request) throws CategoryNotFoundException;
    
}