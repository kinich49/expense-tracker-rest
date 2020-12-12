package mx.kinich49.expensetracker.models.web;

import lombok.Data;
import mx.kinich49.expensetracker.models.database.MonthlyBudget;
import mx.kinich49.expensetracker.models.database.MonthlyBudgetCategory;
import mx.kinich49.expensetracker.models.database.TransactionItem;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class WMonthlyBudgetWebModel {

    private final long monthlyBudgetId;
    private final LocalDate budgetDate;
    private final List<MonthlyCategoryWebModel> monthlyCategories;

    public static WMonthlyBudgetWebModel from(@NonNull MonthlyBudget monthlyBudget,
                                              List<TransactionItem> monthlyTransactions) {
        List<MonthlyCategoryWebModel> monthlyCategoriesDto = null;
        Set<MonthlyBudgetCategory> monthlyCategories = monthlyBudget.getMonthlyBudgetCategories();

        if (monthlyCategories != null && !monthlyCategories.isEmpty()) {
            monthlyCategoriesDto = new ArrayList<>();
            for (MonthlyBudgetCategory monthlyCategory : monthlyCategories) {
                monthlyCategoriesDto.add(MonthlyCategoryWebModel.from(monthlyCategory, monthlyTransactions));
            }
        }

        return new WMonthlyBudgetWebModel(monthlyBudget.getId(), monthlyBudget.getBudgetDate(), monthlyCategoriesDto);
    }
}