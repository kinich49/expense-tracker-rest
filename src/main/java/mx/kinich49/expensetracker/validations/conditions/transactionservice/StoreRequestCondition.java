package mx.kinich49.expensetracker.validations.conditions.transactionservice;

import lombok.Data;
import mx.kinich49.expensetracker.models.web.requests.StoreRequest;
import mx.kinich49.expensetracker.utils.StringUtils;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.ConditionParameter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StoreRequestCondition implements Condition<StoreRequestCondition.Parameter> {

    @Override
    public Optional<String> assertCondition(StoreRequestCondition.Parameter param) {
        StoreRequest request = param.request;

        //Store is not required
        if (request == null)
            return Optional.empty();

        if (request.getId() == null && StringUtils.isNullOrEmptyOrBlank(request.getName())) {
            return Optional.of("Store must have an id and/or name. ");
        }

        return Optional.empty();
    }

    @Data
    public static class Parameter implements ConditionParameter {
        private final StoreRequest request;
    }
}
