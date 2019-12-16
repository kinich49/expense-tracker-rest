package mx.kinich49.expensetracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.kinich49.expensetracker.models.Category;
import mx.kinich49.expensetracker.repositories.custom.CustomCategoryRepository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, CustomCategoryRepository{

}