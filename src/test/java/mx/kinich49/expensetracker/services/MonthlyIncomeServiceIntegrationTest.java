package mx.kinich49.expensetracker.services;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.database.MonthlyIncome;
import mx.kinich49.expensetracker.models.web.MonthlyIncomeWebModel;
import mx.kinich49.expensetracker.models.web.requests.MonthlyIncomeRequest;
import mx.kinich49.expensetracker.repositories.MonthlyIncomeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class MonthlyIncomeServiceIntegrationTest {

    @Autowired
    private MonthlyIncomeService subject;
    @Autowired
    private MonthlyIncomeRepository monthlyIncomeRepository;

    @Test
    public void sanityTest() {
        assertNotNull(subject);
    }

    @Test
    @DisplayName("Delete monthly Income")
    @Transactional
    public void shouldDelete_MonthlyIncome() {
        //when
        subject.deleteMonthlyIncome(1L);
        subject.deleteMonthlyIncome(Long.MAX_VALUE);
    }

    /**
     * This test should try and fail to
     * add a new Monthly Income, given that
     * a income already exists without an endDate
     */
    @Test
    @Transactional
    public void shouldFail_ToAddNewMonthlyIncome() {
        //given
        MonthlyIncomeRequest request = new MonthlyIncomeRequest();
        request.setBeginDate(YearMonth.now());
        request.setUpperIncomeLimit(2_000_000);

        //when
        Exception result = assertThrows(BusinessException.class,
                () -> subject.addMonthlyIncome(request));

        assertNotNull(result);
    }

    /**
     * This test tries to set an enddate to current income
     * and add a new income
     *
     * @throws Exception if monthly income with ID 1 does not exist
     */
    @Test
    @DisplayName("Should add new monthly income")

    public void shouldAdd_NewMonthlyIncome() throws Exception {
        //given
        MonthlyIncomeRequest request = new MonthlyIncomeRequest();
        YearMonth beginDate = YearMonth.now();
        request.setBeginDate(beginDate);
        request.setUpperIncomeLimit(2_000_000);

        //Find current income and
        MonthlyIncome monthlyIncome = monthlyIncomeRepository.findById(1L)
                .orElseThrow(() -> new Exception("monthly income with id 1 not found"));

        monthlyIncome.setEndDate(YearMonth.now());
        monthlyIncomeRepository.save(monthlyIncome);

        //when
        MonthlyIncomeWebModel result = assertDoesNotThrow(() -> subject.addMonthlyIncome(request));

        //then
        assertNotNull(result);
        assertEquals(beginDate, result.getBeginDate());
        assertEquals(String.valueOf(2000000), result.getUpperIncomeLimit());
    }

    @Test
    public void shouldRetrieve_currentIncome() {
        //given
        YearMonth now = YearMonth.now();

        //when
        Optional<MonthlyIncomeWebModel> optResult = subject.findCurrentIncome();

        //then
        assertTrue(optResult.isPresent());
        MonthlyIncomeWebModel result = optResult.get();

        boolean isAfter = now.isAfter(result.getBeginDate());
        boolean isEqual = now.equals(result.getBeginDate());

        assertTrue((isAfter || isEqual));
        assertNull(result.getEndDate());
    }
}
