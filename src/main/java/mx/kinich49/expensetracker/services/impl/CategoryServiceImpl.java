package mx.kinich49.expensetracker.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import mx.kinich49.expensetracker.models.web.CategoryWebModel;
import mx.kinich49.expensetracker.exceptions.CategoryNotFoundException;
import mx.kinich49.expensetracker.models.database.Category;
import mx.kinich49.expensetracker.repositories.CategoryRepository;
import mx.kinich49.expensetracker.services.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<String> validateCategory(@NonNull Category fromRequest) {
        Category fromDB = categoryRepository.findByTitle(fromRequest.getTitle());
        if (fromDB != null)
            return Optional.of("A category with title \'" + fromRequest.getTitle() + "\' already exists");

        return Optional.empty();
    }

    @Transactional
    @Override
    public CategoryWebModel findCategoryAndTransactions(long categoryId) throws CategoryNotFoundException {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        return CategoryWebModel.from(category);
    }


}