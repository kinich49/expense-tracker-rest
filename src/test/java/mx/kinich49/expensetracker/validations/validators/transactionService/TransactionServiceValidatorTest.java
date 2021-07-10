package mx.kinich49.expensetracker.validations.validators.transactionService;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.helpers.MockHelper;
import mx.kinich49.expensetracker.models.web.requests.TransactionRequest;
import mx.kinich49.expensetracker.validations.conditions.transactionservice.CategoryRequestCondition;
import mx.kinich49.expensetracker.validations.conditions.transactionservice.PaymentMethodRequestCondition;
import mx.kinich49.expensetracker.validations.conditions.transactionservice.StoreRequestCondition;
import mx.kinich49.expensetracker.validations.conditions.transactionservice.TransactionRequestCondition;
import mx.kinich49.expensetracker.validations.validators.transactionservice.ConditionProviderImpl;
import mx.kinich49.expensetracker.validations.validators.transactionservice.TransactionServiceValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceValidatorTest {

    TransactionServiceValidatorImpl subject;
    @Mock
    CategoryRequestCondition categoryRequestCondition;
    @Mock
    PaymentMethodRequestCondition paymentMethodRequestCondition;
    @Mock
    StoreRequestCondition storeRequestCondition;
    @Mock
    TransactionRequestCondition transactionRequestCondition;

    ConditionProviderImpl conditionProvider;
    @Test
    public void sanityTest() {
        assertNotNull(subject);
    }

    @BeforeEach
    void setup(){
        conditionProvider = Mockito.spy(new ConditionProviderImpl(categoryRequestCondition,
                paymentMethodRequestCondition, storeRequestCondition, transactionRequestCondition));
        subject = new TransactionServiceValidatorImpl(conditionProvider);
    }

    @Test
    @DisplayName("Should complete with success")
    public void shouldComplete_withSuccess() throws ValidationFlowException {
        //given
        when(categoryRequestCondition.assertCondition(any()))
                .thenReturn(Optional.empty());

        when(paymentMethodRequestCondition.assertCondition(any()))
                .thenReturn(Optional.empty());

        when(storeRequestCondition.assertCondition(any()))
                .thenReturn(Optional.empty());

        when(transactionRequestCondition.assertCondition(any()))
                .thenReturn(Optional.empty());

        //when
        TransactionRequest request = MockHelper.init()
                .with(MockHelper.addMock()
                        .withPersistedPaymentMethod()
                        .withPersistedStore()
                        .withPersistedCategory()
                        .withValidTransaction())
                .get().getTransactionRequest();

        TransactionServiceValidatorImpl.Parameter parameter = new TransactionServiceValidatorImpl
                .Parameter(request);

        //then
        assertDoesNotThrow(() -> subject.validate(parameter));
    }

    @Test
    @DisplayName("Should throw exception when Transaction Request is null")
    public void shouldThrow_exception_whenTransactionRequestIsNull() throws ValidationFlowException {
        lenient().when(categoryRequestCondition.assertCondition(any(CategoryRequestCondition.Parameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(paymentMethodRequestCondition.assertCondition(any(PaymentMethodRequestCondition.Parameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(storeRequestCondition.assertCondition(any(StoreRequestCondition.Parameter.class)))
                .thenReturn(Optional.empty());

        TransactionRequestCondition.Parameter conditionParam = new TransactionRequestCondition.Parameter(null);
        when(transactionRequestCondition.assertCondition(eq(conditionParam)))
                .thenThrow(ValidationFlowException.class);

        TransactionServiceValidatorImpl.Parameter validatorParam = new TransactionServiceValidatorImpl
                .Parameter(null);
        //then
        Exception exception = assertThrows(ValidationFlowException.class, () -> subject.validate(validatorParam));

        verify(storeRequestCondition, never()).assertCondition(any(StoreRequestCondition.Parameter.class));
        verify(categoryRequestCondition, never()).assertCondition(any(CategoryRequestCondition.Parameter.class));
        verify(paymentMethodRequestCondition, never()).assertCondition(any(PaymentMethodRequestCondition.Parameter.class));
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Should throw exception when Category Request is Invalid")
    public void shouldThrow_exception_whenCategoryRequestIsInvalid() throws ValidationFlowException {
        //when
        TransactionRequest request = MockHelper.init()
                .with(MockHelper.addMock()
                        .withNullCategory()
                        .withPersistedStore()
                        .withPersistedPaymentMethod()
                        .withValidTransaction())
                .get().getTransactionRequest();

        CategoryRequestCondition.Parameter categoryParam = new CategoryRequestCondition.Parameter(null);
        when(categoryRequestCondition.assertCondition(eq(categoryParam)))
                .thenReturn(Optional.of("Category Error"));

        when(paymentMethodRequestCondition.assertCondition(any(PaymentMethodRequestCondition.Parameter.class)))
                .thenReturn(Optional.empty());

        when(storeRequestCondition.assertCondition(any(StoreRequestCondition.Parameter.class)))
                .thenReturn(Optional.empty());

        when(transactionRequestCondition.assertCondition(any(TransactionRequestCondition.Parameter.class)))
                .thenReturn(Optional.empty());

        TransactionServiceValidatorImpl.Parameter validatorParam = new TransactionServiceValidatorImpl
                .Parameter(request);

        //then
        Exception exception = assertThrows(BusinessException.class, () -> subject.validate(validatorParam));

        verify(storeRequestCondition, times(1))
                .assertCondition(any(StoreRequestCondition.Parameter.class));
        verify(categoryRequestCondition, times(1))
                .assertCondition(eq(categoryParam));
        verify(paymentMethodRequestCondition, times(1))
                .assertCondition(any(PaymentMethodRequestCondition.Parameter.class));
        verify(transactionRequestCondition, times(1))
                .assertCondition(any(TransactionRequestCondition.Parameter.class));

        assertNotNull(exception);
        assertEquals("Category Error", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when Store Request is Invalid")
    public void shouldThrow_exception_whenStoreRequestIsInvalid() throws ValidationFlowException {
        //when
        TransactionRequest request = MockHelper.init()
                .with(MockHelper.addMock()
                        .withPersistedCategory()
                        .withNullStore()
                        .withPersistedPaymentMethod()
                        .withValidTransaction())
                .get().getTransactionRequest();

        when(categoryRequestCondition.assertCondition(any(CategoryRequestCondition.Parameter.class)))
                .thenReturn(Optional.empty());

        when(paymentMethodRequestCondition.assertCondition(any(PaymentMethodRequestCondition.Parameter.class)))
                .thenReturn(Optional.empty());

        StoreRequestCondition.Parameter storeParam = new StoreRequestCondition.Parameter(null);
        when(storeRequestCondition.assertCondition(eq(storeParam)))
                .thenReturn(Optional.of("Store Error"));

        when(transactionRequestCondition.assertCondition(any(TransactionRequestCondition.Parameter.class)))
                .thenReturn(Optional.empty());

        TransactionServiceValidatorImpl.Parameter validatorParam = new TransactionServiceValidatorImpl
                .Parameter(request);

        //then
        Exception exception = assertThrows(BusinessException.class, () -> subject.validate(validatorParam));

        verify(storeRequestCondition, times(1))
                .assertCondition(eq(storeParam));
        verify(categoryRequestCondition, times(1))
                .assertCondition(any(CategoryRequestCondition.Parameter.class));
        verify(paymentMethodRequestCondition, times(1))
                .assertCondition(any(PaymentMethodRequestCondition.Parameter.class));
        verify(transactionRequestCondition, times(1))
                .assertCondition(any(TransactionRequestCondition.Parameter.class));

        assertNotNull(exception);
        assertEquals("Store Error", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when Payment Method Request is Invalid")
    public void shouldThrow_exception_whenPaymentRequestIsInvalid() throws ValidationFlowException {
        //when
        TransactionRequest request = MockHelper.init()
                .with(MockHelper.addMock()
                        .withPersistedCategory()
                        .withPersistedStore()
                        .withNullPaymentMethod()
                        .withValidTransaction())
                .get().getTransactionRequest();

        when(categoryRequestCondition.assertCondition(any(CategoryRequestCondition.Parameter.class)))
                .thenReturn(Optional.empty());

        PaymentMethodRequestCondition.Parameter paymentParam = new PaymentMethodRequestCondition.Parameter(null);
        when(paymentMethodRequestCondition.assertCondition(eq(paymentParam)))
                .thenReturn(Optional.of("Payment Method Error"));

        when(storeRequestCondition.assertCondition(any(StoreRequestCondition.Parameter.class)))
                .thenReturn(Optional.empty());

        when(transactionRequestCondition.assertCondition(any(TransactionRequestCondition.Parameter.class)))
                .thenReturn(Optional.empty());

        TransactionServiceValidatorImpl.Parameter validatorParam = new TransactionServiceValidatorImpl
                .Parameter(request);

        //then
        Exception exception = assertThrows(BusinessException.class, () -> subject.validate(validatorParam));

        verify(storeRequestCondition, times(1))
                .assertCondition(any(StoreRequestCondition.Parameter.class));
        verify(categoryRequestCondition, times(1))
                .assertCondition(any(CategoryRequestCondition.Parameter.class));
        verify(paymentMethodRequestCondition, times(1))
                .assertCondition(eq(paymentParam));
        verify(transactionRequestCondition, times(1))
                .assertCondition(any(TransactionRequestCondition.Parameter.class));

        assertNotNull(exception);
        assertEquals("Payment Method Error", exception.getMessage());
    }
}
