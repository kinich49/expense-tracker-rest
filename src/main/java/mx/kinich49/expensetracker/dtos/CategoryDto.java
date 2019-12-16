package mx.kinich49.expensetracker.dtos;

import java.util.Collection;

import lombok.Data;
import mx.kinich49.expensetracker.models.Category;
import mx.kinich49.expensetracker.models.TransactionItem;

@Data
public class CategoryDto {

    private final long id;
    private final String title;
    private final String color;
    private final Collection<TransactionItem> transactions;

    public static CategoryDto from(Category category) {
        return new CategoryDto(category.getId(), category.getTitle(), category.getColor(),
                category.getTransactionItems());
    }
}