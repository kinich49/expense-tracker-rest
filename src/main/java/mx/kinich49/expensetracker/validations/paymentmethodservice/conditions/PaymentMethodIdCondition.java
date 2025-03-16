package mx.kinich49.expensetracker.validations.paymentmethodservice.conditions;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.web.ErrorWrapper;
import mx.kinich49.expensetracker.models.web.requests.PaymentMethodRequest;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.paymentmethodservice.PaymentMethodServiceErrorCodes;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PaymentMethodIdCondition implements Condition<PaymentMethodConditionParameter> {

    /**
     * This condition validates {@link PaymentMethodRequest}
     *  is non-null and the ID is non-null and a non-zero positive number
     *
     * @param param the instance to assert it meets all conditions
     * @return An optional containing an error message if the condition is not met.
     * Empty Otherwise
     * @throws ValidationFlowException if a 'gatekeeper validation' is not met.
     */
    @Override
    public Optional<ErrorWrapper> assertCondition(PaymentMethodConditionParameter param)
            throws ValidationFlowException {
        var request = param.getRequest();

        if (request == null)
            throw new ValidationFlowException("Request can't be null");

        if (request.getId() == null || request.getId() <= 0){
            var errorMessage = "Id should be greater than 0";
            return Optional.of(new ErrorWrapper(PaymentMethodServiceErrorCodes.INVALID_ID, errorMessage));
        }

        return Optional.empty();
    }
}
