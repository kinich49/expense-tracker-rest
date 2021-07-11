package mx.kinich49.expensetracker.validations.conditions.monthlybudgetservice;

import mx.kinich49.expensetracker.models.database.MonthlyBudget;
import mx.kinich49.expensetracker.models.database.MonthlyBudgetCategory;
import mx.kinich49.expensetracker.models.database.MonthlyIncome;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetRequest;
import mx.kinich49.expensetracker.repositories.MonthlyBudgetRepository;
import mx.kinich49.expensetracker.repositories.MonthlyIncomeRepository;
import mx.kinich49.expensetracker.utils.Triad;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateBudgetLimitConditionTest {

    private static final long MONTHLY_BUDGET_ID = 100L;

    @InjectMocks
    UpdateBudgetLimitCondition subject;
    @Mock
    MonthlyBudgetRepository budgetRepository;
    @Mock
    MonthlyIncomeRepository incomeRepository;

    @Test
    void sanityTest() {
        assertNotNull(subject);
    }

    @ParameterizedTest
    @MethodSource("monthlyBudgetsWithinBounds")
    @DisplayName("Should return empty when request is valid")
    void shouldReturnEmpty_whenRequestIsValid(List<MonthlyBudget> monthlyBudgets,
                                              MonthlyBudget monthlyBudget,
                                              MonthlyIncome monthlyIncome,
                                              int requestedBaseLimit) {
        //given
        var request = new MonthlyBudgetRequest(MONTHLY_BUDGET_ID, YearMonth.of(2021, Month.JANUARY),
                YearMonth.of(2021, Month.DECEMBER), "Test budget", requestedBaseLimit);
        var conditionParameter = new BudgetRequestConditionParameterImpl(request);

        when(incomeRepository.findByBeginDate(any()))
                .thenReturn(Optional.of(monthlyIncome));

        when(budgetRepository.findByDate(any()))
                .thenReturn(monthlyBudgets);

        when(budgetRepository.findById(eq(MONTHLY_BUDGET_ID)))
                .thenReturn(Optional.of(monthlyBudget));
        //when
        Optional<String> result = assertDoesNotThrow(() -> subject.assertCondition(conditionParameter));

        //then
        assertTrue(result.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("monthlyBudgetsOutOfBounds")
    @DisplayName("Should return error when request is invalid")
    void shouldReturnError_whenRequestIsInvalid(List<MonthlyBudget> monthlyBudgets,
                                                MonthlyBudget monthlyBudget,
                                                MonthlyIncome monthlyIncome,
                                                int requestedBaseLimit) {
        //given
        var request = new MonthlyBudgetRequest(MONTHLY_BUDGET_ID, YearMonth.of(2021, Month.JANUARY),
                YearMonth.of(2021, Month.DECEMBER), "Test budget", requestedBaseLimit);
        var conditionParameter = new BudgetRequestConditionParameterImpl(request);

        when(incomeRepository.findByBeginDate(any()))
                .thenReturn(Optional.of(monthlyIncome));

        when(budgetRepository.findByDate(any()))
                .thenReturn(monthlyBudgets);

        when(budgetRepository.findById(eq(MONTHLY_BUDGET_ID)))
                .thenReturn(Optional.of(monthlyBudget));

        //when
        Optional<String> result = assertDoesNotThrow(() -> subject.assertCondition(conditionParameter));

        //then
        assertTrue(result.isPresent());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> monthlyBudgetsWithinBounds() {
        var firstPair = oneMonthlyBudget();
        var secondPair = threeMonthlyBudgets();
        var thirdPair = twoMonthlyBudgets();
        var fourthPair = sameMonthlyBudget();
        var fifthPair = sameMonthlyBudgetWithMonthlyBudgetCategory();

        return Stream.of(
                Arguments.of(firstPair.getLeft(), firstPair.getMiddle(), firstPair.getRight(), 1000000),
                Arguments.of(secondPair.getLeft(), secondPair.getMiddle(), secondPair.getRight(), 1000000),
                Arguments.of(thirdPair.getLeft(), thirdPair.getMiddle(), thirdPair.getRight(), 1000000),
                Arguments.of(fourthPair.getLeft(), fourthPair.getMiddle(), fourthPair.getRight(), 900000),
                Arguments.of(fifthPair.getLeft(), fifthPair.getMiddle(), fifthPair.getRight(), 400000)
        );
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> monthlyBudgetsOutOfBounds() {
        var firstPair = oneMonthlyBudget();
        var secondPair = threeMonthlyBudgets();
        var thirdPair = twoMonthlyBudgets();
        var fourthPair = sameMonthlyBudget();
        var fifthPair = sameMonthlyBudgetWithMonthlyBudgetCategory();

        return Stream.of(
                Arguments.of(firstPair.getLeft(), firstPair.getMiddle(), firstPair.getRight(), 1500100),
                Arguments.of(secondPair.getLeft(), secondPair.getMiddle(), secondPair.getRight(), 2000100),
                Arguments.of(thirdPair.getLeft(), thirdPair.getMiddle(), thirdPair.getRight(), 2100000),
                Arguments.of(fourthPair.getLeft(), fourthPair.getMiddle(), fourthPair.getRight(), 1000100),
                Arguments.of(fifthPair.getLeft(), fifthPair.getMiddle(), fifthPair.getRight(), 500100)
        );
    }

    /**
     * Monthly Limit of $20,000
     * <p>
     * One Monthly Budget
     * <p>
     * Unique monthly budget with a total limit of $6,0000
     * - 1 Monthly Budget Category with a limit of $5,000
     * - Base Limit of $1,000
     * - No Monthly Category Budgets
     * - Same ID as budget request
     * <p>
     * Free income allocation is $14,000
     *
     * @return a pair of list of monthly budgets and its corresponding
     * monthly income
     */
    private static Triad<List<MonthlyBudget>, MonthlyBudget, MonthlyIncome> oneMonthlyBudget() {
        var monthlyIncome = new MonthlyIncome();
        monthlyIncome.setUpperIncomeLimit(2000000);
        monthlyIncome.setBeginDate(YearMonth.of(2021, Month.JANUARY));

        var monthlyBudgetA = new MonthlyBudget();
        monthlyBudgetA.setId(MONTHLY_BUDGET_ID);
        monthlyBudgetA.setTitle("Monthly Budget");

        var monthlyBudgetCategory = new MonthlyBudgetCategory();
        monthlyBudgetCategory.setMonthlyLimit(500000);
        monthlyBudgetA.addMonthlyBudgetCategory(monthlyBudgetCategory);
        monthlyBudgetA.setBeginDate(YearMonth.of(2021, Month.JANUARY));
        monthlyBudgetA.setBaseLimit(100000);

        var monthlyBudgets = new ArrayList<MonthlyBudget>(1);
        monthlyBudgets.add(monthlyBudgetA);

        return Triad.of(monthlyBudgets, monthlyBudgetA, monthlyIncome);
    }

    /**
     * Monthly Income limit of $50,000
     * <p>
     * Three monthly budgets
     * <p>
     * First monthly budget has a total limit of $5,000
     * - 1 Monthly Budget Category with limit of 5,000
     * <p>
     * Second monthly budget has a total limit of $20,000
     * - 2 Monthly Budget Categories with a combined limit of $10,000,
     * - Base limit of $10,000
     * - Same ID as monthly budget update request
     * <p>
     * Third monthly budget has a total limit of $15,000
     * - Base limit of $15,000
     * <p>
     * Free income allocation is $10,000
     *
     * @return a pair of list of monthly budgets and its corresponding
     * monthly income
     */
    private static Triad<List<MonthlyBudget>, MonthlyBudget, MonthlyIncome> threeMonthlyBudgets() {
        var monthlyIncome = new MonthlyIncome();
        monthlyIncome.setId(1L);
        monthlyIncome.setUpperIncomeLimit(5000000);
        monthlyIncome.setBeginDate(YearMonth.of(2021, Month.JANUARY));

        var monthlyBudgetA = new MonthlyBudget();
        monthlyBudgetA.setId(1L);
        monthlyBudgetA.setTitle("Monthly Budget A");
        var monthlyBudgetCategoryA1 = new MonthlyBudgetCategory();
        monthlyBudgetCategoryA1.setMonthlyLimit(500000);
        monthlyBudgetA.addMonthlyBudgetCategory(monthlyBudgetCategoryA1);
        monthlyBudgetA.setBeginDate(YearMonth.of(2021, Month.JANUARY));

        var monthlyBudgetB = new MonthlyBudget();
        monthlyBudgetB.setId(MONTHLY_BUDGET_ID);
        monthlyBudgetB.setTitle("Monthly Budget B");
        var monthlyBudgetCategoryB1 = new MonthlyBudgetCategory();
        monthlyBudgetCategoryB1.setMonthlyLimit(500000);
        var monthlyBudgetCategoryB2 = new MonthlyBudgetCategory();
        monthlyBudgetCategoryB2.setMonthlyLimit(500000);

        monthlyBudgetB.addMonthlyBudgetCategory(monthlyBudgetCategoryB1);
        monthlyBudgetB.addMonthlyBudgetCategory(monthlyBudgetCategoryB2);
        monthlyBudgetB.setBeginDate(YearMonth.of(2021, Month.JANUARY));
        monthlyBudgetB.setBaseLimit(1000000);

        var monthlyBudgetC = new MonthlyBudget();
        monthlyBudgetC.setId(3L);
        monthlyBudgetC.setTitle("Monthly Budget A");
        monthlyBudgetB.setBeginDate(YearMonth.of(2021, Month.JANUARY));
        monthlyBudgetC.setBaseLimit(1500000);

        var monthlyBudgets = new ArrayList<MonthlyBudget>(3);
        monthlyBudgets.add(monthlyBudgetA);
        monthlyBudgets.add(monthlyBudgetB);
        monthlyBudgets.add(monthlyBudgetC);

        return Triad.of(monthlyBudgets, monthlyBudgetB, monthlyIncome);
    }

    /**
     * Monthly Limit of $20,000
     * <p>
     * Two Monthly Budgets
     * First monthly budget with a total limit of $10,000
     * - 1 Monthly budget category with a limit of $5,000,
     * - Base limit of $5,000
     * <p>
     * Second monthly budget with a total limit of $9,000
     * - Base limit of $9,000
     * - Same ID as the monthly budget update request
     * <p>
     * Free income allocation is $1,000
     * <p>
     *
     * @return a pair of list of monthly budgets and its corresponding
     * monthly income
     */
    private static Triad<List<MonthlyBudget>, MonthlyBudget, MonthlyIncome> twoMonthlyBudgets() {
        var monthlyIncome = new MonthlyIncome();
        monthlyIncome.setUpperIncomeLimit(2000000);
        monthlyIncome.setBeginDate(YearMonth.of(2021, Month.JANUARY));

        var monthlyBudgetA = new MonthlyBudget();
        monthlyBudgetA.setId(1L);
        monthlyBudgetA.setTitle("Monthly Budget");

        var monthlyBudgetCategory = new MonthlyBudgetCategory();
        monthlyBudgetCategory.setMonthlyLimit(500000);
        monthlyBudgetA.addMonthlyBudgetCategory(monthlyBudgetCategory);
        monthlyBudgetA.setBeginDate(YearMonth.of(2021, Month.JANUARY));
        monthlyBudgetA.setBaseLimit(500000);


        var monthlyBudgetB = new MonthlyBudget();
        monthlyBudgetB.setId(MONTHLY_BUDGET_ID);
        monthlyBudgetB.setTitle("Monthly Budget");
        monthlyBudgetB.setBaseLimit(900000);

        var monthlyBudgets = new ArrayList<MonthlyBudget>(1);
        monthlyBudgets.add(monthlyBudgetA);

        return Triad.of(monthlyBudgets, monthlyBudgetB, monthlyIncome);
    }

    /**
     * Monthly income of $10,000
     * <p>
     * One Monthly Budget with a total limit of $10,000
     * - Base limit of $10,000
     * - Same ID as monthly budget update request
     * <p>
     * Free income allocation is $0
     *
     * @return a pair of list of monthly budgets and its corresponding
     * monthly income
     */
    @SuppressWarnings("unused")
    private static Triad<List<MonthlyBudget>, MonthlyBudget, MonthlyIncome> sameMonthlyBudget() {
        var monthlyIncome = new MonthlyIncome();
        monthlyIncome.setUpperIncomeLimit(1000000);
        monthlyIncome.setBeginDate(YearMonth.of(2021, Month.JANUARY));

        var monthlyBudgetA = new MonthlyBudget();
        monthlyBudgetA.setTitle("Test budget");
        monthlyBudgetA.setBaseLimit(1000000);
        monthlyBudgetA.setId(MONTHLY_BUDGET_ID);
        monthlyBudgetA.setBeginDate(YearMonth.of(2021, Month.JANUARY));
        monthlyBudgetA.setEndDate(YearMonth.of(2021, Month.DECEMBER));

        var monthlyBudgets = new ArrayList<MonthlyBudget>(1);
        monthlyBudgets.add(monthlyBudgetA);

        return Triad.of(monthlyBudgets, monthlyBudgetA, monthlyIncome);
    }

    /**
     * Monthly income of $10,000
     * <p>
     * One Monthly Budget with a total limit of $10,000
     * - Base limit of $5,000
     * - Monthly Budget Category with a limit of $5,000
     * - Same ID as monthly budget update request
     * <p>
     * Free income allocation is $0
     *
     * @return a pair of list of monthly budgets and its corresponding
     * monthly income
     */
    @SuppressWarnings("unused")
    private static Triad<List<MonthlyBudget>, MonthlyBudget, MonthlyIncome> sameMonthlyBudgetWithMonthlyBudgetCategory() {
        var monthlyIncome = new MonthlyIncome();
        monthlyIncome.setUpperIncomeLimit(1000000);
        monthlyIncome.setBeginDate(YearMonth.of(2021, Month.JANUARY));

        var monthlyBudgetA = new MonthlyBudget();
        monthlyBudgetA.setTitle("Test budget");
        monthlyBudgetA.setBaseLimit(500000);
        monthlyBudgetA.setId(MONTHLY_BUDGET_ID);
        monthlyBudgetA.setBeginDate(YearMonth.of(2021, Month.JANUARY));
        monthlyBudgetA.setEndDate(YearMonth.of(2021, Month.DECEMBER));


        var monthlyBudgetCategory = new MonthlyBudgetCategory();
        monthlyBudgetCategory.setMonthlyLimit(500000);
        monthlyBudgetCategory.setId(1L);

        monthlyBudgetA.addMonthlyBudgetCategory(monthlyBudgetCategory);

        var monthlyBudgets = new ArrayList<MonthlyBudget>(1);
        monthlyBudgets.add(monthlyBudgetA);

        return Triad.of(monthlyBudgets, monthlyBudgetA, monthlyIncome);
    }
}
