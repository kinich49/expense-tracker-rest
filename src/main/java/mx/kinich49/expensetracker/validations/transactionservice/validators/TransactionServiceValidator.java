package mx.kinich49.expensetracker.validations.transactionservice.validators;

import lombok.Data;
import mx.kinich49.expensetracker.models.web.requests.TransactionRequest;
import mx.kinich49.expensetracker.validations.AbstractValidator;
import mx.kinich49.expensetracker.validations.ValidatorParameter;
import mx.kinich49.expensetracker.validations.transactionservice.providers.TransactionServiceConditionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionServiceValidator extends AbstractValidator<TransactionServiceValidator.Parameter> {

    @Autowired
    public TransactionServiceValidator(TransactionServiceConditionProvider conditionProvider) {
       super(conditionProvider);
    }

    @Data
    public static class Parameter implements ValidatorParameter {
        private final TransactionRequest transactionRequest;
    }
}
