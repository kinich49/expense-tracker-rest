package mx.kinich49.expensetracker.validations.conditions.commercialestablishmentservice;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.repositories.CommercialEstablishmentRepository;
import mx.kinich49.expensetracker.validations.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NotNullCommercialEstablishmentRequestConditionImpl implements Condition<Parameter> {

    private final CommercialEstablishmentRepository repository;

    @Autowired
    public NotNullCommercialEstablishmentRequestConditionImpl(CommercialEstablishmentRepository repository) {
        this.repository = repository;
    }

    /**
     * This condition validates the request is not null
     *
     * @param param the instance to assert it meets all conditions
     * @return Optional containing a String if the condition is no met.
     * Optional empty otherwise
     * @throws ValidationFlowException if the request is null
     */
    @Override
    public Optional<String> assertCondition(Parameter param) throws ValidationFlowException {
        var request = param.getRequest();

        if (request == null) {
            throw new ValidationFlowException("Request can't be null");
        }

        return Optional.empty();
    }
}
