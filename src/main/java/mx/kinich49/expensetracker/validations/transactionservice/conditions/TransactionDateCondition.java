package mx.kinich49.expensetracker.validations.transactionservice.conditions;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.internal.ErrorWrapper;
import mx.kinich49.expensetracker.models.web.requests.PaymentMethodRequest;
import mx.kinich49.expensetracker.models.web.requests.TransactionRequest;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.transactionservice.TransactionServiceErrorCodes;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TransactionDateCondition implements Condition<TransactionRequestParameter> {

    /**
     * This condition validates {@link TransactionRequest} is non-null
     * and as a non-null dateCreated property.
     *
     * @param param the instance to assert it meets all conditions
     * @return An optional containing an error message if the condition is not met.
     * Empty Otherwise
     */
    @Override
    public Optional<ErrorWrapper> assertCondition(TransactionRequestParameter param)
            throws ValidationFlowException {
        var request = param.getRequest();

        if (request == null)
            throw new ValidationFlowException("Transaction is null. ");
        if (request.getDateCreated() == null) {
            var errorMessage = "Transaction must have a date. ";
            return Optional.of(new ErrorWrapper(TransactionServiceErrorCodes.TRANSACTION_NO_DATE, errorMessage));
        }

        return Optional.empty();
    }
}
