package mx.kinich49.expensetracker.validations.monthlybudgetservice.conditions;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.database.MonthlyBudget;
import mx.kinich49.expensetracker.models.database.MonthlyIncome;
import mx.kinich49.expensetracker.models.internal.ErrorWrapper;
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
public class UpdateBudgetLimitCondition implements Condition<BudgetRequestConditionParameter> {

    private final MonthlyBudgetRepository monthlyBudgetRepository;
    private final MonthlyIncomeRepository monthlyIncomeRepository;

    @Autowired
    public UpdateBudgetLimitCondition(MonthlyBudgetRepository monthlyBudgetRepository,
                                      MonthlyIncomeRepository monthlyIncomeRepository) {
        this.monthlyBudgetRepository = monthlyBudgetRepository;
        this.monthlyIncomeRepository = monthlyIncomeRepository;
    }

    /**
     * This condition validates
     *
     * @param param the instance to assert it meets all conditions
     * @return An optional containing an error message if the condition is not met.
     * Empty Otherwise
     * @throws ValidationFlowException if a 'gatekeeper validation' is not met.
     */
    @Override
    public Optional<ErrorWrapper> assertCondition(BudgetRequestConditionParameter param) throws ValidationFlowException {
        var request = param.getRequest();

        int upperIncomeLimit = monthlyIncomeRepository.findByBeginDate(param.getRequest().getBeginDate())
                .map(MonthlyIncome::getUpperIncomeLimit)
                .orElseThrow(() -> new ValidationFlowException("No Monthly Income is set"));

        //TODO replace with custom SpringData method
        int budgetBaseLimit = monthlyBudgetRepository.findById(request.getId())
                .map(MonthlyBudget::getBaseLimit)
                .orElseThrow(() -> new ValidationFlowException("No budget found"));

        if (request.getBaseLimit() <= 0) {
            var errorMessage = "base limit can't be less than 0";
            return Optional.of(new ErrorWrapper(MonthlyBudgetServiceErrorCodes.BASE_LIMIT_LESS_THAN_ZERO, errorMessage));
        }

        int currentLimit = getCurrentLimit(request.getBeginDate(), budgetBaseLimit);
        boolean isRequestBudgetOffLimit = isRequestOffLimits(currentLimit, request.getBaseLimit(), upperIncomeLimit);

        if (isRequestBudgetOffLimit) {
            var errorMessage = buildErrorMessage(currentLimit, request.getBaseLimit(), upperIncomeLimit);
            return Optional.of(new ErrorWrapper(MonthlyBudgetServiceErrorCodes.BUDGET_REQUEST_OVERCOMES_INCOME, errorMessage));
        }

        return Optional.empty();
    }

    /**
     * Returns the sum of all current budget's limit,
     * minus the budget base limit that will be updated.
     *
     * @param date                   The beginDate of the budget request
     * @param currentBudgetBaseLimit the budget-to-update base limit
     * @return the sum of the current limit.
     */
    private int getCurrentLimit(YearMonth date, int currentBudgetBaseLimit) {
        List<MonthlyBudget> budgets = monthlyBudgetRepository.findByDate(date);

        if (budgets == null || budgets.isEmpty())
            return 0;

        return budgets.stream()
                .mapToInt(MonthlyBudget::getMonthlyLimit)
                .reduce(0, Integer::sum) - currentBudgetBaseLimit;
    }

    /**
     * Returns whether the budget request can be allocated with the current income.
     *
     * @param currentLimit     the sum of current budget's limit
     * @param requestBaseLimit the base limit from request
     * @param upperIncomeLimit the current Monthly Income's upperIncomeLimit
     * @return true if the sum of current limit + requestBaseLimit is greater than
     * monthly income. False if the sum is less or equal.
     */
    private boolean isRequestOffLimits(int currentLimit, int requestBaseLimit, int upperIncomeLimit) {
        return (currentLimit + requestBaseLimit) > upperIncomeLimit;
    }

    private String buildErrorMessage(int currentLimit, int budgetRequestLimit, int upperIncomeLimit) {
        return String.format("Your budget request has a base limit of %1$d. The sum of all budget incomes is " +
                        "%2$d and your current income is %3$d",
                budgetRequestLimit, currentLimit, upperIncomeLimit);
    }
}
