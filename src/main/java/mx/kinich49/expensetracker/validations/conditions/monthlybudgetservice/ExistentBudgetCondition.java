package mx.kinich49.expensetracker.validations.conditions.monthlybudgetservice;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.repositories.MonthlyBudgetRepository;
import mx.kinich49.expensetracker.validations.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ExistentBudgetCondition implements Condition<BudgetRequestConditionParameterImpl> {

    private final MonthlyBudgetRepository repository;

    @Autowired
    public ExistentBudgetCondition(MonthlyBudgetRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<String> assertCondition(BudgetRequestConditionParameterImpl param) throws ValidationFlowException {
        var request = param.getRequest();

        if (request.getId() == null || request.getId() <= 0) {
            return Optional.of("Invalid Id. ");
        }

        boolean exists = repository.existsById(request.getId());

        if (!exists) {
            String errorMessage = String.format("MonthlyBudget with id %1$d does not exist", request.getId());
            return Optional.of(errorMessage);
        }

        return Optional.empty();
    }
}
