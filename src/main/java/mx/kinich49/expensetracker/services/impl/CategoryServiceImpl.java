package mx.kinich49.expensetracker.services.impl;

import mx.kinich49.expensetracker.exceptions.InvalidNewCategoryException;
import mx.kinich49.expensetracker.models.database.Category;
import mx.kinich49.expensetracker.models.web.CategoryWebModel;
import mx.kinich49.expensetracker.repositories.CategoryRepository;
import mx.kinich49.expensetracker.models.web.requests.CategoryRequest;
import mx.kinich49.expensetracker.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryWebModel insertCategory(CategoryRequest request) throws InvalidNewCategoryException {
        //Throw exception if name is not set in request
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new InvalidNewCategoryException("Name can't be null");
        }

        //Throw exception if name is already in use
        if (categoryRepository.existsByName(request.getName())) {
            String message = String.format("Name %1$s is already in use", request.getName());
            throw new InvalidNewCategoryException(message);
        }

        Category category = new Category();
        category.setName(request.getName());
        category.setColor(request.getColor());
        category = categoryRepository.save(category);

        return CategoryWebModel.from(category);
    }

    @Override
    public Optional<CategoryWebModel> findCategoryAndTransactions(long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(CategoryWebModel::from);
    }

}