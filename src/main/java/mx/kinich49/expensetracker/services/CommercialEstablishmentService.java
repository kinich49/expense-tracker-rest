package mx.kinich49.expensetracker.services;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.web.CommercialEstablishmentWebModel;
import mx.kinich49.expensetracker.models.web.requests.CommercialEstablishmentRequest;

import java.util.List;
import java.util.Optional;

public interface CommercialEstablishmentService {

    List<CommercialEstablishmentWebModel> findAll();

    Optional<CommercialEstablishmentWebModel> findById(long id);

    void deleteById(long id);

    CommercialEstablishmentWebModel update(CommercialEstablishmentRequest request)
            throws BusinessException;

    CommercialEstablishmentWebModel add(CommercialEstablishmentRequest request)
            throws BusinessException;

    ;
}
