package mx.kinich49.expensetracker.validations.paymentmethodservice.conditions;

import lombok.Data;
import mx.kinich49.expensetracker.models.web.requests.PaymentMethodRequest;
import mx.kinich49.expensetracker.validations.ConditionParameter;

@Data
public class PaymentMethodConditionParameter implements ConditionParameter {

    private final PaymentMethodRequest request;
}
