package mx.kinich49.expensetracker.services.impl;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.database.Category;
import mx.kinich49.expensetracker.models.database.MonthlyBudget;
import mx.kinich49.expensetracker.models.database.MonthlyBudgetCategory;
import mx.kinich49.expensetracker.models.database.MonthlyIncome;
import mx.kinich49.expensetracker.models.web.MonthlyBudgetCategoryWebModel;
import mx.kinich49.expensetracker.models.web.MonthlyBudgetWebModel;
import mx.kinich49.expensetracker.models.web.SimpleMonthlyBudgetWebModel;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetCategoryRequest;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetRequest;
import mx.kinich49.expensetracker.repositories.CategoryRepository;
import mx.kinich49.expensetracker.repositories.MonthlyBudgetCategoryRepository;
import mx.kinich49.expensetracker.repositories.MonthlyBudgetRepository;
import mx.kinich49.expensetracker.repositories.MonthlyIncomeRepository;
import mx.kinich49.expensetracker.services.MonthlyBudgetService;
import mx.kinich49.expensetracker.validations.validators.MonthlyCategoryBudgetValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
@SuppressWarnings("unused")
public class MonthlyBudgetServiceImpl implements MonthlyBudgetService {

    private final MonthlyBudgetRepository monthlyBudgetRepository;
    private final MonthlyBudgetCategoryRepository monthlyBudgetCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final MonthlyCategoryBudgetValidatorImpl monthlyCategoryBudgetValidator;
    private final MonthlyIncomeRepository monthlyIncomeRepository;

    @Autowired
    public MonthlyBudgetServiceImpl(MonthlyBudgetRepository monthlyBudgetRepository,
                                    MonthlyBudgetCategoryRepository monthlyBudgetCategoryRepository,
                                    CategoryRepository categoryRepository,
                                    MonthlyCategoryBudgetValidatorImpl monthlyCategoryBudgetValidator,
                                    MonthlyIncomeRepository monthlyIncomeRepository) {
        this.monthlyBudgetRepository = monthlyBudgetRepository;
        this.monthlyBudgetCategoryRepository = monthlyBudgetCategoryRepository;
        this.categoryRepository = categoryRepository;
        this.monthlyCategoryBudgetValidator = monthlyCategoryBudgetValidator;
        this.monthlyIncomeRepository = monthlyIncomeRepository;
    }

    @Override
    public SimpleMonthlyBudgetWebModel insertMonthlyBudget(MonthlyBudgetRequest request) {
        MonthlyBudget monthlyBudget = new MonthlyBudget();
        monthlyBudget.setBeginDate(request.getBeginDate());
        monthlyBudget.setEndDate(request.getEndDate());
        monthlyBudget.setTitle(request.getTitle());

        monthlyBudget = monthlyBudgetRepository.save(monthlyBudget);

        return SimpleMonthlyBudgetWebModel.from(monthlyBudget);
    }


    @Override
    public Optional<MonthlyBudgetWebModel> findMonthlyBudgets(YearMonth date) throws BusinessException{
        MonthlyIncome monthlyIncome = monthlyIncomeRepository.findByActive(true)
                .orElseThrow(() -> new BusinessException("No active income found"));

        List<MonthlyBudget> monthlyBudgets = monthlyBudgetRepository.findByDate(date);

        return Optional.ofNullable(MonthlyBudgetWebModel.from(monthlyIncome, monthlyBudgets));
    }

    @Override
    public MonthlyBudgetCategoryWebModel addMonthlyBudgetCategory(MonthlyBudgetCategoryRequest request)
            throws BusinessException {
        Optional<MonthlyBudget> optMonthlyBudget = monthlyBudgetRepository.findById(request.getBudgetId());

        if (!optMonthlyBudget.isPresent()) {
            String error = String.format("Monthly Budget with id %1$d not found", request.getBudgetId());
            throw new BusinessException(error);
        }

        MonthlyBudget monthlyBudget = optMonthlyBudget.get();
        MonthlyIncome monthlyIncome = monthlyIncomeRepository.findByActive(true)
                .orElseThrow(() -> new BusinessException("No active income found"));

        Optional<Category> optCategory = categoryRepository.findById(request.getCategoryId());

        if (!optCategory.isPresent()) {
            String error = String.format("Category with id %1$d not found", request.getCategoryId());
            throw new BusinessException(error);
        }

        Category category = optCategory.get();

        List<MonthlyBudget> monthlyBudgets = monthlyBudgetRepository.findByDate(request.getYearMonth());

        if (monthlyBudgets == null || monthlyBudgets.isEmpty()) {
            String error = String.format("Can't find monthly budgets for %s", request.getYearMonth().toString());
            throw new BusinessException(error);
        }

        MonthlyCategoryBudgetValidatorImpl.Parameter validatorParameter =
                new MonthlyCategoryBudgetValidatorImpl.Parameter(request, monthlyIncome, monthlyBudgets);

        monthlyCategoryBudgetValidator.validate(validatorParameter);

        MonthlyBudgetCategory monthlyBudgetCategory = monthlyBudgetCategoryRepository
                .findByCategoryIdAndMonthlyBudgetId(request.getCategoryId(), request.getBudgetId())
                .orElseGet(() -> {
                    MonthlyBudgetCategory newMonthlyBudgetCategory = new MonthlyBudgetCategory();
                    newMonthlyBudgetCategory.setCategory(category);
                    monthlyBudget.addMonthlyBudgetCategory(newMonthlyBudgetCategory);
                    return monthlyBudgetCategoryRepository.save(newMonthlyBudgetCategory);
                });

        monthlyBudgetCategory.setMonthlyLimit(request.getMonthlyLimit());
        monthlyBudgetRepository.save(monthlyBudget);

        return MonthlyBudgetCategoryWebModel.from(monthlyBudgetCategory);
    }

    @Override
    public void removeMonthlyBudgetCategory(long budgetId, long categoryId) {
        Optional<MonthlyBudgetCategory> optionalMonthlyBudgetCategory = monthlyBudgetCategoryRepository
                .findByCategoryIdAndMonthlyBudgetId(categoryId, budgetId);

        if (!optionalMonthlyBudgetCategory.isPresent())
            return;

        Optional<MonthlyBudget> optionalMonthlyBudget = monthlyBudgetRepository.findById(budgetId);
        if (!optionalMonthlyBudget.isPresent())
            return;

        MonthlyBudgetCategory monthlyBudgetCategory = optionalMonthlyBudgetCategory.get();
        MonthlyBudget monthlyBudget = optionalMonthlyBudget.get();

        monthlyBudget.removeMonthlyBudgetCategory(monthlyBudgetCategory);

        monthlyBudgetCategoryRepository.delete(monthlyBudgetCategory);
        monthlyBudgetRepository.save(monthlyBudget);
    }

    @Override
    public void removeMonthlyBudget(long budgetId) {
        monthlyBudgetRepository.deleteById(budgetId);
    }
}
