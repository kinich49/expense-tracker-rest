package mx.kinich49.expensetracker.validations.monthlybudgetservice;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MonthlyBudgetServiceErrorCodes {

    public static final int REQUEST_NAME_IS_NULL_OR_EMPTY = 1;
    public static final int ENTITY_WITH_NAME_ALREADY_EXISTS = 2;
    public static final int INVALID_ID = 3;
    public static final int REQUEST_MUST_HAVE_BEGIN_DATE = 5;
    public static final int END_DATE_BEFORE_BEGIN_DATE = 6;
    public static final int BASE_LIMIT_LESS_THAN_ZERO = 7;
    public static final int NO_MONTHLY_INCOME_SET = 8;
    public static final int BUDGET_REQUEST_OVERCOMES_INCOME = 9;
    public static final int NO_BUDGET_FOUND = 10;
}
