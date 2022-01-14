package mx.kinich49.expensetracker.validations.monthlyincome;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MonthlyIncomeErrorCodes {

    public static final int MONTHLY_INCOME_COLLIDES_WITH_REQUEST = 1;
    public static final int REQUEST_NULL_OR_EMPTY = 2;
    public static final int BEGIN_DATE_NULL = 3;
    public static final int END_DATE_IS_BEFORE_BEGIN_DATE = 4;
    public static final int UPPER_INCOME_LIMIT_IS_ZERO = 5;


}
