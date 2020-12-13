package mx.kinich49.expensetracker.models.web.requests;

import lombok.Data;

@Data
public class MonthlyBudgetCategoryRequest {

    private Long monthlyBudgetCategoryId;
    private long budgetId;
    private long categoryId;
    private int monthlyLimit;
}
