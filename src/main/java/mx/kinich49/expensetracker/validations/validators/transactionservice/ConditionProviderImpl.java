package mx.kinich49.expensetracker.validations.validators.transactionservice;

import mx.kinich49.expensetracker.models.web.requests.TransactionRequest;
import mx.kinich49.expensetracker.utils.Pair;
import mx.kinich49.expensetracker.validations.AbstractConditionProvider;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.ConditionParameter;
import mx.kinich49.expensetracker.validations.conditions.transactionservice.CategoryRequestCondition;
import mx.kinich49.expensetracker.validations.conditions.transactionservice.PaymentMethodRequestCondition;
import mx.kinich49.expensetracker.validations.conditions.transactionservice.StoreRequestCondition;
import mx.kinich49.expensetracker.validations.conditions.transactionservice.TransactionRequestCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Optional;

@Component
public class ConditionProviderImpl extends AbstractConditionProvider<TransactionServiceValidatorImpl.Parameter> {

    private final CategoryRequestCondition categoryCondition;
    private final PaymentMethodRequestCondition paymentMethodCondition;
    private final StoreRequestCondition storeCondition;
    private final TransactionRequestCondition transactionCondition;

    @Autowired
    public ConditionProviderImpl(CategoryRequestCondition categoryCondition,
                                 PaymentMethodRequestCondition paymentMethodCondition,
                                 StoreRequestCondition storeCondition,
                                 TransactionRequestCondition transactionCondition) {
        this.categoryCondition = categoryCondition;
        this.paymentMethodCondition = paymentMethodCondition;
        this.storeCondition = storeCondition;
        this.transactionCondition = transactionCondition;
    }

    @Override
    public void buildConditions(TransactionServiceValidatorImpl.Parameter parameter) {
        var queue = new ArrayDeque<Pair<Condition<? extends ConditionParameter>, ? extends ConditionParameter>>();

        var categoryParameter = Optional
                .ofNullable(parameter.getTransactionRequest())
                .map(TransactionRequest::getCategory)
                .map(CategoryRequestCondition.Parameter::new)
                .orElse(new CategoryRequestCondition.Parameter(null));

        var paymentMethodParameter = Optional
                .ofNullable(parameter.getTransactionRequest())
                .map(TransactionRequest::getPaymentMethod)
                .map(PaymentMethodRequestCondition.Parameter::new)
                .orElse(new PaymentMethodRequestCondition.Parameter(null));

        var storeParameter = Optional
                .ofNullable(parameter.getTransactionRequest())
                .map(TransactionRequest::getStore)
                .map(StoreRequestCondition.Parameter::new)
                .orElse(new StoreRequestCondition.Parameter(null));

        var transactionParameter = Optional
                .ofNullable(parameter.getTransactionRequest())
                .map(TransactionRequestCondition.Parameter::new)
                .orElse(new TransactionRequestCondition.Parameter(null));

        queue.add(Pair.of(transactionCondition, transactionParameter));
        queue.add(Pair.of(categoryCondition, categoryParameter));
        queue.add(Pair.of(storeCondition, storeParameter));
        queue.add(Pair.of(paymentMethodCondition, paymentMethodParameter));

        threadLocal.set(queue);
    }

}
