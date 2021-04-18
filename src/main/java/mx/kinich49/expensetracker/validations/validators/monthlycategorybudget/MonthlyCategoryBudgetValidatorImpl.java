package mx.kinich49.expensetracker.validations.validators.monthlycategorybudget;

import lombok.RequiredArgsConstructor;
import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.database.MonthlyBudget;
import mx.kinich49.expensetracker.models.database.MonthlyBudgetCategory;
import mx.kinich49.expensetracker.models.database.MonthlyIncome;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetCategoryRequest;
import mx.kinich49.expensetracker.validations.Validator;
import mx.kinich49.expensetracker.validations.ValidatorParameter;
import mx.kinich49.expensetracker.validations.conditions.monthlycategorybudget.BudgetsConditionImpl;
import mx.kinich49.expensetracker.validations.conditions.monthlycategorybudget.LimitConditionImpl;
import mx.kinich49.expensetracker.validations.conditions.monthlycategorybudget.RequestConditionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

@Component
public class MonthlyCategoryBudgetValidatorImpl implements Validator<MonthlyCategoryBudgetValidatorImpl.Parameter> {

    private final RequestConditionImpl monthlyCategoryBudgetCondition;
    private final LimitConditionImpl monthlyCategoryLimitCondition;
    private final BudgetsConditionImpl monthlyCategoryBudgetBudgetsCondition;

    @Autowired
    public MonthlyCategoryBudgetValidatorImpl(RequestConditionImpl monthlyCategoryBudgetCondition,
                                              LimitConditionImpl monthlyCategoryLimitCondition,
                                              BudgetsConditionImpl monthlyCategoryBudgetBudgetsCondition) {
        this.monthlyCategoryBudgetCondition = monthlyCategoryBudgetCondition;
        this.monthlyCategoryLimitCondition = monthlyCategoryLimitCondition;
        this.monthlyCategoryBudgetBudgetsCondition = monthlyCategoryBudgetBudgetsCondition;
    }

    @Override
    public void validate(Parameter param) throws BusinessException {

        Optional<String> requestConditionResult = monthlyCategoryBudgetCondition
                .assertCondition(new RequestConditionImpl.Parameter(param.request));

        if (requestConditionResult.isPresent()) {
            throw new BusinessException(requestConditionResult.get());
        }

        Optional<String> budgetsConditionResult = monthlyCategoryBudgetBudgetsCondition
                .assertCondition(new BudgetsConditionImpl.Parameter(param.monthlyBudgets));

        if (budgetsConditionResult.isPresent()) {
            throw new BusinessException(budgetsConditionResult.get());
        }

        int expenseLimit = param.monthlyIncome.getUpperIncomeLimit();
        long categoryId = param.request.getCategoryId();
        int monthlyCategoryLimit = param.request.getMonthlyLimit();

        int currentLimit = param.monthlyBudgets.stream()
                .flatMap((Function<MonthlyBudget, Stream<MonthlyBudgetCategory>>) monthlyBudget ->
                        monthlyBudget.getMonthlyBudgetCategories().stream())
                .filter(monthlyBudgetCategory -> monthlyBudgetCategory.getCategory().getId() != categoryId)
                .mapToInt(MonthlyBudgetCategory::getMonthlyLimit)
                .reduce(0, Integer::sum);

        LimitConditionImpl.Parameter monthlyCategoryLimitParameter =
                new LimitConditionImpl.Parameter(currentLimit, monthlyCategoryLimit, expenseLimit);
        Optional<String> monthlyCategoryLimitResult = monthlyCategoryLimitCondition.assertCondition(monthlyCategoryLimitParameter);

        if (monthlyCategoryLimitResult.isPresent())
            throw new BusinessException(monthlyCategoryLimitResult.get());
    }

    @RequiredArgsConstructor
    public static final class Parameter implements ValidatorParameter {
        private final MonthlyBudgetCategoryRequest request;
        private final MonthlyIncome monthlyIncome;
        private final List<MonthlyBudget> monthlyBudgets;
    }
}
