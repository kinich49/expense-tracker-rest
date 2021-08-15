package mx.kinich49.expensetracker.validations.conditions.paymentmethodservice;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.utils.StringUtils;
import mx.kinich49.expensetracker.validations.Condition;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AddingPaymentMethodRequiredDataCondition implements Condition<PaymentMethodConditionParameter> {

    @Override
    public Optional<String> assertCondition(PaymentMethodConditionParameter param) throws ValidationFlowException {
        var request = param.getRequest();

        if (request == null)
            throw new ValidationFlowException("Request can't be null");

        if (StringUtils.isNullOrEmptyOrBlank(request.getName()))
            return Optional.of("Payment Method must have a name");

        return Optional.empty();
    }
}
