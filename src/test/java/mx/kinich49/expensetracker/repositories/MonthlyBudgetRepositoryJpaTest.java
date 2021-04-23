package mx.kinich49.expensetracker.repositories;

import mx.kinich49.expensetracker.models.database.MonthlyBudget;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class MonthlyBudgetRepositoryJpaTest {

    @Autowired
    private MonthlyBudgetRepository subject;

    @Test
    @DisplayName("Sanity test")
    public void sanityTest() {
        assertNotNull(subject);
    }

    /**
     * A test to validate the beginDate and endDate
     * of Monthly Budgets.
     * <p>
     * Two dates are passed to the repository.
     * The first one should return two budgets,
     * and the second one should return one budget,
     * as inserted in test.sql
     */
    @Test
    @DisplayName("Should return expected budgets for given dates")
    public void shouldReturn_expectedResults() {
        //Given
        //2020-12-05
        YearMonth firstDate = YearMonth.of(2020, 12);

        //2021-02-01
        YearMonth secondDate = YearMonth.of(2021, 2);

        //when
        List<MonthlyBudget> firstResults = subject.findByDate(firstDate);
        List<MonthlyBudget> secondResults = subject.findByDate(secondDate);

        //then
        assertNotNull(firstResults);
        assertFalse(firstResults.isEmpty());
        assertEquals(2, firstResults.size());

        assertNotNull(secondResults);
        assertFalse(secondResults.isEmpty());
        assertEquals(1, secondResults.size());
    }

    @Test
    @DisplayName("Should return no budget results for given date")
    public void shouldReturn_emptyResults() {
        //Given
        YearMonth date = YearMonth.of(2019, 1);

        //when
        List<MonthlyBudget> results = subject.findByDate(date);

        //then
        assertNotNull(results);
        assertEquals(0, results.size());
    }
}
