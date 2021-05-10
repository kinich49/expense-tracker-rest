package mx.kinich49.expensetracker.models.web;

import lombok.Data;
import mx.kinich49.expensetracker.models.database.MonthlyBudget;
import mx.kinich49.expensetracker.utils.StringUtils;

import java.time.YearMonth;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SimpleMonthlyBudgetWebModel {

    private final long id;
    private final String title;

    private final YearMonth beginDate;
    private final YearMonth endDate;

    private final String monthlyLimit;

    private final List<MonthlyBudgetCategoryWebModel> monthlyBudgetCategories;

    public static SimpleMonthlyBudgetWebModel from(MonthlyBudget monthlyBudget) {
        if (monthlyBudget == null)
            return null;

        List<MonthlyBudgetCategoryWebModel> monthlyBudgetCategories = MonthlyBudgetCategoryWebModel
                .from(monthlyBudget.getMonthlyBudgetCategories());

        String formattedLimit = StringUtils.formatCurrencyNumber(monthlyBudget.getMonthlyLimit(), "MXN");

        return new SimpleMonthlyBudgetWebModel(monthlyBudget.getId(),
                monthlyBudget.getTitle(),
                monthlyBudget.getBeginDate(),
                monthlyBudget.getEndDate(),
                formattedLimit,
                monthlyBudgetCategories);
    }

    public static List<SimpleMonthlyBudgetWebModel> from(Collection<MonthlyBudget> monthlyBudgets) {
        if (monthlyBudgets == null || monthlyBudgets.isEmpty())
            return null;

        return monthlyBudgets.stream()
                .map(SimpleMonthlyBudgetWebModel::from)
                .collect(Collectors.toList());
    }

}
