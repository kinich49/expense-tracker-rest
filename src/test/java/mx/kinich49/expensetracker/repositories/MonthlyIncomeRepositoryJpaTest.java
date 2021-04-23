package mx.kinich49.expensetracker.repositories;

import mx.kinich49.expensetracker.models.database.MonthlyIncome;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.YearMonth;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class MonthlyIncomeRepositoryJpaTest {

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
     *
     * @param expected the expected year month yyyy-MM
     * @param result   the subject's result
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

    /**
     * This test should try to validate
     * that a monthly income exists for two dates
     * and doesn't exists for one date
     * <p>
     * The data is in /resources/test.sql
     */
    @Test
    @DisplayName("Validate exists by given date")
    public void validate_existsByDate() {
        //given
        YearMonth now = YearMonth.now();
        YearMonth date_1999_01 = YearMonth.of(1999, 1);
        YearMonth date_2020_01 = YearMonth.of(2020, 1);

        //when and then
        assertTrue(subject.existsByBeginDate(now));
        assertFalse(subject.existsByBeginDate(date_1999_01));
        assertTrue(subject.existsByBeginDate(date_2020_01));
    }

    /**
     * This test tries to retrieve current monthly income
     */
    @Test
    @DisplayName("Should return current monthly income")
    public void shouldReturn_currentMonthlyIncome() {
        //when
        Optional<MonthlyIncome> optResult = subject.findCurrentIncome();

        //then
        assertTrue(optResult.isPresent());

        MonthlyIncome result = optResult.get();
        assertEquals(YearMonth.of(2020, 10), result.getBeginDate());
        assertNull(result.getEndDate());
    }

    /**
     * This test tries to set currentIncome (Which has ID 1,
     * as stated in /resources/test.sql) an endDate of now
     * And then fail to retrieve current income, which
     * should be an Optional.empty()
     *
     * @throws Exception if monthly income with ID 1 does not exist
     */
    @Test
    @DisplayName("Should fail to return current monthly income")
    public void shouldFail_toReturn_currentMonthlyIncome() throws Exception {
        //Given
        MonthlyIncome monthlyIncome = subject.findById(1L)
                .orElseThrow(() -> new Exception("monthly income with id 1 not found"));

        monthlyIncome.setEndDate(YearMonth.now());
        subject.save(monthlyIncome);

        //when
        Optional<MonthlyIncome> optResult = subject.findCurrentIncome();

        //then
        assertFalse(optResult.isPresent());
    }
}
