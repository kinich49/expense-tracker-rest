package mx.kinich49.expensetracker.services.impl;

import mx.kinich49.expensetracker.exceptions.InvalidMonthlyCategoryBudgetException;
import mx.kinich49.expensetracker.models.database.Category;
import mx.kinich49.expensetracker.models.database.MonthlyBudget;
import mx.kinich49.expensetracker.models.database.MonthlyBudgetCategory;
import mx.kinich49.expensetracker.models.web.MonthlyBudgetCategoryWebModel;
import mx.kinich49.expensetracker.models.web.MonthlyBudgetWebModel;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetCategoryRequest;
import mx.kinich49.expensetracker.models.web.requests.MonthlyBudgetRequest;
import mx.kinich49.expensetracker.repositories.CategoryRepository;
import mx.kinich49.expensetracker.repositories.MonthlyBudgetCategoryRepository;
import mx.kinich49.expensetracker.repositories.MonthlyBudgetRepository;
import mx.kinich49.expensetracker.services.MonthlyBudgetService;
import mx.kinich49.expensetracker.services.MonthlyCategoryBudgetValidator;
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

    @Autowired
    public MonthlyBudgetServiceImpl(MonthlyBudgetRepository monthlyBudgetRepository,
                                    MonthlyBudgetCategoryRepository monthlyBudgetCategoryRepository,
                                    CategoryRepository categoryRepository,
                                    MonthlyCategoryBudgetValidator monthlyCategoryBudgetValidator) {
        this.monthlyBudgetRepository = monthlyBudgetRepository;
        this.monthlyBudgetCategoryRepository = monthlyBudgetCategoryRepository;
        this.categoryRepository = categoryRepository;
        this.monthlyCategoryBudgetValidator = monthlyCategoryBudgetValidator;
    }

    @Override
    public MonthlyBudgetWebModel insertMonthlyBudget(MonthlyBudgetRequest request) {
        MonthlyBudget monthlyBudget = new MonthlyBudget();
        monthlyBudget.setBudgetDate(request.getBudgetDate());

        monthlyBudget = monthlyBudgetRepository.save(monthlyBudget);

        return MonthlyBudgetWebModel.from(monthlyBudget);
    }

    @Override
    public Optional<MonthlyBudgetWebModel> findMonthlyBudget(int month, int year) {
        return monthlyBudgetRepository.findByMonthAndYear(month, year)
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

        Optional<Category> optCategory = categoryRepository.findById(request.getCategoryId());

        if (!optCategory.isPresent()) {
            String error = String.format("Category with id %1$d not found", request.getCategoryId());
            throw new InvalidMonthlyCategoryBudgetException(error);
        }

        Category category = optCategory.get();

        if (!monthlyCategoryBudgetValidator.isLimitValid(request, monthlyBudget)) {
            String error = String.format("Setting %1$d as limit puts you over your monthly limit of %2$d",
                    request.getMonthlyLimit(), monthlyBudget.getMonthlyLimit());
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
