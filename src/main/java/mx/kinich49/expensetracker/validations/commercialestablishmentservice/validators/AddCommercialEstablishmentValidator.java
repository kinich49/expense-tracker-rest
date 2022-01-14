package mx.kinich49.expensetracker.validations.commercialestablishmentservice.validators;

import mx.kinich49.expensetracker.validations.AbstractValidator;
import mx.kinich49.expensetracker.validations.commercialestablishmentservice.providers.AddCommercialEstablishmentConditionProvider;
import org.springframework.stereotype.Component;

@Component
public class AddCommercialEstablishmentValidator
        extends AbstractValidator<CommercialEstablishmentValidatorParameter> {

    public AddCommercialEstablishmentValidator(AddCommercialEstablishmentConditionProvider conditionProvider) {
        super(conditionProvider);
    }

}
