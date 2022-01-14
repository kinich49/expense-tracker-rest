package mx.kinich49.expensetracker.validations.paymentmethodservice.validators;

import lombok.Data;
import mx.kinich49.expensetracker.models.web.requests.PaymentMethodRequest;
import mx.kinich49.expensetracker.validations.ValidatorParameter;

@Data
public class PaymentMethodValidatorParameter implements ValidatorParameter {

    private final PaymentMethodRequest request;
}
