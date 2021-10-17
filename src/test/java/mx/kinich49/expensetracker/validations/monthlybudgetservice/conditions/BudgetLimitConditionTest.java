package mx.kinich49.expensetracker.validations.monthlybudgetservice.conditions;

import mx.kinich49.expensetracker.models.database.MonthlyBudget;
import mx.kinich49.expensetracker.models.database.MonthlyIncome;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetRequest;
import mx.kinich49.expensetracker.repositories.MonthlyBudgetRepository;
import mx.kinich49.expensetracker.repositories.MonthlyIncomeRepository;
import mx.kinich49.expensetracker.validations.monthlybudgetservice.MonthlyBudgetServiceErrorCodes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.Month.APRIL;
import static java.time.Month.DECEMBER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BudgetLimitConditionTest {

    @InjectMocks
    BudgetLimitCondition subject;
    @Mock
    MonthlyIncomeRepository monthlyIncomeRepository;
    @Mock
    MonthlyBudgetRepository budgetRepository;

    @Test
    public void sanityTest() {
        assertNotNull(subject);
    }

    @Test
    @DisplayName("Should return empty when budget limit is Zero")
    public void shouldReturnEmpty_whenBudgetLimitIsZero() {
        //given
        MonthlyBudgetRequest request = new MonthlyBudgetRequest(1L,
                YearMonth.of(2021, APRIL), YearMonth.of(2021, DECEMBER),
                "Test title", 0);

        //when
        var optResult = assertDoesNotThrow(() ->
                subject.assertCondition(new BudgetRequestConditionParameter(request)));

        //then
        assertFalse(optResult.isPresent());
    }

    @Test
    @DisplayName("Should return empty when end date is not set")
    public void shouldReturnEmpty_whenEndDateIsNotSet() {
        //given
        MonthlyBudgetRequest request = new MonthlyBudgetRequest(1L,
                YearMonth.of(2021, APRIL), null,
                "Test title", 100000);

        MonthlyIncome monthlyIncome = new MonthlyIncome();
        monthlyIncome.setUpperIncomeLimit(300000);;

        when(monthlyIncomeRepository.findByBeginDate(any()))
                .thenReturn(Optional.of(monthlyIncome));

        //when
        var optResult = assertDoesNotThrow(() ->
                subject.assertCondition(new BudgetRequestConditionParameter(request)));

        //then
        assertFalse(optResult.isPresent());
    }

    @Test
    @DisplayName("Should return empty when end date is after begin date")
    public void shouldReturnEmpty_whenEndDateIsAfterBeginDate() {
        //given
        MonthlyBudgetRequest request = new MonthlyBudgetRequest(1L,
                YearMonth.of(2021, APRIL), YearMonth.of(2021, DECEMBER),
                "Test title", 100000);

        MonthlyIncome monthlyIncome = new MonthlyIncome();
        monthlyIncome.setUpperIncomeLimit(300000);;

        when(monthlyIncomeRepository.findByBeginDate(any()))
                .thenReturn(Optional.of(monthlyIncome));
        //when
        var optResult = assertDoesNotThrow(() ->
                subject.assertCondition(new BudgetRequestConditionParameter(request)));

        //then
        assertFalse(optResult.isPresent());
    }

    @Test
    @DisplayName("Should return empty when end date is equal as begin date")
    public void shouldReturnEmpty_whenEndDateIsEqualAsBeginDate() {
        //given
        MonthlyBudgetRequest request = new MonthlyBudgetRequest(1L,
                YearMonth.of(2021, APRIL), YearMonth.of(2021, APRIL),
                "Test title", 100000);

        MonthlyIncome monthlyIncome = new MonthlyIncome();
        monthlyIncome.setUpperIncomeLimit(300000);;

        when(monthlyIncomeRepository.findByBeginDate(any()))
                .thenReturn(Optional.of(monthlyIncome));

        //when
       var optResult = assertDoesNotThrow(() ->
                subject.assertCondition(new BudgetRequestConditionParameter(request)));

        //then
        assertFalse(optResult.isPresent());
    }

    @Test
    @DisplayName("Should return empty when budget limit is less than Zero")
    public void shouldReturnEmpty_whenBudgetLimitIsLessThanZero() {
        //given
        MonthlyBudgetRequest request = new MonthlyBudgetRequest(1L,
                YearMonth.of(2021, APRIL), YearMonth.of(2021, DECEMBER),
                "Test title", -1);
        //when
       var optResult = assertDoesNotThrow(() ->
                subject.assertCondition(new BudgetRequestConditionParameter(request)));

        //then
        assertFalse(optResult.isPresent());
    }

    @Test
    @DisplayName("Should return error when budget limit is off limits")
    public void shouldReturnError_whenBudgetLimitIsOffLimits() {
        //given
        YearMonth beginDate = YearMonth.of(2021, APRIL);
        MonthlyBudgetRequest request = new MonthlyBudgetRequest(1L,
                beginDate, YearMonth.of(2021, DECEMBER),
                "Test title", 100);

        MonthlyIncome monthlyIncome = new MonthlyIncome();
        monthlyIncome.setId(1L);
        monthlyIncome.setUpperIncomeLimit(100000);

        when(monthlyIncomeRepository.findByBeginDate(eq(beginDate)))
                .thenReturn(Optional.of(monthlyIncome));

        MonthlyBudget monthlyBudget = new MonthlyBudget();
        monthlyBudget.setBaseLimit(100000);
        List<MonthlyBudget> monthlyBudgets = new ArrayList<>();
        monthlyBudgets.add(monthlyBudget);

        when(budgetRepository.findByDate(eq(beginDate)))
                .thenReturn(monthlyBudgets);

        //when
        var optResult = assertDoesNotThrow(() ->
                subject.assertCondition(new BudgetRequestConditionParameter(request)));

        //then
        assertTrue(optResult.isPresent());
        var result = optResult.get();
        assertEquals(MonthlyBudgetServiceErrorCodes.BUDGET_REQUEST_OVERCOMES_INCOME, result.getErrorCode());
    }

    @DisplayName("Should return empty when budget is on limits")
    public void shouldReturnEmpty_whenBudgetLimitIsOnLimits() {
        //given
        YearMonth beginDate = YearMonth.of(2021, APRIL);
        MonthlyBudgetRequest request = new MonthlyBudgetRequest(1L,
                beginDate, YearMonth.of(2021, DECEMBER),
                "Test title", 10000);
        MonthlyIncome monthlyIncome = new MonthlyIncome();
        monthlyIncome.setId(1L);
        monthlyIncome.setUpperIncomeLimit(100000);

        when(monthlyIncomeRepository.findByBeginDate(eq(beginDate)))
                .thenReturn(Optional.of(monthlyIncome));

        MonthlyBudget monthlyBudget = new MonthlyBudget();
        monthlyBudget.setBaseLimit(900000);
        List<MonthlyBudget> monthlyBudgets = new ArrayList<>();
        monthlyBudgets.add(monthlyBudget);

        when(budgetRepository.findByDate(eq(beginDate)))
                .thenReturn(monthlyBudgets);

        //when
        var optResult = assertDoesNotThrow(() ->
                subject.assertCondition(new BudgetRequestConditionParameter(request)));

        //then
        assertFalse(optResult.isPresent());
    }
}
