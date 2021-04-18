package mx.kinich49.expensetracker.services.impl;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.database.MonthlyIncome;
import mx.kinich49.expensetracker.models.web.MonthlyIncomeWebModel;
import mx.kinich49.expensetracker.models.web.requests.MonthlyIncomeRequest;
import mx.kinich49.expensetracker.repositories.MonthlyIncomeRepository;
import mx.kinich49.expensetracker.services.MonthlyIncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MonthlyIncomeServiceImpl implements MonthlyIncomeService {

    private final MonthlyIncomeRepository monthlyIncomeRepository;

    @Autowired
    public MonthlyIncomeServiceImpl(MonthlyIncomeRepository monthlyIncomeRepository) {
        this.monthlyIncomeRepository = monthlyIncomeRepository;
    }

    @Override
    public MonthlyIncomeWebModel addMonthlyIncome(MonthlyIncomeRequest request) throws BusinessException {
        Optional<MonthlyIncome> collusion = monthlyIncomeRepository.collides(request.getBeginDate(), request.getEndDate());
        if (collusion.isPresent()) {
            String message = String.format("Monthly Income with id: %1$d collides with request.",
                    collusion.get().getId());
            throw new BusinessException(message);
        }

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
