package mx.kinich49.expensetracker.validations.conditions.monthlybudgetservice;

import mx.kinich49.expensetracker.models.database.MonthlyBudget;
import mx.kinich49.expensetracker.models.database.MonthlyIncome;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetRequest;
import mx.kinich49.expensetracker.repositories.MonthlyBudgetRepository;
import mx.kinich49.expensetracker.repositories.MonthlyIncomeRepository;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BudgetLimitTest {

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
        MonthlyBudgetRequest request = new MonthlyBudgetRequest();
        request.setBaseLimit(0);
        request.setTitle("Budget title");
        request.setBeginDate(YearMonth.of(2021, APRIL));

        //when
        Optional<String> result = assertDoesNotThrow(() ->
                subject.assertCondition(new BudgetLimitCondition.Parameter(request)));

        //then
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should return empty when end date is not set")
    public void shouldReturnEmpty_whenEndDateIsNotSet() {
        //given
        MonthlyBudgetRequest request = new MonthlyBudgetRequest();
        request.setBaseLimit(0);
        request.setTitle("Budget title");
        request.setBeginDate(YearMonth.of(2021, APRIL));

        //when
        Optional<String> result = assertDoesNotThrow(() ->
                subject.assertCondition(new BudgetLimitCondition.Parameter(request)));

        //then
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should return empty when end date is after begin date")
    public void shouldReturnEmpty_whenEndDateIsAfterBeginDate() {
        //given
        MonthlyBudgetRequest request = new MonthlyBudgetRequest();
        request.setBaseLimit(0);
        request.setTitle("Budget title");
        request.setBeginDate(YearMonth.of(2021, APRIL));
        request.setEndDate(YearMonth.of(2022, APRIL));

        //when
        Optional<String> result = assertDoesNotThrow(() ->
                subject.assertCondition(new BudgetLimitCondition.Parameter(request)));

        //then
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should return empty when end date is equal as begin date")
    public void shouldReturnEmpty_whenEndDateIsEqualAsBeginDate() {
        //given
        MonthlyBudgetRequest request = new MonthlyBudgetRequest();
        request.setBaseLimit(0);
        request.setTitle("Budget title");
        request.setBeginDate(YearMonth.of(2021, APRIL));
        request.setEndDate(YearMonth.of(2021, APRIL));

        //when
        Optional<String> result = assertDoesNotThrow(() ->
                subject.assertCondition(new BudgetLimitCondition.Parameter(request)));

        //then
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should return empty when budget limit is less than Zero")
    public void shouldReturnEmpty_whenBudgetLimitIsLessThanZero() {
        //given
        MonthlyBudgetRequest request = new MonthlyBudgetRequest();
        request.setBaseLimit(-1);
        request.setTitle("Budget title");
        request.setBeginDate(YearMonth.of(2021, APRIL));

        //when
        Optional<String> result = assertDoesNotThrow(() ->
                subject.assertCondition(new BudgetLimitCondition.Parameter(request)));

        //then
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should return error when budget limit is off limits")
    public void shouldReturnError_whenBudgetLimitIsOffLimits() {
        //given
        MonthlyBudgetRequest request = new MonthlyBudgetRequest();
        request.setBaseLimit(100);
        request.setTitle("Budget title");
        YearMonth beginDate = YearMonth.of(2021, APRIL);
        request.setBeginDate(beginDate);

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
        Optional<String> result = assertDoesNotThrow(() ->
                subject.assertCondition(new BudgetLimitCondition.Parameter(request)));

        //then
        assertTrue(result.isPresent());
    }

    @DisplayName("Should return empty when budget is on limits")
    public void shouldReturnEmpty_whenBudgetLimitIsOnLimits() {
        //given
        MonthlyBudgetRequest request = new MonthlyBudgetRequest();
        request.setBaseLimit(10000);
        request.setTitle("Budget title");
        YearMonth beginDate = YearMonth.of(2021, APRIL);
        request.setBeginDate(beginDate);

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
        Optional<String> result = assertDoesNotThrow(() ->
                subject.assertCondition(new BudgetLimitCondition.Parameter(request)));

        //then
        assertFalse(result.isPresent());
    }
}
