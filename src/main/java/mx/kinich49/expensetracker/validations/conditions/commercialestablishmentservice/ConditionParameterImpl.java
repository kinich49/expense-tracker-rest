package mx.kinich49.expensetracker.validations.conditions.commercialestablishmentservice;

import lombok.Data;
import mx.kinich49.expensetracker.models.web.requests.CommercialEstablishmentRequest;
import mx.kinich49.expensetracker.validations.ConditionParameter;

@Data
public class ConditionParameterImpl implements mx.kinich49.expensetracker.validations.ConditionParameter {

    private final CommercialEstablishmentRequest request;
}