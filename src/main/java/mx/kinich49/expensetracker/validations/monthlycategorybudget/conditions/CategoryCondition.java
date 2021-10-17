package mx.kinich49.expensetracker.validations.monthlycategorybudget.conditions;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.internal.ErrorWrapper;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetCategoryRequest;
import mx.kinich49.expensetracker.repositories.CategoryRepository;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.monthlycategorybudget.MonthlyCategoryBudgetErrorCodes;
import mx.kinich49.expensetracker.validations.monthlycategorybudget.RequestParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CategoryCondition implements Condition<RequestParameter> {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryCondition(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<ErrorWrapper> assertCondition(RequestParameter param) throws ValidationFlowException {
        MonthlyBudgetCategoryRequest request = param.getRequest();

        if (request == null)
            throw new ValidationFlowException("Request must not be null");

        boolean categoryExists = categoryRepository.existsById(request.getCategoryId());

        if (!categoryExists) {
            var errorMessage = String.format("Category with id: %d not found. ", request.getCategoryId());
            return Optional.of(new ErrorWrapper(MonthlyCategoryBudgetErrorCodes.CATEGORY_NOT_FOUND, errorMessage));
        }
        return Optional.empty();
    }
}
