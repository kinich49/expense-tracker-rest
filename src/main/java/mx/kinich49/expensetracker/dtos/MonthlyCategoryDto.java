package mx.kinich49.expensetracker.dtos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.lang.NonNull;

import lombok.Data;
import mx.kinich49.expensetracker.models.MonthlyBudgetCategory;
import mx.kinich49.expensetracker.models.TransactionItem;

@Data
public class MonthlyCategoryDto {

    private final long categoryId;
    private final long monthlyLimit;
    private final Set<MonthlyTransactionItemDto> monthlyTransactions;

    public static MonthlyCategoryDto from(@NonNull MonthlyBudgetCategory monthlyBudgetCategory,
            List<TransactionItem> monthlyTransactions) {
        Set<MonthlyTransactionItemDto> monthlyTransactionItemDtos = null;

        if (monthlyTransactions != null && !monthlyTransactions.isEmpty()) {
            monthlyTransactionItemDtos = new HashSet<>();
            for (TransactionItem monthlyTransaction : monthlyTransactions) {
                monthlyTransactionItemDtos.add(MonthlyTransactionItemDto.from(monthlyTransaction));
            }
        }

        return new MonthlyCategoryDto(monthlyBudgetCategory.getCategory().getId(),
                monthlyBudgetCategory.getMonthlyLimit(), monthlyTransactionItemDtos);
    }

}