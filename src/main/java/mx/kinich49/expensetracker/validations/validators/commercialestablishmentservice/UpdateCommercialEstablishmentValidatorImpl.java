package mx.kinich49.expensetracker.validations.validators.commercialestablishmentservice;

import mx.kinich49.expensetracker.validations.AbstractValidator;
import org.springframework.stereotype.Component;

@Component
public class UpdateCommercialEstablishmentValidatorImpl
        extends AbstractValidator<CommercialEstablishmentValidatorParameterImpl> {

    public UpdateCommercialEstablishmentValidatorImpl(UpdateCommercialEstablishmentConditionProviderImpl conditionProvider) {
        super(conditionProvider);
    }
}
