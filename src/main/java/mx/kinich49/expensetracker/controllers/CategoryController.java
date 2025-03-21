package mx.kinich49.expensetracker.controllers;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.web.ApiResponse;
import mx.kinich49.expensetracker.models.web.CategoryWebModel;
import mx.kinich49.expensetracker.models.web.requests.CategoryRequest;
import mx.kinich49.expensetracker.repositories.CategoryRepository;
import mx.kinich49.expensetracker.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/categories")
@SuppressWarnings("unused")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository,
                              CategoryService categoryService) {
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryWebModel>>> getCategories() {
        return Optional.ofNullable(categoryService.findAll())
                .map(ApiResponse::new)
                .map(json -> new ResponseEntity<>(json, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryWebModel>> getCategory(@PathVariable("id") long categoryId) {
        return categoryService.findCategoryAndTransactions(categoryId)
                .map(ApiResponse::new)
                .map(json -> new ResponseEntity<>(json, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryWebModel>> insertCategory(@RequestBody CategoryRequest request) {
        try {
            CategoryWebModel webModel = categoryService.insertCategory(request);
            ApiResponse<CategoryWebModel> response = new ApiResponse<>(webModel);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (BusinessException e) {
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/id/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable("id") long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}