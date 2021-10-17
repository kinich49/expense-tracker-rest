package mx.kinich49.expensetracker.validations.transactionservice.conditions;

import mx.kinich49.expensetracker.models.internal.ErrorWrapper;
import mx.kinich49.expensetracker.utils.StringUtils;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.transactionservice.TransactionServiceErrorCodes;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PaymentMethodRequestCondition implements Condition<TransactionRequestParameter> {

    @Override
    public Optional<ErrorWrapper> assertCondition(TransactionRequestParameter param) {
        var request = param.getRequest();

        if (request == null) {
            var errorMessage = "Transaction cannot be null. ";
            return Optional.of(new ErrorWrapper(TransactionServiceErrorCodes.TRANSACTION_REQUEST_IS_NULL_OR_EMPTY, errorMessage));
        }

        var paymentMethodRequest = request.getPaymentMethod();

        if (paymentMethodRequest == null) {
            var errorMessage = "Payment Method cannot be null";
            return Optional.of(new ErrorWrapper(TransactionServiceErrorCodes.PAYMENT_METHOD_REQUEST_IS_NULL_OR_EMPTY, errorMessage));
        }

        if (paymentMethodRequest.getId() == null && StringUtils.isNullOrEmptyOrBlank(paymentMethodRequest.getName())) {
            var errorMessage = "Payment method must have an id and/or name. ";
            return Optional.of(new ErrorWrapper(TransactionServiceErrorCodes.PAYMENT_METHOD_REQUEST_HAS_NO_NAME_OR_ID, errorMessage));
        }

        return Optional.empty();
    }

}
