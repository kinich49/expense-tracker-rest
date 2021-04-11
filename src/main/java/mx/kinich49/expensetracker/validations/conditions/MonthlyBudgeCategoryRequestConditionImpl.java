package mx.kinich49.expensetracker.validations.conditions;

import lombok.Data;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetCategoryRequest;
import mx.kinich49.expensetracker.repositories.CategoryRepository;
import mx.kinich49.expensetracker.repositories.MonthlyBudgetRepository;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.ConditionParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MonthlyBudgeCategoryRequestConditionImpl
        implements Condition<MonthlyBudgeCategoryRequestConditionImpl.Parameter> {

    private final MonthlyBudgetRepository monthlyBudgetRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public MonthlyBudgeCategoryRequestConditionImpl(MonthlyBudgetRepository monthlyBudgetRepository,
                                                    CategoryRepository categoryRepository) {
        this.monthlyBudgetRepository = monthlyBudgetRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Condition to pass: Both monthlyBudget Id and Category Id should exist
     *
     * @param param the instance to assert it meets all conditions
     * @return An optional containing an error message if the condition is not met.
     * Optional empty otherwise
     */
    @Override
    public Optional<String> assertCondition(Parameter param) {
        MonthlyBudgetCategoryRequest request = param.request;

        if (param.request == null)
            return Optional.of("Request must not be null");

        boolean budgetExists = monthlyBudgetRepository.existsById(request.getBudgetId());
        boolean categoryExists = categoryRepository.existsById(request.getCategoryId());

        if (budgetExists && categoryExists)
            return Optional.empty();

        StringBuilder stringBuilder = new StringBuilder();

        if (!budgetExists)
            stringBuilder.append(String.format("Budget with id: %d not found. ", request.getBudgetId()));

        if (!categoryExists)
            stringBuilder.append(String.format("Category with id: %d not found. ", request.getCategoryId()));

        return Optional.of(stringBuilder.toString());
    }

    @Data
    public final static class Parameter implements ConditionParameter {
        private final MonthlyBudgetCategoryRequest request;
    }
}
