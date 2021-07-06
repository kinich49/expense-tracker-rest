package mx.kinich49.expensetracker.services.impl;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.database.CommercialEstablishment;
import mx.kinich49.expensetracker.models.web.CommercialEstablishmentWebModel;
import mx.kinich49.expensetracker.models.web.requests.CommercialEstablishmentRequest;
import mx.kinich49.expensetracker.repositories.CommercialEstablishmentRepository;
import mx.kinich49.expensetracker.services.CommercialEstablishmentService;
import mx.kinich49.expensetracker.validations.validators.commercialestablishmentservice.AddCommercialEstablishmentValidatorImpl;
import mx.kinich49.expensetracker.validations.validators.commercialestablishmentservice.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CommercialEstablishmentServiceImpl implements CommercialEstablishmentService {

    private final CommercialEstablishmentRepository repository;
    private final AddCommercialEstablishmentValidatorImpl requestValidator;

    @Autowired
    public CommercialEstablishmentServiceImpl(CommercialEstablishmentRepository repository,
                                              AddCommercialEstablishmentValidatorImpl requestValidator) {
        this.repository = repository;
        this.requestValidator = requestValidator;
    }

    @Override
    public List<CommercialEstablishmentWebModel> findAll() {
        return Optional.of(repository.findAll())
                .map(CommercialEstablishmentWebModel::from)
                .orElse(Collections.emptyList());
    }

    @Override
    public Optional<CommercialEstablishmentWebModel> findById(long id) {
        return repository.findById(id)
                .map(CommercialEstablishmentWebModel::from);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @Override
    public CommercialEstablishmentWebModel update(CommercialEstablishmentRequest request) throws BusinessException {
        return null;
    }

    @Override
    public CommercialEstablishmentWebModel add(CommercialEstablishmentRequest request) throws BusinessException {
        var parameter = new Parameter(request);
        requestValidator.validate(parameter);

        var commercialEstablishment = repository.save(CommercialEstablishment.from(request));
        return CommercialEstablishmentWebModel.from(commercialEstablishment);
    }
}
