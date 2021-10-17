package mx.kinich49.expensetracker.validations.transactionservice;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionServiceErrorCodes {

    public static final int REQUEST_NULL_OR_EMPTY = 1;
    public static final int CATEGORY_REQUEST_IS_NULL = 2;
    public static final int CATEGORY_REQUEST_HAS_NO_NAME_OR_ID= 3;
    public static final int PAYMENT_METHOD_REQUEST_IS_NULL_OR_EMPTY = 4;
    public static final int PAYMENT_METHOD_REQUEST_HAS_NO_NAME_OR_ID = 5;
    public static final int STORE_REQUEST_HAS_NO_NAME_OR_ID =  6;
    public static final int TRANSACTION_REQUEST_IS_NULL_OR_EMPTY = 7;
    public static final int TRANSACTION_NO_DATE = 8;
    public static final int TRANSACTION_NO_TITLE = 9;
    public static final int TRANSACTION_NO_AMOUNT = 10;

}
