package mx.kinich49.expensetracker.validations.conditions.monthlyincome;

import mx.kinich49.expensetracker.models.database.MonthlyIncome;
import mx.kinich49.expensetracker.models.web.requests.MonthlyIncomeRequest;
import mx.kinich49.expensetracker.repositories.MonthlyIncomeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.YearMonth;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CollisionConditionTest {

    @InjectMocks
    CollisionConditionImpl subject;

    @Mock
    MonthlyIncomeRepository repository;

    @Test
    public void sanityTest() {
        assertNotNull(subject);
    }

    @Test
    @DisplayName("Should return empty")
    public void shouldReturn_empty() {
        //Given
        MonthlyIncomeRequest request = new MonthlyIncomeRequest();
        YearMonth beginDate = YearMonth.of(2021, 1);
        YearMonth endDate = YearMonth.of(2021, 12);
        request.setBeginDate(beginDate);
        request.setEndDate(endDate);
        request.setUpperIncomeLimit(10000);
        CollisionConditionImpl.Parameter parameter = new CollisionConditionImpl.Parameter(request);

        when(repository.collides(eq(beginDate), eq(endDate)))
                .thenReturn(Optional.empty());

        //when
        Optional<String> optResult = subject.assertCondition(parameter);

        //then
        assertFalse(optResult.isPresent());
    }

    @Test
    @DisplayName("Should complete without exception")
    public void shouldFail_when_incomeRequestCollides() {
        //Given
        MonthlyIncomeRequest request = new MonthlyIncomeRequest();
        YearMonth beginDate = YearMonth.of(2021, 1);
        YearMonth endDate = YearMonth.of(2021, 12);
        request.setBeginDate(beginDate);
        request.setEndDate(endDate);
        request.setUpperIncomeLimit(10000);
        CollisionConditionImpl.Parameter parameter = new CollisionConditionImpl.Parameter(request);

        MonthlyIncome monthlyIncome = new MonthlyIncome();
        monthlyIncome.setId(100L);
        monthlyIncome.setBeginDate(beginDate);

        when(repository.collides(eq(beginDate), eq(endDate)))
                .thenReturn(Optional.of(monthlyIncome));

        //when
        Optional<String> optResult = subject.assertCondition(parameter);

        //then
        assertTrue(optResult.isPresent());
        String errorMessage = optResult.get();
        assertEquals("Monthly Income with id: 100 collides with request.", errorMessage);
    }

}
