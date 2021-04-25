package mx.kinich49.expensetracker.validations.conditions.transactionservice;

import lombok.Data;
import mx.kinich49.expensetracker.models.web.requests.PaymentMethodRequest;
import mx.kinich49.expensetracker.utils.StringUtils;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.ConditionParameter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PaymentMethodRequestCondition implements Condition<PaymentMethodRequestCondition.Parameter> {

    @Override
    public Optional<String> assertCondition(Parameter param) {
        PaymentMethodRequest request = param.request;

        if (request == null)
            return Optional.of("Payment method cannot be null. ");

        if (request.getId() == null || StringUtils.isNullOrEmpty(request.getName())) {
            return Optional.of("Payment method must have an id and/or name. ");
        }

        return Optional.empty();
    }

    @Data
    public static class Parameter implements ConditionParameter {
        private final PaymentMethodRequest request;
    }
}
