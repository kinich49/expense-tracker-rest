package mx.kinich49.expensetracker.services.impl;

import mx.kinich49.expensetracker.models.database.Category;
import mx.kinich49.expensetracker.models.database.Transaction;
import mx.kinich49.expensetracker.models.web.ExpensesWebModel;
import mx.kinich49.expensetracker.repositories.CategoryRepository;
import mx.kinich49.expensetracker.repositories.TransactionRepository;
import mx.kinich49.expensetracker.services.ExpenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    Logger logger = LoggerFactory.getLogger(ExpenseServiceImpl.class);

    @Autowired
    public ExpenseServiceImpl(CategoryRepository categoryRepository,
                              TransactionRepository transactionRepository) {
        this.categoryRepository = categoryRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public ExpensesWebModel findExpensesBetween(LocalDateTime startDate,
                                                LocalDateTime endDate) {

        List<Category> categories = categoryRepository.findAll();
        Map<Category, List<Transaction>> copyCategories = new HashMap<>(categories.size());
        categories.forEach(category -> copyCategories.put(Category.copy(category), new ArrayList<>()));

        List<Transaction> transactions = transactionRepository.findByTransactionDateBetween(startDate, endDate);
        transactions.forEach(transaction -> {
            Transaction copyTransaction = Transaction.copy(transaction);
            if (copyCategories.containsKey(copyTransaction.getCategory())) {
                List<Transaction> copyTransactions = copyCategories.get(copyTransaction.getCategory());
                copyTransactions.add(copyTransaction);
            } else {
                String error = String.format("transaction with id: %1$d could not be added to map", transaction.getId());
                logger.error(error);
            }
        });

        return ExpensesWebModel.from(copyCategories);
    }

    private boolean isBetween(LocalDateTime startDate, LocalDateTime endDate, LocalDateTime transactionDate) {
        return (transactionDate.isAfter(startDate) || transactionDate.isEqual(startDate)) &&
                (transactionDate.isBefore(endDate) || transactionDate.isEqual(endDate));
    }
}
