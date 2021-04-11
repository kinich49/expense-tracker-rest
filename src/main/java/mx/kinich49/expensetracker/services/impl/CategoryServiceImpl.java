package mx.kinich49.expensetracker.services.impl;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.database.Category;
import mx.kinich49.expensetracker.models.web.CategoryWebModel;
import mx.kinich49.expensetracker.models.web.requests.CategoryRequest;
import mx.kinich49.expensetracker.repositories.CategoryRepository;
import mx.kinich49.expensetracker.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryWebModel insertCategory(CategoryRequest request) throws BusinessException {
        //Throw exception if name is not set in request
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new BusinessException("Name can't be null");
        }

        //Throw exception if name is already in use
        if (categoryRepository.existsByName(request.getName())) {
            String message = String.format("Name %1$s is already in use", request.getName());
            throw new BusinessException(message);
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

    @Override
    public List<CategoryWebModel> findAll() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty())
            return null;
        return categories
                .stream()
                .map(CategoryWebModel::from)
                .collect(Collectors.toList());
    }

}