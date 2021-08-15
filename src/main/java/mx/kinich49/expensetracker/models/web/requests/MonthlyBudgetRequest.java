package mx.kinich49.expensetracker.models.web.requests;

import lombok.Data;

import java.time.YearMonth;

@Data
public class MonthlyBudgetRequest {

    private final Long id;
    private final YearMonth beginDate;
    private final YearMonth endDate;
    private final String title;
    private final int baseLimit;
}
