package mx.kinich49.expensetracker.services;

import lombok.Data;
import mx.kinich49.expensetracker.Constants;
import mx.kinich49.expensetracker.models.web.CategoryTransactionWebModel;
import mx.kinich49.expensetracker.models.web.ExpensesWebModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
//This test uses data from test.sql
public class ExpenseServiceIntegrationTest {


    @Autowired
    ExpenseService subject;

    @Test
    public void sanityTest() {
        assertNotNull(subject);
    }

    @Test
    @DisplayName("Should return empty web model")
    public void shouldReturn_emptyWebModel() {
        //given
        LocalDateTime startDate = LocalDateTime.of(1971, Month.JANUARY, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(1971, Month.JANUARY, 31, 23, 59, 59);

        //when
        ExpensesWebModel result = subject.findExpensesBetween(startDate, endDate);

        //then
        assertNotNull(result);
        assertEquals(0, result.getTotalItems());
        assertEquals(0, result.getTotalExpense());
        assertNotNull(result.getCategoryTransactions());

        assertEquals(Constants.TOTAL_CATEGORIES, result.getCategoryTransactions().size());
    }

    /**
     * A test to get Monthly Transactions wrapped in {@link ExpensesWebModel}
     * This tests receives two parameter, one for execute the query, and the second one to
     * validate the results
     *
     * @param parametersHolder    the repository query parameters
     * @param resultHolderWrapper the expected results to validate the test
     */
    @ParameterizedTest
    @DisplayName("Should return categories with limited transactions")
    @MethodSource("categoriesWithLimitedTransactionsSource")
    public void shouldReturn_categoriesWithLimitedTransactions(ParametersHolder parametersHolder,
                                                               ResultHolderListWrapper resultHolderWrapper) {
        //when
        ExpensesWebModel result = subject.findExpensesBetween(parametersHolder.startDate, parametersHolder.endDate);

        //then
        assertNotNull(result);
        List<CategoryTransactionWebModel> categoryTransactions = result.getCategoryTransactions();
        assertNotNull(categoryTransactions);
        assertFalse(categoryTransactions.isEmpty());

        //Validate each CategoryWebModel transaction
        resultHolderWrapper.expectedResultsHolders.forEach(expectedResultsHolder -> {
            CategoryTransactionWebModel categoryTransactionWebModel = categoryTransactions.stream()
                    .filter(categoryTransaction -> categoryTransaction.getCategory().getId() == expectedResultsHolder.categoryId)
                    .findFirst()
                    .orElseThrow();

            assertNotNull(categoryTransactionWebModel.getTransactions());

            assertEquals(expectedResultsHolder.expectedTransactionsQuantity, categoryTransactionWebModel.getTransactionCount());
            assertEquals(expectedResultsHolder.expectedTransactionsQuantity, categoryTransactionWebModel.getTransactions().size());
            assertEquals(expectedResultsHolder.expectedExpense, categoryTransactionWebModel.getExpense());
        });

        assertEquals(resultHolderWrapper.expectedTotalExpenses, result.getTotalExpense());
        assertEquals(resultHolderWrapper.expectedTotalItems, result.getTotalItems());
    }

    //TODO use Named.of() method when JUnit 5.8 is released as GA, for now, deal with ugly name tests
    public static Stream<Arguments> categoriesWithLimitedTransactionsSource() {
        return Stream.of(
                Arguments.of(getJuneParameters(), getJuneExpectedResults()),
                Arguments.of(getMayParameters(), getMayExpectedResults()),
                Arguments.of(getAprilParameters(), getAprilExpectedResults()));
    }

    //Parameters for April 2021
    private static ParametersHolder getAprilParameters() {
        return new ParametersHolder(LocalDateTime.of(2021, Month.APRIL, 1, 0, 0, 0),
                LocalDateTime.of(2021, Month.APRIL, 30, 23, 59, 59));
    }

    //Expected results for April 2021
    private static ResultHolderListWrapper getAprilExpectedResults() {
        ResultHolderListWrapper wrapper = new ResultHolderListWrapper();

        ExpectedResultsHolder takeOutFoodHolder = new ExpectedResultsHolder(Constants.TAKE_OUT_FOOD_CATEGORY_ID,
                50_000, 2);
        ExpectedResultsHolder groceriesHolder = new ExpectedResultsHolder(Constants.GROCERIES_CATEGORY_ID,
                450_000, 3);
        ExpectedResultsHolder gasHolder = new ExpectedResultsHolder(Constants.GAS_CATEGORY_ID,
                75_000, 1);

        wrapper.addResultHolder(takeOutFoodHolder);
        wrapper.addResultHolder(groceriesHolder);
        wrapper.addResultHolder(gasHolder);

        wrapper.setExpectedTotalExpenses(575_000);
        wrapper.setExpectedTotalItems(6);

        return wrapper;
    }


    //Parameters for May 2021
    private static ParametersHolder getMayParameters() {
        return new ParametersHolder(LocalDateTime.of(2021, Month.MAY, 1, 0, 0, 0),
                LocalDateTime.of(2021, Month.MAY, 31, 23, 59, 59));
    }

    //Expected Results for May 2021
    private static ResultHolderListWrapper getMayExpectedResults() {
        ExpectedResultsHolder groceriesHolder = new ExpectedResultsHolder(Constants.GROCERIES_CATEGORY_ID
                , 240000, 2);
        return new ResultHolderListWrapper(groceriesHolder, 240000, 2);
    }

    //Parameters For June 2021
    private static ParametersHolder getJuneParameters() {
        return new ParametersHolder(LocalDateTime.of(2021, Month.JUNE, 1, 0, 0, 0),
                LocalDateTime.of(2021, Month.JUNE, 30, 23, 59, 59));
    }

    //ExpectedResults for June 2021
    private static ResultHolderListWrapper getJuneExpectedResults() {
        ExpectedResultsHolder groceriesHolder = new ExpectedResultsHolder(Constants.GROCERIES_CATEGORY_ID,
                150000, 1);
        return new ResultHolderListWrapper(groceriesHolder, 150000, 1);
    }

    @Data
    public static class ParametersHolder {
        private final LocalDateTime startDate;
        private final LocalDateTime endDate;
    }

    @Data
    public static class ExpectedResultsHolder {
        private final long categoryId;
        private final int expectedExpense;
        private final int expectedTransactionsQuantity;
    }

    @Data
    public static class ResultHolderListWrapper {
        private final List<ExpectedResultsHolder> expectedResultsHolders;
        private int expectedTotalExpenses;
        private int expectedTotalItems;

        public ResultHolderListWrapper(ExpectedResultsHolder expectedResultsHolder,
                                       int expectedTotalExpenses, int expectedTotalItems) {
            expectedResultsHolders = new ArrayList<>(1);
            expectedResultsHolders.add(expectedResultsHolder);
            this.expectedTotalExpenses = expectedTotalExpenses;
            this.expectedTotalItems = expectedTotalItems;
        }

        public ResultHolderListWrapper(int expectedTotalExpenses, int expectedTotalItems) {
            expectedResultsHolders = new ArrayList<>();
            this.expectedTotalExpenses = expectedTotalExpenses;
            this.expectedTotalItems = expectedTotalItems;
        }

        public ResultHolderListWrapper() {
            expectedResultsHolders = new ArrayList<>();
        }

        public void addResultHolder(ExpectedResultsHolder holder) {
            expectedResultsHolders.add(holder);
        }
    }
}
