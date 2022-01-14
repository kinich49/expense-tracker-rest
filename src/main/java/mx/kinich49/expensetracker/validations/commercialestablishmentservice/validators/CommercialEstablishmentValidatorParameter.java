package mx.kinich49.expensetracker.validations.commercialestablishmentservice.validators;

import lombok.Data;
import mx.kinich49.expensetracker.models.web.requests.CommercialEstablishmentRequest;
import mx.kinich49.expensetracker.validations.ValidatorParameter;

@Data
public class CommercialEstablishmentValidatorParameter implements mx.kinich49.expensetracker.validations.ValidatorParameter {

    private final CommercialEstablishmentRequest request;
}