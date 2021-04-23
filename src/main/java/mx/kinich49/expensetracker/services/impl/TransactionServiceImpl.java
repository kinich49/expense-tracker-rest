package mx.kinich49.expensetracker.services.impl;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.database.Category;
import mx.kinich49.expensetracker.models.database.PaymentMethod;
import mx.kinich49.expensetracker.models.database.Transaction;
import mx.kinich49.expensetracker.models.web.TransactionWebModel;
import mx.kinich49.expensetracker.models.web.requests.PaymentMethodRequest;
import mx.kinich49.expensetracker.models.web.requests.TransactionRequest;
import mx.kinich49.expensetracker.repositories.CategoryRepository;
import mx.kinich49.expensetracker.repositories.PaymentMethodRepository;
import mx.kinich49.expensetracker.repositories.TransactionRepository;
import mx.kinich49.expensetracker.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("unused")
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  CategoryRepository categoryRepository,
                                  PaymentMethodRepository paymentMethodRepository) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Override
    public TransactionWebModel addTransaction(TransactionRequest request) throws BusinessException {
        long categoryId = request.getCategoryId();

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    String error = String.format("Category with id %1$d not found", categoryId);
                    return new BusinessException(error);
                });

        PaymentMethod paymentMethod = fetchOrCreate(request.getPaymentMethod());
        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setTitle(request.getTitle());
        transaction.setMemo(request.getMemo());
        transaction.setTransactionDate(request.getDateCreated());
        transaction.setCategory(category);
        transaction.setPaymentMethod(paymentMethod);

        transaction = transactionRepository.save(transaction);

        return TransactionWebModel.from(transaction);
    }

    @Override
    public List<TransactionWebModel> findTransactions(int month, int year) {
        List<Transaction> transactions = transactionRepository.findByMonthAndYear(month, year);

        if (transactions == null || transactions.isEmpty())
            return null;

        return transactions.stream()
                .map(TransactionWebModel::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionWebModel> findTransactions(long paymentMethodId,
                                                      LocalDateTime startDate,
                                                      LocalDateTime endDate) throws BusinessException {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId)
                .orElseThrow(() -> new BusinessException("PaymentMethod with id " + paymentMethodId + " not found"));

        List<Transaction> transactions = transactionRepository
                .findByPaymentMethodAndTransactionDateBetween(paymentMethod, startDate, endDate);

        return TransactionWebModel.from(transactions);
    }

    private PaymentMethod fetchOrCreate(PaymentMethodRequest request) {
        if (request.getId() == null) {
            return paymentMethodRepository.save(PaymentMethod.from(request));
        }

        return paymentMethodRepository.findById(request.getId())
                .orElse(paymentMethodRepository.save(PaymentMethod.from(request)));
    }

    @Override
    public List<TransactionWebModel> findTransactionsByCategory(long categoryId, int month, int year) {
        List<Transaction> transactions = transactionRepository
                .findByCategoryIdAndYearAndMonth(categoryId, month, year);

        if (transactions == null || transactions.isEmpty())
            return null;

        return transactions.stream()
                .map(TransactionWebModel::from)
                .collect(Collectors.toList());
    }

}