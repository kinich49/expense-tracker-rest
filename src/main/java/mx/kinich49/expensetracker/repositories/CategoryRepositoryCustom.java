package mx.kinich49.expensetracker.repositories;

import mx.kinich49.expensetracker.models.database.Category;

import java.time.LocalDateTime;
import java.util.List;

public interface CategoryRepositoryCustom {

    List<Category> findByTransactionsBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
}
