package mx.kinich49.expensetracker.validations.commercialestablishmentservice.validators;

import mx.kinich49.expensetracker.validations.AbstractValidator;
import mx.kinich49.expensetracker.validations.commercialestablishmentservice.providers.UpdateCommercialEstablishmentConditionProvider;
import org.springframework.stereotype.Component;

@Component
public class UpdateCommercialEstablishmentValidator
        extends AbstractValidator<CommercialEstablishmentValidatorParameter> {

    public UpdateCommercialEstablishmentValidator(UpdateCommercialEstablishmentConditionProvider conditionProvider) {
        super(conditionProvider);
    }
}
