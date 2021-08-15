package mx.kinich49.expensetracker.validations.conditions.paymentmethodservice;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.utils.StringUtils;
import mx.kinich49.expensetracker.validations.Condition;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UpdatingPaymentMethodRequiredDataCondition implements Condition<PaymentMethodConditionParameter> {

    @Override
    public Optional<String> assertCondition(PaymentMethodConditionParameter param) throws ValidationFlowException {
        var request = param.getRequest();

        if (request == null)
            throw new ValidationFlowException("Request can't be null");

        StringBuilder accumulator = new StringBuilder();
        assertName(request.getName(), accumulator);
        assertId(request.getId(), accumulator);

        if (accumulator.length() > 0) {
            return Optional.of(accumulator.toString().trim());
        }

        return Optional.empty();
    }

    private void assertName(String name, StringBuilder accum) {
        if (StringUtils.isNullOrEmptyOrBlank(name))
            accum.append("Name can't be null, empty or blank. ");
    }

    private void assertId(Long id, StringBuilder accum) {
        if (id == null) {
            accum.append("Id can't be null");
        }
    }
}
