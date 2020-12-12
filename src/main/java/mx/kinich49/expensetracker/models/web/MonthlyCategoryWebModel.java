package mx.kinich49.expensetracker.models.web;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.lang.NonNull;

import lombok.Data;
import mx.kinich49.expensetracker.models.database.MonthlyBudgetCategory;
import mx.kinich49.expensetracker.models.database.TransactionItem;

@Data
public class MonthlyCategoryWebModel {

    private final long categoryId;
    private final long monthlyLimit;
    private final Set<MonthlyTransactionItemWebModel> monthlyTransactions;

    public static MonthlyCategoryWebModel from(@NonNull MonthlyBudgetCategory monthlyBudgetCategory,
                                               List<TransactionItem> monthlyTransactions) {
        Set<MonthlyTransactionItemWebModel> monthlyTransactionItemDtos = null;

        if (monthlyTransactions != null && !monthlyTransactions.isEmpty()) {
            monthlyTransactionItemDtos = new HashSet<>();
            for (TransactionItem monthlyTransaction : monthlyTransactions) {
                monthlyTransactionItemDtos.add(MonthlyTransactionItemWebModel.from(monthlyTransaction));
            }
        }

        return new MonthlyCategoryWebModel(monthlyBudgetCategory.getCategory().getId(),
                monthlyBudgetCategory.getMonthlyLimit(), monthlyTransactionItemDtos);
    }

}