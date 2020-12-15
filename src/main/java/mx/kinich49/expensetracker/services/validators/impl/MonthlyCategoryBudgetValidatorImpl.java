package mx.kinich49.expensetracker.services.validators.impl;

import mx.kinich49.expensetracker.models.database.MonthlyBudget;
import mx.kinich49.expensetracker.models.database.MonthlyBudgetCategory;
import mx.kinich49.expensetracker.models.database.MonthlyIncome;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetCategoryRequest;
import mx.kinich49.expensetracker.services.validators.MonthlyCategoryBudgetValidator;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Stream;

@Component
public class MonthlyCategoryBudgetValidatorImpl implements MonthlyCategoryBudgetValidator {

    @Override
    public boolean isLimitValid(MonthlyBudgetCategoryRequest request, MonthlyIncome monthlyIncome) {
        int expenseLimit = monthlyIncome.getUpperIncomeLimit();
        long categoryId = request.getCategoryId();
        long monthlyCategoryLimit = request.getMonthlyLimit();

        int currentLimit = monthlyIncome.getMonthlyBudgets().stream()
                .flatMap((Function<MonthlyBudget, Stream<MonthlyBudgetCategory>>) monthlyBudget ->
                        monthlyBudget.getMonthlyBudgetCategories().stream())
                .filter(monthlyBudgetCategory -> monthlyBudgetCategory.getCategory().getId() != categoryId)
                .mapToInt(MonthlyBudgetCategory::getMonthlyLimit)
                .reduce(0, Integer::sum);

        return (monthlyCategoryLimit + currentLimit) <= expenseLimit;
    }
}
