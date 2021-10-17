package mx.kinich49.expensetracker.validations.transactionservice.conditions;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
//TODO break in smaller test classes
public class TransactionRequestConditionTest {
//
//    @InjectMocks
//    TransactionRequestCondition subject;
//
//    @Test
//    void sanityTest() {
//        assertNotNull(subject);
//    }
//
//    @Test
//    @DisplayName("Should return empty when request has no ID")
//    public void shouldReturn_empty_whenRequestIsNew() {
//        //given
//        MockHelper mockHelper = MockHelper.init()
//                .with(MockHelper.addMock()
//                        .withNewStore()
//                        .withNewPaymentMethod()
//                        .withNewCategory()
//                        .withValidTransaction());
//
//        TransactionRequestCondition.Parameter parameter = new TransactionRequestCondition
//                .Parameter(mockHelper.get().getTransactionRequest());
//
//        //when
//        Optional<String> result = assertDoesNotThrow(() -> subject.assertCondition(parameter));
//
//        //then
//        assertFalse(result.isPresent());
//    }
//
//    @Test
//    @DisplayName("Should throw exception when request is null")
//    public void shouldThrow_exception_whenRequestIsNull() {
//        TransactionRequestCondition.Parameter parameter = new TransactionRequestCondition
//                .Parameter(null);
//        //when
//        Exception exception = assertThrows(ValidationFlowException.class,
//                () -> subject.assertCondition(parameter));
//
//        //then
//        assertNotNull(exception);
//    }
//
//    @Test
//    @DisplayName("Should return error when request is empty")
//    public void shouldReturn_error_whenRequestIsEmpty() {
//        //given
//        MockHelper mockHelper = MockHelper.init()
//                .with(MockHelper.addMock()
//                        .withEmptyTransaction());
//
//        TransactionRequestCondition.Parameter parameter = new TransactionRequestCondition
//                .Parameter(mockHelper.get().getTransactionRequest());
//
//        //when
//        Optional<String> result = assertDoesNotThrow(() -> subject.assertCondition(parameter));
//
//        //then
//        assertTrue(result.isPresent());
//    }
}
