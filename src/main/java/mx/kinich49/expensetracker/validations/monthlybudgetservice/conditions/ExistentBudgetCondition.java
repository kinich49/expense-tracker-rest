package mx.kinich49.expensetracker.validations.monthlybudgetservice.conditions;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.internal.ErrorWrapper;
import mx.kinich49.expensetracker.repositories.MonthlyBudgetRepository;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.monthlybudgetservice.MonthlyBudgetServiceErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ExistentBudgetCondition implements Condition<BudgetRequestConditionParameter> {

    private final MonthlyBudgetRepository repository;

    @Autowired
    public ExistentBudgetCondition(MonthlyBudgetRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<ErrorWrapper> assertCondition(BudgetRequestConditionParameter param) throws ValidationFlowException {
        var request = param.getRequest();

        if (request.getId() == null || request.getId() <= 0) {
            return Optional.of(new ErrorWrapper(MonthlyBudgetServiceErrorCodes.INVALID_ID, "Invalid id"));
        }

        boolean exists = repository.existsById(request.getId());

        if (!exists) {
            String errorMessage = String.format("Monthly Budget with id %1$d does not exist", request.getId());
            {
                return Optional.of(new ErrorWrapper(MonthlyBudgetServiceErrorCodes.NO_BUDGET_FOUND, errorMessage));
            }
        }

        return Optional.empty();
    }
}
