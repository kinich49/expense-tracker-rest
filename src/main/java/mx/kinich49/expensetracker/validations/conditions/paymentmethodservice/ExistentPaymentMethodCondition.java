package mx.kinich49.expensetracker.validations.conditions.paymentmethodservice;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.repositories.PaymentMethodRepository;
import mx.kinich49.expensetracker.validations.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ExistentPaymentMethodCondition implements Condition<PaymentMethodConditionParameter> {

    private final PaymentMethodRepository repository;

    @Autowired
    public ExistentPaymentMethodCondition(PaymentMethodRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<String> assertCondition(PaymentMethodConditionParameter param) throws ValidationFlowException {
        var request = param.getRequest();

        if (request == null)
            throw new ValidationFlowException("Request can't be null");

        var id = request.getId();

        if (id == null || id <= 0)
            return Optional.of(String.format("Invalid id: %1$d", id));

        boolean isExistent = repository.existsById(id);

        if (!isExistent)
            return Optional.of(String.format("Invalid id: %1$d", id));

        return Optional.empty();

    }
}
