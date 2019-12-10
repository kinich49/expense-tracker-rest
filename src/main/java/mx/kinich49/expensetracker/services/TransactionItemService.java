package mx.kinich49.expensetracker.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale.Category;

import mx.kinich49.expensetracker.models.TransactionItem;

public interface TransactionItemService {

    public List<TransactionItem> findTransactions(Category category, LocalDate localDate);
}