package mx.kinich49.expensetracker.validations.conditions.monthlyincome;

import lombok.RequiredArgsConstructor;
import mx.kinich49.expensetracker.models.database.MonthlyIncome;
import mx.kinich49.expensetracker.models.web.requests.MonthlyIncomeRequest;
import mx.kinich49.expensetracker.repositories.MonthlyIncomeRepository;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.ConditionParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CollisionConditionImpl implements Condition<CollisionConditionImpl.Parameter> {

    private final MonthlyIncomeRepository repository;

    @Autowired
    public CollisionConditionImpl(MonthlyIncomeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<String> assertCondition(Parameter param) {
        MonthlyIncomeRequest request = param.request;
        Optional<MonthlyIncome> collusion = repository.collides(request.getBeginDate(), request.getEndDate());

        if (collusion.isPresent()) {
            String message = String.format("Monthly Income with id: %1$d collides with request.",
                    collusion.get().getId());
            return Optional.of(message);
        }

        return Optional.empty();
    }

    @RequiredArgsConstructor
    public static final class Parameter implements ConditionParameter {
        private final MonthlyIncomeRequest request;
    }
}
