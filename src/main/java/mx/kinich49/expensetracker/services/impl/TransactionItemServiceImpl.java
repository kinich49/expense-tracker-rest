package mx.kinich49.expensetracker.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.kinich49.expensetracker.exceptions.CategoryNotFoundException;
import mx.kinich49.expensetracker.models.Category;
import mx.kinich49.expensetracker.models.TransactionItem;
import mx.kinich49.expensetracker.repositories.CategoryRepository;
import mx.kinich49.expensetracker.repositories.TransactionItemRepository;
import mx.kinich49.expensetracker.requests.TransactionItemRequest;
import mx.kinich49.expensetracker.services.TransactionItemService;

@Service
public class TransactionItemServiceImpl implements TransactionItemService {

    private TransactionItemRepository transactionItemRepository;;
    private CategoryRepository categoryRepository;

    @Autowired
    public TransactionItemServiceImpl(TransactionItemRepository transactionItemRepository,
            CategoryRepository categoryRepository) {
        this.transactionItemRepository = transactionItemRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public TransactionItem addTransactionItem(TransactionItemRequest request) throws CategoryNotFoundException{
        long categoryId = request.getCategoryId();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        TransactionItem transactionItem = new TransactionItem();
        transactionItem.setAmount(request.getAmount());
        transactionItem.setTitle(request.getTitle());
        transactionItem.setMemo(request.getMemo());
        transactionItem.setDateCreated(request.getDateCreated());
        transactionItem.setCategory(category);

        return transactionItemRepository.saveAndFlush(transactionItem);

    }

}