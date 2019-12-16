package mx.kinich49.expensetracker.dtos;

import java.util.List;

import lombok.Data;
import mx.kinich49.expensetracker.models.MonthlyBudgetCategory;
import mx.kinich49.expensetracker.models.TransactionItem;

@Data
public class MonthlyCategoryDto {

    private long categoryId;
    private long monthlyLimit;
    private List<TransactionItem> monthlyTransactions;

    public static MonthlyCategoryDto from(MonthlyBudgetCategory monthlyBudgetCategory,
            List<TransactionItem> monthlyTransactions) {
        MonthlyCategoryDto dto = new MonthlyCategoryDto();
        dto.categoryId = monthlyBudgetCategory.getCategory().getId();
        dto.monthlyLimit = monthlyBudgetCategory.getMonthlyLimit();
        dto.monthlyTransactions = monthlyTransactions;
        return dto;
    }

}