package mx.kinich49.expensetracker.validations.paymentmethodservice.providers;

import mx.kinich49.expensetracker.validations.AbstractConditionProvider;
import mx.kinich49.expensetracker.validations.paymentmethodservice.conditions.AddingPaymentMethodRequiredDataCondition;
import mx.kinich49.expensetracker.validations.paymentmethodservice.conditions.NotExistentPaymentMethodNameCondition;
import mx.kinich49.expensetracker.validations.paymentmethodservice.conditions.PaymentMethodConditionParameter;
import mx.kinich49.expensetracker.validations.paymentmethodservice.validators.PaymentMethodValidatorParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddPaymentValidatorConditionProvider extends AbstractConditionProvider<PaymentMethodValidatorParameter> {

    private final AddingPaymentMethodRequiredDataCondition requiredDataCondition;
    private final NotExistentPaymentMethodNameCondition notExistentNameCondition;

    @Autowired
    public AddPaymentValidatorConditionProvider(AddingPaymentMethodRequiredDataCondition requiredDataCondition,
                                                NotExistentPaymentMethodNameCondition notExistentNameCondition) {
        this.requiredDataCondition = requiredDataCondition;
        this.notExistentNameCondition = notExistentNameCondition;
    }

    @Override
    public void buildConditions(PaymentMethodValidatorParameter parameter) {
        var conditionParameter = new PaymentMethodConditionParameter(parameter.getRequest());
        addCondition(requiredDataCondition, conditionParameter);
        addCondition(notExistentNameCondition, conditionParameter);
    }
}
