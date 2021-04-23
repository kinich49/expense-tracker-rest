package mx.kinich49.expensetracker.models.web.requests;

import lombok.Data;

import java.time.YearMonth;

@Data
public class MonthlyBudgetRequest {

    private YearMonth beginDate;
    private YearMonth endDate;
    private String title;
}
