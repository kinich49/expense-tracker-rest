package mx.kinich49.expensetracker.services.impl;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.database.Category;
import mx.kinich49.expensetracker.models.database.PaymentMethod;
import mx.kinich49.expensetracker.models.database.Store;
import mx.kinich49.expensetracker.models.database.Transaction;
import mx.kinich49.expensetracker.models.web.TransactionWebModel;
import mx.kinich49.expensetracker.models.web.requests.PaymentMethodRequest;
import mx.kinich49.expensetracker.models.web.requests.StoreRequest;
import mx.kinich49.expensetracker.models.web.requests.TransactionRequest;
import mx.kinich49.expensetracker.repositories.CategoryRepository;
import mx.kinich49.expensetracker.repositories.PaymentMethodRepository;
import mx.kinich49.expensetracker.repositories.StoreRepository;
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
    private final StoreRepository storeRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  CategoryRepository categoryRepository,
                                  PaymentMethodRepository paymentMethodRepository,
                                  StoreRepository storeRepository) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.storeRepository = storeRepository;
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
        Store store = fetchOrCreate(request.getStore());

        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setTitle(request.getTitle());
        transaction.setMemo(request.getMemo());
        transaction.setTransactionDate(request.getDateCreated());
        transaction.setCategory(category);
        transaction.setPaymentMethod(paymentMethod);
        transaction.setStore(store);

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

    private Store fetchOrCreate(StoreRequest request) {
        if (request.getId() == null)
            return storeRepository.save(Store.from(request));

        return storeRepository.findById(request.getId())
                .orElse(storeRepository.save(Store.from(request)));
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