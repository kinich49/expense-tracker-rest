package mx.kinich49.expensetracker.services.impl;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.web.CommercialEstablishmentWebModel;
import mx.kinich49.expensetracker.models.web.requests.CommercialEstablishmentRequest;
import mx.kinich49.expensetracker.repositories.CommercialEstablishmentRepository;
import mx.kinich49.expensetracker.services.CommercialEstablishmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CommercialEstablishmentServiceImpl implements CommercialEstablishmentService {

    private final CommercialEstablishmentRepository repository;

    @Autowired
    public CommercialEstablishmentServiceImpl(CommercialEstablishmentRepository repository) {
        this.repository = repository;
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
        return null;
    }
}
