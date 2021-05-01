package mx.kinich49.expensetracker.services.impl;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.database.Category;
import mx.kinich49.expensetracker.models.database.PaymentMethod;
import mx.kinich49.expensetracker.models.database.CommercialEstablishment;
import mx.kinich49.expensetracker.models.database.Transaction;
import mx.kinich49.expensetracker.models.web.TransactionWebModel;
import mx.kinich49.expensetracker.models.web.requests.CategoryRequest;
import mx.kinich49.expensetracker.models.web.requests.PaymentMethodRequest;
import mx.kinich49.expensetracker.models.web.requests.StoreRequest;
import mx.kinich49.expensetracker.models.web.requests.TransactionRequest;
import mx.kinich49.expensetracker.repositories.CategoryRepository;
import mx.kinich49.expensetracker.repositories.PaymentMethodRepository;
import mx.kinich49.expensetracker.repositories.StoreRepository;
import mx.kinich49.expensetracker.repositories.TransactionRepository;
import mx.kinich49.expensetracker.services.TransactionService;
import mx.kinich49.expensetracker.validations.validators.transactionservice.TransactionServiceValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@SuppressWarnings("unused")
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final StoreRepository storeRepository;
    private final TransactionServiceValidatorImpl transactionValidator;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  CategoryRepository categoryRepository,
                                  PaymentMethodRepository paymentMethodRepository,
                                  StoreRepository storeRepository,
                                  TransactionServiceValidatorImpl transactionValidator) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.storeRepository = storeRepository;
        this.transactionValidator = transactionValidator;
    }

    @Override
    public TransactionWebModel addTransaction(TransactionRequest request) throws BusinessException {
        TransactionServiceValidatorImpl.Parameter param = new TransactionServiceValidatorImpl
                .Parameter(request);
        transactionValidator.validate(param);

        Category category = fetchOrCreate(request.getCategory());
        PaymentMethod paymentMethod = fetchOrCreate(request.getPaymentMethod());
        CommercialEstablishment commercialEstablishment = fetchOrCreate(request.getStore());

        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setTitle(request.getTitle());
        transaction.setMemo(request.getMemo());
        transaction.setTransactionDate(request.getDateCreated());
        transaction.setCategory(category);
        transaction.setPaymentMethod(paymentMethod);
        transaction.setCommercialEstablishment(commercialEstablishment);

        return TransactionWebModel.from(transactionRepository.save(transaction));
    }

    @Override
    public List<TransactionWebModel> findTransactions(LocalDateTime startDate,
                                                      LocalDateTime endDate) {
        return Optional.ofNullable(transactionRepository.findByTransactionDateBetween(startDate, endDate))
                .map(TransactionWebModel::from)
                .orElse(Collections.emptyList());
    }

    @Override
    public List<TransactionWebModel> findTransactionsByPaymentMethod(long paymentMethodId,
                                                                     LocalDateTime startDate,
                                                                     LocalDateTime endDate) throws BusinessException {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId)
                .orElseThrow(() -> new BusinessException("PaymentMethod with id " + paymentMethodId + " not found"));

        return Optional.ofNullable(
                transactionRepository
                        .findByPaymentMethodAndTransactionDateBetween(paymentMethod, startDate, endDate))
                .map(TransactionWebModel::from)
                .orElse(Collections.emptyList());
    }

    @Override
    public List<TransactionWebModel> findTransactionsByCategory(long categoryId,
                                                                LocalDateTime startDate,
                                                                LocalDateTime endDate) throws BusinessException {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BusinessException("Category with id " + categoryId + " not found"));

        return Optional.ofNullable(
                transactionRepository
                        .findByCategoryAndTransactionDateBetween(category, startDate, endDate))
                .map(TransactionWebModel::from)
                .orElse(Collections.emptyList());
    }

    private PaymentMethod fetchOrCreate(PaymentMethodRequest request) {
        return Optional.ofNullable(request.getId())
                .flatMap(paymentMethodRepository::findById)
                .orElseGet(() -> paymentMethodRepository.save(PaymentMethod.from(request)));
    }

    private CommercialEstablishment fetchOrCreate(StoreRequest request) {
        return Optional.ofNullable(request.getId())
                .flatMap(storeRepository::findById)
                .orElseGet(() -> storeRepository.save(CommercialEstablishment.from(request)));
    }

    private Category fetchOrCreate(CategoryRequest request) {
        return Optional.ofNullable(request.getId())
                .flatMap(categoryRepository::findById)
                .orElseGet(() -> categoryRepository.save(Category.from(request)));
    }

}