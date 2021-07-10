package mx.kinich49.expensetracker.validations.conditions.transactionservice;

import lombok.Data;
import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.web.requests.TransactionRequest;
import mx.kinich49.expensetracker.utils.StringUtils;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.ConditionParameter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TransactionRequestCondition implements Condition<TransactionRequestCondition.Parameter> {

    @Override
    public Optional<String> assertCondition(TransactionRequestCondition.Parameter param) throws ValidationFlowException {
        TransactionRequest request = param.request;

        if (request == null)
            throw new ValidationFlowException("Transaction is null. ");

        StringBuilder stringBuilder = new StringBuilder();
        if (request.getDateCreated() == null) {
            stringBuilder.append("Transaction must have a date. ");
        }

        if (StringUtils.isNullOrEmptyOrBlank(request.getTitle())) {
            stringBuilder.append("Transaction must have a title. ");
        }

        if (request.getAmount() <= 0) {
            stringBuilder.append("Transaction must have an amount. ");
        }

        if (stringBuilder.length() == 0) {
            return Optional.empty();
        }

        return Optional.of(stringBuilder.toString().trim());
    }

    @Data
    public static class Parameter implements ConditionParameter {
        private final TransactionRequest request;
    }
}
