package mx.kinich49.expensetracker.validations.transactionservice.conditions;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.internal.ErrorWrapper;
import mx.kinich49.expensetracker.utils.StringUtils;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.transactionservice.TransactionServiceErrorCodes;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CategoryRequestCondition implements Condition<TransactionRequestParameter> {

    @Override
    public Optional<ErrorWrapper> assertCondition(TransactionRequestParameter param) throws ValidationFlowException {
        var request = param.getRequest();

        if (request == null) {
            var errorMessage = "Transaction cannot be null. ";
            return Optional.of(new ErrorWrapper(TransactionServiceErrorCodes.TRANSACTION_REQUEST_IS_NULL_OR_EMPTY, errorMessage));
        }

        var categoryRequest = request.getCategory();

        if (categoryRequest == null){
            var errorMessage = "Category can't be null";
            return Optional.of(new ErrorWrapper(TransactionServiceErrorCodes.CATEGORY_REQUEST_IS_NULL, errorMessage));
        }

        if (categoryRequest.getId() == null && StringUtils.isNullOrEmptyOrBlank(categoryRequest.getName())) {
            var errorMessage = "Category must have an id and/or name. ";
            return Optional.of(new ErrorWrapper(TransactionServiceErrorCodes.CATEGORY_REQUEST_HAS_NO_NAME_OR_ID, errorMessage));
        }

        return Optional.empty();
    }
}
