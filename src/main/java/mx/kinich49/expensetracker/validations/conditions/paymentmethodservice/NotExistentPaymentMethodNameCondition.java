package mx.kinich49.expensetracker.validations.conditions.paymentmethodservice;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.repositories.PaymentMethodRepository;
import mx.kinich49.expensetracker.validations.Condition;
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

    @Override
    public Optional<String> assertCondition(PaymentMethodConditionParameter param) throws ValidationFlowException {
        var request = param.getRequest();

        if (request == null)
            throw new ValidationFlowException("Request can't be null");

        boolean existsByName = repository.existsByNameIgnoreCase(request.getName());

        if (existsByName)
            return Optional.of(String.format("A Payment Method with name: %1$s already exists.", request.getName()));

        return Optional.empty();
    }
}
