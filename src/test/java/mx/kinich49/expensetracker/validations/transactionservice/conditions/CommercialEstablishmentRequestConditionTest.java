package mx.kinich49.expensetracker.validations.transactionservice.conditions;

import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.helpers.MockHelper;
import mx.kinich49.expensetracker.validations.transactionservice.conditions.StoreRequestCondition;
import mx.kinich49.expensetracker.validations.transactionservice.conditions.TransactionRequestParameter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
//TODO test ValidationFlowException
public class CommercialEstablishmentRequestConditionTest {

    @InjectMocks
    StoreRequestCondition subject;

    @Test
    void sanityTest() {
        assertNotNull(subject);
    }

    @Test
    @DisplayName("Should return empty when request has no ID")
    public void shouldReturn_empty_whenRequestIsNew() {
        //given
        MockHelper mockHelper = MockHelper.init()
                .with(MockHelper.addMock()
                        .withNewStore()
                        .withPersistedPaymentMethod()
                        .withPersistedCategory()
                        .withValidTransaction());

        var parameter = new TransactionRequestParameter(mockHelper.get().getTransactionRequest());

        //when
        var optResult = subject.assertCondition(parameter);

        //then
        assertFalse(optResult.isPresent());
    }

    @Test
    @DisplayName("Should return empty when request has ID and name")
    public void shouldReturn_empty_whenRequestIsNotNew() {
        //given
        MockHelper mockHelper = MockHelper.init()
                .with(MockHelper.addMock()
                        .withPersistedStore()
                        .withPersistedCategory()
                        .withPersistedPaymentMethod()
                        .withValidTransaction());

        var parameter = new TransactionRequestParameter(mockHelper.get().getTransactionRequest());

        //when
        var optResult = subject.assertCondition(parameter);

        //then
        assertFalse(optResult.isPresent());
    }

    @Test
    @DisplayName("Should return empty when store request is null")
    public void shouldReturn_error_whenStoreRequestIsNull() {
        //given
        MockHelper mockHelper = MockHelper.init()
                .with(MockHelper.addMock()
                        .withNullStore()
                        .withPersistedCategory()
                        .withPersistedPaymentMethod()
                        .withValidTransaction());

        var parameter = new TransactionRequestParameter(mockHelper.get().getTransactionRequest());

        //when
        var optResult = subject.assertCondition(parameter);

        //then
        assertFalse(optResult.isPresent());
    }

    @Test
    @DisplayName("Should return error when request is empty")
    public void shouldReturn_error_whenRequestIsEmpty() throws ValidationFlowException {
        //given
        MockHelper mockHelper = MockHelper.init()
                .with(MockHelper.addMock()
                        .withEmptyStore());

        var parameter = new TransactionRequestParameter(mockHelper.get().getTransactionRequest());

        //when
        var optResult = subject.assertCondition(parameter);

        //then
        assertTrue(optResult.isPresent());
    }
}
