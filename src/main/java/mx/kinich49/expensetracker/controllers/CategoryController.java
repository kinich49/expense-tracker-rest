package mx.kinich49.expensetracker.controllers;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import mx.kinich49.expensetracker.base.RestResponse;
import mx.kinich49.expensetracker.models.Category;
import mx.kinich49.expensetracker.repositories.CategoryRepository;


@RestController
@RequestMapping("api/categories")
public class CategoryController {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public RestResponse<List<Category>> getCategories() {
         List<Category> categories = Optional
        .ofNullable(categoryRepository.findAll())
        .orElseGet(() -> Collections.emptyList());

        return new RestResponse<>(categories, true, null);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public RestResponse<Category> insertCategory(@RequestBody Category category) {
        return new RestResponse<Category>(categoryRepository.saveAndFlush(category), true, null);
    } 
}