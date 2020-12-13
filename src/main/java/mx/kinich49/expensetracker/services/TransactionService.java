package mx.kinich49.expensetracker.services;

import mx.kinich49.expensetracker.exceptions.CategoryNotFoundException;
import mx.kinich49.expensetracker.models.web.TransactionWebModel;
import mx.kinich49.expensetracker.models.web.requests.TransactionRequest;

import java.util.List;

public interface TransactionService {

    TransactionWebModel addTransaction(TransactionRequest request) throws CategoryNotFoundException;

    List<TransactionWebModel> findTransactions(int month, int year);

    List<TransactionWebModel> findTransactionsByCategory(long categoryId, int month, int year);

}