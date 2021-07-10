package mx.kinich49.expensetracker.validations.validators.transactionservice;

import lombok.Data;
import mx.kinich49.expensetracker.models.web.requests.TransactionRequest;
import mx.kinich49.expensetracker.validations.AbstractValidator;
import mx.kinich49.expensetracker.validations.ValidatorParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionServiceValidatorImpl extends AbstractValidator<TransactionServiceValidatorImpl.Parameter> {

    @Autowired
    public TransactionServiceValidatorImpl(ConditionProviderImpl conditionProvider) {
       super(conditionProvider);
    }

    @Data
    public static class Parameter implements ValidatorParameter {
        private final TransactionRequest transactionRequest;
    }
}
