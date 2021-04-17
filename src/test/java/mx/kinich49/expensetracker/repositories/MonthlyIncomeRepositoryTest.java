package mx.kinich49.expensetracker.repositories;

import mx.kinich49.expensetracker.models.database.MonthlyIncome;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.YearMonth;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class MonthlyIncomeRepositoryTest {

    @Autowired
    private MonthlyIncomeRepository subject;

    @Test
    @DisplayName("Sanity Test")
    void sanityTest() {
        assertNotNull(subject);
    }

    /**
     * This tests should assert
     * that for every given beginDate, the result's
     * begin date should be before or equals to the parameter.
     * <p>
     * i.e. When the subject's parameter is now,
     * it should return a single result with
     * a begin date of 2020-10
     * (As stated in resources/test.sql)
     */
    @Test
    @DisplayName("Retrieve monthly income by beginDate")
    public void shouldReturn_monthlyIncomeByDate() {
        //given
        YearMonth now = YearMonth.now();
        YearMonth beginDate_2020_01 = YearMonth.of(2020, 1);
        YearMonth beginDate_2020_07 = YearMonth.of(2020, 7);

        //when
        Optional<MonthlyIncome> optResult_now = subject.findByBeginDate(now);
        Optional<MonthlyIncome> optResult_2020_01 = subject.findByBeginDate(beginDate_2020_01);
        Optional<MonthlyIncome> optResult_2020_07 = subject.findByBeginDate(beginDate_2020_07);

        //then
        assertTrue(optResult_now.isPresent());
        assertBeginDate(now, optResult_now.get());
        assertTrue(optResult_2020_01.isPresent());
        assertBeginDate(beginDate_2020_01, optResult_2020_01.get());
        assertTrue(optResult_2020_07.isPresent());
        assertBeginDate(beginDate_2020_07, optResult_2020_07.get());
    }

    /**
     * Asserts that the expected YearMonth is equal or after
     * the result's beginDate
     * @param expected the expected year month yyyy-MM
     * @param result the subject's result
     */
    private void assertBeginDate(YearMonth expected, MonthlyIncome result) {
        assertNotNull(result);
        assertNotNull(result.getBeginDate());
        boolean isEquals = expected.equals(result.getBeginDate());
        boolean isAfter = expected.isAfter(result.getBeginDate());
        assertTrue(isEquals || isAfter);
    }


    @Test
    @DisplayName("Retrieve monthly income by id")
    public void shouldReturn_monthlyIncomeById() {
        //given
        long id = 1L;

        //when
        Optional<MonthlyIncome> optResult = subject.findById(id);

        //then
        assertTrue(optResult.isPresent());
        MonthlyIncome result = optResult.get();
        assertEquals(id, result.getId());
    }

    /**
     * This test tries to assert
     * that asking for a Monthly Income
     * with a begin date that is not
     * on the database, results in
     * an Optional.empty()
     */
    @Test
    @DisplayName("Fail to retrieve monthly income")
    public void shouldReturn_empty_when_beginDateIsLess() {
        //given
        YearMonth beginDate = YearMonth.of(1999, 1);

        //when
        Optional<MonthlyIncome> optResult = subject.findByBeginDate(beginDate);

        //then
        assertFalse(optResult.isPresent());
    }
}
