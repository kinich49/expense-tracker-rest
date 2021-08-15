package mx.kinich49.expensetracker.services;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.web.PaymentMethodWebModel;
import mx.kinich49.expensetracker.models.web.requests.PaymentMethodRequest;

import java.util.List;
import java.util.Optional;

public interface PaymentMethodService {

    PaymentMethodWebModel addPaymentMethod(PaymentMethodRequest request) throws BusinessException;

    List<PaymentMethodWebModel> findAll();

    Optional<PaymentMethodWebModel> findById(long paymentMethodId);

    Optional<PaymentMethodWebModel> update(PaymentMethodRequest request);

    void delete(long paymentMethodId);

}
