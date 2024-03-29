package mx.kinich49.expensetracker.models.web;

import lombok.Data;
import mx.kinich49.expensetracker.models.database.Category;

@Data
public class CategoryWebModel {

    private final long id;
    private final String name;
    private final String color;

    public static CategoryWebModel from(Category category) {
        if (category == null)
            return null;

        return new CategoryWebModel(
                category.getId(),
                category.getName(),
                category.getColor()
        );
    }
}