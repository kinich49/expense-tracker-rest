package mx.kinich49.expensetracker.validations.commercialestablishmentservice.conditions;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.internal.ErrorWrapper;
import mx.kinich49.expensetracker.repositories.CommercialEstablishmentRepository;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.commercialestablishmentservice.CommercialEstablishmentServiceErrorCodes;
import mx.kinich49.expensetracker.validations.commercialestablishmentservice.ConditionParameterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UpdateCommercialEstablishmentCondition implements Condition<ConditionParameterImpl> {

    private final CommercialEstablishmentRepository repository;

    @Autowired
    public UpdateCommercialEstablishmentCondition(CommercialEstablishmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<ErrorWrapper> assertCondition(ConditionParameterImpl param) throws ValidationFlowException {
        var request = param.getRequest();

        if (request.getId() == null || request.getId() <= 0) {
            var errorMessage = "Invalid Id. ";
            return Optional.of(new ErrorWrapper(CommercialEstablishmentServiceErrorCodes.INVALID_ID, errorMessage));
        }

        boolean exists = repository.existsById(request.getId());

        if (!exists) {
            String errorMessage = String.format("Commercial Establishment with id %1$d does not exist", request.getId());
            return Optional.of(new ErrorWrapper(CommercialEstablishmentServiceErrorCodes.COMMERCIAL_ESTABLISHMENT_WITH_ID_DOES_NOT_EXISTS, errorMessage));
        }

        return Optional.empty();
    }
}
