package mx.kinich49.expensetracker.validations.paymentmethodservice.validators;

import mx.kinich49.expensetracker.validations.AbstractValidator;
import mx.kinich49.expensetracker.validations.paymentmethodservice.providers.UpdatePaymentMethodConditionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdatePaymentMethodValidator extends AbstractValidator<PaymentMethodValidatorParameter> {

    @Autowired
    public UpdatePaymentMethodValidator(UpdatePaymentMethodConditionProvider conditionProvider) {
        super(conditionProvider);
    }
}
