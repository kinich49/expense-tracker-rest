package mx.kinich49.expensetracker.validations.transactionservice.conditions;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.web.ErrorWrapper;
import mx.kinich49.expensetracker.models.web.requests.CategoryRequest;
import mx.kinich49.expensetracker.models.web.requests.TransactionRequest;
import mx.kinich49.expensetracker.utils.NumberUtils;
import mx.kinich49.expensetracker.utils.StringUtils;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.transactionservice.TransactionServiceErrorCodes;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CategoryRequestCondition implements Condition<TransactionRequestParameter> {

    /**
     * This condition validates {@link TransactionRequest} is non-null
     * and has a non-null {@link CategoryRequest} and
     * the categoryRequest instance as a non-null positive id property,
     * or a non-null name property
     *
     * @param param the instance to assert it meets all conditions
     * @return An optional containing an error message if the condition is not met.
     * Empty Otherwise
     * @throws ValidationFlowException if a 'gatekeeper validation' is not met.
     */
    @Override
    public Optional<ErrorWrapper> assertCondition(TransactionRequestParameter param) throws ValidationFlowException {
        var request = param.getRequest();

        if (request == null) {
            var errorMessage = "Transaction cannot be null. ";
            return Optional.of(new ErrorWrapper(TransactionServiceErrorCodes.TRANSACTION_REQUEST_IS_NULL_OR_EMPTY,
                    errorMessage));
        }

        var categoryRequest = request.getCategory();

        if (categoryRequest == null){
            var errorMessage = "Category can't be null";
            return Optional.of(new ErrorWrapper(TransactionServiceErrorCodes.CATEGORY_REQUEST_IS_NULL,
                    errorMessage));
        }

        if (NumberUtils.isEitherNullZeroOrNegative(categoryRequest.getId()) &&
                StringUtils.isNullOrEmptyOrBlank(categoryRequest.getName())) {
            var errorMessage = "Category must have an id and/or name. ";
            return Optional.of(new ErrorWrapper(TransactionServiceErrorCodes.CATEGORY_REQUEST_HAS_NO_NAME_OR_ID,
                    errorMessage));
        }

        return Optional.empty();
    }
}
