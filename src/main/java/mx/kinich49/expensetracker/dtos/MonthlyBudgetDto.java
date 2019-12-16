package mx.kinich49.expensetracker.dtos;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class MonthlyBudgetDto {

    private long monthlyBudgetId;
    private LocalDate date;
    private List<MonthlyCategoryDto> monthlyCategories;
}