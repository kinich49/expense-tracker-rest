package mx.kinich49.expensetracker.validations.validators.commercialestablishmentservice;

import mx.kinich49.expensetracker.validations.AbstractValidator;
import org.springframework.stereotype.Component;

@Component
public class AddCommercialEstablishmentValidatorImpl
        extends AbstractValidator<CommercialEstablishmentValidatorParameterImpl> {

    public AddCommercialEstablishmentValidatorImpl(AddCommercialEstablishmentConditionProviderImpl conditionProvider) {
        super(conditionProvider);
    }

}
