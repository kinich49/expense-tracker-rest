package mx.kinich49.expensetracker.models.web;

import lombok.Data;
import mx.kinich49.expensetracker.models.database.Category;
import mx.kinich49.expensetracker.models.database.Transaction;
import mx.kinich49.expensetracker.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class ExpensesWebModel {

    private final List<CategoryTransactionWebModel> categoryTransactions;
    private final int totalItems;
    private final int totalExpense;

    public static ExpensesWebModel from(Map<Category, List<Transaction>> categories) {
        if (categories == null || categories.isEmpty())
            return null;

        List<CategoryTransactionWebModel> categoryTransactions = new ArrayList<>();

        AtomicInteger totalItems = new AtomicInteger();
        AtomicInteger totalExpense = new AtomicInteger();
        categories.forEach((category, transactions)-> {
            category.setTransactions(transactions);
            CategoryTransactionWebModel categoryTransaction = CategoryTransactionWebModel.from(category);

            if (CollectionUtils.isNeitherNullNorEmpty(transactions)) {
                int currentTotalItemsValue = totalItems.get();

                int newTotalItemsValue = currentTotalItemsValue + transactions.size();
                totalItems.set(newTotalItemsValue);

                int currentTotalExpense = totalExpense.get();
                int newTotalExpense = currentTotalExpense + categoryTransaction.getExpense();
                totalExpense.set(newTotalExpense);
            }

            categoryTransactions.add(categoryTransaction);
        });

        return new ExpensesWebModel(categoryTransactions, totalItems.get(), totalExpense.get());
    }

}
