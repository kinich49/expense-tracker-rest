package mx.kinich49.expensetracker.services.impl;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.database.MonthlyIncome;
import mx.kinich49.expensetracker.models.web.MonthlyIncomeWebModel;
import mx.kinich49.expensetracker.models.web.requests.MonthlyIncomeRequest;
import mx.kinich49.expensetracker.repositories.MonthlyIncomeRepository;
import mx.kinich49.expensetracker.services.MonthlyIncomeService;
import mx.kinich49.expensetracker.validations.validators.monthlyincome.MonthlyIncomeValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MonthlyIncomeServiceImpl implements MonthlyIncomeService {

    private final MonthlyIncomeRepository monthlyIncomeRepository;
    private final MonthlyIncomeValidatorImpl monthlyIncomeValidator;

    @Autowired
    public MonthlyIncomeServiceImpl(MonthlyIncomeRepository monthlyIncomeRepository,
                                    MonthlyIncomeValidatorImpl monthlyIncomeValidator) {
        this.monthlyIncomeRepository = monthlyIncomeRepository;
        this.monthlyIncomeValidator = monthlyIncomeValidator;
    }

    @Override
    public MonthlyIncomeWebModel addMonthlyIncome(MonthlyIncomeRequest request) throws BusinessException {
        MonthlyIncomeValidatorImpl.Parameter parameter = new MonthlyIncomeValidatorImpl.Parameter(request);
        monthlyIncomeValidator.validate(parameter);

        MonthlyIncome monthlyIncome = monthlyIncomeRepository.save(MonthlyIncome.from(request));
        return MonthlyIncomeWebModel.from(monthlyIncome);
    }

    @Override
    public MonthlyIncomeWebModel updateMonthlyIncome(MonthlyIncomeRequest request) throws BusinessException {
        MonthlyIncome monthlyIncome = monthlyIncomeRepository.findById(request.getId())
                .orElseThrow(() -> new BusinessException("Monthly income with id " + request.getId() + "not found"));

        monthlyIncome.setUpperIncomeLimit(request.getUpperIncomeLimit());
        monthlyIncome.setBeginDate(request.getBeginDate());
        monthlyIncome.setEndDate(request.getEndDate());

        return MonthlyIncomeWebModel.from(monthlyIncomeRepository.save(monthlyIncome));
    }

    @Override
    public void deleteMonthlyIncome(long id) {
        monthlyIncomeRepository.deleteById(id);
    }

    @Override
    public Optional<MonthlyIncomeWebModel> findCurrentIncome() {
        return monthlyIncomeRepository.findCurrentIncome()
                .map(MonthlyIncomeWebModel::from);
    }

    @Override
    public Optional<MonthlyIncomeWebModel> findMonthlyIncomeBy(long id) {
        return monthlyIncomeRepository.findById(id)
                .map(MonthlyIncomeWebModel::from);
    }
}
