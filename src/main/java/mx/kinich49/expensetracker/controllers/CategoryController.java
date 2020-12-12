package mx.kinich49.expensetracker.controllers;

import mx.kinich49.expensetracker.base.RestError;
import mx.kinich49.expensetracker.models.web.CategoryWebModel;
import mx.kinich49.expensetracker.exceptions.CategoryNotFoundException;
import mx.kinich49.expensetracker.models.database.Category;
import mx.kinich49.expensetracker.repositories.CategoryRepository;
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
    public ResponseEntity<?> getCategory(@PathVariable("id") long categoryId){
        try {
            CategoryWebModel dto = categoryService.findCategoryAndTransactions(categoryId);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (CategoryNotFoundException e) {
            RestError restError = new RestError(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY.value());
            return new ResponseEntity<>(restError, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PostMapping
    public ResponseEntity<?> insertCategory(@RequestBody Category category) {
        Optional<String> optionalError = categoryService.validateCategory(category);
        if(optionalError.isPresent())
            return new ResponseEntity<RestError>(new RestError(optionalError.get(), 422), HttpStatus.UNPROCESSABLE_ENTITY);
        else
            return new ResponseEntity<Category>(categoryRepository.save(category), HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable("id") long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}