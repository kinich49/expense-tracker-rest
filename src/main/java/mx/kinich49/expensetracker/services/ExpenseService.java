package mx.kinich49.expensetracker.services;

import mx.kinich49.expensetracker.models.web.ExpensesWebModel;

import java.time.LocalDateTime;

public interface ExpenseService {

    ExpensesWebModel findExpensesBetween(LocalDateTime startDate,
                                               LocalDateTime endDate);
}
