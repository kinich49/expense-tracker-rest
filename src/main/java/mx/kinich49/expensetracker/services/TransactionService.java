package mx.kinich49.expensetracker.services;

import mx.kinich49.expensetracker.exceptions.CategoryNotFoundException;
import mx.kinich49.expensetracker.models.web.TransactionWebModel;
import mx.kinich49.expensetracker.models.web.requests.TransactionRequest;

public interface TransactionService {

    TransactionWebModel addTransaction(TransactionRequest request) throws CategoryNotFoundException;
    
}