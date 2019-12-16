package mx.kinich49.expensetracker.dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.lang.NonNull;

import lombok.Data;
import mx.kinich49.expensetracker.models.MonthlyBudget;
import mx.kinich49.expensetracker.models.MonthlyBudgetCategory;
import mx.kinich49.expensetracker.models.TransactionItem;

@Data
public class MonthlyBudgetDto {

    private final long monthlyBudgetId;
    private final LocalDate budgetDate;
    private final List<MonthlyCategoryDto> monthlyCategories;

    public static MonthlyBudgetDto from(@NonNull MonthlyBudget monthlyBudget, List<TransactionItem> monthlyTransactions) {
        List<MonthlyCategoryDto> monthlyCategoriesDto = null;
        Set<MonthlyBudgetCategory> monthlyCategories = monthlyBudget.getMonthlyBudgetCategories();

        if (monthlyCategories != null && !monthlyCategories.isEmpty()) {
            monthlyCategoriesDto = new ArrayList<>();
            for (MonthlyBudgetCategory monthlyCategory : monthlyCategories) {
                monthlyCategoriesDto.add(MonthlyCategoryDto.from(monthlyCategory, monthlyTransactions));
            }
        }

        return new MonthlyBudgetDto(monthlyBudget.getId(), monthlyBudget.getBudgetDate(), monthlyCategoriesDto);
    }
}