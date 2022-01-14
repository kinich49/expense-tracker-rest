package mx.kinich49.expensetracker.validations.monthlybudgetservice.conditions;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
//TODO break in smaller test classes
public class BudgetRequestTest {
//
//    @InjectMocks
//    BudgetRequestCondition subject;
//
//    @Test
//    public void sanityTest() {
//        assertNotNull(subject);
//    }
//
//    @Test
//    @DisplayName("Should return empty when request is valid")
//    public void shouldReturnEmpty_whenRequestIsValid() {
//        //given
//        MonthlyBudgetRequest request = new MonthlyBudgetRequest(null,
//                YearMonth.of(2021, Month.JANUARY), YearMonth.of(2021, Month.DECEMBER),
//                "Test title", 100000 );
//
//        //when
//        Optional<String> result = assertDoesNotThrow(() ->
//                subject.assertCondition(new BudgetRequestConditionParameterImpl(request)));
//
//        //then
//        assertFalse(result.isPresent());
//    }
//
//    @Test
//    @DisplayName("Should throw exception when request is null")
//    public void shouldThrowException_whenRequestIsNull() {
//        //when
//        ValidationFlowException exception = assertThrows(ValidationFlowException.class, () -> {
//            subject.assertCondition(new BudgetRequestConditionParameterImpl(null));
//        });
//
//        assertNotNull(exception);
//    }
//
//
//    @Test
//    @DisplayName("Should return error when begin date is not set")
//    public void shouldReturnError_whenBeginDateIsNotSet() {
//        //given
//        MonthlyBudgetRequest request = new MonthlyBudgetRequest(null,
//                null, YearMonth.of(2021, Month.DECEMBER),
//                "Test title", 100000 );
//
//        //when
//        Optional<String> result = assertDoesNotThrow(() ->
//                subject.assertCondition(new BudgetRequestConditionParameterImpl(request)));
//
//        //then
//        assertTrue(result.isPresent());
//    }
//
//    @Test
//    @DisplayName("Should return error when baseLimit is less than zero")
//    public void shouldReturnError_whenBaseLimitIsLessThanZero() {
//        //given
//        MonthlyBudgetRequest request = new MonthlyBudgetRequest(null,
//                YearMonth.of(2021, Month.JANUARY), YearMonth.of(2021, Month.DECEMBER),
//                "Test title", -1 );
//
//        //when
//        Optional<String> result = assertDoesNotThrow(() ->
//                subject.assertCondition(new BudgetRequestConditionParameterImpl(request)));
//
//        //then
//        assertTrue(result.isPresent());
//    }
}
