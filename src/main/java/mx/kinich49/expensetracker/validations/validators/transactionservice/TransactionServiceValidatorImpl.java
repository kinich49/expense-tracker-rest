package mx.kinich49.expensetracker.validations.validators.transactionservice;

import lombok.Data;
import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.web.requests.TransactionRequest;
import mx.kinich49.expensetracker.utils.StringUtils;
import mx.kinich49.expensetracker.validations.Validator;
import mx.kinich49.expensetracker.validations.ValidatorParameter;
import mx.kinich49.expensetracker.validations.conditions.transactionservice.CategoryRequestCondition;
import mx.kinich49.expensetracker.validations.conditions.transactionservice.PaymentMethodRequestCondition;
import mx.kinich49.expensetracker.validations.conditions.transactionservice.StoreRequestCondition;
import mx.kinich49.expensetracker.validations.conditions.transactionservice.TransactionRequestCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionServiceValidatorImpl implements Validator<TransactionServiceValidatorImpl.Parameter> {

    private final CategoryRequestCondition categoryRequestCondition;
    private final PaymentMethodRequestCondition paymentMethodRequestCondition;
    private final StoreRequestCondition storeRequestCondition;
    private final TransactionRequestCondition transactionRequestCondition;

    @Autowired
    public TransactionServiceValidatorImpl(CategoryRequestCondition categoryRequestCondition,
                                           PaymentMethodRequestCondition paymentMethodRequestCondition,
                                           StoreRequestCondition storeRequestCondition,
                                           TransactionRequestCondition transactionRequestCondition) {
        this.categoryRequestCondition = categoryRequestCondition;
        this.paymentMethodRequestCondition = paymentMethodRequestCondition;
        this.storeRequestCondition = storeRequestCondition;
        this.transactionRequestCondition = transactionRequestCondition;
    }

    @Override
    public void validate(Parameter param) throws BusinessException {
        StringBuilder accumulator = new StringBuilder();
        validateTransaction(param.transactionRequest, accumulator);
        validateCategory(param.transactionRequest, accumulator);
        validateStore(param.transactionRequest, accumulator);
        validatePaymentMethod(param.transactionRequest, accumulator);

        if (StringUtils.isNeitherNullNorEmptyNorBlank(accumulator.toString())) {
            throw new BusinessException(accumulator.toString());
        }
    }

    private void validateTransaction(TransactionRequest request, StringBuilder accumulator)
            throws ValidationFlowException {
        TransactionRequestCondition.Parameter transactionParam = new TransactionRequestCondition
                .Parameter(request);

        transactionRequestCondition.assertCondition(transactionParam)
                .ifPresent(error -> {
                    accumulator.append(error);
                    accumulator.append(" ");
                });
    }

    private void validateCategory(TransactionRequest request, StringBuilder accumulator) {
        CategoryRequestCondition.Parameter categoryParam = new CategoryRequestCondition
                .Parameter(request.getCategory());

        categoryRequestCondition.assertCondition(categoryParam)
                .ifPresent(error -> {
                    accumulator.append(error);
                    accumulator.append(" ");
                });
    }

    private void validateStore(TransactionRequest request, StringBuilder accumulator) {
        StoreRequestCondition.Parameter storeParam = new StoreRequestCondition
                .Parameter(request.getStore());

        storeRequestCondition.assertCondition(storeParam)
                .ifPresent(error -> {
                    accumulator.append(error);
                    accumulator.append(" ");
                });

    }

    private void validatePaymentMethod(TransactionRequest request, StringBuilder accumulator) {
        PaymentMethodRequestCondition.Parameter paymentParam = new PaymentMethodRequestCondition
                .Parameter(request.getPaymentMethod());

        paymentMethodRequestCondition.assertCondition(paymentParam)
                .ifPresent(error -> {
                    accumulator.append(error);
                    accumulator.append(" ");
                });
    }

    @Data
    public static class Parameter implements ValidatorParameter {
        private final TransactionRequest transactionRequest;
    }
}
