package mx.kinich49.expensetracker.models.web.requests;

import lombok.Data;

import java.time.YearMonth;

@Data
public class MonthlyBudgetCategoryRequest {

    private long monthlyBudgetCategoryId;
    private long budgetId;
    private YearMonth yearMonth;
    private long categoryId;
    private int monthlyLimit;
}
