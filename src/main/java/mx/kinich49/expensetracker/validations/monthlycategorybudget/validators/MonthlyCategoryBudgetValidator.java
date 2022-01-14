package mx.kinich49.expensetracker.validations.monthlycategorybudget.validators;

import lombok.Data;
import mx.kinich49.expensetracker.models.database.MonthlyBudget;
import mx.kinich49.expensetracker.models.database.MonthlyIncome;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetCategoryRequest;
import mx.kinich49.expensetracker.validations.AbstractValidator;
import mx.kinich49.expensetracker.validations.ValidatorParameter;
import mx.kinich49.expensetracker.validations.monthlycategorybudget.providers.MonthlyCategoryBudgetConditionProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MonthlyCategoryBudgetValidator extends AbstractValidator<MonthlyCategoryBudgetValidator.Parameter> {

    public MonthlyCategoryBudgetValidator(MonthlyCategoryBudgetConditionProvider conditionProvider) {
        super(conditionProvider);
    }

    @Data
    public static final class Parameter implements ValidatorParameter {
        private final MonthlyBudgetCategoryRequest request;
        private final MonthlyIncome monthlyIncome;
        private final List<MonthlyBudget> monthlyBudgets;
        private final int currentLimit;
    }
}
