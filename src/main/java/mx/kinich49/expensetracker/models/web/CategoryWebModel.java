package mx.kinich49.expensetracker.models.web;

import java.util.Collection;

import lombok.Data;
import mx.kinich49.expensetracker.models.database.Category;
import mx.kinich49.expensetracker.models.database.TransactionItem;

@Data
public class CategoryWebModel {

    private final long id;
    private final String title;
    private final String color;
    private final Collection<TransactionItem> transactions;

    public static CategoryWebModel from(Category category) {
        return new CategoryWebModel(category.getId(), category.getTitle(), category.getColor(),
                category.getTransactionItems());
    }
}