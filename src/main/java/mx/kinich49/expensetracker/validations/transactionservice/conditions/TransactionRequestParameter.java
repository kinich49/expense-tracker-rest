package mx.kinich49.expensetracker.validations.transactionservice.conditions;

import lombok.Data;
import mx.kinich49.expensetracker.models.web.requests.TransactionRequest;
import mx.kinich49.expensetracker.validations.ConditionParameter;

@Data
public class TransactionRequestParameter implements ConditionParameter {

    private final TransactionRequest request;
}
