package mx.kinich49.expensetracker.validations.validators.paymentmethodservice;

import mx.kinich49.expensetracker.validations.AbstractValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdatePaymentMethodValidatorImpl extends AbstractValidator<PaymentMethodValidatorParameter> {

    @Autowired
    public UpdatePaymentMethodValidatorImpl(UpdatePaymentMethodConditionProvider conditionProvider) {
        super(conditionProvider);
    }
}
