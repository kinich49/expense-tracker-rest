package mx.kinich49.expensetracker.validations.paymentmethodservice.conditions;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.web.ErrorWrapper;
import mx.kinich49.expensetracker.models.web.requests.PaymentMethodRequest;
import mx.kinich49.expensetracker.utils.StringUtils;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.paymentmethodservice.PaymentMethodServiceErrorCodes;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AddingPaymentMethodRequiredDataCondition implements Condition<PaymentMethodConditionParameter> {

    /**
     * This condition validates {@link PaymentMethodRequest}
     * is not null and has a non-null non-blank name
     *
     * @param param the instance to assert it meets all conditions
     * @return An optional containing an error message if the condition is not met.
     * Empty Otherwise
     * @throws ValidationFlowException if a 'gatekeeper validation' is not met.
     */
    @Override
    public Optional<ErrorWrapper> assertCondition(PaymentMethodConditionParameter param) throws ValidationFlowException {
        var request = param.getRequest();

        if (request == null)
            throw new ValidationFlowException("Request can't be null");

        if (StringUtils.isNullOrEmptyOrBlank(request.getName())){
            var errorMessage = "Payment Method must have a name";
            return Optional.of(new ErrorWrapper(PaymentMethodServiceErrorCodes.NAME_IS_NULL_EMPTY_OR_BLANK, errorMessage));
        }

        return Optional.empty();
    }
}
