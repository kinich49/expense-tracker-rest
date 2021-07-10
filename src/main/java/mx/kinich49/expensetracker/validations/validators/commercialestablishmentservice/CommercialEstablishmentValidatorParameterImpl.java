package mx.kinich49.expensetracker.validations.validators.commercialestablishmentservice;

import lombok.Data;
import mx.kinich49.expensetracker.models.web.requests.CommercialEstablishmentRequest;
import mx.kinich49.expensetracker.validations.ValidatorParameter;

@Data
public class CommercialEstablishmentValidatorParameterImpl implements mx.kinich49.expensetracker.validations.ValidatorParameter {

    private final CommercialEstablishmentRequest request;
}