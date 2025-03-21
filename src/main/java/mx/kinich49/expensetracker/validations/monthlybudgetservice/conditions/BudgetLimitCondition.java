package mx.kinich49.expensetracker.validations.monthlybudgetservice.conditions;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.database.MonthlyBudget;
import mx.kinich49.expensetracker.models.database.MonthlyIncome;
import mx.kinich49.expensetracker.models.web.ErrorWrapper;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetRequest;
import mx.kinich49.expensetracker.repositories.MonthlyBudgetRepository;
import mx.kinich49.expensetracker.repositories.MonthlyIncomeRepository;
import mx.kinich49.expensetracker.validations.Condition;
import mx.kinich49.expensetracker.validations.monthlybudgetservice.MonthlyBudgetServiceErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Component
public class BudgetLimitCondition implements Condition<BudgetRequestConditionParameter> {

    private final MonthlyBudgetRepository monthlyBudgetRepository;
    private final MonthlyIncomeRepository monthlyIncomeRepository;

    @Autowired
    public BudgetLimitCondition(MonthlyBudgetRepository monthlyBudgetRepository,
                                MonthlyIncomeRepository monthlyIncomeRepository) {
        this.monthlyBudgetRepository = monthlyBudgetRepository;
        this.monthlyIncomeRepository = monthlyIncomeRepository;
    }

    /**
     * This condition validates the {@link MonthlyBudgetRequest baseLimit}
     * is withing the limits/range
     *
     * @param param the instance to assert it meets all conditions
     * @return An optional containing an error message if the condition is not met.
     * Empty Otherwise
     * @throws ValidationFlowException if a 'gatekeeper validation' is not met.
     */
    @Override
    public Optional<ErrorWrapper> assertCondition(BudgetRequestConditionParameter param) throws ValidationFlowException {
        var request = param.getRequest();

        //In this test, base limit less than zero is valid
        //because it's being validated as part of the request conditions
        if (request.getBaseLimit() <= 0)
            return Optional.empty();

        int currentLimit = getCurrentLimit(request.getBeginDate());
        boolean isRequestBudgetOffLimit = isRequestOffLimits(currentLimit, request.getBaseLimit(),
                request.getBeginDate());

        if (isRequestBudgetOffLimit)
            return Optional.of(buildErrorMessage(request.getBeginDate(), currentLimit, request.getBaseLimit()));

        return Optional.empty();
    }

    /**
     * Returns the sum of all current budget's limit.
     *
     * @param date The beginDate of the budget request
     * @return the sum of the current limit.
     */
    private int getCurrentLimit(YearMonth date) {
        List<MonthlyBudget> budgets = monthlyBudgetRepository.findByDate(date);

        if (budgets == null || budgets.isEmpty())
            return 0;

        return budgets.stream()
                .mapToInt(MonthlyBudget::getMonthlyLimit)
                .reduce(0, Integer::sum);
    }

    private ErrorWrapper buildErrorMessage(YearMonth beginDate, int currentLimit, int budgetRequestLimit)
            throws ValidationFlowException {
        MonthlyIncome monthlyIncome = monthlyIncomeRepository.findByBeginDate(beginDate)
                .orElseThrow(() -> new ValidationFlowException("No Monthly Income is set"));

        var errorMessage = String.format("Your budget request has a base limit of %1$d. The sum of all budget incomes is " +
                        "%2$d and your current income is %3$d",
                budgetRequestLimit, currentLimit, monthlyIncome.getUpperIncomeLimit());

        return new ErrorWrapper(MonthlyBudgetServiceErrorCodes.BUDGET_REQUEST_OVERCOMES_INCOME, errorMessage);
    }


    /**
     * Returns whether the budget request can be allocated with the current income.
     *
     * @param currentLimit     the sum of current budget's limit
     * @param requestBaseLimit the base limit from request
     * @param beginDate        the budget request beginDate
     * @return true if the sum of current limit + requestBaseLimit is greater than
     * monthly income. False if the sum is less or equal.
     * @throws ValidationFlowException if current Monthly Income could not be found
     */
    private boolean isRequestOffLimits(int currentLimit, int requestBaseLimit,
                                       YearMonth beginDate) throws ValidationFlowException {
        MonthlyIncome monthlyIncome = monthlyIncomeRepository.findByBeginDate(beginDate)
                .orElseThrow(() -> new ValidationFlowException("No Monthly Income is set"));

        return (currentLimit + requestBaseLimit) > monthlyIncome.getUpperIncomeLimit();
    }

}
