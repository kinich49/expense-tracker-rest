package mx.kinich49.expensetracker.models.web;

import lombok.Data;
import mx.kinich49.expensetracker.models.database.Category;
import mx.kinich49.expensetracker.models.database.Transaction;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CategoryTransactionWebModel {

    private final CategoryWebModel category;
    private final int transactionCount;
    private final int expense;
    private final List<TransactionWebModel> transactions;

    public static CategoryTransactionWebModel from(Category category) {
        if (category == null)
            return null;

        CategoryWebModel categoryWebModel = CategoryWebModel.from(category);

        if (category.getTransactions() == null || category.getTransactions().isEmpty())
            return new CategoryTransactionWebModel(categoryWebModel, 0, 0, null);
        else {
            List<TransactionWebModel> webModels = category.getTransactions()
                    .stream().map(TransactionWebModel::from)
                    .collect(Collectors.toList());

            int transactionCount = category.getTransactions().size();

            int expense = category.getTransactions()
                    .stream().mapToInt(Transaction::getAmount)
                    .reduce(0, Integer::sum);

            return new CategoryTransactionWebModel(categoryWebModel, transactionCount, expense, webModels);
        }
    }
}
