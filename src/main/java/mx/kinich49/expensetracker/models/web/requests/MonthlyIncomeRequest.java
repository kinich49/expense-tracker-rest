package mx.kinich49.expensetracker.models.web.requests;

import lombok.Data;

import java.time.YearMonth;

@Data
public class MonthlyIncomeRequest {

    private Long id;
    private int upperIncomeLimit;
    private YearMonth beginDate;
    private YearMonth endDate;
}
