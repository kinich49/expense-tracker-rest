package mx.kinich49.expensetracker.models.web.requests;

import lombok.Data;

@Data
public class CategoryRequest {

    private final String name;
    private final String color;

}
