package mx.kinich49.expensetracker.services.impl;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.database.Category;
import mx.kinich49.expensetracker.models.database.Transaction;
import mx.kinich49.expensetracker.models.web.TransactionWebModel;
import mx.kinich49.expensetracker.models.web.requests.TransactionRequest;
import mx.kinich49.expensetracker.repositories.CategoryRepository;
import mx.kinich49.expensetracker.repositories.TransactionRepository;
import mx.kinich49.expensetracker.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("unused")
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    ;
    private final CategoryRepository categoryRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public TransactionWebModel addTransaction(TransactionRequest request) throws BusinessException {
        long categoryId = request.getCategoryId();

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    String error = String.format("Category with id %1$d not found", categoryId);
                    return new BusinessException(error);
                });

        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setTitle(request.getTitle());
        transaction.setMemo(request.getMemo());
        transaction.setTransactionDate(request.getDateCreated());
        transaction.setCategory(category);

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