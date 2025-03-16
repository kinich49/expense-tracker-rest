package mx.kinich49.expensetracker.validations.monthlycategorybudget.validators;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.exceptions.ValidationFlowException;
import mx.kinich49.expensetracker.models.database.MonthlyBudget;
import mx.kinich49.expensetracker.models.database.MonthlyIncome;
import mx.kinich49.expensetracker.models.web.ErrorWrapper;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetCategoryRequest;
import mx.kinich49.expensetracker.validations.monthlycategorybudget.MonthlyCategoryBudgetErrorCodes;
import mx.kinich49.expensetracker.validations.monthlycategorybudget.RequestParameter;
import mx.kinich49.expensetracker.validations.monthlycategorybudget.conditions.*;
import mx.kinich49.expensetracker.validations.monthlycategorybudget.providers.MonthlyCategoryBudgetConditionProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class MonthlyCategoryBudgetValidatorTest {

    MonthlyCategoryBudgetValidator subject;
    MonthlyCategoryBudgetConditionProvider conditionProvider;

    @Mock
    LimitCondition limitCondition;
    @Mock
    BudgetCondition budgetCondition;
    @Mock
    CategoryCondition categoryCondition;
    @Mock
    MonthlyLimitCondition monthlyLimitCondition;
    @Mock
    MonthlyBudgetCondition monthlyBudgetCondition;


    @BeforeEach
    void setUp() {
        conditionProvider = Mockito
                .spy(new MonthlyCategoryBudgetConditionProvider(limitCondition,
                        budgetCondition, categoryCondition, monthlyLimitCondition,
                        monthlyBudgetCondition));

        subject = new MonthlyCategoryBudgetValidator(conditionProvider);
    }

    @Test
    @DisplayName("Sanity Test")
    public void sanityTest() {
        assertNotNull(subject);
    }

    @Test
    @DisplayName("Should finish when conditions complete")
    public void shouldFinish_when_conditionsComplete() throws BusinessException {
        //given
        MonthlyBudgetCategoryRequest request = new MonthlyBudgetCategoryRequest();

        MonthlyIncome monthlyIncome = new MonthlyIncome();
        List<MonthlyBudget> monthlyBudgets = new ArrayList<>();

        MonthlyCategoryBudgetValidator.Parameter parameter =
                new MonthlyCategoryBudgetValidator.Parameter(request, monthlyIncome, monthlyBudgets, 1000);

        when(limitCondition.assertCondition(any(LimitCondition.Parameter.class)))
                .thenReturn(Optional.empty());

        when(budgetCondition
                .assertCondition(any(RequestParameter.class)))
                .thenReturn(Optional.empty());

        when(categoryCondition
                .assertCondition(any(RequestParameter.class)))
                .thenReturn(Optional.empty());

        when(monthlyLimitCondition
                .assertCondition(any(RequestParameter.class)))
                .thenReturn(Optional.empty());

        when(monthlyBudgetCondition
                .assertCondition(any(MonthlyBudgetCondition.Parameter.class)))
                .thenReturn(Optional.empty());
        //when
        assertDoesNotThrow(() -> subject.validate(parameter));
    }

    @Test
    @DisplayName("Should throw exception when limit condition fails")
    public void shouldThrowException_when_monthlyCategoryLimitConditionFails()
            throws ValidationFlowException {
        //given
        MonthlyBudgetCategoryRequest request = new MonthlyBudgetCategoryRequest();

        MonthlyIncome monthlyIncome = new MonthlyIncome();
        List<MonthlyBudget> monthlyBudgets = new ArrayList<>();

        MonthlyCategoryBudgetValidator.Parameter parameter =
                new MonthlyCategoryBudgetValidator.Parameter(request, monthlyIncome, monthlyBudgets, 1000);

        var errorWrapper = new ErrorWrapper(MonthlyCategoryBudgetErrorCodes.BUDGET_NOT_FOUND,
                "Something went wrong");

        lenient().when(limitCondition
                        .assertCondition(any(LimitCondition.Parameter.class)))
                .thenReturn(Optional.of(errorWrapper));

        lenient().when(budgetCondition
                        .assertCondition(any(RequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(categoryCondition
                        .assertCondition(any(RequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(monthlyLimitCondition
                        .assertCondition(any(RequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(monthlyBudgetCondition
                        .assertCondition(any(MonthlyBudgetCondition.Parameter.class)))
                .thenReturn(Optional.empty());


        //when
        Exception result = assertThrows(BusinessException.class,
                () -> subject.validate(parameter));

        //then
        assertEquals(errorWrapper.getErrorMessage(), result.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when budgets condition fails")
    public void shouldThrowException_whenBudgetConditionFails()
            throws ValidationFlowException {
        //given
        MonthlyBudgetCategoryRequest request = new MonthlyBudgetCategoryRequest();

        MonthlyIncome monthlyIncome = new MonthlyIncome();
        List<MonthlyBudget> monthlyBudgets = new ArrayList<>();

        MonthlyCategoryBudgetValidator.Parameter parameter =
                new MonthlyCategoryBudgetValidator.Parameter(request, monthlyIncome, monthlyBudgets, 1000);

        var errorWrapper = new ErrorWrapper(MonthlyCategoryBudgetErrorCodes.BUDGET_NOT_FOUND,
                "Something went wrong");

        lenient().when(limitCondition
                        .assertCondition(any(LimitCondition.Parameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(budgetCondition
                        .assertCondition(any(RequestParameter.class)))
                .thenReturn(Optional.of(errorWrapper));

        lenient().when(categoryCondition
                        .assertCondition(any(RequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(monthlyLimitCondition
                        .assertCondition(any(RequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(monthlyBudgetCondition
                        .assertCondition(any(MonthlyBudgetCondition.Parameter.class)))
                .thenReturn(Optional.empty());

        //when
        Exception result = assertThrows(BusinessException.class, () -> subject.validate(parameter));

        //then
        assertEquals(errorWrapper.getErrorMessage(), result.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when category condition fails")
    public void shouldThrowException_whenCategoryConditionFails() throws BusinessException {
        //given
        MonthlyBudgetCategoryRequest request = new MonthlyBudgetCategoryRequest();

        MonthlyIncome monthlyIncome = new MonthlyIncome();
        List<MonthlyBudget> monthlyBudgets = new ArrayList<>();

        MonthlyCategoryBudgetValidator.Parameter parameter =
                new MonthlyCategoryBudgetValidator.Parameter(request,
                        monthlyIncome, monthlyBudgets, 1000);

        var errorWrapper = new ErrorWrapper(MonthlyCategoryBudgetErrorCodes.BUDGET_NOT_FOUND,
                "Something went wrong");

        lenient().when(limitCondition
                        .assertCondition(any(LimitCondition.Parameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(budgetCondition
                        .assertCondition(any(RequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(categoryCondition
                        .assertCondition(any(RequestParameter.class)))
                .thenReturn(Optional.of(errorWrapper));

        lenient().when(monthlyLimitCondition
                        .assertCondition(any(RequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(monthlyBudgetCondition
                        .assertCondition(any(MonthlyBudgetCondition.Parameter.class)))
                .thenReturn(Optional.empty());

        //when
        Exception result = assertThrows(BusinessException.class, () -> subject.validate(parameter));

        //then
        assertEquals(errorWrapper.getErrorMessage(), result.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when monthly limit condition fails")
    public void shouldThrowException_whenMonthlyLimitConditionFails()
            throws ValidationFlowException {
        //given
        MonthlyBudgetCategoryRequest request = new MonthlyBudgetCategoryRequest();

        MonthlyIncome monthlyIncome = new MonthlyIncome();
        List<MonthlyBudget> monthlyBudgets = new ArrayList<>();

        MonthlyCategoryBudgetValidator.Parameter parameter =
                new MonthlyCategoryBudgetValidator.Parameter(request, monthlyIncome, monthlyBudgets, 1000);

        var errorWrapper = new ErrorWrapper(MonthlyCategoryBudgetErrorCodes.BUDGET_NOT_FOUND,
                "Something went wrong");

        lenient().when(limitCondition
                        .assertCondition(any(LimitCondition.Parameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(budgetCondition
                        .assertCondition(any(RequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(categoryCondition
                        .assertCondition(any(RequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(monthlyLimitCondition
                        .assertCondition(any(RequestParameter.class)))
                .thenReturn(Optional.of(errorWrapper));

        lenient().when(monthlyBudgetCondition
                        .assertCondition(any(MonthlyBudgetCondition.Parameter.class)))
                .thenReturn(Optional.empty());

        //when
        Exception result = assertThrows(BusinessException.class, () -> subject.validate(parameter));

        //then
        assertEquals(errorWrapper.getErrorMessage(), result.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when monthly budget condition fails")
    public void shouldThrowException_whenMonthlyBudgetConditionFails()
            throws ValidationFlowException {
        //given
        MonthlyBudgetCategoryRequest request = new MonthlyBudgetCategoryRequest();

        MonthlyIncome monthlyIncome = new MonthlyIncome();
        List<MonthlyBudget> monthlyBudgets = new ArrayList<>();

        MonthlyCategoryBudgetValidator.Parameter parameter =
                new MonthlyCategoryBudgetValidator.Parameter(request, monthlyIncome, monthlyBudgets, 1000);

        var errorWrapper = new ErrorWrapper(MonthlyCategoryBudgetErrorCodes.BUDGET_NOT_FOUND,
                "Something went wrong");

        lenient().when(limitCondition
                        .assertCondition(any(LimitCondition.Parameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(budgetCondition
                        .assertCondition(any(RequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(categoryCondition
                        .assertCondition(any(RequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(monthlyLimitCondition
                        .assertCondition(any(RequestParameter.class)))
                .thenReturn(Optional.empty());

        lenient().when(monthlyBudgetCondition
                        .assertCondition(any(MonthlyBudgetCondition.Parameter.class)))
                .thenReturn(Optional.of(errorWrapper));

        //when
        Exception result = assertThrows(BusinessException.class, () -> subject.validate(parameter));

        //then
        assertEquals(errorWrapper.getErrorMessage(), result.getMessage());
    }

}
