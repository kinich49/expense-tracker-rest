package mx.kinich49.expensetracker.validations.commercialestablishmentservice.conditions;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.internal.ErrorWrapper;
import mx.kinich49.expensetracker.repositories.CommercialEstablishmentRepository;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.commercialestablishmentservice.ConditionParameterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NotNullCommercialEstablishmentRequestCondition implements Condition<ConditionParameterImpl> {

    private final CommercialEstablishmentRepository repository;

    @Autowired
    public NotNullCommercialEstablishmentRequestCondition(CommercialEstablishmentRepository repository) {
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
    public Optional<ErrorWrapper> assertCondition(ConditionParameterImpl param) throws ValidationFlowException {
        var request = param.getRequest();

        if (request == null) {
            throw new ValidationFlowException("Request can't be null");
        }

        return Optional.empty();
    }
}
