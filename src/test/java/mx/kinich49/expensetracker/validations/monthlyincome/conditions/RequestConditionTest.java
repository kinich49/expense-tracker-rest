package mx.kinich49.expensetracker.validations.monthlyincome.conditions;

import mx.kinich49.expensetracker.models.web.requests.MonthlyIncomeRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.YearMonth;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
//TODO break in smaller test classes
public class RequestConditionTest {
//
//    @InjectMocks
//    RequestConditionImpl subject;
//
//    @Test
//    public void sanityTest() {
//        assertNotNull(subject);
//    }
//
//    @Test
//    @DisplayName("Should return empty when all properties are set")
//    public void shouldReturn_empty_whenAllFieldsAreSet() {
//        //given
//        MonthlyIncomeRequest request = new MonthlyIncomeRequest();
//        request.setUpperIncomeLimit(1000);
//        request.setBeginDate(YearMonth.of(2021, 1));
//        request.setEndDate(YearMonth.of(2021, 12));
//        RequestConditionImpl.Parameter parameter = new RequestConditionImpl.Parameter(request);
//
//        //when
//        Optional<String> result = subject.assertCondition(parameter);
//
//        //then
//        assertFalse(result.isPresent());
//    }
//
//    @Test
//    @DisplayName("Should return empty when missing only endDate")
//    public void shouldReturn_empty_when_missingEndDate() {
//        //given
//        MonthlyIncomeRequest request = new MonthlyIncomeRequest();
//        request.setUpperIncomeLimit(1000);
//        request.setBeginDate(YearMonth.of(2021, 1));
//        RequestConditionImpl.Parameter parameter = new RequestConditionImpl.Parameter(request);
//
//        //when
//        Optional<String> result = subject.assertCondition(parameter);
//
//        //then
//        assertFalse(result.isPresent());
//    }
//
//    @Test
//    @DisplayName("Should fail when request is null")
//    public void shouldFail_when_requestIsNull() {
//        //given
//        RequestConditionImpl.Parameter parameter = new RequestConditionImpl.Parameter(null);
//
//        //when
//        Optional<String> result = subject.assertCondition(parameter);
//
//        //then
//        assertTrue(result.isPresent());
//        String errorMessage = result.get();
//        assertEquals("Request must not be null.", errorMessage);
//    }
//
//    @Test
//    @DisplayName("Should fail when beginDate is null")
//    public void shouldFail_when_beginDateIsNull() {
//        //given
//        MonthlyIncomeRequest request = new MonthlyIncomeRequest();
//        request.setUpperIncomeLimit(1000);
//        request.setEndDate(YearMonth.now());
//        RequestConditionImpl.Parameter parameter = new RequestConditionImpl.Parameter(request);
//
//        //when
//        Optional<String> result = subject.assertCondition(parameter);
//
//        //then
//        assertTrue(result.isPresent());
//        String errorMessage = result.get();
//        assertEquals("Begin Date must not be null.", errorMessage);
//    }
//
//    @Test
//    @DisplayName("Should fail when upperIncomeLimit is 0")
//    public void shouldFail_when_upperIncomeLimitIsZero() {
//        //given
//        MonthlyIncomeRequest request = new MonthlyIncomeRequest();
//        request.setBeginDate(YearMonth.now());
//        request.setEndDate(YearMonth.now());
//        RequestConditionImpl.Parameter parameter = new RequestConditionImpl.Parameter(request);
//
//        //when
//        Optional<String> result = subject.assertCondition(parameter);
//
//        //then
//        assertTrue(result.isPresent());
//        String errorMessage = result.get();
//        assertEquals("Upper Income Limit must not be zero.", errorMessage);
//    }
//
//    @Test
//    @DisplayName("Should fail when endDate is before beginDate")
//    public void shouldFail_when_endDateIsBeforeBeginDate() {
//        //given
//        MonthlyIncomeRequest request = new MonthlyIncomeRequest();
//        request.setBeginDate(YearMonth.of(2021, 1));
//        request.setEndDate(YearMonth.of(2020, 12));
//        request.setUpperIncomeLimit(10000);
//        RequestConditionImpl.Parameter parameter = new RequestConditionImpl.Parameter(request);
//
//        //when
//        Optional<String> result = subject.assertCondition(parameter);
//
//        //then
//        assertTrue(result.isPresent());
//        String errorMessage = result.get();
//        assertEquals("End Date must be equal or after Begin Date.", errorMessage);
//    }
//
//    @Test
//    @DisplayName("Should return empty when endDate is equals to beginDate")
//    public void shouldReturn_empty_when_endDateIsEqualsToBeginDate() {
//        //given
//        MonthlyIncomeRequest request = new MonthlyIncomeRequest();
//        request.setUpperIncomeLimit(1000);
//        request.setBeginDate(YearMonth.of(2021, 1));
//        request.setEndDate(YearMonth.of(2021, 1));
//        RequestConditionImpl.Parameter parameter = new RequestConditionImpl.Parameter(request);
//
//        //when
//        Optional<String> result = subject.assertCondition(parameter);
//
//        //then
//        assertFalse(result.isPresent());
//    }
//
//    @Test
//    @DisplayName("Should fail when endDate is before beginDate and upperIncomeLimit is Zero")
//    public void shouldFail_when_endDateIsBeforeBeginDate_and_upperIncomeLimitIsZero() {
//        //given
//        MonthlyIncomeRequest request = new MonthlyIncomeRequest();
//        request.setBeginDate(YearMonth.of(2021, 1));
//        request.setEndDate(YearMonth.of(2020, 12));
//        RequestConditionImpl.Parameter parameter = new RequestConditionImpl.Parameter(request);
//
//        //when
//        Optional<String> result = subject.assertCondition(parameter);
//
//        //then
//        assertTrue(result.isPresent());
//        String errorMessage = result.get();
//        assertTrue(errorMessage.contains("End Date must be equal or after Begin Date."));
//        assertTrue(errorMessage.contains("Upper Income Limit must not be zero."));
//    }
//
//    @Test
//    @DisplayName("Should fail when beginDate is null and upperIncomeLimit is Zero")
//    public void shouldFail_when_beginDateIsNull_and_upperIncomeLimitIsZero() {
//        //given
//        MonthlyIncomeRequest request = new MonthlyIncomeRequest();
//        request.setEndDate(YearMonth.of(2020, 12));
//        RequestConditionImpl.Parameter parameter = new RequestConditionImpl.Parameter(request);
//
//        //when
//        Optional<String> result = subject.assertCondition(parameter);
//
//        //then
//        assertTrue(result.isPresent());
//        String errorMessage = result.get();
//        assertTrue(errorMessage.contains("Begin Date must not be null."));
//        assertTrue(errorMessage.contains("Upper Income Limit must not be zero."));
//    }
}
