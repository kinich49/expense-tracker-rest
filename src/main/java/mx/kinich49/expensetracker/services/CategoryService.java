package mx.kinich49.expensetracker.services;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.web.CategoryWebModel;
import mx.kinich49.expensetracker.models.web.requests.CategoryRequest;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    /**
     * @param request the new category
     * @return and instance of CategoryWebModel if the request is valid
     * @throws InvalidNewCategoryException if the category cannot be created
     */
    CategoryWebModel insertCategory(CategoryRequest request) throws BusinessException;

    Optional<CategoryWebModel> findCategoryAndTransactions(long categoryId);

    List<CategoryWebModel> findAll();

}