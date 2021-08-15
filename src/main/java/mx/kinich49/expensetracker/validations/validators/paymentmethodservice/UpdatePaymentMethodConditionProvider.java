package mx.kinich49.expensetracker.validations.validators.paymentmethodservice;

import mx.kinich49.expensetracker.validations.AbstractConditionProvider;
import mx.kinich49.expensetracker.validations.conditions.paymentmethodservice.ExistentPaymentMethodCondition;
import mx.kinich49.expensetracker.validations.conditions.paymentmethodservice.PaymentMethodConditionParameter;
import mx.kinich49.expensetracker.validations.conditions.paymentmethodservice.UpdatingPaymentMethodRequiredDataCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdatePaymentMethodConditionProvider extends AbstractConditionProvider<PaymentMethodValidatorParameter> {

    private final UpdatingPaymentMethodRequiredDataCondition requiredDataCondition;
    private final ExistentPaymentMethodCondition existentCondition;

    @Autowired
    public UpdatePaymentMethodConditionProvider(UpdatingPaymentMethodRequiredDataCondition requiredDataCondition,
                                                ExistentPaymentMethodCondition existentCondition) {
        this.requiredDataCondition = requiredDataCondition;
        this.existentCondition = existentCondition;
    }

    @Override
    public void buildConditions(PaymentMethodValidatorParameter parameter) {
        var conditionParameter = new PaymentMethodConditionParameter(parameter.getRequest());
        addCondition(requiredDataCondition, conditionParameter);
        addCondition(existentCondition, conditionParameter);
    }
}
