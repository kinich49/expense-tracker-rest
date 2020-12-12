package mx.kinich49.expensetracker.models.web;

import lombok.Data;
import mx.kinich49.expensetracker.models.database.Category;

import java.util.List;

@Data
public class CategoryWebModel {

    private final long id;
    private final String title;
    private final String color;
    private final List<TransactionWebModel> transactions;

    public static CategoryWebModel from(Category category) {
        if (category == null)
            return null;

        List<TransactionWebModel> transactionWebModels = TransactionWebModel.from(category.getTransactions());

        return new CategoryWebModel(
                category.getId(),
                category.getName(),
                category.getColor(),
                transactionWebModels
        );
    }
}