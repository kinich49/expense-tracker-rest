package mx.kinich49.expensetracker.validations.transactionservice.conditions;

import mx.kinich49.expensetracker.models.internal.ErrorWrapper;
import mx.kinich49.expensetracker.models.web.requests.CommercialEstablishmentRequest;
import mx.kinich49.expensetracker.models.web.requests.PaymentMethodRequest;
import mx.kinich49.expensetracker.models.web.requests.TransactionRequest;
import mx.kinich49.expensetracker.utils.NumberUtils;
import mx.kinich49.expensetracker.utils.StringUtils;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.transactionservice.TransactionServiceErrorCodes;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StoreRequestCondition implements Condition<TransactionRequestParameter> {

    /**
     * This condition validates {@link TransactionRequest} is non-null
     * and as a non-null {@link CommercialEstablishmentRequest} with a
     * non-null ID or a non-null name properties,
     * or a null {@code CommercialEstablishmentRequest}
     *
     * @param param the instance to assert it meets all conditions
     * @return An optional containing an error message if the condition is not met.
     * Empty Otherwise
     */
    @Override
    public Optional<ErrorWrapper> assertCondition(TransactionRequestParameter param) {
        var request = param.getRequest();

        if (request == null) {
            var errorMessage = "Transaction cannot be null. ";
            return Optional.of(new ErrorWrapper(TransactionServiceErrorCodes.TRANSACTION_REQUEST_IS_NULL_OR_EMPTY, errorMessage));
        }

        var storeRequest = request.getStore();
        //Store is not required
        if (storeRequest == null)
            return Optional.empty();

        if ( NumberUtils.isEitherNullZeroOrNegative(storeRequest.getId())  &&
                StringUtils.isNullOrEmptyOrBlank(storeRequest.getName())) {
            var errorMessage = "Store must have an id and/or name. ";
            return Optional.of(new ErrorWrapper(TransactionServiceErrorCodes.STORE_REQUEST_HAS_NO_NAME_OR_ID, errorMessage));
        }

        return Optional.empty();
    }

}
