package mx.kinich49.expensetracker.repositories.impl;

import mx.kinich49.expensetracker.models.database.MonthlyIncome;
import mx.kinich49.expensetracker.repositories.MonthlyIncomeRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public class MonthlyIncomeRepositoryImpl implements MonthlyIncomeRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<MonthlyIncome> findCurrentIncome() {

        List<MonthlyIncome> results = entityManager
                .createQuery("SELECT mi FROM Monthly_Incomes mi " +
                                "WHERE mi.endDate IS NULL ORDER BY mi.beginDate DESC",
                        MonthlyIncome.class)
                .setMaxResults(1)
                .getResultList();
        if (results == null || results.isEmpty())
            return Optional.empty();

        return Optional.of(results.get(0));
    }

    @Override
    public Optional<MonthlyIncome> collides(YearMonth beginDate, YearMonth endDate) {
        TypedQuery<MonthlyIncome> query;
        try {
            if (endDate == null) {
                query = entityManager.createQuery(
                        "SELECT mi FROM Monthly_Incomes mi " +
                                "WHERE mi.endDate IS NULL OR " +
                                "mi.endDate > :beginDate", MonthlyIncome.class)
                        .setParameter("beginDate", beginDate);
            } else {
                query = entityManager.createQuery(
                        "SELECT mi FROM Monthly_Incomes mi " +
                                "WHERE mi.beginDate < :endDate AND " +
                                "(mi.endDate IS NULL OR mi.endDate > :beginDate)", MonthlyIncome.class)
                        .setParameter("beginDate", beginDate)
                        .setParameter("endDate", endDate);
            }

            return Optional.ofNullable(query.setMaxResults(1).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }

    }
}
