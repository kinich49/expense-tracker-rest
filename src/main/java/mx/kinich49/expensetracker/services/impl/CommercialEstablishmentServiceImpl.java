package mx.kinich49.expensetracker.services.impl;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.database.CommercialEstablishment;
import mx.kinich49.expensetracker.models.web.CommercialEstablishmentWebModel;
import mx.kinich49.expensetracker.models.web.requests.CommercialEstablishmentRequest;
import mx.kinich49.expensetracker.repositories.CommercialEstablishmentRepository;
import mx.kinich49.expensetracker.services.CommercialEstablishmentService;
import mx.kinich49.expensetracker.validations.commercialestablishmentservice.validators.AddCommercialEstablishmentValidator;
import mx.kinich49.expensetracker.validations.commercialestablishmentservice.validators.CommercialEstablishmentValidatorParameter;
import mx.kinich49.expensetracker.validations.commercialestablishmentservice.validators.UpdateCommercialEstablishmentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CommercialEstablishmentServiceImpl implements CommercialEstablishmentService {

    private final CommercialEstablishmentRepository repository;
    private final AddCommercialEstablishmentValidator addValidator;
    private final UpdateCommercialEstablishmentValidator updateValidator;

    @Autowired
    public CommercialEstablishmentServiceImpl(CommercialEstablishmentRepository repository,
                                              AddCommercialEstablishmentValidator addValidator,
                                              UpdateCommercialEstablishmentValidator updateValidator) {
        this.repository = repository;
        this.addValidator = addValidator;
        this.updateValidator = updateValidator;
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
        var parameter = new CommercialEstablishmentValidatorParameter(request);
        updateValidator.validate(parameter);

        var commercialEstablishment = repository.findById(request.getId())
                .orElseThrow(() ->
                        new BusinessException(String.format("Commercial Establishment with id %1$d not found",
                                request.getId())));

        commercialEstablishment.setName(request.getName());
        repository.save(commercialEstablishment);

        return CommercialEstablishmentWebModel.from(commercialEstablishment);
    }

    @Override
    public CommercialEstablishmentWebModel add(CommercialEstablishmentRequest request) throws BusinessException {
        var parameter = new CommercialEstablishmentValidatorParameter(request);
        addValidator.validate(parameter);

        var commercialEstablishment = repository.save(CommercialEstablishment.from(request));
        return CommercialEstablishmentWebModel.from(commercialEstablishment);
    }
}
