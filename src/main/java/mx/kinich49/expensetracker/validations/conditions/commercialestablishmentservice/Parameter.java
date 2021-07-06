package mx.kinich49.expensetracker.validations.conditions.commercialestablishmentservice;

import lombok.Data;
import mx.kinich49.expensetracker.models.web.requests.CommercialEstablishmentRequest;
import mx.kinich49.expensetracker.validations.ConditionParameter;

@Data
public class Parameter implements ConditionParameter {

    private final CommercialEstablishmentRequest request;
}