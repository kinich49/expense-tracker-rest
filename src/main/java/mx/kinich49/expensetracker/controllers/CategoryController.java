package mx.kinich49.expensetracker.controllers;

import mx.kinich49.expensetracker.base.ApiError;
import mx.kinich49.expensetracker.exceptions.InvalidNewCategoryException;
import mx.kinich49.expensetracker.models.database.Category;
import mx.kinich49.expensetracker.models.web.CategoryWebModel;
import mx.kinich49.expensetracker.models.web.JsonApi;
import mx.kinich49.expensetracker.repositories.CategoryRepository;
import mx.kinich49.expensetracker.models.web.requests.CategoryRequest;
import mx.kinich49.expensetracker.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = Optional.of(categoryRepository.findAll())
                .orElseGet(Collections::emptyList);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JsonApi<CategoryWebModel>> getCategory(@PathVariable("id") long categoryId) {
        return categoryService.findCategoryAndTransactions(categoryId)
                .map(JsonApi::new)
                .map(json -> new ResponseEntity<>(json, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> insertCategory(@RequestBody CategoryRequest request) {
        try {
            CategoryWebModel webModel = categoryService.insertCategory(request);
            JsonApi<CategoryWebModel> response = new JsonApi<>(webModel);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (InvalidNewCategoryException e) {
            ApiError error = new ApiError(e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/id/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable("id") long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}