package mx.kinich49.expensetracker.repositories.custom;

import org.springframework.data.repository.Repository;

import mx.kinich49.expensetracker.models.Category;

public interface CustomCategoryRepository extends Repository<Category, Long> {

    Category findByTitle(String title);
}