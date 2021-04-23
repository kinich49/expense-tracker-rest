package mx.kinich49.expensetracker.models.web;

import lombok.Data;
import mx.kinich49.expensetracker.Constants;
import mx.kinich49.expensetracker.models.database.MonthlyBudget;

import java.text.DecimalFormat;
import java.time.YearMonth;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SimpleMonthlyBudgetWebModel {

    public static final DecimalFormat LIMIT_FORMAT = new DecimalFormat("#,##0.00");

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

        String monthlyLimitWithFormat = formatLimit(monthlyBudget.getMonthlyLimit(), "MXN");

        return new SimpleMonthlyBudgetWebModel(monthlyBudget.getId(),
                monthlyBudget.getTitle(),
                monthlyBudget.getBeginDate(),
                monthlyBudget.getEndDate(),
                monthlyLimitWithFormat,
                monthlyBudgetCategories);
    }

    public static List<SimpleMonthlyBudgetWebModel> from(Collection<MonthlyBudget> monthlyBudgets) {
        if (monthlyBudgets == null || monthlyBudgets.isEmpty())
            return null;

        return monthlyBudgets.stream()
                .map(SimpleMonthlyBudgetWebModel::from)
                .collect(Collectors.toList());
    }

    public static String formatLimit(int limit, String currency) {
        double scaledLimit = transformLimit(limit);

        return String.format("%1$s%2$s %3$s",
                "$", LIMIT_FORMAT.format(scaledLimit), currency);
    }

    private static double transformLimit(int limit) {
        return limit / Constants.PRICE_SCALE;
    }

}
