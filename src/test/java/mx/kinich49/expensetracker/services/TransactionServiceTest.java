package mx.kinich49.expensetracker.services;

import mx.kinich49.expensetracker.models.database.Category;
import mx.kinich49.expensetracker.models.database.PaymentMethod;
import mx.kinich49.expensetracker.models.database.Store;
import mx.kinich49.expensetracker.models.database.Transaction;
import mx.kinich49.expensetracker.models.web.TransactionWebModel;
import mx.kinich49.expensetracker.repositories.CategoryRepository;
import mx.kinich49.expensetracker.repositories.PaymentMethodRepository;
import mx.kinich49.expensetracker.repositories.StoreRepository;
import mx.kinich49.expensetracker.repositories.TransactionRepository;
import mx.kinich49.expensetracker.services.impl.TransactionServiceImpl;
import mx.kinich49.expensetracker.validations.validators.transactionservice.TransactionServiceValidatorImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @InjectMocks
    TransactionServiceImpl subject;

    @Mock
    TransactionRepository transactionRepository;
    @Mock
    PaymentMethodRepository paymentMethodRepository;
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    StoreRepository storeRepository;
    @Mock
    TransactionServiceValidatorImpl transactionServiceValidator;

    @Test
    public void sanityTest() {
        assertNotNull(subject);
    }

    @Test
    @DisplayName("Should add new Transaction with new dependencies")
    public void should_addTransactionWithNewDependencies() {
        MockHelper mockHelper = MockHelper.init()
                .with(MockHelper.addMock()
                        .withNewCategory()
                        .withNewPaymentMethod()
                        .withNewStore()
                        .withNewPaymentMethod()
                        .withValidTransaction());

        MockHelper.Mock mock = mockHelper.get();

        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(mock.getPostPersistTransaction());

        when(categoryRepository.save(eq(mock.getPrePersistCategory())))
                .thenReturn(mock.getPostPersistCategory());

        when(storeRepository.save(eq(mock.getPrePersistStore())))
                .thenReturn(mock.getPostPersistStore());

        when(paymentMethodRepository.save(eq(mock.getPrePersistPaymentMethod())))
                .thenReturn(mock.getPostPersistPaymentMethod());

        //when
        TransactionWebModel result = assertDoesNotThrow(() -> subject.addTransaction(mock.getTransactionRequest()));

        //then
        verify(categoryRepository, times(1)).save(eq(mock.getPrePersistCategory()));
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(paymentMethodRepository, times(1)).save(eq(mock.getPrePersistPaymentMethod()));
        verify(storeRepository, times(1)).save(eq(mock.getPrePersistStore()));

        verify(categoryRepository, never()).findById(any());
        verify(paymentMethodRepository, never()).findById(any());
        verify(storeRepository, never()).findById(any());

        assertNotNull(result);
        assertEquals(mock.getTransactionWebModel(), result);
    }

    @Test
    @DisplayName("Should add new Transaction with persisted dependencies")
    public void should_addTransactionWithPersistedDependencies() {
        MockHelper mockHelper = MockHelper.init()
                .with(MockHelper.addMock()
                        .withPersistedStore()
                        .withPersistedPaymentMethod()
                        .withPersistedCategory()
                        .withValidTransaction());

        MockHelper.Mock mock = mockHelper.get();

        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(mock.getPostPersistTransaction());

        when(categoryRepository.findById(eq(mock.getCategoryId())))
                .thenReturn(Optional.of(mock.getPostPersistCategory()));

        when(storeRepository.findById(eq(mock.getStoreId())))
                .thenReturn(Optional.of(mock.getPostPersistStore()));

        when(paymentMethodRepository.findById(mock.getPaymentMethodId()))
                .thenReturn(Optional.of(mock.getPostPersistPaymentMethod()));
        //when
        TransactionWebModel result = assertDoesNotThrow(() -> subject.addTransaction(mock.getTransactionRequest()));

        //then
        verify(categoryRepository, never()).save(any(Category.class));
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(paymentMethodRepository, never()).save(any(PaymentMethod.class));
        verify(storeRepository, never()).save(any(Store.class));

        assertNotNull(result);
        assertEquals(mock.getTransactionWebModel(), result);
    }

    @Test
    @DisplayName("Should retrieve transactions")
    public void shouldRetrieve_transactions() {
        MockHelper mockHelper = MockHelper.init(4)
                .with(MockHelper.addMock()
                        .withPersistedStore()
                        .withPersistedPaymentMethod()
                        .withPersistedCategory()
                        .withValidTransaction(LocalDateTime.of(2021, 4, 5, 23, 50)))
                .with(MockHelper.addMock()
                        .withPersistedStore()
                        .withPersistedPaymentMethod()
                        .withPersistedCategory()
                        .withValidTransaction(LocalDateTime.of(2021, 4, 20, 5, 18)))
                .with(MockHelper.addMock()
                        .withPersistedStore()
                        .withPersistedPaymentMethod()
                        .withPersistedCategory()
                        .withValidTransaction(LocalDateTime.of(2021, 4, 21, 13, 23)))
                .with(MockHelper.addMock()
                        .withPersistedStore()
                        .withPersistedPaymentMethod()
                        .withPersistedCategory()
                        .withValidTransaction(LocalDateTime.of(2021, 4, 12, 21, 7)));

        List<Transaction> transactions = mockHelper.getTransactions();

        //when
        LocalDateTime startDate = LocalDateTime.of(2021, 4, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2021, 4, 1, 23, 59, 59);

        when(transactionRepository.findByTransactionDateBetween(eq(startDate), eq(endDate)))
                .thenReturn(transactions);

        List<TransactionWebModel> results = subject.findTransactions(startDate, endDate);

        //then
        assertNotNull(results);
        assertEquals(transactions.size(), results.size());
    }
}
