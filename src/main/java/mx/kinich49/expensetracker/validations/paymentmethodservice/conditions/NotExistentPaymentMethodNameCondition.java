package mx.kinich49.expensetracker.validations.paymentmethodservice.conditions;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.web.ErrorWrapper;
import mx.kinich49.expensetracker.models.web.requests.PaymentMethodRequest;
import mx.kinich49.expensetracker.repositories.PaymentMethodRepository;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.paymentmethodservice.PaymentMethodServiceErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NotExistentPaymentMethodNameCondition implements Condition<PaymentMethodConditionParameter> {

    private final PaymentMethodRepository repository;


    @Autowired
    public NotExistentPaymentMethodNameCondition(PaymentMethodRepository repository) {
        this.repository = repository;
    }

    /**
     * This condition validates {@link PaymentMethodRequest}
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


        if (repository.existsByNameIgnoreCase(request.getName())){
            var errorMessage = String.format("A Payment Method with name: %1$s already exists.", request.getName());
            return Optional.of(new ErrorWrapper(PaymentMethodServiceErrorCodes.PAYMENT_METHOD_WITH_NAME_ALREADY_EXISTS, errorMessage));
        }

        return Optional.empty();
    }
}
