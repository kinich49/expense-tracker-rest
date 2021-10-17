package mx.kinich49.expensetracker.validations.transactionservice.conditions;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.internal.ErrorWrapper;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.transactionservice.TransactionServiceErrorCodes;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AmountCondition implements Condition<TransactionRequestParameter> {

    @Override
    public Optional<ErrorWrapper> assertCondition(TransactionRequestParameter param)
            throws ValidationFlowException {
        var request = param.getRequest();

        if (request == null)
            throw new ValidationFlowException("Transaction is null. ");

        if (request.getAmount() <= 0) {
            var errorMessage = "Transaction must have an amount. ";
            return Optional.of(new ErrorWrapper(TransactionServiceErrorCodes.TRANSACTION_NO_AMOUNT, errorMessage));
        }

        return Optional.empty();
    }
}
