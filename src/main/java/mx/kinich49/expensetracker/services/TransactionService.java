package mx.kinich49.expensetracker.services;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.web.TransactionWebModel;
import mx.kinich49.expensetracker.models.web.requests.TransactionRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {

    TransactionWebModel addTransaction(TransactionRequest request) throws BusinessException;

    List<TransactionWebModel> findTransactions(LocalDateTime startDate,
                                               LocalDateTime endDate);

    List<TransactionWebModel> findTransactionsByPaymentMethod(long paymentMethod,
                                                              LocalDateTime startDate,
                                                              LocalDateTime endDate) throws BusinessException;

    List<TransactionWebModel> findTransactionsByCategory(long categoryId,
                                                         LocalDateTime startDate,
                                                         LocalDateTime endDate) throws BusinessException;

}