package mx.kinich49.expensetracker.validations.monthlycategorybudget;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MonthlyCategoryBudgetErrorCodes {

    public static final int NO_MONTHLY_BUDGETS = 1;
    public static final int LIMIT_OVERCOMES_MONTHLY_EXPENSES = 2;
    public static final int REQUEST_NULL_OR_EMPTY = 3;
    public static final int BUDGET_NOT_FOUND = 4;
    public static final int CATEGORY_NOT_FOUND = 5;
    public static final int MONTHLY_LIMIT_NOT_SET = 6;
}
