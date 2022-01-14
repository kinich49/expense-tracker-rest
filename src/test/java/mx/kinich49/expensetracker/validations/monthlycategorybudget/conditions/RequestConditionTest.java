package mx.kinich49.expensetracker.validations.monthlycategorybudget.conditions;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
//TODO break in smaller test classes
public class RequestConditionTest {
//
//    @InjectMocks
//    private RequestConditionImpl subject;
//
//    @Mock
//    MonthlyBudgetRepository monthlyBudgetRepository;
//    @Mock
//    CategoryRepository categoryRepository;
//
//
//    @Test
//    @DisplayName("Sanity Test")
//    public void sanityTest() {
//        assertNotNull(subject);
//    }
//
//    @Test
//    @DisplayName("Should return empty when request is valid")
//    public void shouldReturnEmpty_when_requestIsValid() {
//        //given
//        MonthlyBudgetCategoryRequest request = new MonthlyBudgetCategoryRequest();
//        request.setBudgetId(1L);
//        request.setCategoryId(1L);
//        request.setMonthlyLimit(1_000);
//        request.setYearMonth(YearMonth.of(2020, 12));
//
//        RequestConditionImpl.Parameter parameter =
//                new RequestConditionImpl.Parameter(request);
//
//        when(monthlyBudgetRepository.existsById(eq(1L)))
//                .thenReturn(true);
//        when(categoryRepository.existsById(eq(1L)))
//                .thenReturn(true);
//        //when
//        Optional<String> result = subject.assertCondition(parameter);
//
//        //then
//        assertFalse(result.isPresent());
//    }
//
//    @Test
//    @DisplayName("Should return error message when request is null")
//    public void shouldReturnErrorMessage_when_requestIsNull() {
//        //given
//        RequestConditionImpl.Parameter parameter =
//                new RequestConditionImpl.Parameter(null);
//
//        //when
//        Optional<String> result = subject.assertCondition(parameter);
//
//        //then
//        assertTrue(result.isPresent());
//    }
//
//    @Test
//    @DisplayName("Should return error message when budgetID is not valid")
//    public void shouldReturnErrorMessage_when_budgetIDIsNotValid() {
//        //given
//        MonthlyBudgetCategoryRequest request = new MonthlyBudgetCategoryRequest();
//        request.setBudgetId(0);
//        request.setCategoryId(1L);
//        request.setMonthlyLimit(1_000);
//        request.setYearMonth(YearMonth.of(2020, 12));
//
//        RequestConditionImpl.Parameter parameter =
//                new RequestConditionImpl.Parameter(request);
//
//        when(monthlyBudgetRepository.existsById(eq(0L)))
//                .thenReturn(false);
//        when(categoryRepository.existsById(eq(1L)))
//                .thenReturn(true);
//        //when
//        Optional<String> result = subject.assertCondition(parameter);
//
//        //then
//        assertTrue(result.isPresent());
//        assertEquals("Budget with id: 0 not found.", result.get());
//    }
//
//    @Test
//    @DisplayName("Should return error message when categoryID is not valid")
//    public void shouldReturnErrorMessage_when_categoryIDIsNotValid() {
//        //given
//        MonthlyBudgetCategoryRequest request = new MonthlyBudgetCategoryRequest();
//        request.setBudgetId(1L);
//        request.setCategoryId(0L);
//        request.setMonthlyLimit(1_000);
//        request.setYearMonth(YearMonth.of(2020, 12));
//
//        RequestConditionImpl.Parameter parameter =
//                new RequestConditionImpl.Parameter(request);
//
//        when(monthlyBudgetRepository.existsById(eq(1L)))
//                .thenReturn(true);
//        when(categoryRepository.existsById(eq(0L)))
//                .thenReturn(false);
//        //when
//        Optional<String> result = subject.assertCondition(parameter);
//
//        //then
//        assertTrue(result.isPresent());
//        assertEquals("Category with id: 0 not found.", result.get());
//    }
//
//    @Test
//    @DisplayName("Should return error message when categoryID is not valid")
//    public void shouldReturnErrorMessage_when_monthlyLimitIsNotSet() {
//        //given
//        MonthlyBudgetCategoryRequest request = new MonthlyBudgetCategoryRequest();
//        request.setBudgetId(1L);
//        request.setCategoryId(1L);
//        request.setYearMonth(YearMonth.of(2020, 12));
//
//        RequestConditionImpl.Parameter parameter =
//                new RequestConditionImpl.Parameter(request);
//
//        when(monthlyBudgetRepository.existsById(eq(1L)))
//                .thenReturn(true);
//        when(categoryRepository.existsById(eq(1L)))
//                .thenReturn(true);
//        //when
//        Optional<String> result = subject.assertCondition(parameter);
//
//        //then
//        assertTrue(result.isPresent());
//        assertEquals("Monthly limit is not set.", result.get());
//    }
}
