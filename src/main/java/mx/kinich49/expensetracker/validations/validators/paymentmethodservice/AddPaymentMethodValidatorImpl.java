package mx.kinich49.expensetracker.validations.validators.paymentmethodservice;

import mx.kinich49.expensetracker.validations.AbstractValidator;
import org.springframework.stereotype.Component;

@Component
public class AddPaymentMethodValidatorImpl extends AbstractValidator<PaymentMethodValidatorParameter> {

    public AddPaymentMethodValidatorImpl(AddPaymentValidatorConditionProvider conditionProvider) {
        super(conditionProvider);
    }

}
