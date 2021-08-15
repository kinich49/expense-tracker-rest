package mx.kinich49.expensetracker.validations.conditions.monthlybudgetservice;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.database.MonthlyBudget;
import mx.kinich49.expensetracker.models.database.MonthlyIncome;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetRequest;
import mx.kinich49.expensetracker.repositories.MonthlyBudgetRepository;
import mx.kinich49.expensetracker.repositories.MonthlyIncomeRepository;
import mx.kinich49.expensetracker.validations.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Component
public class UpdateBudgetLimitCondition implements Condition<BudgetRequestConditionParameterImpl> {

    private final MonthlyBudgetRepository monthlyBudgetRepository;
    private final MonthlyIncomeRepository monthlyIncomeRepository;

    @Autowired
    public UpdateBudgetLimitCondition(MonthlyBudgetRepository monthlyBudgetRepository,
                                      MonthlyIncomeRepository monthlyIncomeRepository) {
        this.monthlyBudgetRepository = monthlyBudgetRepository;
        this.monthlyIncomeRepository = monthlyIncomeRepository;
    }

    @Override
    public Optional<String> assertCondition(BudgetRequestConditionParameterImpl param) throws ValidationFlowException {
        MonthlyBudgetRequest request = param.getRequest();

        int upperIncomeLimit = monthlyIncomeRepository.findByBeginDate(param.getRequest().getBeginDate())
                .map(MonthlyIncome::getUpperIncomeLimit)
                .orElseThrow(() -> new ValidationFlowException("No Monthly Income is set"));

        //TODO replace with custom SpringData method
        int budgetBaseLimit = monthlyBudgetRepository.findById(request.getId())
                .map(MonthlyBudget::getBaseLimit)
                .orElseThrow(() -> new ValidationFlowException("No budget found"));

        //In this test, base limit less than zero is valid
        //because it's being validated as part of the request conditions
        if (request.getBaseLimit() <= 0)
            return Optional.empty();

        int currentLimit = getCurrentLimit(request.getBeginDate(), budgetBaseLimit);
        boolean isRequestBudgetOffLimit = isRequestOffLimits(currentLimit, request.getBaseLimit(), upperIncomeLimit);

        if (isRequestBudgetOffLimit)
            return Optional.of(buildErrorMessage(currentLimit, request.getBaseLimit(), upperIncomeLimit));

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
