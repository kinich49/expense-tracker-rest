package mx.kinich49.expensetracker.validations.monthlybudgetservice.conditions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
public class BudgetRequestEndDateConditionTest {

    @InjectMocks
    BudgetRequestEndDateCondition subject;

    @DisplayName("Sanity Test")
    @Test
    public void sanityTest(){
        assertNotNull(subject);
    }
}
