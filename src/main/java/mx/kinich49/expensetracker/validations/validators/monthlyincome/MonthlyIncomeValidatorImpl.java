package mx.kinich49.expensetracker.validations.validators.monthlyincome;

import lombok.RequiredArgsConstructor;
import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.web.requests.MonthlyIncomeRequest;
import mx.kinich49.expensetracker.validations.Validator;
import mx.kinich49.expensetracker.validations.ValidatorParameter;
import mx.kinich49.expensetracker.validations.conditions.monthlyincome.CollisionConditionImpl;
import mx.kinich49.expensetracker.validations.conditions.monthlyincome.RequestConditionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MonthlyIncomeValidatorImpl implements Validator<MonthlyIncomeValidatorImpl.Parameter> {

    @Qualifier("MonthlyIncome.RequestCondition")
    private final RequestConditionImpl requestCondition;
    private final CollisionConditionImpl collisionCondition;

    @Autowired
    public MonthlyIncomeValidatorImpl(RequestConditionImpl requestCondition,
                                      CollisionConditionImpl collisionCondition) {
        this.requestCondition = requestCondition;
        this.collisionCondition = collisionCondition;
    }

    @Override
    public void validate(Parameter param) throws BusinessException {
        RequestConditionImpl.Parameter requestParam = new RequestConditionImpl.Parameter(param.request);
        Optional<String> requestConditionResult = requestCondition.assertCondition(requestParam);

        if (requestConditionResult.isPresent()) {
            throw new BusinessException(requestConditionResult.get());
        }

        CollisionConditionImpl.Parameter collisionParam = new CollisionConditionImpl.Parameter(param.request);
        Optional<String> collisionConditionResult = collisionCondition.assertCondition(collisionParam);

        if (collisionConditionResult.isPresent()) {
            throw new BusinessException(collisionConditionResult.get());
        }
    }

    @RequiredArgsConstructor
    public static final class Parameter implements ValidatorParameter {
        private final MonthlyIncomeRequest request;
    }
}
