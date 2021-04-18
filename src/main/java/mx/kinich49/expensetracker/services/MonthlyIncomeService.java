package mx.kinich49.expensetracker.services;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.web.MonthlyIncomeWebModel;
import mx.kinich49.expensetracker.models.web.requests.MonthlyIncomeRequest;

import java.util.Optional;

public interface MonthlyIncomeService {

    MonthlyIncomeWebModel addMonthlyIncome(MonthlyIncomeRequest request) throws BusinessException;

    MonthlyIncomeWebModel updateMonthlyIncome(MonthlyIncomeRequest request) throws BusinessException;

    void deleteMonthlyIncome(long id);

    Optional<MonthlyIncomeWebModel> findCurrentIncome();

    Optional<MonthlyIncomeWebModel> findMonthlyIncomeBy(long id);
}
