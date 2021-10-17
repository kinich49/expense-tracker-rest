package mx.kinich49.expensetracker.validations.transactionservice.providers;

import mx.kinich49.expensetracker.validations.AbstractConditionProvider;
import mx.kinich49.expensetracker.validations.transactionservice.conditions.*;
import mx.kinich49.expensetracker.validations.transactionservice.validators.TransactionServiceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionServiceConditionProvider extends
        AbstractConditionProvider<TransactionServiceValidator.Parameter> {

    private final CategoryRequestCondition categoryCondition;
    private final PaymentMethodRequestCondition paymentMethodCondition;
    private final StoreRequestCondition storeCondition;
    private final AmountCondition amountCondition;
    private final TransactionDateCondition transactionDateCondition;
    private final TransactionTitleCondition transactionTitleCondition;

    @Autowired
    public TransactionServiceConditionProvider(CategoryRequestCondition categoryCondition,
                                               PaymentMethodRequestCondition paymentMethodCondition,
                                               StoreRequestCondition storeCondition,
                                               AmountCondition amountCondition,
                                               TransactionDateCondition transactionDateCondition,
                                               TransactionTitleCondition transactionTitleCondition) {
        this.categoryCondition = categoryCondition;
        this.paymentMethodCondition = paymentMethodCondition;
        this.storeCondition = storeCondition;
        this.amountCondition = amountCondition;
        this.transactionDateCondition = transactionDateCondition;
        this.transactionTitleCondition = transactionTitleCondition;
    }

    @Override
    public void buildConditions(TransactionServiceValidator.Parameter parameter) {
        var conditionParameter = new TransactionRequestParameter(parameter.getTransactionRequest());

        addCondition(amountCondition, conditionParameter);
        addCondition(transactionDateCondition, conditionParameter);
        addCondition(transactionTitleCondition, conditionParameter);
        addCondition(categoryCondition, conditionParameter);
        addCondition(paymentMethodCondition, conditionParameter);
        addCondition(storeCondition, conditionParameter);
    }

}
