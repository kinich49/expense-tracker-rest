package mx.kinich49.expensetracker.services;

import java.util.Optional;

import org.springframework.lang.NonNull;
import mx.kinich49.expensetracker.models.Category;

public interface CategoryService {

    /**
     * 
     * @param category the new category to be inserted
     * @return an Optional String. If String is not present,
     * then the Category is safe to insert. An invalid category
     * will return the error message inside Optional
     */
    Optional<String> validateCategory(@NonNull Category category);
}