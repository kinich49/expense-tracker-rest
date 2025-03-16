package mx.kinich49.expensetracker.validations.transactionservice.validators;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.helpers.MockHelper;
import mx.kinich49.expensetracker.models.web.ErrorWrapper;
import mx.kinich49.expensetracker.models.web.requests.TransactionRequest;
import mx.kinich49.expensetracker.validations.transactionservice.TransactionServiceErrorCodes;
import mx.kinich49.expensetracker.validations.transactionservice.conditions.*;
import mx.kinich49.expensetracker.validations.transactionservice.providers.TransactionServiceConditionProvider;
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

    TransactionServiceValidator subject;
    TransactionServiceConditionProvider conditionProvider;

    @Mock
    CategoryRequestCondition categoryCondition;
    @Mock
    PaymentMethodRequestCondition paymentMethodCondition;
    @Mock
    StoreRequestCondition storeCondition;
    @Mock
    AmountCondition amountCondition;
    @Mock
    TransactionDateCondition transactionDateCondition;
    @Mock
    TransactionTitleCondition transactionTitleCondition;

    @Test
    public void sanityTest() {
        assertNotNull(subject);
    }

    @BeforeEach
    void setup() {
        conditionProvider = Mockito.spy(new TransactionServiceConditionProvider(
                categoryCondition, paymentMethodCondition, storeCondition,
                amountCondition, transactionDateCondition, transactionTitleCondition));
        subject = new TransactionServiceValidator(conditionProvider);
    }

    @Test
    @DisplayName("Should complete when conditions are met")
    public void shouldComplete_whenConditionsAreMet() throws ValidationFlowException {
        //given
        when(categoryCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        when(paymentMethodCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        when(storeCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        when(amountCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        when(transactionDateCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        when(transactionTitleCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        //when
        TransactionRequest request = MockHelper.init()
                .with(MockHelper.addMock()
                        .withPersistedPaymentMethod()
                        .withPersistedStore()
                        .withPersistedCategory()
                        .withValidTransaction())
                .get().getTransactionRequest();

        TransactionServiceValidator.Parameter parameter = new TransactionServiceValidator
                .Parameter(request);

        //then
        assertDoesNotThrow(() -> subject.validate(parameter));
    }

    @Test
    @DisplayName("Should throw exception when CategoryCondition fails")
    public void shouldThrowException_whenCategoryConditionFails() throws ValidationFlowException {
        //given
        var errorWrapper = new ErrorWrapper(TransactionServiceErrorCodes.TRANSACTION_NO_TITLE,
                "Something went wrong.");

        lenient().when(categoryCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.of(errorWrapper));

        lenient().when(paymentMethodCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(storeCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(amountCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(transactionDateCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(transactionTitleCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        //when
        TransactionRequest request = MockHelper.init()
                .with(MockHelper.addMock()
                        .withPersistedPaymentMethod()
                        .withPersistedStore()
                        .withPersistedCategory()
                        .withValidTransaction())
                .get().getTransactionRequest();

        TransactionServiceValidator.Parameter parameter = new TransactionServiceValidator
                .Parameter(request);

        //then
        var result = assertThrows(BusinessException.class, () -> subject.validate(parameter));

        assertNotNull(result);
        assertEquals(errorWrapper.getErrorMessage(), result.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when PaymentMethodCondition fails")
    public void shouldThrowException_whenPaymentMethodConditionFails() throws ValidationFlowException {
        //given
        var errorWrapper = new ErrorWrapper(TransactionServiceErrorCodes.TRANSACTION_NO_TITLE,
                "Something went wrong.");

        lenient().when(categoryCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(paymentMethodCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.of(errorWrapper));

        lenient().when(storeCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(amountCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(transactionDateCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(transactionTitleCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        //when
        TransactionRequest request = MockHelper.init()
                .with(MockHelper.addMock()
                        .withPersistedPaymentMethod()
                        .withPersistedStore()
                        .withPersistedCategory()
                        .withValidTransaction())
                .get().getTransactionRequest();

        TransactionServiceValidator.Parameter parameter = new TransactionServiceValidator
                .Parameter(request);

        //then
        var result = assertThrows(BusinessException.class, () -> subject.validate(parameter));

        assertNotNull(result);
        assertEquals(errorWrapper.getErrorMessage(), result.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when StoreCondition fails")
    public void shouldThrowException_whenStoreConditionFails() throws ValidationFlowException {
        //given
        var errorWrapper = new ErrorWrapper(TransactionServiceErrorCodes.TRANSACTION_NO_TITLE,
                "Something went wrong.");

        lenient().when(categoryCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(paymentMethodCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(storeCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.of(errorWrapper));

        lenient().when(amountCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(transactionDateCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(transactionTitleCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        //when
        TransactionRequest request = MockHelper.init()
                .with(MockHelper.addMock()
                        .withPersistedPaymentMethod()
                        .withPersistedStore()
                        .withPersistedCategory()
                        .withValidTransaction())
                .get().getTransactionRequest();

        TransactionServiceValidator.Parameter parameter = new TransactionServiceValidator
                .Parameter(request);

        //then
        var result = assertThrows(BusinessException.class, () -> subject.validate(parameter));

        assertNotNull(result);
        assertEquals(errorWrapper.getErrorMessage(), result.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when AmountCondition fails")
    public void shouldThrowException_whenAmountConditionFails() throws ValidationFlowException {
        //given
        var errorWrapper = new ErrorWrapper(TransactionServiceErrorCodes.TRANSACTION_NO_TITLE,
                "Something went wrong.");

        lenient().when(categoryCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(paymentMethodCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(storeCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(amountCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.of(errorWrapper));

        lenient().when(transactionDateCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(transactionTitleCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        //when
        TransactionRequest request = MockHelper.init()
                .with(MockHelper.addMock()
                        .withPersistedPaymentMethod()
                        .withPersistedStore()
                        .withPersistedCategory()
                        .withValidTransaction())
                .get().getTransactionRequest();

        TransactionServiceValidator.Parameter parameter = new TransactionServiceValidator
                .Parameter(request);

        //then
        var result = assertThrows(BusinessException.class, () -> subject.validate(parameter));

        assertNotNull(result);
        assertEquals(errorWrapper.getErrorMessage(), result.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when TransactionDateCondition fails")
    public void shouldThrowException_whenTransactionDateConditionFails() throws ValidationFlowException {
        //given
        var errorWrapper = new ErrorWrapper(TransactionServiceErrorCodes.TRANSACTION_NO_TITLE,
                "Something went wrong.");

        lenient().when(categoryCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(paymentMethodCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(storeCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(amountCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(transactionDateCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.of(errorWrapper));

        lenient().when(transactionTitleCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        //when
        TransactionRequest request = MockHelper.init()
                .with(MockHelper.addMock()
                        .withPersistedPaymentMethod()
                        .withPersistedStore()
                        .withPersistedCategory()
                        .withValidTransaction())
                .get().getTransactionRequest();

        TransactionServiceValidator.Parameter parameter = new TransactionServiceValidator
                .Parameter(request);

        //then
        var result = assertThrows(BusinessException.class, () -> subject.validate(parameter));

        assertNotNull(result);
        assertEquals(errorWrapper.getErrorMessage(), result.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when TransactionTitle fails")
    public void shouldThrowException_whenTransactionTitleConditionFails() throws ValidationFlowException {
        //given
        var errorWrapper = new ErrorWrapper(TransactionServiceErrorCodes.TRANSACTION_NO_TITLE,
                "Something went wrong.");

        lenient().when(categoryCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(paymentMethodCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(storeCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(amountCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(transactionDateCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(transactionTitleCondition
                .assertCondition(any(TransactionRequestParameter.class)))
                .thenReturn(Optional.of(errorWrapper));

        //when
        TransactionRequest request = MockHelper.init()
                .with(MockHelper.addMock()
                        .withPersistedPaymentMethod()
                        .withPersistedStore()
                        .withPersistedCategory()
                        .withValidTransaction())
                .get().getTransactionRequest();

        TransactionServiceValidator.Parameter parameter = new TransactionServiceValidator
                .Parameter(request);

        //then
        var result = assertThrows(BusinessException.class, () -> subject.validate(parameter));

        assertNotNull(result);
        assertEquals(errorWrapper.getErrorMessage(), result.getMessage());
    }
}
