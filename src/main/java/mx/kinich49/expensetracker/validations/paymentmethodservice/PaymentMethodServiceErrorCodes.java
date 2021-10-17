package mx.kinich49.expensetracker.validations.paymentmethodservice;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentMethodServiceErrorCodes {

    public static final int REQUEST_NULL_OR_EMPTY = 1;
    public static final int PAYMENT_MUST_HAVE_A_NAME = 2;
    public static final int INVALID_ID = 3;
    public static final int PAYMENT_METHOD_WITH_NAME_ALREADY_EXISTS = 4;
    public static final int NAME_IS_NULL_EMPTY_OR_BLANK = 4;
    public static final int ID_IS_NULL_OR_ZERO = 5;
}
