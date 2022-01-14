package mx.kinich49.expensetracker.services.impl;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.database.PaymentMethod;
import mx.kinich49.expensetracker.models.web.PaymentMethodWebModel;
import mx.kinich49.expensetracker.models.web.requests.PaymentMethodRequest;
import mx.kinich49.expensetracker.repositories.PaymentMethodRepository;
import mx.kinich49.expensetracker.services.PaymentMethodService;
import mx.kinich49.expensetracker.validations.paymentmethodservice.validators.AddPaymentMethodValidator;
import mx.kinich49.expensetracker.validations.paymentmethodservice.validators.PaymentMethodValidatorParameter;
import mx.kinich49.expensetracker.validations.paymentmethodservice.validators.UpdatePaymentMethodValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    private final PaymentMethodRepository repository;
    private final AddPaymentMethodValidator addPaymentMethodValidator;
    private final UpdatePaymentMethodValidator updatePaymentMethodValidator;

    @Autowired
    public PaymentMethodServiceImpl(PaymentMethodRepository repository,
                                    AddPaymentMethodValidator addPaymentMethodValidator,
                                    UpdatePaymentMethodValidator updatePaymentMethodValidator) {
        this.repository = repository;
        this.addPaymentMethodValidator = addPaymentMethodValidator;
        this.updatePaymentMethodValidator = updatePaymentMethodValidator;
    }

    @Override
    public PaymentMethodWebModel addPaymentMethod(PaymentMethodRequest request) throws BusinessException {
        addPaymentMethodValidator.validate(new PaymentMethodValidatorParameter(request));

        return Optional.of(repository.save(PaymentMethod.from(request)))
                .map(PaymentMethodWebModel::from)
                .orElseThrow(() -> new BusinessException("Payment method could not be created"));
    }

    @Override
    public List<PaymentMethodWebModel> findAll() {
        return Optional.of(repository.findAll())
                .map(PaymentMethodWebModel::from)
                .orElseGet(Collections::emptyList);
    }

    @Override
    public Optional<PaymentMethodWebModel> findById(long paymentMethodId) {
        return repository.findById(paymentMethodId)
                .map(PaymentMethodWebModel::from);
    }

    @Override
    public Optional<PaymentMethodWebModel> update(PaymentMethodRequest request) {
        try {
            updatePaymentMethodValidator.validate(new PaymentMethodValidatorParameter(request));

            PaymentMethod fromPersistence = repository.findById(request.getId())
                    .orElseThrow(() -> new BusinessException(
                            String.format("Could not find payment method with id %1$d", request.getId())));

            fromPersistence.setName(request.getName());

            return Optional.of(repository.save(fromPersistence))
                    .map(PaymentMethodWebModel::from);

        } catch (BusinessException e) {
            //TODO log exception
            return Optional.empty();
        }
    }

    @Override
    public void delete(long paymentMethodId) {
        repository.deleteById(paymentMethodId);
    }
}
