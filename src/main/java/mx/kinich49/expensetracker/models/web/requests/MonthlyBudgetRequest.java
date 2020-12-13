package mx.kinich49.expensetracker.models.web.requests;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MonthlyBudgetRequest {

    private LocalDate budgetDate;

}
