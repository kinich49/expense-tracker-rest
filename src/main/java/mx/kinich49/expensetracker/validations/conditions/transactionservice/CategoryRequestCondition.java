package mx.kinich49.expensetracker.validations.conditions.transactionservice;

import lombok.Data;
import mx.kinich49.expensetracker.models.web.requests.CategoryRequest;
import mx.kinich49.expensetracker.utils.StringUtils;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.ConditionParameter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CategoryRequestCondition implements Condition<CategoryRequestCondition.Parameter> {

    @Override
    public Optional<String> assertCondition(Parameter param) {
        CategoryRequest request = param.request;

        if (request == null)
            return Optional.of("Category cannot be null. ");

        if (request.getId() == null && StringUtils.isNullOrEmptyOrBlank(request.getName())) {
            return Optional.of("Category must have an id and/or name. ");
        }

        return Optional.empty();
    }

    @Data
    public static class Parameter implements ConditionParameter {
        private final CategoryRequest request;
    }
}
