package mx.kinich49.expensetracker.models.web;

import lombok.Data;
import mx.kinich49.expensetracker.models.database.MonthlyIncome;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MonthlyIncomeWebModel {

    private long id;
    private String upperIncomeLimit;
    private YearMonth beginDate;
    private YearMonth endDate;

    public static MonthlyIncomeWebModel from(MonthlyIncome monthlyIncome) {
        if (monthlyIncome == null)
            return null;

        MonthlyIncomeWebModel monthlyIncomeWebModel = new MonthlyIncomeWebModel();
        monthlyIncomeWebModel.setBeginDate(monthlyIncome.getBeginDate());
        monthlyIncomeWebModel.setEndDate(monthlyIncome.getEndDate());
        monthlyIncomeWebModel.setUpperIncomeLimit(formatIncome(monthlyIncome.getUpperIncomeLimit()));

        return monthlyIncomeWebModel;
    }

    public static List<MonthlyIncomeWebModel> from(List<MonthlyIncome> monthlyIncomes) {
        if (monthlyIncomes == null || monthlyIncomes.isEmpty())
            return null;

        return monthlyIncomes.stream()
                .map(MonthlyIncomeWebModel::from)
                .collect(Collectors.toList());
    }

    private static String formatIncome(int income) {
        return String.valueOf(income);
    }
}
