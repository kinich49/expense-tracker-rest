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
public class BudgetLimitCondition implements Condition<BudgetRequestConditionParameterImpl> {

    private final MonthlyBudgetRepository monthlyBudgetRepository;
    private final MonthlyIncomeRepository monthlyIncomeRepository;

    @Autowired
    public BudgetLimitCondition(MonthlyBudgetRepository monthlyBudgetRepository,
                                MonthlyIncomeRepository monthlyIncomeRepository) {
        this.monthlyBudgetRepository = monthlyBudgetRepository;
        this.monthlyIncomeRepository = monthlyIncomeRepository;
    }

    @Override
    public Optional<String> assertCondition(BudgetRequestConditionParameterImpl param) throws ValidationFlowException {
        MonthlyBudgetRequest request = param.getRequest();

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

    private String buildErrorMessage(YearMonth beginDate, int currentLimit, int budgetRequestLimit)
            throws ValidationFlowException {
        MonthlyIncome monthlyIncome = monthlyIncomeRepository.findByBeginDate(beginDate)
                .orElseThrow(() -> new ValidationFlowException("No Monthly Income is set"));

        return String.format("Your budget request has a base limit of %1$d. The sum of all budget incomes is " +
                        "%2$d and your current income is %3$d",
                budgetRequestLimit, currentLimit, monthlyIncome.getUpperIncomeLimit());
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
