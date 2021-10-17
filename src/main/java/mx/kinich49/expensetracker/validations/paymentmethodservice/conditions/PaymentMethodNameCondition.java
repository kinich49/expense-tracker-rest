package mx.kinich49.expensetracker.validations.paymentmethodservice.conditions;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.internal.ErrorWrapper;
import mx.kinich49.expensetracker.utils.StringUtils;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.paymentmethodservice.PaymentMethodServiceErrorCodes;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PaymentMethodNameCondition implements Condition<PaymentMethodConditionParameter> {

    @Override
    public Optional<ErrorWrapper> assertCondition(PaymentMethodConditionParameter param)
            throws ValidationFlowException {
        var request = param.getRequest();

        if (request == null)
            throw new ValidationFlowException("Request can't be null");

        if (StringUtils.isNullOrEmptyOrBlank(request.getName())) {
            var errorMessage = "Name can't be null, empty or blank. ";
            return Optional.of(new ErrorWrapper(PaymentMethodServiceErrorCodes.NAME_IS_NULL_EMPTY_OR_BLANK, errorMessage));
        }
        return Optional.empty();
    }
}
