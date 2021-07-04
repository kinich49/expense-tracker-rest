package mx.kinich49.expensetracker.models.web;

import lombok.Data;
import mx.kinich49.expensetracker.helpers.MockHelper;
import mx.kinich49.expensetracker.models.database.Category;
import mx.kinich49.expensetracker.models.database.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ExpenseWebModelTest {

    @DisplayName("Should print sum of all transaction prices")
    @MethodSource("sumOfPricesAndTotalItemsParameters")
    @ParameterizedTest
    public void shouldPrint_sumOfPrices(Holder holder) {
        //given
        Map<Category, List<Transaction>> mapTransaction = new HashMap<>();
        holder.transactions.forEach(transaction -> {
            Category category = transaction.getCategory();
            if (mapTransaction.containsKey(category)) {
                mapTransaction.get(transaction.getCategory()).add(transaction);
            } else {
                List<Transaction> list = new ArrayList<>();
                list.add(transaction);
                mapTransaction.put(category, list);
            }
        });

        //when
        ExpensesWebModel result = ExpensesWebModel.from(mapTransaction);

        //then
        assertNotNull(result);
        int totalExpense = result.getTotalExpense();
        int totalItems = result.getTotalItems();

        assertEquals(holder.totalExpense, totalExpense);
        assertEquals(holder.totalItems, totalItems);

    }

    //TODO use Named.of() method when JUnit 5.8 is released as GA, for now, deal with ugly name tests
    public static Stream<Arguments> sumOfPricesAndTotalItemsParameters() {

        List<Transaction> transactionA = MockHelper.init()
                .with(MockHelper.addMock()
                        .withPersistedStore()
                        .withPersistedCategory()
                        .withPersistedPaymentMethod()
                        .withValidTransaction(150000))
                .with(MockHelper.addMock()
                        .withPersistedCategory()
                        .withNewPaymentMethod()
                        .withNewStore()
                        .withValidTransaction(300000))
                .getTransactions();
        Holder holderA = new Holder(transactionA, 2, 450000);

        List<Transaction> transactionB = MockHelper.init()
                .with(MockHelper.addMock()
                        .withPersistedStore()
                        .withPersistedCategory()
                        .withPersistedPaymentMethod()
                        .withValidTransaction(30000))
                .with(MockHelper.addMock()
                        .withPersistedCategory()
                        .withNewPaymentMethod()
                        .withNewStore()
                        .withValidTransaction(5000))
                .with(MockHelper.addMock()
                        .withPersistedStore()
                        .withPersistedCategory()
                        .withPersistedPaymentMethod()
                        .withValidTransaction(10000))
                .getTransactions();
        Holder holderB = new Holder(transactionB, 3, 45000);

        List<Transaction> transactionC = MockHelper.init()
                .with(MockHelper.addMock()
                        .withPersistedStore()
                        .withPersistedCategory()
                        .withPersistedPaymentMethod()
                        .withValidTransaction(10000))
                .getTransactions();
        Holder holderC = new Holder(transactionC, 1, 10000);

        return Stream.of(
                Arguments.of(holderA),
                Arguments.of(holderB),
                Arguments.of(holderC)
        );
    }

    private static Holder[] getParameterAndExpectedResults() {
        return new Holder[0];
    }

    @Data
    private static class Holder {
        private final List<Transaction> transactions;
        private final int totalItems;
        private final int totalExpense;
    }
}