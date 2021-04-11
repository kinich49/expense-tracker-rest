package mx.kinich49.expensetracker.repositories.impl;

import mx.kinich49.expensetracker.models.database.MonthlyIncome;
import mx.kinich49.expensetracker.repositories.MonthlyIncomeRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

public class MonthlyIncomeRepositoryCustomImpl implements MonthlyIncomeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<MonthlyIncome> customFind(int month, int year) {
        String query = "SELECT mi " +
                "FROM Monthly_Incomes mi " +
                "INNER JOIN Monthly_Budgets mb " +
                "ON mi.id = mb.monthlyIncome.id " +
                "WHERE month(mb.beginDate) =:month " +
                "AND year(mb.beginDate) =:year";

        MonthlyIncome monthlyIncome = entityManager.createQuery(query, MonthlyIncome.class)
                .setParameter("month", month)
                .setParameter("year", year)
                .setMaxResults(1)
                .getSingleResult();

        return Optional.ofNullable(monthlyIncome);
    }
}
