package mx.kinich49.expensetracker.validations.paymentmethodservice.validators;

import mx.kinich49.expensetracker.validations.AbstractValidator;
import mx.kinich49.expensetracker.validations.paymentmethodservice.providers.AddPaymentValidatorConditionProvider;
import org.springframework.stereotype.Component;

@Component
public class AddPaymentMethodValidator extends AbstractValidator<PaymentMethodValidatorParameter> {

    public AddPaymentMethodValidator(AddPaymentValidatorConditionProvider conditionProvider) {
        super(conditionProvider);
    }

}
