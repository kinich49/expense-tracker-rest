package mx.kinich49.expensetracker.validations.monthlyincome.conditions;

import mx.kinich49.expensetracker.models.database.MonthlyIncome;
import mx.kinich49.expensetracker.models.web.requests.MonthlyIncomeRequest;
import mx.kinich49.expensetracker.repositories.MonthlyIncomeRepository;
import mx.kinich49.expensetracker.validations.monthlyincome.MonthlyIncomeErrorCodes;
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
    CollisionCondition subject;

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
        CollisionCondition.Parameter parameter = new CollisionCondition.Parameter(request);

        when(repository.collides(eq(beginDate), eq(endDate)))
                .thenReturn(Optional.empty());

        //when
        var optResult = subject.assertCondition(parameter);

        //then
        assertFalse(optResult.isPresent());
    }

    @Test
    @DisplayName("Should fail when income request collides")
    public void shouldFail_when_incomeRequestCollides() {
        //Given
        MonthlyIncomeRequest request = new MonthlyIncomeRequest();
        YearMonth beginDate = YearMonth.of(2021, 1);
        YearMonth endDate = YearMonth.of(2021, 12);
        request.setBeginDate(beginDate);
        request.setEndDate(endDate);
        request.setUpperIncomeLimit(10000);
        CollisionCondition.Parameter parameter = new CollisionCondition.Parameter(request);

        MonthlyIncome monthlyIncome = new MonthlyIncome();
        monthlyIncome.setId(100L);
        monthlyIncome.setBeginDate(beginDate);

        when(repository.collides(eq(beginDate), eq(endDate)))
                .thenReturn(Optional.of(monthlyIncome));

        //when
        var optResult = subject.assertCondition(parameter);

        //then
        assertTrue(optResult.isPresent());
        var result = optResult.get();
        assertEquals(MonthlyIncomeErrorCodes.MONTHLY_INCOME_COLLIDES_WITH_REQUEST, result.getErrorCode());
    }

}
