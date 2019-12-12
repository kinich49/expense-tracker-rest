package mx.kinich49.expensetracker.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.kinich49.expensetracker.base.RestError;
import mx.kinich49.expensetracker.dtos.CategoryDto;
import mx.kinich49.expensetracker.exceptions.CategoryNotFoundException;
import mx.kinich49.expensetracker.models.Category;
import mx.kinich49.expensetracker.repositories.CategoryRepository;
import mx.kinich49.expensetracker.services.CategoryService;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

    private CategoryRepository categoryRepository;
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository, 
                              CategoryService categoryService) {
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = Optional.ofNullable(categoryRepository.findAll())
                .orElseGet(() -> Collections.emptyList());
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getCategory(@PathVariable("id") long categoryId){
        try {
            CategoryDto dto = categoryService.findCategoryAndTransactions(categoryId);
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
}