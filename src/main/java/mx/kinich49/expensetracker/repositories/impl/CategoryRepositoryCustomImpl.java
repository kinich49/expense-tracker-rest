package mx.kinich49.expensetracker.repositories.impl;

import mx.kinich49.expensetracker.models.database.Category;
import mx.kinich49.expensetracker.repositories.CategoryRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom {

    @PersistenceContext
    EntityManager persistenceContext;

    @Override
    public List<Category> findByTransactionsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        List<Category> categories = persistenceContext.createQuery(
                "SELECT c FROM Categories c " +
                        "JOIN c.transactions t " +
                        "WHERE t.transactionDate >= :startDate " +
                        "AND t.transactionDate<= :endDate")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();

        return categories;
    }
}
