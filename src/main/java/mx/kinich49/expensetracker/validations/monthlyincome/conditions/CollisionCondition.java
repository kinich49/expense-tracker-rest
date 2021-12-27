package mx.kinich49.expensetracker.validations.monthlyincome.conditions;

import lombok.RequiredArgsConstructor;
import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.database.MonthlyIncome;
import mx.kinich49.expensetracker.models.internal.ErrorWrapper;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetCategoryRequest;
import mx.kinich49.expensetracker.models.web.requests.MonthlyIncomeRequest;
import mx.kinich49.expensetracker.repositories.MonthlyIncomeRepository;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.ConditionParameter;
import mx.kinich49.expensetracker.validations.monthlyincome.MonthlyIncomeErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CollisionCondition implements Condition<CollisionCondition.Parameter> {

    private final MonthlyIncomeRepository repository;

    @Autowired
    public CollisionCondition(MonthlyIncomeRepository repository) {
        this.repository = repository;
    }
    /**
     * This condition validates {@link MonthlyIncomeRequest} dates do not overlap with other
     * Monthly Income's dates.
     *
     * @param param the instance to assert it meets all conditions
     * @return An optional containing an error message if the condition is not met.
     * Empty Otherwise
     */
    @Override
    public Optional<ErrorWrapper> assertCondition(Parameter param) {
        var request = param.request;
        Optional<MonthlyIncome> collusion = repository.collides(request.getBeginDate(), request.getEndDate());

        if (collusion.isPresent()) {
            String errorMessage = String.format("Monthly Income with id: %1$d collides with request.",
                    collusion.get().getId());
            return Optional.of(new ErrorWrapper(MonthlyIncomeErrorCodes.MONTHLY_INCOME_COLLIDES_WITH_REQUEST, errorMessage));
        }

        return Optional.empty();
    }

    @RequiredArgsConstructor
    public static final class Parameter implements ConditionParameter {
        private final MonthlyIncomeRequest request;
    }
}
