package mx.kinich49.expensetracker.services.impl;

import mx.kinich49.expensetracker.exceptions.InvalidMonthlyCategoryBudgetException;
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
import mx.kinich49.expensetracker.services.validators.MonthlyCategoryBudgetValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@SuppressWarnings("unused")
public class MonthlyBudgetServiceImpl implements MonthlyBudgetService {

    private final MonthlyBudgetRepository monthlyBudgetRepository;
    private final MonthlyBudgetCategoryRepository monthlyBudgetCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final MonthlyCategoryBudgetValidator monthlyCategoryBudgetValidator;
    private final MonthlyIncomeRepository monthlyIncomeRepository;

    @Autowired
    public MonthlyBudgetServiceImpl(MonthlyBudgetRepository monthlyBudgetRepository,
                                    MonthlyBudgetCategoryRepository monthlyBudgetCategoryRepository,
                                    CategoryRepository categoryRepository,
                                    MonthlyCategoryBudgetValidator monthlyCategoryBudgetValidator,
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
        monthlyBudget.setBudgetDate(request.getBudgetDate());

        monthlyBudget = monthlyBudgetRepository.save(monthlyBudget);

        return SimpleMonthlyBudgetWebModel.from(monthlyBudget);
    }

    @Override
    public Optional<MonthlyBudgetWebModel> findMonthlyBudget(int month, int year) {
        return monthlyIncomeRepository.customFind(month, year)
                .map(MonthlyBudgetWebModel::from);
    }

    @Override
    public MonthlyBudgetCategoryWebModel addMonthlyBudgetCategory(MonthlyBudgetCategoryRequest request)
            throws InvalidMonthlyCategoryBudgetException {
        Optional<MonthlyBudget> optMonthlyBudget = monthlyBudgetRepository.findById(request.getBudgetId());

        if (!optMonthlyBudget.isPresent()) {
            String error = String.format("Monthly Budget with id %1$d not found", request.getBudgetId());
            throw new InvalidMonthlyCategoryBudgetException(error);
        }

        MonthlyBudget monthlyBudget = optMonthlyBudget.get();
        MonthlyIncome monthlyIncome = monthlyBudget.getMonthlyIncome();

        Optional<Category> optCategory = categoryRepository.findById(request.getCategoryId());

        if (!optCategory.isPresent()) {
            String error = String.format("Category with id %1$d not found", request.getCategoryId());
            throw new InvalidMonthlyCategoryBudgetException(error);
        }

        Category category = optCategory.get();

        if (!monthlyCategoryBudgetValidator.isLimitValid(request, monthlyIncome)) {
            String error = String.format("Setting %1$d as limit puts you over your monthly limit",
                    request.getMonthlyLimit());
            throw new InvalidMonthlyCategoryBudgetException(error);
        }

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
}
