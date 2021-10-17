package mx.kinich49.expensetracker.validations.paymentmethodservice.providers;

import mx.kinich49.expensetracker.validations.AbstractConditionProvider;
import mx.kinich49.expensetracker.validations.paymentmethodservice.conditions.ExistentPaymentMethodCondition;
import mx.kinich49.expensetracker.validations.paymentmethodservice.conditions.PaymentMethodConditionParameter;
import mx.kinich49.expensetracker.validations.paymentmethodservice.conditions.PaymentMethodIdCondition;
import mx.kinich49.expensetracker.validations.paymentmethodservice.conditions.PaymentMethodNameCondition;
import mx.kinich49.expensetracker.validations.paymentmethodservice.validators.PaymentMethodValidatorParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdatePaymentMethodConditionProvider extends AbstractConditionProvider<PaymentMethodValidatorParameter> {


    private final ExistentPaymentMethodCondition existentCondition;
    private final PaymentMethodIdCondition idCondition;
    private final PaymentMethodNameCondition nameCondition;

    @Autowired
    public UpdatePaymentMethodConditionProvider(ExistentPaymentMethodCondition existentCondition,
                                                PaymentMethodIdCondition idCondition,
                                                PaymentMethodNameCondition nameCondition) {
        this.existentCondition = existentCondition;
        this.idCondition = idCondition;
        this.nameCondition = nameCondition;
    }

    @Override
    public void buildConditions(PaymentMethodValidatorParameter parameter) {
        var conditionParameter = new PaymentMethodConditionParameter(parameter.getRequest());
        addCondition(nameCondition, conditionParameter);
        addCondition(idCondition, conditionParameter);
        addCondition(existentCondition, conditionParameter);
    }
}
