package mx.kinich49.expensetracker.validations.monthlycategorybudget.conditions;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.internal.ErrorWrapper;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetCategoryRequest;
import mx.kinich49.expensetracker.repositories.MonthlyBudgetRepository;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.monthlycategorybudget.MonthlyCategoryBudgetErrorCodes;
import mx.kinich49.expensetracker.validations.monthlycategorybudget.RequestParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BudgetCondition implements Condition<RequestParameter> {

    private final MonthlyBudgetRepository monthlyBudgetRepository;

    @Autowired
    public BudgetCondition(MonthlyBudgetRepository monthlyBudgetRepository) {
        this.monthlyBudgetRepository = monthlyBudgetRepository;
    }

    @Override
    public Optional<ErrorWrapper> assertCondition(RequestParameter param) throws ValidationFlowException {
        MonthlyBudgetCategoryRequest request = param.getRequest();

        if (request == null)
            throw new ValidationFlowException("Request must not be null");

        boolean budgetExists = monthlyBudgetRepository.existsById(request.getBudgetId());

        if (!budgetExists) {
            var errorMessage = String.format("Budget with id: %d not found. ", request.getBudgetId());
            return Optional.of(new ErrorWrapper(MonthlyCategoryBudgetErrorCodes.BUDGET_NOT_FOUND, errorMessage));
        }

        return Optional.empty();
    }
}
